package com.bubblebob.tool.parsing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe abstraite proposant les traitements ligne par ligne de base d'un fichier texte
 * @author bubble
 *
 */
public abstract class AbstractParser {

	private String fileName;
	// l'indice de la ligne
	private int index;
	
	public AbstractParser(String fileName){
		this.fileName = fileName;
	}

	public abstract boolean initParsing() throws InvalidParsingFormatException, UnknownParsingException, InitParsingException;

	public abstract boolean readLine(String line) throws InvalidParsingFormatException, UnknownParsingException;

	public abstract boolean finalizeParsing() throws InvalidParsingFormatException, UnknownParsingException;

	public final void parse() throws InvalidParsingFormatException, UnknownParsingException, InitParsingException{
		boolean startParsingOk = false;
		BufferedReader mapFileReader = null;
		try {
			mapFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			if (mapFileReader != null){
				startParsingOk = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (startParsingOk){
			startParsingOk = initParsing();
		}
		if (!startParsingOk){
			throw new InitParsingException();
		}
		boolean readFileOk = true;
		try{
			index = 0;
			String line = mapFileReader.readLine();
			while (line != null) {
				index++;
				readFileOk = readFileOk && readLine(line);
				line = mapFileReader.readLine();
			}
		}catch (IOException e){
			e.printStackTrace();
			readFileOk = false;
			throw new UnknownParsingException();
		}
		if (!readFileOk){
			throw new InvalidParsingFormatException();
		}
		boolean finalizeOk = finalizeParsing();
		if (!finalizeOk){
			throw new UnknownParsingException();
		}
	}

	public int getIndex() {
		return index;
	}


}
