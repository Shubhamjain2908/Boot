package org.sj.service;

import org.sj.entity.User;
import org.sj.exception.BadRequestException;
import org.sj.repository.UserRepository;
import org.sj.service.encryption.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService 
{
	@Autowired
	private UserRepository userRepository;
	
	EncryptionService eS=new EncryptionService();
	
	public User saveUser(User user) 
	{
		String method=user.getMethod();
		String data=user.getPassword();
		String encryptedPassword="";
		try 
		{
			if(method.equalsIgnoreCase("bcrypt"))
			{
				encryptedPassword=eS.bcryptE(data);
			}
			if(method.equalsIgnoreCase("base64")) 
			{
				encryptedPassword=eS.base64E(data);
			}
			if(method.equalsIgnoreCase("TripleDES"))
			{
				encryptedPassword=eS.desE(data);
			}
			if(method.equalsIgnoreCase("PBKD")) 
			{
				encryptedPassword=eS.pbkdE(data);
			}
			if(method.equalsIgnoreCase("md5")) 
			{
				encryptedPassword=eS.md5E(data);
			}
			if(method.equalsIgnoreCase("sha")) 
			{
				encryptedPassword=eS.shaE(data);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
		if(encryptedPassword.isEmpty()) 
		{
			throw new BadRequestException("No such algorithm Available");
		}
		else 
		{
			user.setPassword(encryptedPassword);
			return userRepository.save(user);
		
		}
	}
}
