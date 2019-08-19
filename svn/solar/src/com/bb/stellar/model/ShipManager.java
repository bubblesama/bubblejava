package com.bb.stellar.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShipManager {

	private Map<StellarSystem, List<Ship>> shipsBySystem;
	private List<Ship> ships;
	private Galaxy galaxy;
	
	
	public ShipManager(Galaxy galaxy){
		this.galaxy = galaxy;
		this.ships = new ArrayList<Ship>();
		this.shipsBySystem = new HashMap<StellarSystem, List<Ship>>();
		for (StellarSystem system: galaxy.getSystems()){
			shipsBySystem.put(system, new ArrayList<Ship>());
		}
	}
	
	public Ship addShip(StellarSystem system, 	double stellarX, double stellarY, String name){
		Ship result = new Ship(name,stellarX,stellarY,galaxy);
		ships.add(result);
		shipsBySystem.get(system).add(result);
		result.setSystem(system);
		return result;
	}
	
	public void addSystem(StellarSystem system){
		shipsBySystem.put(system, new ArrayList<Ship>());
	}
	
	
	public void removeShip(Ship ship){
		ships.remove(ship);
		shipsBySystem.get(ship.getSystem()).remove(ship);
	}
	
	
	public void putShipToSystem(Ship ship, StellarSystem system){
		shipsBySystem.get(ship.getSystem()).remove(ship);
		ship.setSystem(system);
		shipsBySystem.get(system).add(ship);
	}
	
	
	public List<Ship> getShipsBySystem(StellarSystem system){
		return shipsBySystem.get(system);
	}
	
	public void updateShips(){
		for (Ship ship: ships){
			ship.update();
		}
		
		
	}
	
	
	
	
	
}
