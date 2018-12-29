package io.sj.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import io.sj.exception.MailException;
import io.sj.service.MailProcessor;

/**
 * Servlet implementation class SendMailServlet
 */
public class SendMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String UPLOAD_DIR_NAME = "/upload";
	private static Logger logger = Logger.getLogger(SendMailServlet.class.getName());
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Handles file upload request.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("inside doPost of SendMailServlet");
		
		boolean sendMailflag = false;
		String uploadPath = "";
		String to = null;
		String subject = null;
		String message = null;
		String attachmentName = null;
		String htmlContent = null;

		// File Uploading starts
		String name = null;
		String rootPath = getServletContext().getRealPath("/");
		File uploadDir = new File(rootPath + UPLOAD_DIR_NAME);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		if (ServletFileUpload.isMultipartContent(request)) {

			try {
				List<FileItem> multiparts = new ServletFileUpload(
						new DiskFileItemFactory()).parseRequest(request);
				for (FileItem item : multiparts) {
					if (!item.isFormField()) {
						if ("file".equals(item.getFieldName())) {
							if (item.getSize() > 0) {
         							name = new File(item.getName()).getName();
									uploadPath = rootPath + UPLOAD_DIR_NAME + File.separator+ name;
									item.write(new File(uploadPath));
							}
						}
					} else {
						// Parameters initialisation
						String itemFieldName = item.getFieldName();
						String itemvalue = item.getString();
						if (itemFieldName != null && itemvalue != null) {
							if ("to".equalsIgnoreCase(itemFieldName)) {
								to = itemvalue;
							} else if ("subject"
									.equalsIgnoreCase(itemFieldName)) {
								subject = itemvalue;
							} else if ("message"
									.equalsIgnoreCase(itemFieldName)) {
								message = itemvalue;
							} else if ("attachmentName"
									.equalsIgnoreCase(itemFieldName)) {
								attachmentName = itemvalue;
							} else if ("htmlContent"
									.equalsIgnoreCase(itemFieldName)) {
								htmlContent = "<html>" + itemvalue + "</html>";
							}
						}
					}
				}
				sendMailflag = true;
			} catch (Exception ex) {
				logger.error("File Upload Failed: " + ex);
			}
		}
		
		logger.debug("sendMailflag : "+sendMailflag);
		
		// File Uploading finished , parameters initialized
		// Now send mail after uploading
		if (sendMailflag) {

			try {
				// Initialization
				// Using default constructor for now.
				MailProcessor mail = new MailProcessor();
				if (mail.sendMail(to, subject, message, htmlContent,uploadPath,attachmentName)) {
				//	out.println("<h1>Mail sent successfully!!</h1><br>");
					request.setAttribute("successMessage", "Mail sent successfully!!");
					logger.debug("successMessage to : "+to);
				} else {
				//	out.println("<h1>Error Sending Email!!</h1>");
					request.setAttribute("errorMessage", "Error Sending Email!!");
				}

			} catch (MailException err) {
				logger.error(err.getErrorMessage());
			} catch (AddressException ae) {
				logger.error(ae.getMessage());
			} catch (MessagingException me) {
				logger.error(me.getMessage());
			}

		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);

	}

}
