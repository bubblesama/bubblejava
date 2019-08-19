package com.bb.stellar.model;

import java.util.List;

public class Asteroid extends Place{

	public Asteroid(String name,  StellarSystem system, double x, double y) {
		super(name, TypePlace.ASTEROID, system, x, y);
	}

	@Override
	public List<String> generateDescription() {
		return generateDefaultDescription();
	}

}
