package com.bubblebob.tool.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.bubblebob.tool.server.client.AbstractClientListener;
import com.bubblebob.tool.server.client.ClientListenerJustLog;
import com.bubblebob.tool.server.server.EchoServerCom;

public class SocketClient {

	public PrintWriter writer;

	public SocketClient(){
		try {
			Socket socket = new Socket("localhost", port);
			BufferedReader reader = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			ClientListenerJustLog logger = new ClientListenerJustLog(reader);
			Thread loggerThread = new Thread(logger);
			loggerThread.start();
		} 
		catch (UnknownHostException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
	}

	static final int port = 9000;

}
