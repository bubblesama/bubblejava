package com.bubblebob.uto;

import com.bubblebob.uto.setting.SettingsManager;

public enum Period {

	FREE(false,true),SHOW_POP(true,false),SHOW_SCORE(true,false);

	private boolean freeze;
	private boolean showValue;

	private Period(boolean free, boolean showValue) {
		this.freeze = free;
		this.showValue = showValue;
	}

	public boolean isFreezed() {
		return freeze;
	}
	
	public boolean showValue() {
		return showValue;
	}

	public Period next(){
		switch (this) {
		case FREE:
			return SHOW_POP;
		case SHOW_POP:
			return SHOW_SCORE;
		default:
			return FREE;
		}
	}
	
}
