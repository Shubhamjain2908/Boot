package org.sj.service.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class CryptoTest 
{
    static String fileProcessor(int cipherMode,String key,File inputFile,File outputFile)
    {
        try 
        {
            KeyGenerator secretKey = KeyGenerator.getInstance("AES");
            secretKey.init(128);
            SecretKey skey = secretKey.generateKey();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(cipherMode, skey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();
            
            return "Success";

        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException e) 
        {
            System.out.println(e);
            e.printStackTrace();
            return e.toString();
        }
    }
	
    public String encryptCrypto(String key,String tempZip) 
    {
    	File inputFile = new File(tempZip);
		File encryptedFile = new File("E:\\Boot\\course_API\\log\\Enc\\zip.encrypted");
		try 
	    {
	        return CryptoTest.fileProcessor(Cipher.ENCRYPT_MODE,key,inputFile,encryptedFile);
		}
	    catch (Exception ex) 
	    {
	        System.out.println(ex.getMessage());
	        return ex.getMessage();
	    }
    }
    
    public String decryptCrypto(String key,File encryptedFile) 
    {
    	File decryptedFile = new File("E:\\Boot\\course_API\\log\\Enc\\decrypted.zip");
		try 
	    {
			return CryptoTest.fileProcessor(Cipher.DECRYPT_MODE,key,encryptedFile,decryptedFile);
		}
	    catch (Exception ex) 
	    {
	        System.out.println(ex.getMessage());
	        return ex.getMessage();
	    }
    }
	
}