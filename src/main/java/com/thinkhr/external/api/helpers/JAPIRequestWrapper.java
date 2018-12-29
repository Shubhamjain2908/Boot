package com.thinkhr.external.api.helpers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * API Request Wrapper : To wrap the request as multiple reads are needed for a request, first in the API RequestBodyFilter and then the API controllers.
 * 
 * @author Surabhi Bhawsar
 * @since 2018-03-05
 *
 */
public class JAPIRequestWrapper extends HttpServletRequestWrapper {
    private final String body;

    public JAPIRequestWrapper(HttpServletRequest request) throws IOException, ServletException {
        super(request);
        StringBuilder stringBuilder = new StringBuilder("");
        if(MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType()) || RequestMethod.GET.name().equals(request.getMethod()) 
                || RequestMethod.DELETE.name().equals(request.getMethod())) {
            try (InputStream inputStream = request.getInputStream();) {
                if (inputStream != null) {
                    try (BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(inputStream));) {
                        char[] charBuffer = new char[128];
                        int bytesRead = -1;
                        while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                            stringBuilder.append(charBuffer, 0, bytesRead);
                        }
                    }
                }
            }
            body = stringBuilder.toString();
        } else {
            Part filePart = request.getPart("file");
            List<Map<?, ?>> data = readObjectsFromCsv(filePart);
            ObjectMapper mapper = new ObjectMapper();
            body = mapper.writeValueAsString(data);
        }
    }

    private List<Map<?, ?>> readObjectsFromCsv(Part filePart) throws JsonGenerationException, JsonMappingException, IOException {
        
        CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<Map<?, ?>> mappingIterator = csvMapper.readerFor(Map.class).with(bootstrap).readValues(filePart.getInputStream());

        return mappingIterator.readAll();
        
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isReady() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                // TODO Auto-generated method stub

            }
        };
        return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return this.body;
    }
}