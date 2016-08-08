package com.bbsama.krole.view;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;

import com.bubblebob.tool.font.SlickFont;
import com.bubblebob.tool.font.SlickText;

public class Logger {

	private final static int LOG_SIZE = 30;
	private final static int DISPLAYED_LOG = 4;
	private List<String> logs;

	//slick et rendu visuel
	private SlickFont font;
	private SlickText fullVisibleLog;
	
	private static Logger instance;
	
	private Logger(){
		this.logs = new ArrayList<String>();
		this.font = SlickFont.getFont("assets/alphabet_white.png","assets/numbers_white.png",2);
		this.fullVisibleLog = new SlickText("Welcome, Player ", font, 19, 4, 1000, 1000, 1);
	}
	
	public synchronized static Logger getInstance(){
		if (instance == null){
			instance = new Logger();
		}
		return instance;
	}
	
	public void addLog(String log){
		if (logs.size()>LOG_SIZE){
			logs.remove(0);
		}
		logs.add(log);
		List<String> displayedLog = new ArrayList<String>();
//		for (int i=0;(i<DISPLAYED_LOG) && (logs.size()-i-1>=0);i++){
//			displayedLog.add(logs.get(logs.size()-i-1));
//		}
		for (int i=Math.max(0, logs.size()-DISPLAYED_LOG);i<logs.size();i++){
			displayedLog.add(logs.get(i));
		}
		
		this.fullVisibleLog.modifyText(displayedLog);
	}

	public void render(Graphics g){
		fullVisibleLog.setGraphics(g);
		fullVisibleLog.paint();
	}
	
	
}
