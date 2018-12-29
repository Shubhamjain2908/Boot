package org.sj.service.encryption;

import java.security.NoSuchAlgorithmException;

public class BcryptEnc 
{
	public String Bcrypt(String data) throws NoSuchAlgorithmException
    {
        String  originalPassword = data;
        String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
        System.out.println(generatedSecuredPasswordHash);
         
        boolean matched = BCrypt.checkpw(originalPassword, generatedSecuredPasswordHash);
        System.out.println(matched);
        return generatedSecuredPasswordHash;
    }
}
