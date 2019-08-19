package com.bb.stellar.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Galaxy {

	private Map<String,StellarSystem> systemsByName;
	private List<Gate> gates;
	private ShipManager shipManager;



	public Galaxy() {
		super();
		this.systemsByName = new HashMap<String, StellarSystem>();
		this.gates = new ArrayList<Gate>();
		this.shipManager = new ShipManager(this);
	}

	public void addGate(String name, StellarSystem systemA, double xA, double yA, StellarSystem systemB, double xB, double yB){
		Gate gateAB = new Gate(name, systemA, xA, yA, systemB);
		systemA.add(gateAB);
		Gate gateBA = new Gate(name, systemB, xB, yB, systemB);
		systemB.add(gateBA);
		gates.add(gateAB);
		gates.add(gateBA);
	}

	public void addSystem(StellarSystem system){
		systemsByName.put(system.getName(), system);
		shipManager.addSystem(system);
	}

	public static Galaxy getDefaultGalaxy(){
		Galaxy result = new Galaxy();
		StellarSystem systemA = new StellarSystem("A-system",0,-2);
		systemA.add(new Factory("FAC-A1", systemA, 4, 4));
		systemA.add(new Asteroid("AST-A1",systemA, 4, 6));
		StellarSystem systemB = new StellarSystem("B-system",0,1);
		StellarSystem systemC = new StellarSystem("C-system",16,3);
		StellarSystem systemD = new StellarSystem("D-system",6,-12);
		result.addSystem(systemA);
		result.addSystem(systemB);
		result.addSystem(systemC);
		result.addSystem(systemD);
		result.shipManager.addShip(systemA, (double)4, (double)5, "SHI-1");
		result.addGate("AB-gate",systemA,0,5,systemB, 0,-5);
		result.addGate("BC-gate",systemB,3,3,systemC,-3,-3);
		result.addGate("AD-gate",systemA,3,3,systemD,-3,-3);
		result.addGate("BD-gate",systemB,3,3,systemD,-4,-4);
		return result;
	}

	public Collection<StellarSystem> getSystems(){
		return systemsByName.values();
	}

	public Collection<Gate> getGates(){
		return gates;
	}

	public ShipManager getShipManager() {
		return shipManager;
	}

}
