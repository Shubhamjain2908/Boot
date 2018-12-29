package org.sj;

import org.sj.properties.StorageProperties;
import org.sj.service.CsvJsonService;
import org.sj.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class courseAPI 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(courseAPI.class, args);
	}
	
	
	CsvJsonService csv =new CsvJsonService();
	@Bean
    CommandLineRunner init(StorageService storageService) 
	{
        return (args) -> {
            storageService.deleteAll();
            csv.deleteTemp();
            storageService.init();
        };   
    }
}
