package com.bubblebob.uto.setting;

import com.bubblebob.uto.BuyableType;
import com.bubblebob.uto.Period;

public interface Setting {

	public int getMaxCycles();
	public int getPeriodLength(Period p);
	public int getPrice(BuyableType b);
}
