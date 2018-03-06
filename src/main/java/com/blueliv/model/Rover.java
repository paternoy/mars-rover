package com.blueliv.model;

/**
 * This class represents a Rover vehicle and contains all needed data to
 * preserve its state within the Plateau. This includes its position and
 * orientation.
 * 
 * @author Jaume Paternoy
 *
 */
public class Rover {

	Coordinates position;
	Orientation orientation;

	public Rover(Coordinates position, Orientation orientation) {
		super();
		this.position = position;
		this.orientation = orientation;
	}

	public Coordinates getPosition() {
		return position;
	}

	public void setPosition(Coordinates position) {
		this.position = position;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	@Override
	public String toString() {
		return "Rover [position=" + position + ", orientation=" + orientation + "]";
	}
}
