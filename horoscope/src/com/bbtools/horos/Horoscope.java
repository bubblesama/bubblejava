package com.bbtools.horos;

public class Horoscope {

	public enum Sign{
		Aries,Taurus,Gemini,Cancer,Leo,Virgo,Libra,Scorpius,Sagittarius,Capricorn,Aquarius,Pisces
	}
	
	public Sign sign;
	public String prediction;
	
	public Horoscope(Sign sign, String prediction) {
		super();
		this.sign = sign;
		this.prediction = prediction;
	}
	
	
	public String toJson(){
		String result ="{\"sign\":\""+sign+"\", \"prediction\"=\""+prediction+"\"}";
		return result;
	}
	
}
