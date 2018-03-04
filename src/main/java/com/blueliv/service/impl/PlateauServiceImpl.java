package com.blueliv.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blueliv.model.Coordinates;
import com.blueliv.model.Plateau;
import com.blueliv.service.PlateauService;

@Service
public class PlateauServiceImpl implements PlateauService{

	private static final Logger logger = LoggerFactory.getLogger(PlateauServiceImpl.class);
	
	@Override
	public Plateau createPlateau(Coordinates size) {
		return new Plateau(size);
	}



}
