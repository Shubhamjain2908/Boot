package org.sj.service.encryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES 
{
	public String Encryption(String key)
    {
        try
        {
            // file to be encrypted
            FileInputStream inFile = new FileInputStream("E:\\Boot\\course_api\\myLog.log");

            // encrypted file
            FileOutputStream outFile = new FileOutputStream("E:\\Boot\\course_api\\log\\Enc\\encryptedfile.des");

            // password to encrypt the file
            String password = key;

            // password, iv and salt should be transferred to the other end
            // in a secure manner

            // salt is used for encoding
            // writing it to a file
            // salt should be transferred to the recipient securely
            // for decryption
            byte[] salt = new byte[8];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(salt);
            FileOutputStream saltOutFile = new FileOutputStream("E:\\Boot\\course_api\\log\\Enc\\salt.enc");
            saltOutFile.write(salt);
            saltOutFile.close();

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,128);
            SecretKey secretKey = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

            //
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();

            // iv adds randomness to the text and just makes the mechanism more
            // secure
            // used while initializing the cipher
            // file to store the iv
            FileOutputStream ivOutFile = new FileOutputStream("E:\\Boot\\course_api\\log\\Enc\\iv.enc");
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            ivOutFile.write(iv);
            ivOutFile.close();

            //file encryption
            byte[] input = new byte[64];
            int bytesRead;

            while ((bytesRead = inFile.read(input)) != -1) 
            {
                byte[] output = cipher.update(input, 0, bytesRead);
                if (output != null)
                    outFile.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null)
                    outFile.write(output);

            inFile.close();
            outFile.flush();
            outFile.close();

            return "File Encrypted.";    
        }
        catch(IOException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidParameterSpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e)
        {
            System.out.println(e);
            e.printStackTrace();
            return "Encryption failed";
        }
    }
    
    public String Decryption(String key)
    {
        try
        {
            String password = key;

            // reading the salt
            // user should have secure mechanism to transfer the
            // salt, iv and password to the recipient
            FileInputStream saltFis = new FileInputStream("E:\\Boot\\course_api\\log\\Enc\\salt.enc");
            byte[] salt = new byte[8];
            saltFis.read(salt);
            saltFis.close();

            // reading the iv
            FileInputStream ivFis = new FileInputStream("E:\\Boot\\course_api\\log\\Enc\\iv.enc");
            byte[] iv = new byte[16];
            ivFis.read(iv);
            ivFis.close();

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536,128);
            SecretKey tmp = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            // file decryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
            FileInputStream fis = new FileInputStream("E:\\Boot\\course_api\\log\\Enc\\encryptedfile.des");
            FileOutputStream fos = new FileOutputStream("E:\\Boot\\course_api\\log\\Enc\\decrypted.txt");
            byte[] in = new byte[64];
            int read;
            while ((read = fis.read(in)) != -1) 
            {
                byte[] output = cipher.update(in, 0, read);
                if (output != null)
                   fos.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null)
                    fos.write(output);
            fis.close();
            fos.flush();
            fos.close();
            return "File Decrypted.";    
        }
        catch(IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e)
        {
            System.out.println(e);
            e.printStackTrace();
            return "Decryption failed";
        }
    }
}
