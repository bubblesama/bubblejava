package com.bubblebob.tool.server.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.bubblebob.tool.server.SocketServer;

public abstract class AbstractServerCom implements Runnable, ServerCom{

	private Socket socket;
	private PrintWriter writer;
	public SocketServer server;
	private int id;
	
	public AbstractServerCom(Socket socket, SocketServer server) {
		super();
		this.socket = socket;
		this.server = server;
		this.id = server.register(this);
	}

	
	public void run() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()) );
			this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			boolean alive = true;
			while (alive) {
				String commandLine = reader.readLine();
				alive = readCommand(commandLine);
			}
			
		} 
		catch (IOException e) {e.printStackTrace();}
		finally {
			server.unregister(id);
			try {
				writer.close();
				reader.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean readCommand(String commandLine){
		System.out.println("ServerComThread#readCommand IN commandLine="+commandLine);
		return treatCommand(commandLine);
	}
	
	public abstract boolean treatCommand(String commandLine);
	
	public void sendToClient(String msg){
		writer.println(msg);
	}
	
	
}
