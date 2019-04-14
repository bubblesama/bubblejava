package com.worldline.bcmc.gar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Object de stockage des données analysées lors du parsing, construit au fur et à mesure et passé de parser en parser.
 * Pour obtenir un contrôle de cohérence, le même contexte doit servir pour tous les parser
 * TODO: OPTI possible: des Map avec code clé l'UAI
 * @author A163661
 *
 */
public class EntContext {
    
    // ATTRIBUTS
    //données extraites
    private Set<String> gpids = new HashSet<String>();
    
    //comptes bruts pour comparaison en fin de parcours
    
    private int etablissementsCount = 0;
    private int enseignantsCount = 0;
    private int mefsCount = 0;
    private int matieresCount = 0;
    private int elevesCount = 0;
    private int identitesCount = 0;
    private int enseignantUaiLinksCount = 0;
    private int enseignantUaiMefLinksCount = 0;
    private int groupesCount = 0;
    private int uaiDivisionGroupeLinksCount = 0;
    private int uaiGroupePersonneLinksCount = 0;
    private int uaiEnseignantGroupeMatiereLinksCount = 0;
    private int uaiEnseignantDivisionMatiereLinksCount = 0;
    
    //liste des UAIs en erreurs
    private Set<String> koUais = new HashSet<String>();
    
    //fichier etablissement
    //etablissements
    private Set<String> uais = new HashSet<String>();
    //codes MEF par UAI
    private Map<String,Set<String>> mefCodesByUai = new HashMap<String,Set<String>>();
    //codes matière par UAI
    private Set<String> uaisAndMatiereCodes= new HashSet<String>();
    
    // fichier enseignant
    private Set<String> enseignantGpids = new HashSet<String>();
    // pour les etabs
    private Map<String,Set<String>> enseignantsGpidsByEtab = new HashMap<String,Set<String>>();
    // pour les structures (dans les profils)
    private Map<String,Set<String>> enseignantsGpidsByProfileUai = new HashMap<String,Set<String>>();
    //<GARPersonMEF>
    private Map<String,Set<String>> enseignantGpidsAndMefCodesByUai = new HashMap<String,Set<String>>();
    
    //fichier eleves
    private Set<String> eleveGpids = new HashSet<String>();
    //prise en compte des <GARPersonEtab>
    private Map<String,Set<String>> elevesGpidsByEtab = new HashMap<String,Set<String>>();
    // pour les structures (<GARStructureUAI>)
    private Map<String,List<String>> elevesGpidsByProfileUai = new HashMap<String,List<String>>();
    //<GAREleveEnseignement>
    private Map<String,Set<String>> eleveGpidsAndMatiereCodesByUai = new HashMap<String,Set<String>>();
    //<GARPersonMEF>
    private Map<String,Set<String>> eleveGpidsAndMefCodesByUai = new HashMap<String,Set<String>>();
    
    
    //fichier groupes
    private Set<String> uaisAndGroupeCodes = new HashSet<String>();
    private Set<String> uaisAndDivisionCodes = new HashSet<String>();
    //<GARGroupeDivAppartenance>
    private Set<String> uaisAndDivisionCodesAndGroupeCodes = new HashSet<String>();
    //<GARPersonGroupe>
    private Set<String> uaisAndGroupeCodesAndGpids = new HashSet<String>();
    //<GAREnsGroupeMatiere>
    private Set<String> uaisAndEnseignantGpidAndGroupeCodesAndMatiereCodes = new HashSet<String>();
    //<GAREnsClasseMatiere>
    private Set<String> uaisAndEnseignantGpidAndDivisionCodesAndMatiereCodes = new HashSet<String>();
    
    //METHODES
    public String getDebugStatus() {
        StringBuilder result = new StringBuilder();
        result.append("Données récupérées \n");
        result.append("etabs: ");
        result.append(String.valueOf(uais.size()));
        result.append("/");
        result.append(String.valueOf(etablissementsCount));
        result.append("\n");
        result.append("matieres: ");
        result.append(uaisAndMatiereCodes.size());
        result.append("/");
        result.append(String.valueOf(matieresCount));
        result.append("\n");
        result.append("mefs: ");
        int mefCodesCount = 0;
        for (Set<String> mefCodesForUai: mefCodesByUai.values()) {
            mefCodesCount += mefCodesForUai.size();
        }
        result.append(String.valueOf(mefCodesCount));
        result.append("/");
        result.append(String.valueOf(mefsCount));
        result.append("\n");
        result.append("identites: ");
        result.append(String.valueOf(gpids.size()));
        result.append("/");
        result.append(String.valueOf(identitesCount));
        result.append("\n");
        result.append("enseignants: ");
        result.append(String.valueOf(enseignantGpids.size()));
        result.append("/");
        result.append(String.valueOf(enseignantsCount));
        result.append("\n");
        result.append("liens UAI/enseignant: ");
        int enseignantsByUaiCount = 0;
        for (Set<String> enseignantGpidsForUai: enseignantsGpidsByProfileUai.values()) {
            enseignantsByUaiCount += enseignantGpidsForUai.size();
        }
        result.append(String.valueOf(enseignantsByUaiCount));
        result.append("/");
        result.append(String.valueOf(enseignantUaiLinksCount));
        result.append("\n");
        result.append("liens UAI/enseignant/MEF: ");
        int enseignantMefCount = 0;
        for (Set<String> enseignantGpidsMefCodeForUai: enseignantGpidsAndMefCodesByUai.values()) {
            enseignantMefCount += enseignantGpidsMefCodeForUai.size();
        }
        result.append(String.valueOf(enseignantMefCount));
        result.append("/");
        result.append(String.valueOf(enseignantUaiMefLinksCount));
        result.append("\n");
        result.append("eleves: ");
        result.append(String.valueOf(eleveGpids.size()));
        result.append("/");
        result.append(String.valueOf(elevesCount));
        result.append("\n");
        result.append("liens UAI/eleve/MEF: ");
        int eleveMefCount = 0;
        for (Set<String> eleveGpidsMefCodeForUai: eleveGpidsAndMefCodesByUai.values()) {
            eleveMefCount += eleveGpidsMefCodeForUai.size();
        }
        result.append(String.valueOf(eleveMefCount));
        result.append("\n");
        result.append("liens UAI/eleve/matiere: ");
        int eleveMatieresCount = 0;
        for (Set<String> eleveGpidsAndMatiereCodesForUai: eleveGpidsAndMatiereCodesByUai.values()) {
            eleveMatieresCount += eleveGpidsAndMatiereCodesForUai.size();
        }
        result.append(String.valueOf(eleveMatieresCount));
        result.append("\n");
        result.append("groupes: ");
        result.append(String.valueOf(uaisAndGroupeCodes.size()));
        result.append("/");
        result.append(String.valueOf(groupesCount));
        result.append("\n");
        result.append("divisions: ");
        result.append(String.valueOf(uaisAndDivisionCodes.size()));
        result.append("\n");
        result.append("liens UAI/code division/code groupe: ");
        result.append(String.valueOf(uaisAndDivisionCodesAndGroupeCodes.size()));
        result.append("/");
        result.append(String.valueOf(uaiDivisionGroupeLinksCount));
        result.append("\n");
        result.append("liens UAI/code groupe/personne: ");
        result.append(String.valueOf(uaisAndGroupeCodesAndGpids.size()));
        result.append("/");
        result.append(String.valueOf(uaiGroupePersonneLinksCount));
        result.append("\n");
        result.append("liens UAI/enseignant/groupe/matiere: ");
        result.append(String.valueOf(uaisAndEnseignantGpidAndGroupeCodesAndMatiereCodes.size()));
        result.append("/");
        result.append(String.valueOf(uaiEnseignantGroupeMatiereLinksCount));
        result.append("\n");
        result.append("liens UAI/enseignant/division/matiere: ");
        result.append(String.valueOf(uaisAndEnseignantGpidAndDivisionCodesAndMatiereCodes.size()));
        result.append("/");
        result.append(String.valueOf(uaiEnseignantDivisionMatiereLinksCount));
        result.append("\n");
        result.append("nombre d'UAI KO: ");
        result.append(String.valueOf(koUais.size()));
        result.append("\n");
        return result.toString();
    }
    
    //etablissement
    public void createUai(String uai) {
        uais.add(uai);
        // init des tables
        enseignantsGpidsByEtab.put(uai, new HashSet<String>());
        enseignantsGpidsByProfileUai.put(uai, new HashSet<String>());
        enseignantGpidsAndMefCodesByUai.put(uai, new HashSet<String>());
        elevesGpidsByEtab.put(uai, new HashSet<String>());
        elevesGpidsByProfileUai.put(uai, new ArrayList<String>());
        eleveGpidsAndMefCodesByUai.put(uai, new HashSet<String>());
        eleveGpidsAndMatiereCodesByUai.put(uai, new HashSet<String>());
        mefCodesByUai.put(uai, new HashSet<String>());
    }
    public boolean containsUai(String uai) {
        return uais.contains(uai);
        //return etablissementsByUai.containsKey(uai);
    }
    //UAI et code MEF
    public boolean uaiContainsMefCode(String uai, String mefCode) {
        return uais.contains(uai) && mefCodesByUai.get(uai).contains(mefCode);
    }
    public void addMefCodeToUai(String uai, String mefCode) {
        mefCodesByUai.get(uai).add(mefCode);
//      uaisAndMefCodes.add(uai+":"+mefCode);
    }
    
    //UAI et code matiere
    public boolean uaiContainsMatiereCode(String uai, String matiereCode) {
        return uaisAndMatiereCodes.contains(uai+":"+matiereCode);
    }
    public void addMatiereCodeToUai(String uai, String matiereCode) {
        uaisAndMatiereCodes.add(uai+":"+matiereCode);
    }

    //identité
    public boolean containsGarPersonneId(String gpid) {
        return gpids.contains(gpid);
    }
    
    public boolean containsEnseignant(String gpid) {
        return enseignantGpids.contains(gpid);
    }
    
    public boolean containsEleve(String gpid) {
        return eleveGpids.contains(gpid);
    }
    
    public void createGpid(String gpid) {
        gpids.add(gpid);
    }
    
    public void createEnseignantGpid(String gpid) {
      enseignantGpids.add(gpid);
    }
    
    public void createEleveGpid(String gpid) {
        eleveGpids.add(gpid);
    }
    
    //UAI et enseignants
    public boolean uaiContainsEnseignantGpid(String uai, String enseignantGpid) {
        return uais.contains(uai) && enseignantsGpidsByProfileUai.get(uai).contains(enseignantGpid);
    }
    public void addEnseignantGpidToEtab(String uai, String enseignantGpid) {
        enseignantsGpidsByEtab.get(uai).add(enseignantGpid);
    }
    public void addEnseignantGpidToProfileUai(String uai, String enseignantGpid) {
        enseignantsGpidsByProfileUai.get(uai).add(enseignantGpid);
    }
    
    //UAI et enseignants et MEF
    public boolean uaiContainsEnseignantGpidAndMefCode(String uai, String enseignantGpid, String mefCode) {
        return uais.contains(uai) && enseignantGpidsAndMefCodesByUai.get(uai).contains(enseignantGpid+":"+mefCode);
    }
    public void addMefCodeToEnseignantGpidToUai(String uai, String enseignantGpid, String mefCode) {
        enseignantGpidsAndMefCodesByUai.get(uai).add(enseignantGpid+":"+mefCode);
    }
    
    //UAI et eleves
    public boolean uaiContainsEleveGpid(String uai, String eleveGpid) {
        return uais.contains(uai) && elevesGpidsByProfileUai.get(uai).contains(eleveGpid);
    }
    public void addEleveGpidToEtab(String uai, String eleveGpid) {
        elevesGpidsByEtab.get(uai).add(eleveGpid);
    }
    public void addEleveGpidToProfileUai(String uai, String eleveGpid) {
        elevesGpidsByProfileUai.get(uai).add(eleveGpid);
    }
    
    //UAI et eleves et MEF
    public boolean uaiContainsEleveGpidAndMefCode(String uai, String eleveGpid, String mefCode) {
        return uais.contains(uai) && eleveGpidsAndMefCodesByUai.get(uai).contains(eleveGpid+":"+mefCode);
    }
    public void addMefCodeToEleveGpidToUai(String uai, String eleveGpid, String mefCode) {
        eleveGpidsAndMefCodesByUai.get(uai).add(eleveGpid+":"+mefCode);
    }
    
    //UAI et eleves et matieres
    public boolean uaiContainsEleveGpidAndMatiereCode(String uai, String eleveGpid, String matiereCode) {
        return uais.contains(uai) && eleveGpidsAndMatiereCodesByUai.get(uai).contains(eleveGpid+":"+matiereCode);
    }
    public void addMatiereCodeToEleveGpidToUai(String uai, String eleveGpid, String matiereCode) {
        eleveGpidsAndMatiereCodesByUai.get(uai).add(eleveGpid+":"+matiereCode);
    }
    
    //UAI et codes groupe
    public boolean uaiContainsGroupeCode(String uai, String groupeCode) {
        return uaisAndGroupeCodes.contains(uai+":"+groupeCode);
    }
    public void addGroupeCodeToUai(String uai, String groupeCode) {
        uaisAndGroupeCodes.add(uai+":"+groupeCode);
    }
    public boolean uaiContainsDivisionCode(String uai, String divisionCode) {
        return uaisAndDivisionCodes.contains(uai+":"+divisionCode);
    }
    public void addDivisionCodeToUai(String uai, String divisionCode) {
        uaisAndDivisionCodes.add(uai+":"+divisionCode);
    }
    //UAI et divisions et groupes
    public boolean uaiContainsDivisionCodeAndGroupeCode(String uai, String divisionCode, String groupeCode) {
        return uaisAndDivisionCodesAndGroupeCodes.contains(uai+":"+divisionCode+":"+groupeCode);
    }
    public void addGroupeCodeToDivisionCodeToUai(String uai, String divisionCode, String groupeCode) {
        uaisAndDivisionCodesAndGroupeCodes.add(uai+":"+divisionCode+":"+groupeCode);
    }
    
    //UAI et groupes et personnes
    public boolean uaiContainsGroupeCodeAndGpid(String uai, String groupeCode, String gpid) {
        return uaisAndGroupeCodesAndGpids.contains(uai+":"+groupeCode+":"+gpid);
    }
    public void addGpidToGroupeCodeToUai(String uai, String groupeCode, String gpid) {
        uaisAndGroupeCodesAndGpids.add(uai+":"+groupeCode+":"+gpid);
    }
    
    //UAI et code groupes/ code matiere / gpid enseignant
    public boolean uaiContainsEnseignantGpidAndGroupeCodeAndMatiereCode(String uai, String enseignantGpid, String groupeCode, String matiereCode) {
        return uaisAndEnseignantGpidAndGroupeCodesAndMatiereCodes.contains(uai+":"+enseignantGpid+":"+groupeCode+":"+matiereCode);
    }
    
    public void addEnseignantGpidAndGroupeCodeAndMatiereCodeToUai(String uai, String enseignantGpid, String groupeCode, String matiereCode) {
        uaisAndEnseignantGpidAndGroupeCodesAndMatiereCodes.add(uai+":"+enseignantGpid+":"+groupeCode+":"+matiereCode);
    }
    
    //UAI et code divisions/ code matiere / gpid enseignant
    public boolean uaiContainsEnseignantGpidAndDivisionCodeAndMatiereCode(String uai, String enseignantGpid, String divisionCode, String matiereCode) {
        return uaisAndEnseignantGpidAndDivisionCodesAndMatiereCodes.contains(uai+":"+enseignantGpid+":"+divisionCode+":"+matiereCode);
    }
    
    public void addEnseignantGpidAndDivisionCodeAndMatiereCodeToUai(String uai, String enseignantGpid, String divisionCode, String matiereCode) {
        uaisAndEnseignantGpidAndDivisionCodesAndMatiereCodes.add(uai+":"+enseignantGpid+":"+divisionCode+":"+matiereCode);
    }
    
    public void countEtablissement() {
        etablissementsCount++;
    }
    
    public void countEnseignant() {
        enseignantsCount++;
    }
    
    public void countEleve() {
        elevesCount++;
    }
    
    public void countMef() {
        mefsCount++;
    }
    
    public void countMatiere() {
        matieresCount++;
    }
    
    public void countIdentite() {
        identitesCount++;
    }
    
    public void countEnseignantUaiLink() {
        enseignantUaiLinksCount++;
    }
    
    public void countEnseignantUaiMefLink() {
        enseignantUaiMefLinksCount++;
    }
    
    public void countGroupe() {
        groupesCount++;
    }
    
    public void countUaiDivisionGroupeLink() {
        uaiDivisionGroupeLinksCount++;
    }
    
    public void countUaiGroupePersonneLink() {
        uaiGroupePersonneLinksCount++;
    }
    
    public void countUaiEnseignantGroupeMatiereLink() {
        uaiEnseignantGroupeMatiereLinksCount++;
    }
    
    public void countUaiEnseignantDivisionMatiereLink() {
        uaiEnseignantDivisionMatiereLinksCount++;
    }
    
    public void addKoUai(String uai) {
    	koUais.add(uai);
    }
    
    
}

