package com.bbsama.krole.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bbsama.krole.model.action.Action;
import com.bbsama.krole.model.action.MultiLootingAction;
import com.bbsama.krole.model.action.SimpleLootingAction;
import com.bbsama.krole.model.action.SimpleMoveAction;
import com.bbsama.krole.view.Logger;

public class Mob {

	public MobType type = MobType.MONSTER;
	private Cell cell;
	
	// stats
	private int str;
	private int dxt;
	private int itl;

	private List<Action> planning;
	private List<Stuff> inventory;

	private static final int delay = 10;
	public int timer = delay;

	public Mob(Cell cell, MobType type, int str, int dxt, int itl) {
		this.cell = cell;
		this.type = type;
		this.str = str;
		this.dxt = dxt;
		this.itl = itl;
		this.planning = new ArrayList<Action>();
		this.inventory = new ArrayList<Stuff>();
		cell.notifiedAsIn(this);
	}

	/**
	 * Tente le déplacement, l'effectue et dépense le temps
	 * @param di
	 * @param dj
	 * @return the move is done
	 */
	public boolean tryMoving(int di, int dj){
		boolean hasMoved = false;
		//TODO gestion de l'autorisation
		if (cell.i()+di>=0 && cell.i()+di<cell.level().w() && cell.j()+dj >=0 && cell.j()+dj <cell.level().h()){
			Cell nextCell = cell.level().getCell(cell.i()+di, cell.j()+dj);
			if (nextCell.isPassable()){
				cell.notifiedAsOut(this);
				nextCell.notifiedAsIn(this);
				cell = nextCell;
				hasMoved = true;
				spendTime(delay);
			}
		}
		return hasMoved;
	}

	public void act(){
		//TODO des actions
		if (!planning.isEmpty()){
			boolean perform = planning.get(0).resolve();
			planning.remove(0);
			if (!perform){
				spendTime(delay);
			}
		}else{
			Random random = new Random();
			// rien de planifié
			//TODO: planification d'un mouvement
			boolean hasMoved = tryMoving(random.nextInt(3)-1, random.nextInt(3)-1);
			if (!hasMoved){
				spendTime(delay);
			}
		}

	}

	private void spendTime(int delay){
		timer+=delay;
	}

	public void spendActionTime(){
		spendTime(delay);
	}


	/**
	 * Reset les actions pour planifier un mouvement
	 * @param di
	 * @param dj
	 */
	public void planSimpleMove(int di, int dj){
		planning.clear();
		planning.add(new SimpleMoveAction(this,di,dj));
	}
	
	public void planPathMove(Cell targetCell){
		planning.clear();
		//TODO: gestion du level actuel
		List<Cell> pathToClickedCell = WorldManager.getInstance().getLevelById(0).getDijsktraPath(cell,targetCell);
		Cell fromCell = cell;
		for (Cell nextCell: pathToClickedCell){
			planning.add(new SimpleMoveAction(this, nextCell.i()-fromCell.i(), nextCell.j()-fromCell.j()));
			fromCell = nextCell;
		}
	}

	public boolean isBusy(){
		return !planning.isEmpty();
	}
	public Cell cell() {
		return cell;
	}
	public int i() {
		return cell.i();
	}
	public int j() {
		return cell.j();
	}

	//TODO gestion du choix du loot si plusieurs objets au sol
	public void planLootingFirst(){
		if (!cell.getLoot().isEmpty()){
			if (this != MobManager.getInstance().getPlayer()){
				// cas d'un streum: je prends le premier truc
				planning.clear();
				planning.add(new SimpleLootingAction(this,cell.getLoot().get(0)));
			}else{
				// cas du joueur
				if (cell.getLoot().size() == 1){
					// un seul objet: je tente de le chopper direct
					if (StuffManager.getInstance().attributeSlot(cell.getLoot().get(0).type) != '!'){
						planning.clear();
						planning.add(new SimpleLootingAction(this,cell.getLoot().get(0)));
					}else{
						Logger.getInstance().addLog("player inventory full!");
					}
				}else{
					// cas du joueur face à plusieurs objets
					Logger.getInstance().addLog("Choose your loot");
					GameStateManager.getInstance().enterState(ActionState.LOOTING);
				}
			}
		}else{
			Logger.getInstance().addLog("nothing to pick!");
		}
	}
	
	public boolean resolveTaking(Stuff stuff){
		boolean success = false;
		if (stuff.isDropped()){
			if (cell.level() == stuff.level()){
				if (StuffManager.getInstance().getSlot(stuff) == StuffManager.NULL_SLOT){
					StuffManager.getInstance().attributeSlot(stuff.type);
				}
				if (StuffManager.getInstance().getSlot(stuff) != StuffManager.NULL_SLOT){
					inventory.add(stuff);
					stuff.notifyAsPicked(this);
					Logger.getInstance().addLog("picked "+StuffManager.getInstance().getSlot(stuff)+" "+stuff.type);
					success = true;
				}else{
					Logger.getInstance().addLog("cannot loot this: no slot available!");
				}

			}else{
				Logger.getInstance().addLog("cannot loot this: too far!");
			}
		}else{
			Logger.getInstance().addLog("cannot loot this: not dropped!");
		}
		return success;
	}

	public List<Stuff> getInventory() {
		return inventory;
	}
	
	public List<Stuff> getCurrentLoot(){
		return cell.getLoot();	
	}
	
	
	public void planLootingMulti(){
		planning.clear();
		planning.add(new MultiLootingAction(this,StuffManager.getInstance().getSelectedLoot()));
		
	}
	
}
