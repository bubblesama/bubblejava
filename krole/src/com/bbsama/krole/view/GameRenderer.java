package com.bbsama.krole.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.bbsama.krole.model.Cell;
import com.bbsama.krole.model.CellType;
import com.bbsama.krole.model.Level;
import com.bbsama.krole.model.Mob;
import com.bbsama.krole.model.MobManager;
import com.bbsama.krole.model.MobType;
import com.bbsama.krole.model.Stuff;
import com.bbsama.krole.model.StuffManager;
import com.bbsama.krole.model.StuffType;
import com.bbsama.krole.model.WorldManager;
import com.bubblebob.tool.font.SlickFont;
import com.bubblebob.tool.font.SlickText;

public class GameRenderer {


	private static SlickFont font = SlickFont.getFont("assets/alphabet_white.png","assets/numbers_white.png",2);

	// grille
	private int cellSize = 17;
	private int scale =2;
	private int xGridOffset = 3;//19;
	private int yGridOffset = 54;
	//private int gridThickness = 1;
	private int gridThickness = 0;
	private int renderedW=64;
	private int renderedH=38;
	public int fromI;
	public int fromJ;
	// inventory
	private boolean displayInventory = false;
	private int inventoryFromX = 1300;
	private int inventoryFromY = 10;
	// loot
	private boolean displayLoot = false;
	private int lootFromX = inventoryFromX;
	private int lootFromY = inventoryFromY;

	private SpriteSheet sheet; 
	private int displayedLevelId = 0;
	private static GameRenderer instance;

	private GameRenderer(){}

	public synchronized static GameRenderer getInstance(){
		if (instance == null){
			instance = new GameRenderer();
		}
		return instance;
	}

	public void loadSpriteSheet(String spriteSheetPath, int spriteSize){
		try {
			this.sheet = new SpriteSheet(spriteSheetPath,spriteSize,spriteSize);
			this.cellSize = spriteSize;
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g){
		//reset
		g.setColor(Palette.COLOR_02_DARK_PURPLE);
		g.fillRect(0, 0, 10000, 10000);
		// TODO: link du level actuel
		Level level = WorldManager.getInstance().getLevelById(displayedLevelId);
		// render du level
		for (int i=0;i<level.w();i++){
			for (int j=0;j<level.h();j++){
				render(level.getCell(i, j),g);
			}
		}
		// render des objets
		for (Stuff stuff: level.loot){
			render(stuff,g);
		}
		// render des mobs
		for (Mob mob: MobManager.getInstance().getMobs()){
			if (mob.cell().level().id() == displayedLevelId){
				render(mob,g);
			}
		}
		// render des infos
		g.setColor(Palette.COLOR_07_DARK_PINK);
		Logger.getInstance().render(g);
		// rendu de l'inventaire
		if (displayInventory){
			//TODO gestion intelligente de la non-reconstruction systematique de la liste, surtout SlickText
			List<String> displayedInventory = new ArrayList<String>();
			displayedInventory.add("INVENTORY");
			displayedInventory.add("");
			if (MobManager.getInstance().getPlayer().getInventory().isEmpty()){
				displayedInventory.add("empty");
			}
			Map<StuffType,Integer> stuffAmounts = new HashMap<StuffType,Integer>();
			for (Stuff loot: MobManager.getInstance().getPlayer().getInventory()){
				//displayedInventory.add(" "+loot.getSlot()+": "+loot.type);
				if (stuffAmounts.get(loot.type) != null){
					stuffAmounts.put(loot.type, stuffAmounts.get(loot.type)+1);
				}else{
					stuffAmounts.put(loot.type, 1);
				}
			}
			// affichage ordonne
			for (Character character: StuffManager.getInstance().getOrderedSlotChars()){
				StuffType type = StuffManager.getInstance().getTypeByChar(character);
				if (stuffAmounts.get(type) != null){
					boolean isSelected = StuffManager.getInstance().isSelectedForDrop(type);
					displayedInventory.add((isSelected?"x":" ")+" "+character+" "+stuffAmounts.get(type)+" "+type);
				}
			}
			SlickText inventoryText = new SlickText("", font, inventoryFromX, inventoryFromY, 1000, 1000, 1);
			inventoryText.modifyText(displayedInventory);
			inventoryText.setGraphics(g);
			inventoryText.paint();
		}
		// rendu du loot
		if (displayLoot){
			//TODO gestion intelligente de la non-reconstruction systematique de la liste, surtout SlickText
			List<String> displayedLoot = new ArrayList<String>();
			displayedLoot.add("LOOT");
			displayedLoot.add("");
			for (Character c: StuffManager.getInstance().getOrderedSlotChars()){
				Stuff stuff = StuffManager.getInstance().getLootByChar().get(c);
				if (stuff != null){
					boolean isSelected = StuffManager.getInstance().isSelectedLoot(stuff);
					displayedLoot.add((isSelected?"x":" ")+" "+c+" "+stuff.type);
				}
			}
			SlickText lootText = new SlickText("", font, lootFromX, lootFromY, 1000, 1000, 1);
			lootText.modifyText(displayedLoot);
			lootText.setGraphics(g);
			lootText.paint();
		}

	}

	public void render(Cell cell, Graphics g){
		if ((cell.i()>=fromI && cell.i()<fromI+renderedW)&&(cell.j()>=fromJ && cell.j()<fromJ+renderedH)){
			Image drawnImage = getImageFromCellType(cell.type).getScaledCopy(scale);
			if (cell.isDebug){
				drawnImage = getImageFromCellType(CellType.VOID).getScaledCopy(scale);
			}
			g.drawImage(drawnImage,xGridOffset+(cellSize*scale+gridThickness)*(cell.i()-fromI), yGridOffset+ (cellSize*scale+gridThickness)*(cell.j()-fromJ));
		}
	}

	public void render(Stuff stuff, Graphics g){
		if (stuff.isDropped()){
			if ((stuff.i()>=fromI && stuff.i()<fromI+renderedW)&&(stuff.j()>=fromJ && stuff.j()<fromJ+renderedH)){
				g.drawImage(getImageFromStuffType(stuff.type).getScaledCopy(scale),xGridOffset+(cellSize*scale+gridThickness)*(stuff.i()-fromI), yGridOffset+ (cellSize*scale+gridThickness)*(stuff.j()-fromJ));
			}
		}

	}

	public void render(Mob mob, Graphics g){
		if ((mob.i()>=fromI && mob.i()<fromI+renderedW)&&(mob.j()>=fromJ && mob.j()<fromJ+renderedH)){
			g.drawImage(getImageFromMobType(mob.type).getScaledCopy(scale),xGridOffset+(cellSize*scale+gridThickness)*(mob.i()-fromI), yGridOffset+ (cellSize*scale+gridThickness)*(mob.j()-fromJ));
		}
	}

	public void recenter(int i, int j){
		this.fromI = i-renderedW/2;
		this.fromJ = j-renderedH/2;
	}

	public Image getImageFromMobType(MobType type){
		if (type == MobType.MONSTER){
			return sheet.getSprite(0, 2);
		}else{
			return sheet.getSprite(1, 2);
		}

	}

	public Image getImageFromStuffType(StuffType type){
		switch (type) {
		case ROCK:
			return sheet.getSprite(0, 3);
		case STICK:
			return sheet.getSprite(1, 3);
		default:
			return sheet.getSprite(1, 2);
		}
	}

	public Image getImageFromCellType(CellType type){
		switch (type) {
		case VOID:
			return sheet.getSprite(0, 0);
		case WAY:
			return sheet.getSprite(1, 0);
		case WALL:
			return sheet.getSprite(2, 0);
		default:
			return sheet.getSprite(0, 0);
		}
	}

	public Cell getCellByScreenXY(int x, int y){
		//TODO indication de la cellule en fonction de l'affichage ecran
		int i = (x-xGridOffset)/(cellSize*scale+gridThickness)+fromI;
		int j = (y-yGridOffset)/(cellSize*scale+gridThickness)+fromJ;
		Level displayedLevel = WorldManager.getInstance().getLevelById(displayedLevelId);
		if ( i>=0 && i<displayedLevel.w() && j>=0 && j<displayedLevel.h() ){
			Cell cell = displayedLevel.getCell(i, j);
			return cell;
		}
		return null;
	}

	public void toggleInventoryDisplay(){
		this.displayInventory = !this.displayInventory;
	}

	public void showInventory(){
		this.displayInventory = true;
	}

	public void hideInventory(){
		this.displayInventory = false;
	}


	public void showLoot(){
		this.displayLoot = true;
	}

	public void hideLoot(){
		this.displayLoot = false;
	}

}



