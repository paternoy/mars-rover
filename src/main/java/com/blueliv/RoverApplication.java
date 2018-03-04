package com.blueliv;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.blueliv.command.PlateauCreationCommand;
import com.blueliv.command.exception.CommandFormatException;
import com.blueliv.model.Coordinates;
import com.blueliv.model.Plateau;
import com.blueliv.service.PlateauService;

@SpringBootApplication
public class RoverApplication implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(RoverApplication.class);
	
	
	@Autowired
	PlateauService plateauService;

	public static void main(String[] args) {
		SpringApplication.run(RoverApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			String[] arguments = args.getSourceArgs();
			logger.debug("RoverApplication started with args : {}", Arrays.toString(arguments));
			if(arguments.length<3 || arguments.length%2==0) {
				throw new CommandFormatException("Wrong number of input lines, must be odd");
			}
			
			PlateauCreationCommand plateauCreationCommand = PlateauCreationCommand.parseCommand(arguments[0]);
			Plateau plateau = plateauService.createPlateau(new Coordinates(plateauCreationCommand.getX()+1,
					plateauCreationCommand.getY()+1));
			logger.debug("Plateau created : {}", plateau);
			
		} catch (CommandFormatException e) {
			logger.error(e.getMessage());
			System.exit(1);
		}

	}
}
