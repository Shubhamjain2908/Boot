package org.sj.service.encryption;

import java.security.MessageDigest;

public class MD5Encryption 
{
	public String enc(String password)
	{
		String generatedPassword=null;
        try 
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
            return generatedPassword;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.toString();
        }
	}
}
