package com.worldline.bcmc.gar;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Pour le fichier enseignants
 * @author A163661
 *
 */
public class EnseignantsControlHandler extends LazySaxHandler{

    private EntContext context;

    private  EnseignantParsingStage stage;

    public enum  EnseignantParsingStage{
        // passe sur  GAREnseignant
        FIRST,
        // passe sur GARPersonMEF
        SECOND,
        //contrôle tout le fichier en une passe, en espérant la cohérence d'ordre des définitions d'éléments
        FULL
    }

    //format
    private static final String LOCALNAME_GAREnseignant = "GAREnseignant";
    private static final String LOCALNAME_GARPersonIdentifiant = "GARPersonIdentifiant";
    private static final String LOCALNAME_GARPersonEtab = "GARPersonEtab";
    private static final String LOCALNAME_GARPersonMEF = "GARPersonMEF";
    private static final String LOCALNAME_GARStructureUAI = "GARStructureUAI";
    private static final String LOCALNAME_GARMEFCode= "GARMEFCode";

    //tampon
    private StringBuilder charactersBuilder = null;
    private String currentGpid = null;
    // oui etab et uai c'est pareil mais pas au même endroit >:X
    //liste des établissements d'un enseignant
    private List<String> currentEtabs = null;
    private List<String> currentUais = null;
    private String currentUai = null;
    private String currentMefCode = null;


    public EnseignantsControlHandler(EntContext context, EnseignantParsingStage stage) {
        super();
        this.stage = stage;
        this.context = context;
    }

    public EnseignantsControlHandler(EntContext context) {
        super();
        this.stage = EnseignantParsingStage.FULL;
        this.context = context;
    }


    public void startElement(String namespaceURI, String localName, String qName,  Attributes atts) throws SAXException {
        //on reset le parsing sur les éléments intéressants
        //éléments branches
        if (LOCALNAME_GAREnseignant.equals(localName)) {
            this.currentEtabs = new ArrayList<String>();
            this.currentUais = new ArrayList<String>();
        }
        //éléments feuilles
        else if (LOCALNAME_GARPersonIdentifiant.equals(localName)){
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GARPersonEtab.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GARStructureUAI.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GARMEFCode.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }
        //TODO parser tous les éléments
    }

    public void endElement(String uri, String localName, String qName) {
    	boolean ok = false;
        //éléments branches
        if (LOCALNAME_GAREnseignant.equals(localName)){
            if (stage != EnseignantParsingStage.SECOND) {
                context.countEnseignant();
                context.countIdentite();
                if (currentGpid == null) {
                    //TODO pas de GARPersonId
                }else if(currentUais == null|| currentUais.isEmpty()){
                    //TODO pas d'UAI de profil (GARPersonProfils> GARStructureUAI)
                }else if(currentEtabs == null || currentEtabs.isEmpty()){
                    //TODO pas d'établissement (GARPersonEtab)
                }else  if (context.containsGarPersonneId(currentGpid)) {
                    logConsistencyError("Impossible de créer le <GAREnseignant> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" car un utilisateur avec cet identifiant existe déjà dans le GAR pour le même projet ENT");
                }else {
                    boolean allKnownEtabs = true;
                    for (String uai: currentEtabs) {
                        if (!context.containsUai(uai)) {
                            logConsistencyError("Impossible de créer le <GAREnseignant> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" et le <GARPersonEtab> "+uai+" car l'établissement GARStructureUAI n'existe pas dans le GAR");
                            allKnownEtabs = false;
                        }
                    }
                    if (allKnownEtabs) {
                        boolean allKnowUais = true;
                        for (String uai: currentUais) {
                            if (!context.containsUai(uai)) {
                                logConsistencyError("Impossible de créer le <GAREnseignant> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+", le <GARPersonProfil> GARPersonProfil et le <GARStructureUAI> "+uai+" car il n'existe aucun établissement avec cet UAI dans le GAR");
                                allKnowUais = false;
                            }
                        }
                        if (allKnowUais) {
                            //creation des données de l'enseignant
                            context.createGpid(currentGpid);
                            context.createEnseignantGpid(currentGpid);
                            currentEtabs.forEach((uai)->{
                                context.countEnseignantUaiLink();
                                context.addEnseignantGpidToEtab(uai, currentGpid);
                                context.addEnseignantGpidToProfileUai(uai, currentGpid);
                            });
                        }
                    }
                }
            }
            //clean de fin
            //pas de contrôle d'UAI ici: on a des cas de multi-UAI
            this.currentGpid = null;
            this.currentUais = null;
            this.currentEtabs = null;
        }else if (LOCALNAME_GARPersonMEF.equals(localName)){
            if (stage != EnseignantParsingStage.FIRST) {
                if (currentGpid == null) {
                    //TODO pas de GARPersonIdentifiant
                }else if(currentUai == null){
                    //TODO pas de structure de rattachement (GARStructureUAI)
                }else if(currentMefCode == null){
                    //TODO pas de code MEF (GARMEFCode)
                }else if(!context.containsUai(currentUai)) {
                    logConsistencyError("Impossible de créer le <GARPersonMEF> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+", le <GARMEFCode> "+currentMefCode+" et l'établissement <GARStructureUAI> "+currentUai+" car il n'existe aucun établissement avec cet UAI dans le GAR");
                }else if(!context.uaiContainsMefCode(currentUai,currentMefCode)) {
                    logConsistencyError("CUSTOM Impossible de créer le <GARPersonMEF> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" car le <GARMEFCode> "+currentMefCode+" n'existe pour l'établissement <GARStructureUAI> "+currentUai);
                }else if (!context.uaiContainsEnseignantGpid(currentUai, currentGpid)){
                    logConsistencyError("CUSTOM Impossible de créer le <GARPersonMEF> avec le <GARMEFCode> "+currentMefCode+" pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" car cet utilisateur n'existe pas pour l'établissement <GARStructureUAI> "+currentUai);
                }else {
                    context.countEnseignantUaiMefLink();
                    context.addMefCodeToEnseignantGpidToUai(currentUai, currentGpid, currentMefCode);
                    ok = true;
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
