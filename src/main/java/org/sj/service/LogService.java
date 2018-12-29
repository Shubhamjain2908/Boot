package org.sj.service;

import org.sj.entity.Log;
import org.sj.service.utility.CountLogger;
import org.sj.service.utility.DeleteLogger;
import org.sj.service.utility.GenerateLogger;
import org.springframework.stereotype.Service;

@Service
public class LogService 
{
	
	GenerateLogger rl=new GenerateLogger();
	CountLogger cl=new CountLogger();
	DeleteLogger dL=new DeleteLogger();
	String l[]= {"DEBUG","INFO","WARN","ERROR","TRACE"};
	
	public Log generateAll() 
	{
		Log log=new Log();
		
		try 
		{
			for (int i = 0; i < l.length; i++) 
			{
				rl.readF(l[i]);		//generating logger of debug,info...
			}
			
	        log.setDebug(cl.countLine("DEBUG"));
	        log.setError(cl.countLine("ERROR"));
	        log.setInfo(cl.countLine("INFO"));
	        log.setWarn(cl.countLine("WARN"));
	        log.setTrace(cl.countLine("TRACE"));
	        
	        deleteLogger();
	        
	        return log;
		}
		catch(Exception e) 
		{
			System.out.println(e);
			return log;
		}
	}
	
	public String deleteLogger() 
	{
		try 
		{
			for (int i = 0; i < l.length; i++) 
			{
				dL.deleteLog(l[i]);   	//deleting the generated logger
			}
			
			dL.deleteTempLog();
			
			return "Logs deleted";
		}
		catch(Exception e) 
		{
			System.out.println(e);
			return e.toString();
		}
	}
}
