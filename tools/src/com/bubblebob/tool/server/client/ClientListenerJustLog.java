package com.bubblebob.tool.server.client;

import java.io.BufferedReader;

public class ClientListenerJustLog extends AbstractClientListener{

	public ClientListenerJustLog(BufferedReader reader) {
		super(reader);
	}

	public void treatLine(String line) {
		System.out.println("ClientListenerJustLog#treatLine line="+line);
	}



}
