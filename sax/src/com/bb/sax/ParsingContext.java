package com.bb.sax;

import java.util.HashMap;
import java.util.Map;

public class ParsingContext {
	
	public ParsingContext() {
		super();
	}
	private Map<String,Item> itemsById = new HashMap<String,Item>();
	private Map<String,Category> categoriesById = new HashMap<String,Category>();
	private Map<String,Person> personsById = new HashMap<String,Person>();

}
