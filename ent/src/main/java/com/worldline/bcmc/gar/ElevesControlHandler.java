package com.worldline.bcmc.gar;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ElevesControlHandler extends LazySaxHandler{

    private EntContext context;

    private  EleveParsingStage stage;

    public enum  EleveParsingStage{
        // passe sur GAREleve
        FIRST,
        // passe sur GARPersonMEF
        SECOND,
        //contrôle tout le fichier en une passe, en espérant la cohérence d'ordre des définitions d'éléments
        FULL
    }

    //format
    //branches
    private static final String LOCALNAME_GAREleve = "GAREleve";
    private static final String LOCALNAME_GARPersonMEF ="GARPersonMEF";
    private static final String LOCALNAME_GAREleveEnseignement ="GAREleveEnseignement";
    //feuilles
    private static final String LOCALNAME_GARPersonIdentifiant = "GARPersonIdentifiant";
    private static final String LOCALNAME_GARPersonEtab = "GARPersonEtab";
    private static final String LOCALNAME_GARStructureUAI = "GARStructureUAI";
    private static final String LOCALNAME_GARMatiereCode= "GARMatiereCode";
    private static final String LOCALNAME_GARMEFCode= "GARMEFCode";

    //tampon
    private StringBuilder charactersBuilder = null;
    private String currentGpid = null;
    //liste des établissements d'un eleve
    private List<String> currentEtabs = null;
    private String currentUai = null;
    private List<String> currentUais = null;
    private String currentMatiereCode = null;
    private String currentMefCode = null;

    public ElevesControlHandler(EntContext context, EleveParsingStage stage) {
        super();
        this.stage = stage;
        this.context = context;
    }

    public ElevesControlHandler(EntContext context) {
        super();
        this.stage = EleveParsingStage.FULL;
        this.context = context;
    }


    public void startElement(String namespaceURI, String localName, String qName,  Attributes atts) throws SAXException {
        //on reset le parsing sur les éléments intéressants
        //éléments branches
        if (LOCALNAME_GAREleve.equals(localName)) {
            this.currentEtabs = new ArrayList<String>();
            this.currentUais = new ArrayList<String>();
        }
        //éléments feuilles
        else if (LOCALNAME_GARPersonIdentifiant.equals(localName)){
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GARPersonEtab.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GAREleveEnseignement.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GARStructureUAI.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GARMatiereCode.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GARMEFCode.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }
        //TODO parser tous les éléments
    }

    public void endElement(String uri, String localName, String qName) {
    	boolean ok = false;
        //éléments branches
        if (LOCALNAME_GAREleve.equals(localName)){
            if (stage != EleveParsingStage.SECOND) {
                context.countEleve();
                context.countIdentite();
                if (currentGpid == null) {
                    //TODO pas de GARPersonId
                }else if(currentEtabs == null || currentEtabs.isEmpty()){
                    logConsistencyError("NEW GAREleve "+currentGpid+" erreur: pas de GAREtab");
                }else if(currentUais == null || currentUais.isEmpty()){
                    logConsistencyError("NEW GAREleve "+currentGpid+" erreur: pas de GARStructureUAI");
                }else  if (context.containsGarPersonneId(currentGpid)) {
                    logConsistencyError("Impossible de créer le <GAREleve> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" car un utilisateur avec cet identifiant existe déjà dans le GAR pour le même projet ENT");
                }else {
                    boolean allKnownEtabs = true;
                    for (String uai: currentEtabs) {
                        if (!context.containsUai(uai)) {
                            logConsistencyError("CUSTOM Impossible de créer le <GAREleve> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" et le <GARPersonEtab> "+uai+" car l'établissement GARStructureUAI n'existe pas");
                            allKnownEtabs = false;
                        }
                    }
                    if (allKnownEtabs) {
                        boolean allKnowUais = true;
                        for (String uai: currentUais) {
                            if (!context.containsUai(uai)) {
                                logConsistencyError("Impossible de créer le <GAREleve> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+", le <GARPersonProfil> GARPersonProfil et le <GARStructureUAI> "+uai+" car il n'existe aucun établissement avec cet UAI dans le GAR");
                                allKnowUais = false;
                            }
                        }
                        if (allKnowUais) {
                            //creation des données de l'eleve
                            context.createGpid(currentGpid);
                            context.createEleveGpid(currentGpid);
                            currentUais.forEach((uai)->{
                                context.addEleveGpidToEtab(uai, currentGpid);
                                context.addEleveGpidToProfileUai(uai, currentGpid);
                            });
                        }
                    }
                }
            }
            //clean de fin
            // pas de contrôle des UAIs, en cas de multi-UAI
            this.currentGpid = null;
            this.currentUais = null;
            this.currentEtabs = null;
        }else if (LOCALNAME_GAREleveEnseignement.equals(localName)){
            if (stage != EleveParsingStage.FIRST) {
                if (currentGpid == null) {
                    //TODO pas de GARPersonIdentifiant
                }else if(currentUai == null){
                    //TODO pas de structure de rattachement (GARStructureUAI)
                }else if(currentMatiereCode == null){
                    //TODO pas de code MEF (GARMatiereCode)
                }else if(!context.containsUai(currentUai)) {
                    logConsistencyError("CUSTOM Impossible de créer le <GAREleveEnseignement> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+", le <GARMatiereCode> "+currentMatiereCode+", le <GARPersonProfil> GARPersonProfil et le <GARStructureUAI> "+currentUai+" car il n'existe aucun établissement avec cet UAI dans le GAR");
                }else if(!context.uaiContainsMatiereCode(currentUai, currentMatiereCode)) {
                    logConsistencyError("CUSTOM Impossible de créer le <GAREleveEnseignement> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+", le <GARMatiereCode> "+currentMatiereCode+" car cette matière n'existe pas pour l'établissement <GARStructureUAI> "+currentUai);
                }else if (!context.uaiContainsEleveGpid(currentUai, currentGpid)){
                    logConsistencyError("CUSTOM Impossible de créer le <GAREleveEnseignement> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+", le <GARMatiereCode> "+currentMatiereCode+" car cet utilisateur n'existe pas pour l'établissement <GARStructureUAI> "+currentUai);
                } else {
                    context.addMatiereCodeToEleveGpidToUai(currentUai, currentGpid, currentMatiereCode);
                    ok  = true;
                }
                if (!ok) {
    				context.addKoUai(currentUai);
    			}
            }
            //clean de fin
            this.currentUai = null;
            this.currentMatiereCode = null;
            this.currentGpid = null;
        }else if (LOCALNAME_GARPersonMEF.equals(localName)){
            if (stage != EleveParsingStage.FIRST) {
                if (currentGpid == null) {
                    logConsistencyError("CUSTOM pas de GARPersonIdentifiant fourni lors de la création d'un lien UAI eleve MEF (context: uai="+currentUai+" gpid="+currentGpid+" mef code="+currentMefCode);
                }else if(currentUai == null){
                    logConsistencyError("CUSTOM pas de GARStructureUAI fourni lors de la création d'un lien UAI eleve MEF (context: uai="+currentUai+" gpid="+currentGpid+" mef code="+currentMefCode);
                }else if(currentMefCode == null){
                    logConsistencyError("CUSTOM pas de GARMEFCode fourni lors de la création d'un lien UAI eleve MEF (context: uai="+currentUai+" gpid="+currentGpid+" mef code="+currentMefCode);
                }else if(!context.containsUai(currentUai)) {
                    logConsistencyError("Impossible de créer le <GARPersonMEF> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+", le <GARMEFCode> "+currentMefCode+" et l'établissement <GARStructureUAI> "+currentUai+" car il n'existe aucun établissement avec cet UAI dans le GAR");
                }else if(!context.uaiContainsMefCode(currentUai,currentMefCode)) {
                    logConsistencyError("Impossible d’ajouter le <GARMEF> de <GARMEFCode> "+currentMefCode+" au <GARPersonMEF> de <GARPersonIdentifiant> "+currentGpid+" pour l’établissement de <GARStructureUAI> "+currentUai+" car ce <GARMEFCode> n’existe pas");
                }else if (!context.uaiContainsEleveGpid(currentUai, currentGpid)){
                    logConsistencyError("CUSTOM Impossible de créer le <GARPersonMEF> de <GARMEFCode> "+currentMefCode+" pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" car cet utilisateur n'existe pas pour l'établissement <GARStructureUAI> "+currentUai);
                }else {
                    context.addMefCodeToEleveGpidToUai(currentUai, currentGpid, currentMefCode);
                    ok  = true;
                }
                if (!ok) {
    				context.addKoUai(currentUai);
    			}
            }
            //clean de fin
            this.currentUai = null;
            this.currentMefCode = null;
            this.currentGpid = null;
        }
        //éléments feuilles
        else if (LOCALNAME_GARPersonIdentifiant.equals(localName)){
            this.currentGpid = charactersBuilder.toString();
            this.charactersBuilder = null;
        }else if(LOCALNAME_GARPersonEtab.equals(localName)) {
            this.currentEtabs.add(charactersBuilder.toString());
            this.charactersBuilder = null;
        }else if(LOCALNAME_GARStructureUAI.equals(localName)) {
            this.currentUai = charactersBuilder.toString();
            if (this.currentUais != null){
                this.currentUais.add(this.currentUai);
            }
            this.charactersBuilder = null;
        }else if(LOCALNAME_GARMatiereCode.equals(localName)) {
            this.currentMatiereCode = charactersBuilder.toString();
            this.charactersBuilder = null;
        }else if(LOCALNAME_GARMEFCode.equals(localName)) {
            this.currentMefCode = charactersBuilder.toString();
            this.charactersBuilder = null;
        }
    }

    public void characters(char[] ch, int start, int length){
        if (charactersBuilder != null) {
            charactersBuilder.append(new String(ch,start,length).trim());
        }
    }

    public void startDocument() throws SAXException {}

    public void endDocument() throws SAXException {}

}
