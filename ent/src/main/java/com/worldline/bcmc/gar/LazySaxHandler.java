package com.worldline.bcmc.gar;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Exemple par défaut pour connaître les méthodes à surcharger et avoir une méthode centrale de log des erreurs
 * @author a163661
 *
 */
public abstract class LazySaxHandler extends DefaultHandler{
    
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public void startElement(String namespaceURI, String localName, String qName,  Attributes atts) throws SAXException {}
    public void endElement(String uri, String localName, String qName) {}
    public void characters(char[] ch, int start, int length){}
    public void startDocument() throws SAXException {}
    public void endDocument() throws SAXException {}
    
    public void logConsistencyError(String consistencyErrorLog) {
       System.out.println(""+fileName+": "+consistencyErrorLog);
    }
    
}
