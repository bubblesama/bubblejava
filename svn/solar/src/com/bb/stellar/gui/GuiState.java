package com.bb.stellar.gui;

import com.bb.stellar.model.Place;
import com.bb.stellar.model.Ship;
import com.bb.stellar.model.StellarSystem;

public class GuiState {

	private StellarSystem selectedSystem;
	private Place selectedPlace;
	private Ship selectedShip = null;
	private ViewMode mode;
	private boolean multiSelectionOn = false;
	
	
	public GuiState(ViewMode mode){
		this.mode = mode;
	}

	public StellarSystem getSelectedSystem() {return selectedSystem;}
	public void setSelectedSystem(StellarSystem selectedSystem) {this.selectedSystem = selectedSystem;}

	public Place getSelectedPlace() {return selectedPlace;}
	public void setSelectedPlace(Place selectedPlace) {this.selectedPlace = selectedPlace;}

	public ViewMode getMode() {return mode;}
	public void setMode(ViewMode mode) {this.mode = mode;}

	public void toggleViewMode(){
		if (mode == ViewMode.GALAXY){
			if (getSelectedSystem() != null){
				mode = ViewMode.SYSTEM;
			}
		}else{
			mode = ViewMode.GALAXY;
		}
	}

	public Ship getSelectedShip() {return selectedShip;}
	public void setSelectedShip(Ship selectedShip) {this.selectedShip = selectedShip;}

	public boolean isMultiSelectionOn() {return multiSelectionOn;}
	public void setMultiSelectionOn(boolean multiSelectionOn) {this.multiSelectionOn = multiSelectionOn;}

}
