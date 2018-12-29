package org.sj.service.encryption;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.sj.service.encryption.EncryptionService;

public class EncryptionService 
{
	public String bcryptE(String data) throws NoSuchAlgorithmException 
	{
		BcryptEnc b=new BcryptEnc();
		return b.Bcrypt(data);
	}
	
	public String base64E(String data) throws NoSuchAlgorithmException 
	{
		Base64Encoding b=new Base64Encoding();
		return b.mimeEncoding(data);
	}
	
	public String pbkdE(String data) throws NoSuchAlgorithmException, InvalidKeySpecException 
	{
		PBKD p=new PBKD();
		return p.enc(data);
	}
	
	public String desE(String data) throws Exception 
	{
		TrippleDES t=new TrippleDES();
		return t.enc(data);
	}
	
	public String aesE(String key) 
	{
		AES a=new AES();
		return a.Encryption(key);
	}
	
	public String aesD(String key) 
	{
		AES a=new AES();
		return a.Decryption(key);
	}
	
	public String md5E(String data) 
	{
		MD5Encryption m=new MD5Encryption();
		return m.enc(data);
	}
	
	public String shaE(String data) throws NoSuchAlgorithmException
	{
		SHA s=new SHA();
		return s.encrypt(data);
	}
}
