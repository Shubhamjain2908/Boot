package org.sj.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HelloService 
{
	public String readFile(MultipartFile file) 
	{
		try 
        {
            byte[] bytes = file.getBytes();
            String completeData = new String(bytes);
            /*int counter = 0;
            for( int i=0; i<completeData.length(); i++ ) {
                if( completeData.charAt(i) == '$' ) {
                    counter++;
                } 
            }*/
            completeData.chars().filter(num -> num == '$').count();  
            int d = StringUtils.countOccurrencesOf(completeData, "DEBUG");
            int e = StringUtils.countOccurrencesOf(completeData, "ERROR");
            int i = StringUtils.countOccurrencesOf(completeData, "INFO");
            int t = StringUtils.countOccurrencesOf(completeData, "TRACE");
            int w = StringUtils.countOccurrencesOf(completeData, "WARN");
            return "#1. DEBUG : "+d+" #2. ERROR : "+e+" #3. INFO : "+i+" #4. TRACE : "+t+" #5. WARN : "+w;
        }
		catch(Exception e) 
		{
			return e.toString();
		}
	}
	
	public List<String> readMulti(MultipartFile file) 
	{
		BufferedReader br  = null;
		try 
		{
			br=new BufferedReader(new InputStreamReader(file.getInputStream()));
			return br.lines().collect(Collectors.toList());
		}
		catch(Exception e) 
		{

			return null;
		}
	}
}
