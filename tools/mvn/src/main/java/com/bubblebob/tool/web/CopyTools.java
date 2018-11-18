package com.bubblebob.tool.web;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;



/**
 * Cette classe procure des outils pour la copie sur le net.
 * @author bubblebob
 */
public class CopyTools {



	/**
	 * 
	 * @param fromFileUrl
	 * @param toFileFullName
	 * @return
	 */
	public static boolean copyFile(String fromFileUrl, String toFileFullName){
		return copyFile(fromFileUrl,toFileFullName,false);
	}
	
	/**
	 * 
	 * @param fromFileUrl
	 * @param toFileFullName
	 * @return
	 */
	public static boolean copyFile(String fromFileUrl, String toFileFullName, boolean createDirs){
		if (createDirs){
			String folderFullPath = getFolderFromPath(toFileFullName);
			File fullFolder = new File(folderFullPath);
			if (!fullFolder.exists()){
				fullFolder.mkdirs();
			}
		}
		File file = new File(toFileFullName);
		boolean empty = true;
		try {
			URL location = new URL(fromFileUrl);
			InputStream stream = location.openStream();
			OutputStream fileStream = new FileOutputStream(file);
			byte[] buffer = new byte[1];
			while(stream.read(buffer)!= -1){
				fileStream.write(buffer);
				buffer = new byte[1];
				empty = false;
			}
			fileStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return empty;
	}
	
	
	private static String getFolderFromPath(String path){
		String[] fields = path.split("/");
		String result = "";
		for (int i=0; i<fields.length-1; i++){
			result += fields[i]+"/";
		}
		return result;
	}
	

	/**
	 * Renvoie la chaine de caractere correspondant au fichier (texte, typiquement du HTML)  l'url donnee
	 * @param url
	 * @return
	 */
	public static String getStringFromUrl(String url){
		String result = "";
		System.out.println("[CopyTools#getStringFromUrl] IN url="+url);
		try {
			URL anUrl = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(anUrl.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null){
				result += inputLine+"\n";
			}
			in.close();
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e){
			return null;
		}
//		System.out.println("result");
		return result;

	}




}
