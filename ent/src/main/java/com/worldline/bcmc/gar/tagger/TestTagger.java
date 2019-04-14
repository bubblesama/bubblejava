package com.worldline.bcmc.gar.tagger;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Test de taggage d'élements avec manimpluation DOM
 * @author a163661
 *
 */
public class TestTagger {

    public static void addIgnoreAttribute() {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {

            final DocumentBuilder builder = factory.newDocumentBuilder();
            String fileName = "C:\\Utilisateurs\\a163661\\Desktop\\GAR\\rentrée\\tagger\\U3_GAR-ENT_Complet_20190111_230002_Enseignant_0000.xml";

            XPathFactory xpf = XPathFactory.newInstance();
            XPath path = xpf.newXPath();

            final Document document = builder.parse(new File(fileName));
            lightLog("version : " + document.getXmlVersion());
            lightLog("encodage : " + document.getXmlEncoding());      

            //check xpath des enseignants
            String enseignantXPathExpression = "//*[local-name()='GAREnseignant']";
            path.compile(enseignantXPathExpression);
            NodeList enseignantNodesList = (NodeList)path.evaluate(enseignantXPathExpression, document.getDocumentElement(), XPathConstants.NODESET);
            lightLog("enseignants:" +enseignantNodesList.getLength());

            //check xpath d'un enseignant
            String singleEnseignantXPathExpression = "//*[local-name()='GAREnseignant'][descendant::*[local-name()='GARPersonNom'][. ='GUISOL']]";
            path.compile(singleEnseignantXPathExpression);
            NodeList singleEnseignantNodesList = (NodeList)path.evaluate(singleEnseignantXPathExpression, document.getDocumentElement(), XPathConstants.NODESET);
            lightLog("enseignant solo:" +singleEnseignantNodesList.getLength());

            if (singleEnseignantNodesList.getLength() == 1){
                lightLog("enseignant trouvé");
                Node singleEnseignantNode = singleEnseignantNodesList.item(0);
                for (int i=0;i<singleEnseignantNode.getChildNodes().getLength();i++) {
                    Node analysedNode = singleEnseignantNode.getChildNodes().item(i);
                    lightLog(analysedNode.getNodeName()+"/"+analysedNode.getNodeValue()+"/"+analysedNode.getTextContent());
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        addIgnoreAttribute();
    }

    public static void lightLog(String log) {
        System.out.println(System.currentTimeMillis()+" "+log);
    }


}
