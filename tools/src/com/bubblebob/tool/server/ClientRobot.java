package com.bubblebob.tool.server;

public class ClientRobot {

	
	
	public static void main(String[] args) {
		SocketClient client = new SocketClient();
		client.writer.println("toto");
		client.writer.println("tutu");
	}
	
	
}
