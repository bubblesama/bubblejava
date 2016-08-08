package com.bbsama.krole.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.bbsama.krole.view.Logger;

public class StuffManager {
	
	public static final char NULL_SLOT = '!';
	
	
	//gestion de l'inventaire
	private Map<StuffType,Character> slotCharByStuffType;
	private Map<Character,StuffType> slotStuffTypeByChar;
	private List<Character> slotChars;
	
	// tout le stuff de l'univers
	private List<Stuff> stuffs;

	// gestion de l'affichage pour looting au sol
	private Map<Character,Stuff> lootByChar;
	private Map<Stuff,Boolean> selectedLoot;
	
	// gestion du drop depuis l'inventaire
	private Map<StuffType,Boolean> selectedDrop;
	
	
	private static StuffManager instance;
	public static synchronized StuffManager getInstance(){
		if (instance == null){
			instance = new StuffManager();
		}
		return instance;
	}
	
	public StuffManager(){
		// recuperation de la liste des caracteres d'inventaire
		Properties properties = new Properties();
		try{
			FileInputStream input = new FileInputStream("assets/krole.properties");
			properties.load(input);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String allSlotCharsString = properties.getProperty("inventorySlotChars");
		this.slotChars = new ArrayList<Character>();
		for (String character: allSlotCharsString.split(",")){
			if (character.length() == 1){
				slotChars.add(character.charAt(0));
			}
		}
		this.slotCharByStuffType = new HashMap<StuffType,Character>();
		this.slotStuffTypeByChar = new HashMap<Character,StuffType>();
		// stuffs
		this.stuffs = new ArrayList<Stuff>();
		materializeStuff(new Stuff(StuffType.ROCK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.ROCK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.ROCK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.ROCK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.ROCK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getFirstPassableCell());
		materializeStuff(new Stuff(StuffType.STICK), WorldManager.getInstance().getLevelById(0).getCell(WorldManager.getInstance().getLevelById(0).getFirstPassableCell().i()+1,WorldManager.getInstance().getLevelById(0).getFirstPassableCell().j()+1));

	}
	
	public List<Stuff> getStuffs(){
		return stuffs;
	}
	
	public void materializeStuff(Stuff stuff, Cell cell){
		stuffs.add(stuff);
		stuff.dropFromThinAir(cell);
	}
	
	public char attributeSlot(StuffType type){
		char result = StuffManager.NULL_SLOT;
		if (slotCharByStuffType.get(type) != null){
			result = slotCharByStuffType.get(type);
		}else{
			// attribution d'un nouveau slot
			boolean found = false;
			for(char c: slotChars){
				if (!found && (slotStuffTypeByChar.get(c) == null)){
					// slot trouve
					found = true;
					slotCharByStuffType.put(type, c);
					slotStuffTypeByChar.put(c,type);
					result = c;
				}
			}
		}
		if (result == StuffManager.NULL_SLOT){
			//TODO gestion du clean des slots par rapport à l'inventaire du keum
			Logger.getInstance().addLog("no slot available");
		}
		System.out.println("attributeSlot type="+type+" result="+result);
		return result;
	}
	
	public boolean isStuffSlotCharacter(char character){
		boolean result = false;
		for (char c: slotChars){
			if (c == character){result = true;}
		}
		return result;
	}
	
	public void initLootByChar(List<Stuff> loot){
		lootByChar = new HashMap<Character,Stuff>();
		selectedLoot = new HashMap<Stuff,Boolean>();
		int charIndex = 0;
		for (Stuff stuff: loot){
			if (charIndex < slotChars.size()){
				lootByChar.put(slotChars.get(charIndex), stuff);
				charIndex++;
				selectedLoot.put(stuff, false);
			}
		}
	}

	public Map<Character, Stuff> getLootByChar() {
		return lootByChar;
	}
	
	public boolean isSelectedLoot(Stuff stuff){
		return selectedLoot.get(stuff);
	}
	
	public void toggleLootSelectionByChar(char c){
		Stuff selectedStuff = lootByChar.get(c);
		if (selectedStuff != null){
			selectedLoot.put(selectedStuff,!selectedLoot.get(selectedStuff));
		}
	}
	
	public void toggleDropSelectionByChar(char c){
		StuffType type = slotStuffTypeByChar.get(c);
		if (selectedDrop.get(type) != null){
			selectedDrop.put(type,!selectedDrop.get(type));
		}
	}

	public List<Character> getOrderedSlotChars() {
		return slotChars;
	}
	
	public List<Stuff> getSelectedLoot(){
		List<Stuff> result = new ArrayList<Stuff>();
		if (lootByChar != null){
			for (Character slotChar: slotChars){
//				System.out.println("getSelectedLoot slotChar="+slotChar+" lootByChar.get(slotChar)="+lootByChar.get(slotChar));
				if (lootByChar.get(slotChar) != null && selectedLoot.get(lootByChar.get(slotChar))){
					result.add(lootByChar.get(slotChar));
				}
			}
		}
		return result;
	}

	public Character getSlot(StuffType type) {
		return slotCharByStuffType.get(type) == null?StuffManager.NULL_SLOT:slotCharByStuffType.get(type);
	}
	
	public Character getSlot(Stuff stuff){
		return getSlot(stuff.type);
	}
	
	public StuffType getTypeByChar(char c){
		return slotStuffTypeByChar.get(c);
	}
	
	public void initDroppingSelection(List<Stuff> inventory){
		selectedDrop = new HashMap<StuffType, Boolean>();
		for (Stuff stuff: inventory){
			selectedDrop.put(stuff.type, false);
		}
	}
	
	public boolean isSelectedForDrop(StuffType type){
		return selectedDrop.get(type);
	}
	
}
