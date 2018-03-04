package com.blueliv.model;

import java.util.ArrayList;
import java.util.List;

public class Plateau {
	Coordinates size;
	List<Rover> rovers = new ArrayList<Rover>();

	public Plateau(Coordinates size) {
		super();
		this.size = new Coordinates(size.x, size.y);
	}

	public List<Rover> getRovers() {
		return rovers;
	}

	@Override
	public String toString() {
		return "Plateau [size=" + size + "]";
	}

	public Coordinates getSize() {
		return new Coordinates(size);
	}

}
