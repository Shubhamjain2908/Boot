package org.sj.service.utility;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CountLogger 
{
	public int countLine(String type) throws IOException 
    {
		CountLogger c=new CountLogger();
        int total=c.count("E:\\Boot\\course_API\\myLog.log");
        System.out.println(total);
        
        int tC=c.count("E:\\Boot\\course_API\\log\\"+type+".log");
        return tC;
    }
    
    public int count(String filename) throws IOException 
    {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try 
        {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) 
            {
                empty = false;
                for (int i = 0; i < readChars; ++i) 
                {
                    if (c[i] == '\n') 
                    {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        }
        finally 
        {
            is.close();
        }
    }
}
