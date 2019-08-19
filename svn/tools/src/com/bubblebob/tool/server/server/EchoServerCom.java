package com.bubblebob.tool.server.server;

import java.net.Socket;

import com.bubblebob.tool.server.SocketServer;

public class EchoServerCom extends AbstractServerCom{

	public EchoServerCom(Socket socket, SocketServer server) {
		super(socket,server);
	}

	public boolean treatCommand(String commandLine) {
//		sendToClient("echo "+commandLine);
		server.broadcast("echo: "+commandLine);
		return true;
	}

}
