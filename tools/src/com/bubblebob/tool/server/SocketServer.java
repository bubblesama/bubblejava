package com.bubblebob.tool.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.bubblebob.tool.server.server.EchoServerCom;
import com.bubblebob.tool.server.server.ServerCom;

public class SocketServer {

	private Map<Integer, ServerCom> coms;
	
	public SocketServer(){
		this.coms = new HashMap<Integer,ServerCom>();
	}
	
	
	static final int port = 9000;
	public static void main(String[] args) throws Exception {
		
		SocketServer server = new SocketServer();
		ServerSocket s = new ServerSocket(port);
		while (true) {
			Socket socket = s.accept();
			EchoServerCom com = new EchoServerCom(socket,server);
			Thread comThread = new Thread(com);
			comThread.start();
		}
	}
	
	public int register(ServerCom com){
		int id = new Random().nextInt();
		coms.put(id, com);
		return id;
	}
	
	public void unregister(int id){
		coms.remove(id);
	}
	
	public void broadcast(String msg){
		for (ServerCom com: coms.values()){
			com.sendToClient(msg);
		}
	}
	
	
	

}
