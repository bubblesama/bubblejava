package com.bb.catane;

import java.awt.image.VolatileImage;
import java.util.Scanner;

import com.bb.catane.control.Action;
import com.bb.catane.control.ActionType;
import com.bb.catane.control.annotation.ActionLineParser;
import com.bb.catane.model.Colony;
import com.bb.catane.model.Game;
import com.bb.catane.model.PlayerType;

public class CataneLauncher {


	//	public static void main(String[] args) {
	//		Game game = new Game(3);
	//		PlayerType firstPlayer = game.getCurrentPlayer();
	//		List<ActionType> actions = game.getListAction(game.getPlayer(firstPlayer));
	//		for (ActionType action : actions){
	//			System.out.println("[GameLauncher#main] action available 1 for "+firstPlayer+"="+action);
	//		}
	//
	//		try {
	//			game.placeFreeColony(game.getPlayer(PlayerType.BLUE), game.getVertex(10));
	//			actions = game.getListAction(game.getPlayer(PlayerType.BLUE));
	//			for (ActionType action : actions){
	//				System.out.println("[GameLauncher#main] action available 2="+action);
	//			}
	//			game.placeFreeRoad(game.getPlayer(PlayerType.BLUE), game.getSide(12));
	//			actions = game.getListAction(game.getPlayer(PlayerType.RED));
	//			for (ActionType action : actions){
	//				System.out.println("[GameLauncher#main] action available 3="+action);
	//			}
	//		} catch (ImpossibleActionException e) {
	//			e.printStackTrace();
	//		}
	//		
	//	}



	public static void main(String[] args) {
		Game game = new Game(3);
		Scanner scanner = new Scanner(System.in);
		System.out.print("> ");
		while (true){
			String commandLine = scanner.nextLine();
			if (commandLine != null && !commandLine.equals("")){
				try {
					if (commandLine.trim().equals("quit")){
						break;
					}else if(commandLine.trim().equals("infos")){
						for (PlayerType playerType:game.getPlayers()){
							System.out.println(playerType.toString());
							for (Colony colony : game.getPlayer(playerType).colonies){
								System.out.println("colony "+colony.type.toString()+" "+colony.place.name);
							}
						}
					}else{
						System.out.println(commandLine);
						Action action = ActionLineParser.getInstance().parseCommandLine(commandLine);
						action.resolve(game);
					}
				} catch (Exception e) {
					System.out.println("error:"+e.getClass().toString()+" "+e.getMessage());
					//e.printStackTrace();
				}
			}
			String actions = "";
			for (ActionType action: game.getActionList(game.getPlayer(game.getCurrentPlayer()))){
				actions += action+" ";
			}
			System.out.println("player: "+game.getCurrentPlayer()+" actions:["+actions+"]");
			System.out.print("> ");
		}




	}


}
