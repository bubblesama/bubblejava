package com.bubblebob.tool.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public abstract class AbstractClientListener implements Runnable{

	private Socket socket;
	private BufferedReader reader;
	private boolean alive = true;
	
	public AbstractClientListener(BufferedReader reader){
		this.reader = reader;
	}

	public abstract void treatLine(String line);
	
	public void run() {
		while (alive){
			try {
				String line = reader.readLine();
				treatLine(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
