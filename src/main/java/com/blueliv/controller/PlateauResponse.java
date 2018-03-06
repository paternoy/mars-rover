package com.blueliv.controller;

import com.blueliv.model.Plateau;
import com.blueliv.model.Rover;

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
		StringBuilder sb = new StringBuilder();
		for (Rover rover : plateau.getRovers()) {
			sb.append(
					rover.getPosition().getX() + " " + rover.getPosition().getY() + " " + rover.getOrientation() + " ");
		}
		sb.deleteCharAt(sb.length() - 1);
		String response = sb.toString();
		return new PlateauResponse(response);
	}
}
