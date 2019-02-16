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
public class ShallowParser extends DefaultHandler{


	// simple parser: 828 ms
	// + listing and counting tags: 907ms
	// + listing id and names for items and categories: 1353ms
	
	private long startTime;

	private long startsCount = 0;
	private long endsCount = 0;
	private long charsCount = 0;

	private Map<String,Integer> countsByTag = new HashMap<String,Integer>();

	private Map<String,Item> itemsById = new HashMap<String,Item>();
	private Map<String,Category> categoriesById = new HashMap<String,Category>();
	private Map<String,Person> personsById = new HashMap<String,Person>();

	private boolean isParsingName = false;
	private Item currentItem;
	private Category currentCategory;
	private Person currentPerson;

	public void startElement(String namespaceURI, String localName, String qName,  Attributes atts) throws SAXException {
		startsCount++;
		countsByTag.put(localName, countsByTag.containsKey(localName)?countsByTag.get(localName)+1:1);
		if ("name".equals(localName)){
			isParsingName = true;
		}else if ("item".equals(localName)){
			currentItem = new Item();
			//id
			String id = atts.getValue("id");
			if (id != null) {
				currentItem.id = id;
			}else {
				//CC#2 existence item.id
				System.out.println("no id for item!");
			}
		}else if ("category".equals(localName)) {
			currentCategory = new Category();
			//id
			String id = atts.getValue("id");
			if (id != null) {
				currentCategory.id = id;
			}else {
				//CC#3 existence category.id
				System.out.println("no id for category!");
			}
		}else if ("person".equals(localName)) {
			currentPerson = new Person();
			//id
			String id = atts.getValue("id");
			if (id != null) {
				currentPerson.id = id;
			}else {
				//CC#5 existence person.id
				System.out.println("no id for currentPerson!");
			}
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
		}else if ("category".equals(localName)){
			if (categoriesById.containsKey(currentCategory.id)) {
				//CC#4 unicité category.id
				System.out.println("category with same id! "+currentCategory.id);
			}else {
				categoriesById.put(currentCategory.id, currentCategory);
				currentCategory =null;
			}
		}else if ("person".equals(localName)){
			if (personsById.containsKey(currentPerson.id)) {
				//CC#6 unicité person.id
				System.out.println("person with same id! "+currentPerson.id);
			}else {
				personsById.put(currentPerson.id, currentPerson);
				currentPerson =null;
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
			}else if (currentCategory != null) {
				currentCategory.name = String.valueOf(ch);
			}else if (currentPerson != null) {
				currentPerson.name = String.valueOf(ch);
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
		//categories
		System.out.println("categories: found "+categoriesById.size());
		//persons
		System.out.println("persons: found "+personsById.size());
		System.out.println("document parsed: duration="+duration+"ms starts="+startsCount+" ends="+endsCount+" charsCounts="+charsCount);
	}

	public static void main(String[] args) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser;
		try {
			saxParser = spf.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(new ShallowParser());
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




