package com.blueliv.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class encloses the representation of a rectangular plateau, defined by
 * the x and y lengths contained in attribute size.
 * 
 * The Plateau is responsible of containing deployed rovers and register their
 * position.
 * 
 * @author Jaume Paternoy
 *
 */
public class Plateau {
	Coordinates size;
	List<Rover> rovers = new ArrayList<Rover>();

	public Plateau(Coordinates size) {
		super();
		this.size = new Coordinates(size.x, size.y);
	}

	/**
	 * Method to retrieve the Rovers deployed in this Plateau, preserving the
	 * insertion order.
	 * 
	 * @return
	 */
	public List<Rover> getRovers() {
		return Collections.unmodifiableList(rovers);
	}

	/**
	 * Adds a new Rover to this Plateau. If this Rover was already contained in the
	 * Plateau or another Rover is already at the same position, will return false.
	 * Otherwise adds the Rover and returns true.
	 * 
	 * @param rover
	 *            The new Rover to be added
	 * @return
	 */
	public boolean addRover(Rover rover) {
		if (rovers.contains(rover))
			return false;
		if (!isPositionEmpty(rover.getPosition()))
			return false;
		rovers.add(rover);
		return true;
	}

	/**
	 * Checks the availability of a specific position in this Plateau.
	 * 
	 * @param position
	 * @return True if the position is empty.
	 */
	public boolean isPositionEmpty(Coordinates position) {
		if (rovers.isEmpty())
			return true;
		return rovers.stream().noneMatch(r -> r.position.equals(position));
	}

	/**
	 * Checks whether the position is within Plateau bounds or not.
	 * 
	 * @param position
	 * @return True if the position is inside this plateau.
	 */
	public boolean isPositionInBounds(Coordinates position) {
		if (position.getX() < 0 || position.getX() >= size.getX())
			return false;
		if (position.getY() < 0 || position.getY() >= size.getY())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Plateau [size=" + size + "]";
	}

	public Coordinates getSize() {
		return new Coordinates(size);
	}

}
