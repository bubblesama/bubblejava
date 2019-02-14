package com.bb.sax;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

//file found on http://www.ins.cwi.nl/projects/xmark/Assets/standard.gz
public class MainSampleParser extends DefaultHandler{

	
	// simple parser 828 ms
	// + listing and counting tags 907ms: 
	private long startTime;

	private long startsCount = 0;
	private long endsCount = 0;
	private long charsCount = 0;
	
	private Map<String,Integer> countsByTag = new HashMap<String,Integer>();
	
	private Map<String,Item> itemsById = new HashMap<String,Item>();
	
	private Item currentItem;
	private boolean isParsingName = false;
	
	
	
	public void startElement(String namespaceURI, String localName, String qName,  Attributes atts) throws SAXException {
		startsCount++;
		countsByTag.put(localName, countsByTag.containsKey(localName)?countsByTag.get(localName)+1:1);
		if ("item".equals(localName)){
			currentItem = new Item();
			//id
			String id = atts.getValue("id");
			if (id != null) {
				currentItem.id = id;
			}else {
				//CC#2 existence item.id
				System.out.println("no id for item!");
			}
		}
		if ("name".equals(localName)){
			isParsingName = true;
		}
	}
	
	public void endElement(String uri, String localName, String qName) {
		endsCount++;
		//item
		if ("item".equals(localName)){
			if (itemsById.containsKey(currentItem.id)) {
				//CC#1 unicité item.id
				System.out.println("item with same id! "+currentItem.id);
			}else {
				itemsById.put(currentItem.id, currentItem);
				currentItem =null;
			}
		}
		//name
		if ("name".equals(localName)){
			isParsingName = false;
		}
	}

	public void characters(char[] ch, int start, int length){
		charsCount++;
		if (isParsingName) {
			// item
			if (currentItem != null) {
				currentItem.name = String.valueOf(ch);
			}
		}
	}
	
	public void startDocument() throws SAXException {
		startTime = System.currentTimeMillis();
	}
	
	public void endDocument() throws SAXException {
		long duration = System.currentTimeMillis()-startTime;
		//listing counted tags
		countsByTag.forEach((tag, count)->{
			System.out.println("tags: tag: "+tag+" found "+count+" times.");
		});
		//items
		System.out.println("items: found "+itemsById.size());
		System.out.println("document parsed: duration="+duration+"ms starts="+startsCount+" ends="+endsCount+" charsCounts="+charsCount);
	}

	public static void main(String[] args) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser;
		try {
			saxParser = spf.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(new MainSampleParser());
			xmlReader.parse("sample/standard.xml");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}




