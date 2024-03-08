package com.example.sensordata;

import java.io.Reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.example.Radar;
@RestController
@SpringBootApplication
public class SensorDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorDataApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
	return String.format("Hello %s!", name);
	}

	@RequestMapping(value="/yaml",method=RequestMethod.POST,produces="application/json")
	public Object ParseYamlText(@RequestParam(value="file") MultipartFile file) throws IOException
	{
		
		// parse CSV file to create a list of `User` objects
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream())))
		{
			Yaml yaml = new Yaml();
			Object obj=yaml.loadAs(reader,Radar.class);
			return obj;	
		}
		
		
	}


}
