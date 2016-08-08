package com.bubblebob.tool.worldgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GoodEnoughDwarfLegendNameGenerator {

	//Exemples Kal-zu-rak, Em-no-roth, A-me-ruz, Tel-va-rion, Arogath
	
	String startingSyllabe = "A,Am,An,Ab,Ad,As,Ba,Bak,Bal,Bar,Bel,Bim,Bas,Bis,Bus,Bu,Ga,Go,Gir,It,O,Ol,Or,Od,Os,U,Ur";
	String centerSyllable = "da,de,di,do,ma,me,mi,mo,ra,ri,ro,ru";
	String endSyllable = "dot,doth,guk,gath,goth,k,on,rak,roth,rion,ron,t,th";
	String qualifier="the";
	String epithet= "Smart,Faithful,Brave,Glorious,Invincible,Deceiving,Clever,Brilliant,Dark,Stupendous,Horrible,Bold,Grand,Magnanimous,Great,Dire,Mighty,Ravenous,Red,Grey,Green,Blue,Ominous,Terrible";
	String name = "Raven,Scourge,Beast,Lion,Hound,Cleaver,Light,Sword,Fang,Claw,Destruction,Fire,Rain,Slit,Mouth,Orator,Knight,Stallion,Ring,Hand,Mountain,Pestilence,Might,Furor,Darkness,Mist,Ire";
	
	Random rand = new Random();
	
	private String genName(){
		String[] startingSyllabes = startingSyllabe.split(",");
		String[] centerSyllables = centerSyllable.split(",");
		String[] endSyllables = endSyllable.split(",");
		String[] epithets = epithet.split(",");
		String[] names = name.split(",");
		String result = "";
		result += startingSyllabes[rand.nextInt(startingSyllabes.length)];
		result += centerSyllables[rand.nextInt(centerSyllables.length)];
		result += endSyllables[rand.nextInt(endSyllables.length)]+" ";
		// retrait des lettres en doublon
		result = result.replaceAll("(.)\\1+", "$1");
		result += qualifier+" ";
		result += epithets[rand.nextInt(epithets.length)]+" ";
		result += names[rand.nextInt(names.length)]+" ";
		return result;
	}
	
	public static void main(String[] args) {
		
		List<String> names = new ArrayList<String>();
		GoodEnoughDwarfLegendNameGenerator engine = new GoodEnoughDwarfLegendNameGenerator();
		for (int i=0;i<100;i++){
			names.add(engine.genName());
		}
		Collections.sort(names);
		for (String name: names){
			System.out.println(name);
		}
		
	}
	
	
	
}
