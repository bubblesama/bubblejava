package com.worldline.bcmc.gar;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.worldline.bcmc.gar.ElevesControlHandler.EleveParsingStage;
import com.worldline.bcmc.gar.EnseignantsControlHandler.EnseignantParsingStage;
import com.worldline.bcmc.gar.EtablissementsControlHandler.EtabParsingStage;
import com.worldline.bcmc.gar.GroupesControlHandler.GroupeParsingStage;

/**
 * Classe de lancement d'un parsing de complet 
 * @author a163661
 */
public class QuickParsingMaestro {

	public static void main(String[] args) {
		if (args.length != 1) {
			//show usage
			lightLog("Usage incorrect: précisez le dossier du complet à contrôler");
		}else {
			String entFolderPath = args[0];
			lightLog("Lancement de l'analyse de cohérence du complet dans le dossier: "+entFolderPath);
			checkEnt(entFolderPath);
		}
	}

	private static void checkEnt(String entFolderPath) {
		long startTime = System.currentTimeMillis();
		try {
			EntFolderLister entFolderLister = EntFolderLister.listEntFiles(entFolderPath);
			entFolderLister.printStatus();

			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			saxParserFactory.setNamespaceAware(true);
			SAXParser saxParser = saxParserFactory.newSAXParser();
			XMLReader saxXmlReader = saxParser.getXMLReader();

			EntContext context = new EntContext();

			// parsing fichiers "Etab"
			lightLog("établissements passe 1");
			// première passe
			EtablissementsControlHandler firstStageEtablissementsHandler = new EtablissementsControlHandler(context,EtabParsingStage.FIRST);
			saxXmlReader.setContentHandler(firstStageEtablissementsHandler);
			for (String etablissementsFilePath: entFolderLister.etablissementsFilePaths) {
				firstStageEtablissementsHandler.setFileName(etablissementsFilePath);
				saxXmlReader.parse(new InputSource(new FileInputStream(etablissementsFilePath)));
			}

			lightLog("établissements passe 2");
			// seconde passe
			EtablissementsControlHandler secondStageEtablissementsHandler = new EtablissementsControlHandler(context,EtabParsingStage.SECOND);
			saxXmlReader.setContentHandler(secondStageEtablissementsHandler);
			for (String etablissementsFilePath: entFolderLister.etablissementsFilePaths) {
				firstStageEtablissementsHandler.setFileName(etablissementsFilePath);
				saxXmlReader.parse(new InputSource(new FileInputStream(etablissementsFilePath)));
			}

			lightLog("enseignants passe 1");
			// liste des fichiers "Enseignant"
			// passe 1
			EnseignantsControlHandler firstEnseignantsHandler = new EnseignantsControlHandler(context,EnseignantParsingStage.FIRST);
			saxXmlReader.setContentHandler(firstEnseignantsHandler);
			for (String enseignantsFilePath: entFolderLister.enseignantsFilePaths) {
				firstEnseignantsHandler.setFileName(enseignantsFilePath);
				saxXmlReader.parse(new InputSource(new FileInputStream(enseignantsFilePath)));
			}

			lightLog("enseignants passe 2");
			//passe 2
			EnseignantsControlHandler secondEnseignantsHandler = new EnseignantsControlHandler(context,EnseignantParsingStage.SECOND);
			saxXmlReader.setContentHandler(secondEnseignantsHandler);
			for (String enseignantsFilePath: entFolderLister.enseignantsFilePaths) {
				secondEnseignantsHandler.setFileName(enseignantsFilePath);
				saxXmlReader.parse(new InputSource(new FileInputStream(enseignantsFilePath)));
			}

			lightLog("élèves passe 1");
			// liste des fichiers "Eleve"
			ElevesControlHandler firstElevesHandler = new ElevesControlHandler(context,EleveParsingStage.FIRST);
			saxXmlReader.setContentHandler(firstElevesHandler);
			for (String elevesFilePath: entFolderLister.elevesFilePaths) {
				// lightLog("passe 1: fichier: "+elevesFilePath);
				firstElevesHandler.setFileName(elevesFilePath);
				saxXmlReader.parse(new InputSource(new FileInputStream(elevesFilePath)));
			}

			lightLog("élèves passe 2");
			ElevesControlHandler secondStageElevesHandler = new ElevesControlHandler(context,EleveParsingStage.SECOND);
			saxXmlReader.setContentHandler(secondStageElevesHandler);
			for (String elevesFilePath: entFolderLister.elevesFilePaths) {
				// lightLog("passe 2: fichier: "+elevesFilePath);
				secondStageElevesHandler.setFileName(elevesFilePath);
				saxXmlReader.parse(new InputSource(new FileInputStream(elevesFilePath)));
			}

			lightLog("groupes passe 1");
			//liste des fichiers "Groupe", première passe pour les divisions
			GroupesControlHandler firstStageGroupesHandler = new GroupesControlHandler(context,GroupeParsingStage.FIRST);
			saxXmlReader.setContentHandler(firstStageGroupesHandler);
			for (String groupesFilePath: entFolderLister.groupesFilePaths) {
				firstStageGroupesHandler.setFileName(groupesFilePath);
				saxXmlReader.parse(new InputSource(new FileInputStream(groupesFilePath)));
			}

			lightLog("groupes passe 2");
			//deuxième passe pour les groupes seulement
			GroupesControlHandler secondStageGroupesHandler = new GroupesControlHandler(context,GroupeParsingStage.SECOND);
			saxXmlReader.setContentHandler(secondStageGroupesHandler);
			for (String groupesFilePath: entFolderLister.groupesFilePaths) {
				secondStageGroupesHandler.setFileName(groupesFilePath);
				saxXmlReader.parse(new InputSource(new FileInputStream(groupesFilePath)));
			}

			lightLog("groupes passe 3");
			//troisième passe pour les éléments autres
			GroupesControlHandler thirdStageGroupesHandler = new GroupesControlHandler(context,GroupeParsingStage.THIRD);
			saxXmlReader.setContentHandler(thirdStageGroupesHandler);
			for (String groupesFilePath: entFolderLister.groupesFilePaths) {
				thirdStageGroupesHandler.setFileName(groupesFilePath);
				saxXmlReader.parse(new InputSource(new FileInputStream(groupesFilePath)));
			}

			long duration = System.currentTimeMillis()-startTime;
			System.out.println(context.getDebugStatus());
			System.out.println("bench: execution du script en "+duration+"ms");

		} catch (ParserConfigurationException e) {
			lightLog("Usage incorrect: complet non trouvé ou mal formaté");
			e.printStackTrace();
		} catch (SAXException e) {
			lightLog("Usage incorrect: complet non trouvé ou mal formaté");
			e.printStackTrace();
		} catch (IOException e) {
			lightLog("Usage incorrect: complet non dans le dossier "+entFolderPath);
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * TODO méthode de logs "propre"
	 * @param log
	 */
	public static void lightLog(String log) {
		System.out.println(System.currentTimeMillis()+" "+log);
	}

}
