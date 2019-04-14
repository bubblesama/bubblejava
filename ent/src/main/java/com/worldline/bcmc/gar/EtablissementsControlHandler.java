package com.worldline.bcmc.gar;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Parse le fichier établissement
 * @author A163661
 * TODO: gestion de plusieurs fichiers établissement
 */
public class EtablissementsControlHandler extends LazySaxHandler{

	private EntContext context;

	private EtabParsingStage stage;

	public enum EtabParsingStage{
		// passe sur  GAREtab
		FIRST,
		// passe sur GARMEF et GARMatiere
		SECOND,
		//contrôle tout le fichier en une passe, en espérant la cohérence d'ordre des définitions d'éléments
		FULL
	}

	//tampon
	private StringBuilder charactersBuilder = new StringBuilder();
	private String currentUai = null;
	private String currentMefCode = null;
	private String currentMatiereCode = null;

	//format
	private static final String LOCALNAME_GAREtab = "GAREtab";
	private static final String LOCALNAME_GARStructureUAI = "GARStructureUAI";
	private static final String LOCALNAME_GARMEF = "GARMEF";
	private static final String LOCALNAME_GARMEFCode = "GARMEFCode";
	private static final String LOCALNAME_GARMatiere = "GARMatiere";
	private static final String LOCALNAME_GARMatiereCode = "GARMatiereCode";

	public EtablissementsControlHandler(EntContext context) {
		super();
		this.stage = EtabParsingStage.FULL;
		this.context = context;
	}

	public EtablissementsControlHandler(EntContext context, EtabParsingStage stage) {
		super();
		this.stage = stage;
		this.context = context;
	}

	public void startElement(String namespaceURI, String localName, String qName,  Attributes atts) throws SAXException {
		//on reset le parsing sur les éléments intéressants
		if (LOCALNAME_GARStructureUAI.equals(localName)){
			this.charactersBuilder = new StringBuilder();
		}else if (LOCALNAME_GARMEFCode.equals(localName)){
			this.charactersBuilder = new StringBuilder();
		}else if (LOCALNAME_GARMatiereCode.equals(localName)){
			this.charactersBuilder = new StringBuilder();
		}
		//TODO parser toutes les feuilles
	}

	public void endElement(String uri, String localName, String qName) {
		boolean ok = false;
		//elements branches
		if (LOCALNAME_GAREtab.equals(localName)){
			if (stage != EtabParsingStage.SECOND) {
				context.countEtablissement();
				//TODO récupération complète des paramètres
				if (currentUai == null) {
					//TODO erreur pas d'UAI
					logConsistencyError("etablissement: pas d'UAI");
				}else {
					if (context.containsUai(currentUai)) {
						logConsistencyError("Impossible de créer le <GAREtab> pour l'établissement avec le <GARStructureUAI> "+currentUai+" car un établissement avec cet UAI existe déjà dans le GAR pour le même projet ENT");
					}else {
						context.createUai(currentUai);
						ok = true;
					}
				}
				if (!ok) {
					context.addKoUai(currentUai);
				}
			}
			//clean de fin
			this.currentUai = null;
		}else if (LOCALNAME_GARMEF.equals(localName)){
			if (stage != EtabParsingStage.FIRST) {
				context.countMef();
				if (currentMefCode == null) {
					//TODO erreur pas de code MEF
				}else {
					if (currentUai == null) {
						//TODO erreur pas d'UAI
					}else {
						if (context.containsUai(currentUai)) {
							if (context.uaiContainsMefCode(currentUai, currentMefCode)) {
								//TODO erreur même combinaison uai/code
							}else {
								context.addMefCodeToUai(currentUai, currentMefCode);
								ok = true;
							}
						}else {
							logConsistencyError("Impossible de créer des <GARMEF> pour l'établissement avec le <GARStructureUAI> "+currentUai+" car il n'existe aucun établissement avec cet UAI dans le GAR");
						}
					}
				} 
				if (!ok) {
					context.addKoUai(currentUai);
				}
			}
			//clean de fin
			this.currentUai = null;
			this.currentMefCode = null;
		}else if (LOCALNAME_GARMatiere.equals(localName)){
			if (stage != EtabParsingStage.FIRST) {
				context.countMatiere();
				if (currentMatiereCode == null) {
					logConsistencyError("matiere: pas de code matiere");
				}else if (currentUai == null) {
					logConsistencyError("matiere: pas d'UAI");
				}else {
					if (context.containsUai(currentUai)) {
						if (context.uaiContainsMatiereCode(currentUai, currentMatiereCode)) {
							//TODO erreur même combinaison uai/code matiere
							logConsistencyError("matiere: même combinaison uai/code matiere (uai="+currentUai+" codeMatiere="+this.currentMatiereCode);
						}else {
							context.addMatiereCodeToUai(currentUai, currentMatiereCode);
							ok = true;
						}
					}else {
						logConsistencyError("Impossible de créer des <GARMatiere> pour l'établissement avec le <GARStructureUAI> "+currentUai+" car il n'existe aucun établissement avec cet UAI dans le GAR");
					}
				}
				if (!ok) {
					context.addKoUai(currentUai);
				}
			}
			//clean de fin
			this.currentUai = null;
			this.currentMatiereCode = null;
		}
		// elements simples - feuilles
		else if (LOCALNAME_GARStructureUAI.equals(localName)){
			this.currentUai = charactersBuilder.toString();
			charactersBuilder = new StringBuilder();
		}else if (LOCALNAME_GARMEFCode.equals(localName)){
			this.currentMefCode = charactersBuilder.toString();
			charactersBuilder = new StringBuilder();
		}else if (LOCALNAME_GARMatiereCode.equals(localName)){
			this.currentMatiereCode = charactersBuilder.toString();
			charactersBuilder = new StringBuilder();
		}
	}

	public void characters(char[] ch, int start, int length){
		charactersBuilder.append(new String(ch,start,length).trim());
	}

	public void startDocument() throws SAXException {}

	public void endDocument() throws SAXException {}

}
