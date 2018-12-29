package io.sj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/d")
public class Download {
	
	@GetMapping
	public void et(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		String uri = request.getScheme() + "://" +   // "http" + "://
	             request.getServerName() +       // "myhost"
	             ":" +                           // ":"
	             request.getServerPort() +       // "8080"
	             request.getRequestURI() +       // "/people"
	             "?" +                           // "?"
	             request.getQueryString();       // "lastname=Fox&age=30"
System.out.println(uri);
        response.setContentType("text/plain");

        
         response.setHeader("Content-disposition","attachment; filename=check.txt"); // Used to name the download file and its format

         File my_file = new File("C:\\Njs\\aaa.java"); // We are downloading .txt file, in the format of doc with name check - check.doc

         
         OutputStream out = response.getOutputStream();
         FileInputStream in = new FileInputStream(my_file);
         byte[] buffer = new byte[4096];
         int length;
         while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
         }
         in.close();
         out.flush();
    }
	
}
