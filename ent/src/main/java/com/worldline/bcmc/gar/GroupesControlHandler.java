package com.worldline.bcmc.gar;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.worldline.bcmc.gar.EnseignantsControlHandler.EnseignantParsingStage;

public class GroupesControlHandler extends LazySaxHandler{

    private EntContext context;

    private GroupeParsingStage stage;

    public enum  GroupeParsingStage{
        // passe sur  GARGroupe pour les divisions
        FIRST,
        // passe sur  GARGroupe pour les groupes
        SECOND,
        // passe sur  GARGroupe pour les associations
        THIRD,
        //contrôle tout le fichier en une passe, en espérant la cohérence d'ordre des définitions d'éléments
        FULL
    }
    //format
    private static final String LOCALNAME_GARGroupe = "GARGroupe";
    private static final String LOCALNAME_GARGroupeCode = "GARGroupeCode";
    private static final String LOCALNAME_GARStructureUAI = "GARStructureUAI";
    private static final String LOCALNAME_GARGroupeStatut = "GARGroupeStatut";
    private static final String LOCALNAME_GARGroupeDivAppartenance = "GARGroupeDivAppartenance";
    private static final String LOCALNAME_GARPersonGroupe = "GARPersonGroupe";
    private static final String LOCALNAME_GARPersonIdentifiant = "GARPersonIdentifiant";
    private static final String LOCALNAME_GAREnsGroupeMatiere = "GAREnsGroupeMatiere";
    private static final String LOCALNAME_GARMatiereCode = "GARMatiereCode";
    private static final String LOCALNAME_GAREnsClasseMatiere = "GAREnsClasseMatiere";
    private static final String GROUPE_STATUT_DIVISION = "DIVISION";
    private static final String GROUPE_STATUT_GROUPE = "GROUPE";

    //tampon
    private StringBuilder charactersBuilder = null;
    private String currentGroupeCode;
    private String currentUai;
    private String currentGroupeStatut;
    private List<String> currentDivCodes;
    private String currentGpid;
    private List<String> currentMatiereCodes;

    public GroupesControlHandler(EntContext context) {
        super();
        this.context = context;
        this.stage = GroupeParsingStage.FULL;
    }

    public GroupesControlHandler(EntContext context, GroupeParsingStage stage) {
        super();
        this.context = context;
        this.stage = stage;
    }

    public void startElement(String namespaceURI, String localName, String qName,  Attributes atts) throws SAXException {
        //on reset le parsing sur les éléments intéressants
        //éléments branches
        if (LOCALNAME_GARGroupe.equals(localName)) {
            this.currentDivCodes = new ArrayList<String>();
        }else if (LOCALNAME_GAREnsClasseMatiere.equals(localName)) {
            this.currentMatiereCodes = new ArrayList<String>();
        }else if (LOCALNAME_GAREnsGroupeMatiere.equals(localName)) {
            this.currentMatiereCodes = new ArrayList<String>();
        }
        //éléments feuilles
        else if (LOCALNAME_GARGroupeCode.equals(localName)){
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GARStructureUAI.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GARGroupeStatut.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GARGroupeDivAppartenance.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GARPersonIdentifiant.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }else if(LOCALNAME_GARMatiereCode.equals(localName)) {
            this.charactersBuilder = new StringBuilder();
        }
        //TODO parser tous les éléments
    }

    public void endElement(String uri, String localName, String qName) {
    	boolean ok = false;
        //éléments branches
        if (LOCALNAME_GARGroupe.equals(localName)){
            if (stage != GroupeParsingStage.THIRD){
                if (stage != GroupeParsingStage.SECOND){
                    context.countGroupe();
                }
                if (this.currentGroupeCode == null) {
                    //TODO code groupe non fourni
                }else if (this.currentGroupeStatut == null) {
                    //TODO statut du groupe non fourni
                }else if (!this.currentGroupeStatut.equals(GROUPE_STATUT_DIVISION) && !(this.currentGroupeStatut.equals(GROUPE_STATUT_GROUPE))){
                    //TODO statut de groupe fourni mais incorrect
                }else if (this.currentUai == null) {
                    //TODO UAI non fourni pour le groupe
                }else if (!context.containsUai(currentUai)){
                    logConsistencyError("Impossible de créer le <GARGroupe> pour le groupe avec le <GARGroupeCode> "+currentGroupeCode+", le <GARGroupeLibelle> GARGroupeLibelle et l'établissement <GARStructureUAI> "+currentUai+" car il n'existe aucun établissement avec cet UAI dans le GAR");
                }else if (stage != GroupeParsingStage.SECOND //on ne contrôle pas les doublons de groupe à la deuxième passe
                        && context.uaiContainsGroupeCode(currentUai, currentGroupeCode)) {
                    logConsistencyError("Impossible de créer le <GARGroupe> pour le groupe (statut "+currentGroupeStatut+") avec le <GARGroupeCode> "+currentGroupeCode+", le <GARGroupeLibelle> GARGroupeLibelle et l'établissement <GARStructureUAI> "+currentUai+" car ce groupe existe déjà dans cet établissement");
                }else {
                    //TODO contrôle des divisions et groupes
                    if (this.currentGroupeStatut.equals(GROUPE_STATUT_DIVISION)) {
                        if  (stage == GroupeParsingStage.FULL || stage == GroupeParsingStage.FIRST) {
                            //TODO pas de groupe associé
                            if (!currentDivCodes.isEmpty()) {
                                //TODO appartenance à une  division pour une division
                            }else {
                                context.addDivisionCodeToUai(currentUai, currentGroupeCode);
                                context.addGroupeCodeToUai(currentUai,currentGroupeCode);
                                ok = true;
                            }
                        }
                    }else{
                        //cas d'un groupe
                        if  (stage == GroupeParsingStage.FULL || stage == GroupeParsingStage.SECOND) {
                            //contrôle d'existence des divisions
                            // boolean unknownDivision = currentDivCodes.stream().filter(divisionCode->!context.uaiContainsDivisionCode(currentUai,divisionCode)).findFirst().isPresent();
                            boolean unknownDivision = false;
                            for (String divisionCode: currentDivCodes) {
                                if (!context.uaiContainsDivisionCode(currentUai,divisionCode)){
                                    logConsistencyError("Impossible de créer le <GARGroupe> pour le groupe avec le <GARGroupeCode> "+currentGroupeCode+", le <GARGroupeLibelle> GARGroupeLibelle, la <GARGroupeDivAppartenance> "+divisionCode+" et l'établissement <GARStructureUAI> "+currentUai+" car la division d'appartenance n'existe pas");
                                    unknownDivision = true;
                                }
                            }
                            if (!unknownDivision) {
                                context.addGroupeCodeToUai(currentUai,currentGroupeCode);
                                currentDivCodes.forEach(divCode->{
                                    context.countUaiDivisionGroupeLink(); 
                                    context.addGroupeCodeToDivisionCodeToUai(currentUai, divCode, currentGroupeCode);
                                });
                                ok = true;
                            }
                        }
                    }
                }
                //clean de fin
//                if (!ok) {
//    				context.addKoUai(currentUai);
//    			}
                this.currentDivCodes = null;
                this.currentGroupeCode = null;
                this.currentGroupeStatut = null;
                this.currentUai = null;
            }
        }//check seulement quand les groupes ont été faits
        else if (LOCALNAME_GARPersonGroupe.equals(localName)) {
            if (stage == GroupeParsingStage.FULL || stage == GroupeParsingStage.THIRD){
                context.countUaiGroupePersonneLink();
                if (this.currentGroupeCode == null) {
                    //TODO code groupe non fourni
                }else if (this.currentUai == null) {
                    //TODO UAI non fourni pour le groupe
                }else if (this.currentGpid == null) {
                    //TODO GAR person id non fourni pour le groupe
                }else if (!context.containsUai(currentUai)){
                    logConsistencyError("CUSTOM Impossible de créer le <GARPersonGroupe> "+currentGroupeCode+" pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" car cet utilisateur n'existe pas pour l'établissement <GARStructureUAI> "+currentUai);
                }else if (!context.uaiContainsGroupeCode(currentUai, currentGroupeCode)) {
                    logConsistencyError("Impossible de créer le <GARPersonGroupe> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+", le <GARGroupeCode> "+currentGroupeCode+" et l'établissement <GARStructureUAI> "+currentUai+" car il n'existe aucun groupe avec ce code");
                }else if (!context.uaiContainsEleveGpid(currentUai, currentGpid)&&!context.uaiContainsEnseignantGpid(currentUai, currentGpid)){
                    logConsistencyError("Impossible de créer le <GARPersonGroupe> "+currentGroupeCode+" pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" car cet utilisateur n'existe pas pour l'établissement <GARStructureUAI> "+currentUai);
                } else if (context.uaiContainsGroupeCodeAndGpid(currentUai, currentGroupeCode, currentGpid)){
                    logConsistencyError("CUSTOM lien UAI groupe gpid existant: "+currentUai+" "+currentGroupeCode+" "+currentGpid);
                }else {
                    context.addGpidToGroupeCodeToUai(currentUai, currentGroupeCode, currentGpid);
                    ok = true;
                }
                if (!ok) {
    				context.addKoUai(currentUai);
    			}
            }
            //clean de fin
            this.currentUai = null;
            this.currentGpid = null;
            this.currentGroupeCode = null;
        }else if (LOCALNAME_GAREnsGroupeMatiere.equals(localName)) {
            if (stage == GroupeParsingStage.FULL || stage == GroupeParsingStage.THIRD){
                for(String matiereCode: currentMatiereCodes) {
                    context.countUaiEnseignantGroupeMatiereLink();
                }
                if (this.currentGroupeCode == null) {
                    //TODO code groupe non fourni
                }else if (this.currentUai == null) {
                    //TODO UAI non fourni
                }else if (this.currentGpid == null) {
                    //TODO GAR person id
                }else if (this.currentMatiereCodes == null || this.currentMatiereCodes.isEmpty()) {
                    //TODO codes matiere non fourni
                }else if (!context.containsUai(currentUai)){
                    logConsistencyError("Impossible de créer le <GAREnsGroupeMatiere> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" , le <GARGroupeCode> "+currentGroupeCode+" et l'établissement <GARStructureUAI> "+currentUai+" car il n'existe aucun établissement avec cet UAI dans le GAR");
                }else if (!context.uaiContainsGroupeCode(currentUai, currentGroupeCode)) {
                    logConsistencyError("Impossible de créer le <GAREnsGroupeMatiere> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" et le <GARGroupeCode> "+currentGroupeCode+" car ce groupe n'existe pas pour l'établissement <GARStructureUAI> "+currentUai+" et/ou n'a pas le statut GROUPE");
                }else if (context.uaiContainsDivisionCode(currentUai, currentGroupeCode)){
                    logConsistencyError("Impossible de créer le <GAREnsGroupeMatiere> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" et le <GARGroupeCode> "+currentGroupeCode+" car ce groupe n'existe pas pour l'établissement <GARStructureUAI> "+currentUai+" et/ou n'a pas le statut GROUPE");
                }else if (!context.uaiContainsEnseignantGpid(currentUai, currentGpid)){
                    logConsistencyError("CUSTOM Impossible de créer le <GAREnsGroupeMatiere> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" car cet enseignant n'existe pas pour l'établissement <GARStructureUAI> "+currentUai);
                }else {
                    boolean unknownMatiereCode = false;
                    for (String matiereCode: currentMatiereCodes) {
                        if (!context.uaiContainsMatiereCode(currentUai, matiereCode)) {
                            logConsistencyError("CUSTOM Impossible de créer le <GAREnsGroupeMatiere> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" et le <GARGroupeCode> "+currentGroupeCode+" car la matière "+matiereCode+" n'existe pas pour l'établissement <GARStructureUAI> "+currentUai+".");
                            unknownMatiereCode = true;
                        }
                    }
                    if (!unknownMatiereCode) {
                        for(String matiereCode: currentMatiereCodes) {
                            context.addEnseignantGpidAndGroupeCodeAndMatiereCodeToUai(currentUai, currentGpid, currentGroupeCode, matiereCode);
                        }
                        ok = true;
                    } 
                    if (!ok) {
        				context.addKoUai(currentUai);
        			}
                }
                //clean de fin
                this.currentUai = null;
                this.currentGpid = null;
                this.currentMatiereCodes = null;
                this.currentGroupeCode = null;
            }
        }else if (LOCALNAME_GAREnsClasseMatiere.equals(localName)) {
            if (stage == GroupeParsingStage.FULL || stage == GroupeParsingStage.THIRD){
                for(String matiereCode: currentMatiereCodes) {
                    context.countUaiEnseignantDivisionMatiereLink();
                }
                if (this.currentGroupeCode == null) {
                    //TODO code groupe non fourni
                }else if (this.currentUai == null) {
                    //TODO UAI non fourni
                }else if (this.currentGpid == null) {
                    //TODO GAR person id
                }else if (this.currentMatiereCodes == null || this.currentMatiereCodes.isEmpty()) {
                    //TODO codes matiere non fourni
                }else if (!context.containsUai(currentUai)){
                    logConsistencyError("Impossible de créer le <GAREnsClasseMatiere> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" , le <GARGroupeCode> "+currentGroupeCode+" et l'établissement <GARStructureUAI> "+currentUai+" car il n'existe aucun établissement avec cet UAI dans le GAR");
                }else if (!context.uaiContainsGroupeCode(currentUai, currentGroupeCode)) {
                    logConsistencyError("Impossible de créer le <GAREnsClasseMatiere> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" et le <GARGroupeCode> "+currentGroupeCode+" car ce groupe n'existe pas pour l'établissement <GARStructureUAI> "+currentUai+" et/ou n'a pas le statut DIVISION");
                }else if (!context.uaiContainsDivisionCode(currentUai, currentGroupeCode)){
                    logConsistencyError("Impossible de créer le <GAREnsClasseMatiere> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" et le <GARGroupeCode> "+currentGroupeCode+" car ce groupe n'existe pas pour l'établissement <GARStructureUAI> "+currentUai+" et/ou n'a pas le statut DIVISION");
                }else if (!context.uaiContainsEnseignantGpid(currentUai, currentGpid)){
                    logConsistencyError("CUSTOM Impossible de créer le <GAREnsClasseMatiere> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" car cet enseignant n'existe pas pour l'établissement <GARStructureUAI> "+currentUai);
                }else {
                    boolean unknownMatiereCode = false;
                    for (String matiereCode: currentMatiereCodes) {
                        if (!context.uaiContainsMatiereCode(currentUai, matiereCode)) {
                            logConsistencyError("CUSTOM Impossible de créer le <GAREnsClasseMatiere> pour l'utilisateur avec le <GARPersonIdentifiant> "+currentGpid+" et le <GARGroupeCode> "+currentGroupeCode+" car la matière "+matiereCode+" n'existe pas pour l'établissement <GARStructureUAI> "+currentUai+".");
                            unknownMatiereCode = true;
                        }
                    }
                    if (!unknownMatiereCode) {
                        for(String matiereCode: currentMatiereCodes) {
                            context.addEnseignantGpidAndDivisionCodeAndMatiereCodeToUai(currentUai, currentGpid, currentGroupeCode, matiereCode);
                        }
                        ok = true;
                    }
                }
                if (!ok) {
    				context.addKoUai(currentUai);
    			}
            }
            //clean de fin
            this.currentUai = null;
            this.currentGpid = null;
            this.currentMatiereCodes = null;
            this.currentGroupeCode = null;
        }
        //éléments feuilles
        else if (LOCALNAME_GARGroupeCode.equals(localName)){
            this.currentGroupeCode = charactersBuilder.toString();
            this.charactersBuilder = null;
        }else if(LOCALNAME_GARGroupeStatut.equals(localName)) {
            this.currentGroupeStatut = charactersBuilder.toString();
            this.charactersBuilder = null;
        }else if(LOCALNAME_GARStructureUAI.equals(localName)) {
            this.currentUai = charactersBuilder.toString();
            this.charactersBuilder = null;
        }else if(LOCALNAME_GARGroupeDivAppartenance.equals(localName)) {
            this.currentDivCodes.add(charactersBuilder.toString());
            this.charactersBuilder = null;
        }else if(LOCALNAME_GARPersonIdentifiant.equals(localName)) {
            this.currentGpid = charactersBuilder.toString();
            this.charactersBuilder = null;
        }else if(LOCALNAME_GARMatiereCode.equals(localName)) {
            if (this.currentMatiereCodes != null) {
                this.currentMatiereCodes.add(charactersBuilder.toString());
            }
            this.charactersBuilder = null;
        }
        //TODO parser tous les éléments
    }

    public void characters(char[] ch, int start, int length){
        if (charactersBuilder != null) {
            charactersBuilder.append(new String(ch,start,length).trim());
        }
    }

    public void startDocument() throws SAXException {}

    public void endDocument() throws SAXException {}
}