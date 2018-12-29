package org.sj.service.encryption;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Base64Encoding 
{
	public String mimeEncoding(String data)
    {
        try 
        {
            String mimeE=Base64.getMimeEncoder().encodeToString(data.getBytes("utf-8"));
            System.out.println("Encoded :     "+mimeE);
            return mimeE;
        }
        catch (UnsupportedEncodingException ex) 
        {
            Logger.getLogger(Base64Encoding.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }
	
	public String mimeDecoding(String encryptedString) 
	{
		try 
		{
			byte[] mi=Base64.getMimeDecoder().decode(encryptedString);
	        String decodeM=new String(mi,"utf-8");
	        System.out.println("DEcoded   :   "+decodeM);
	        return decodeM;
		}
		catch (UnsupportedEncodingException ex) 
        {
            Logger.getLogger(Base64Encoding.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
	}
	
	
}
