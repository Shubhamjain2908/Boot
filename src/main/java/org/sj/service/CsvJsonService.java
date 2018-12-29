package org.sj.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.commons.io.FilenameUtils;
import org.simpleflatmapper.csv.CloseableCsvReader;
import org.simpleflatmapper.csv.CsvParser;
import org.sj.exception.BadRequestException;
import org.sj.exception.FileNotSupportedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvJsonService
{
	private static final String tempJson= "E:\\Boot\\course_API\\log\\csv\\tempJSON.json";
	private static final String tempCsv="E:\\Boot\\course_API\\log\\csv\\temp.csv";
	
	public String processCSV(MultipartFile Mfile) 
	{
		String readJson="";
		
		/*
		 * Validating file extension
		 * */
		String filename=Mfile.getOriginalFilename();
		String ext1 = FilenameUtils.getExtension(filename);
		System.out.println(filename+" : "+ext1);
		if(!ext1.equals("csv")) 
		{
			throw new BadRequestException("Only CSV file allowed");
		}
		
		/**
		 *saving file at temporary location
		 */
		String save=saveCsvFile(Mfile);	
		if(!save.equals("Success")) 
		{
			throw new BadRequestException("File cannot be saved");
		}
		
		/**
		 *parsing csv file into json 
		 *reading json response
		 */
		try 
		{
			CSVtoJSONParser();	 
			readJson=readJson();	
		}
		catch(Exception e) {System.out.println(e);}
		
		/**
		 *deleting all temporary files
		 */
		finally 
		{
			//deleteTemp();	
		}
		
		return readJson;
	}
	
	public void CSVtoJSONParser() throws IOException 
    {
		File convFile=new File(tempCsv);
		
        File file = new File(tempJson);
    	BufferedWriter output = new BufferedWriter(new FileWriter(file));
	
        CloseableCsvReader reader = CsvParser.reader(convFile);

        JsonFactory jsonFactory = new JsonFactory();
        Iterator<String[]> iterator = reader.iterator();
        String[] headers = iterator.next();

        try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(output)) 
        {
            jsonGenerator.writeStartArray();
    
            while (iterator.hasNext()) 
            {
                jsonGenerator.writeStartObject();
                String[] values = iterator.next();
                int nbCells = Math.min(values.length, headers.length);
                for(int i = 0; i < nbCells; i++) 
                {
                    jsonGenerator.writeFieldName(headers[i]);
                    jsonGenerator.writeString(values[i]);
                }
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
        }
        catch(Exception e) 
        {
        	System.out.println(e);
        }
        finally {output.close();}
    }
    
    public String saveCsvFile(MultipartFile Mfile)
	{
		try 
	    {	
			byte[] bytes = Mfile.getBytes();
            String completeData = new String(bytes);
			BufferedWriter output = null;
			File file = new File(tempCsv);
			output = new BufferedWriter(new FileWriter(file));
			output.write(completeData);
			if ( output != null ) 
			{
				output.close();
			}
			return "Success";
		} 
		catch(IOException e) 
		{
			System.out.println(e);
			return e.toString();
		}
	}

	public String readJson() throws FileNotFoundException 
	{
		BufferedReader br = new BufferedReader(new FileReader(tempJson));
        try 
        {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            sb.append(line);
            /*while (line != null) 
            {
                sb.append(line);
                line = br.readLine();
            }*/
            br.close();
            if(sb.toString().contains("���")) 
            {
            	throw new FileNotSupportedException("File not correctly coded");
            }
            return sb.toString();
        }
        catch(Exception e) 
        {
        	return e.toString();
        }
	}
	
	public void deleteTemp() 
	{
		try
        {
            Files.deleteIfExists(Paths.get(tempCsv));
            Files.deleteIfExists(Paths.get(tempJson));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists : "+e);
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty. : "+e);
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions : "+e);
            e.printStackTrace();
        }
	}
}
