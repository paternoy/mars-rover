package com.blueliv.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blueliv.controller.MarsRoverController;
import com.blueliv.controller.PlateauResponse;
import com.blueliv.controller.command.CommandFactoryProvider;
import com.blueliv.controller.command.CompositeCommand;
import com.blueliv.controller.command.exception.CommandFormatException;
import com.blueliv.service.exception.PlateauServiceException;

@RestController
@RequestMapping("/rover")
public class RoverRestController {
	private static final Logger logger = LoggerFactory.getLogger(RoverRestController.class);

	@Autowired
	CommandFactoryProvider commandFactoryProvider;

	@Autowired
	MarsRoverController marsRoverController;

	@RequestMapping(method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> postCommands(@RequestBody String commandString) {
		logger.debug("Commands received \"{}\"", commandString);
		try {
			CompositeCommand compositeCommand = (CompositeCommand) commandFactoryProvider
					.parseCommand(CompositeCommand.class, commandString);
			PlateauResponse response = marsRoverController.processCommands(compositeCommand);
			return new ResponseEntity<String>(response.getResponse(), HttpStatus.OK);
		} catch (PlateauServiceException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (CommandFormatException e) {
			logger.error(e.getMessage(), e);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
