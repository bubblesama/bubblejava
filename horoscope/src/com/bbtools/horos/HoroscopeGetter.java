package com.bbtools.horos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bbtools.horos.Horoscope.Sign;
import com.bubblebob.tool.web.CopyTools;

public class HoroscopeGetter {

	private static Map<String,Sign> translationMapping;
	
	public HoroscopeGetter() {

	}
	
	static{
		translationMapping = new HashMap<String,Sign>();
		translationMapping.put("bélier", Sign.Aries);
		translationMapping.put("taureau", Sign.Taurus);
		translationMapping.put("gémeaux", Sign.Gemini);
		translationMapping.put("cancer", Sign.Cancer);
		translationMapping.put("lion", Sign.Leo);
		translationMapping.put("vierge", Sign.Virgo);
		translationMapping.put("balance", Sign.Libra);
		translationMapping.put("scorpion", Sign.Scorpius);
		translationMapping.put("sagittaire", Sign.Sagittarius);
		translationMapping.put("capricorne", Sign.Capricorn);
		translationMapping.put("verseau", Sign.Aquarius);
		translationMapping.put("poissons", Sign.Pisces);
	}
	
	public static void main(String[] args) {
		String horosPageContent = CopyTools.getStringFromUrl("http://horoscope.20minutes.fr/");
		Pattern  pattern = Pattern.compile("<div class=\"signe\">\\W+?<.+?></.+?>\\W+?<h2>Horoscope (.+?) :</h2>\\W+?<p>\\W+?(.+?)</p>",Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(horosPageContent);
		DailyPrediction daily = new DailyPrediction();
		while(matcher.find()){
			String frenchSign = matcher.group(1);
			String prediction = matcher.group(2);
			Sign sign = translationMapping.get(frenchSign);
			Horoscope horoscope = new Horoscope(sign, prediction);
			daily.horoscopes.put(sign, horoscope);
		}
		Date today = new Date();
		daily.date = today;
		System.out.println(daily.toJson());
	}
}
