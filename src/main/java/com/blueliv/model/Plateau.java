package com.blueliv.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plateau {
	Coordinates size;
	List<Rover> rovers = new ArrayList<Rover>();
	Map<Coordinates, Rover> roverMap = new HashMap<>();

	public Plateau(Coordinates size) {
		super();
		this.size = new Coordinates(size.x, size.y);
	}

	public List<Rover> getRovers() {
		return rovers;
	}

	public boolean addRover(Rover rover) {
		if (rovers.contains(rover))
			return false;
		if (isPositionEmpty(rover.getPosition()))
			return false;
		rovers.add(rover);
		return true;
	}

	public boolean isPositionEmpty(Coordinates position) {
		if (rovers.isEmpty())
			return true;
		return rovers.stream().noneMatch(r -> r.position.equals(position));
	}

	@Override
	public String toString() {
		return "Plateau [size=" + size + "]";
	}

	public Coordinates getSize() {
		return new Coordinates(size);
	}

}
