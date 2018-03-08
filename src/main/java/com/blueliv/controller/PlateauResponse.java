package com.blueliv.controller;

import java.util.StringJoiner;

import com.blueliv.model.Plateau;
import com.blueliv.model.Rover;

/**
 * Class that encloses expected application output. Output will contain position
 * and orientation for each rover deployed in the plateau.
 * 
 * @author Jaume Paternoy
 *
 */
public class PlateauResponse {
	String response;

	public PlateauResponse(String response) {
		super();
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public static PlateauResponse createResponse(Plateau plateau) {
		StringJoiner sj = new StringJoiner(" ");
		for (Rover rover : plateau.getRovers()) {
			sj.add(rover.getPosition().getX() + " " + rover.getPosition().getY() + " " + rover.getOrientation());
		}
		String response = sj.toString();
		return new PlateauResponse(response);
	}
}
