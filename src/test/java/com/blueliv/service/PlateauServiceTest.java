package com.blueliv.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.blueliv.TestConfiguration;
import com.blueliv.model.Coordinates;
import com.blueliv.model.Orientation;
import com.blueliv.model.Plateau;
import com.blueliv.model.Rover;
import com.blueliv.service.PlateauService;
import com.blueliv.service.exception.PlateauServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class PlateauServiceTest {
	@Autowired
	PlateauService plateauService;

	@Test
	public void testPlateauCreationOK() {
		try {
			Plateau plateau = plateauService.createPlateau(new Coordinates(5, 4));
			assertEquals(5, plateau.getSize().getX());
			assertEquals(4, plateau.getSize().getY());
			assertEquals(0, plateau.getRovers().size());
		} catch (PlateauServiceException e) {
			fail("Unexpected PlateauServiceException thrown in Plateau creation");
		}
	}

	@Test
	public void testPlateauCreationNullKO() {
		try {
			plateauService.createPlateau(null);
			fail("A PlateauServiceException was expected");
		} catch (PlateauServiceException e) {
		}
	}

	@Test
	public void testRoverCreationOK() {
		try {
			Plateau plateau = new Plateau(new Coordinates(5, 5));
			Coordinates position = new Coordinates(3, 2);
			Orientation orientation = Orientation.S;
			Rover rover = plateauService.addRoverToPlateau(plateau, position, orientation);
			assertEquals(3, rover.getPosition().getX());
			assertEquals(2, rover.getPosition().getY());
			assertEquals(orientation, rover.getOrientation());
		} catch (PlateauServiceException e) {
			fail("Unexpected PlateauServiceException thrown in Plateau creation");
		}
	}

	@Test
	public void testRoverCreationOutOfBoundsKO() {
		try {
			Plateau plateau = new Plateau(new Coordinates(4, 4));
			Coordinates position = new Coordinates(5, 5);
			Orientation orientation = Orientation.S;

			plateauService.addRoverToPlateau(plateau, position, orientation);
			fail("A PlateauServiceException was expected");
		} catch (PlateauServiceException e) {
		}
	}

	@Test
	public void testRoverCreationInOccupiedPosition() {
		try {
			Plateau plateau = new Plateau(new Coordinates(4, 4));
			Coordinates position = new Coordinates(3, 3);
			Orientation orientation = Orientation.S;
			plateau.addRover(new Rover(position, orientation));
			plateauService.addRoverToPlateau(plateau, position, orientation);
			fail("A PlateauServiceException was expected");
		} catch (PlateauServiceException e) {
		}
	}

}
