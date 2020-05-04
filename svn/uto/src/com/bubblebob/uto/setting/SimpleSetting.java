package com.bubblebob.uto.setting;

import com.bubblebob.uto.BuyableType;
import com.bubblebob.uto.Period;

public class SimpleSetting implements Setting{

	public SimpleSetting(int maxCycles, int freePeriodLength) {
		super();
		this.maxCycles = maxCycles;
		this.freePeriodLength = freePeriodLength;
	}

	private int maxCycles;
	private int freePeriodLength;
	
	public int getMaxCycles() {
		return maxCycles;
	}

	public int getPeriodLength(Period p) {
		if (p == Period.FREE){
			return freePeriodLength;
		}else{
			return SettingsManager.getInstance().getPeriodLength(p);
		}
	}

	public int getPrice(BuyableType b) {
		return SettingsManager.getInstance().getPrice(b);
	}

}
