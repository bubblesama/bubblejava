package com.bb.stellar.model;

import java.util.List;

public class Factory extends Place{

	public Factory(String name, StellarSystem system, double x, double y) {
		super(name, TypePlace.FACTORY, system, x, y);
	}

	@Override
	public List<String> generateDescription() {
		return generateDefaultDescription();
	}

}
