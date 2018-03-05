package com.blueliv.model;

public class Coordinates {
	int x;
	int y;

	public Coordinates(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Coordinates(Coordinates coordinates) {
		super();
		this.x = coordinates.getX();
		this.y = coordinates.getY();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null)
			return false;
		Coordinates c2 = (Coordinates) arg0;
		return x == c2.x && y == c2.y;
	}
}
