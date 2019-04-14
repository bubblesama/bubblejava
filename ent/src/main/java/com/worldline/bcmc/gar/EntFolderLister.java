package com.worldline.bcmc.gar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EntFolderLister {
    
    private static final String FILE_PATTERN_ETAB = "Etab";
    private static final String FILE_PATTERN_ENSEIGNANT = "Enseignant";
    private static final String FILE_PATTERN_ELEVE = "Eleve";
    private static final String FILE_PATTERN_GROUPE = "Groupe";

    public List<String> filePaths = new ArrayList<String>();
    public List<String> etablissementsFilePaths = new ArrayList<>();
    public List<String> enseignantsFilePaths = new ArrayList<>();
    public List<String> elevesFilePaths = new ArrayList<>();
    public List<String> groupesFilePaths = new ArrayList<>();

    private String entFolderPath;
    
    private EntFolderLister(String entFolderPath) {
        this.entFolderPath = entFolderPath;
    }

    public static EntFolderLister listEntFiles(String entFolderPath) {
        EntFolderLister result = new EntFolderLister(entFolderPath);
        try {
            Stream<Path> paths = Files.walk(Paths.get(entFolderPath));
            paths.filter(Files::isRegularFile).forEach((path)->{
                String filePath = path.toString();
                result.filePaths.add(filePath);
                if (filePath.contains(FILE_PATTERN_ETAB)) {
                    result.etablissementsFilePaths.add(filePath);
                }else if (filePath.contains(FILE_PATTERN_ENSEIGNANT)) {
                    result.enseignantsFilePaths.add(filePath);
                }else if (filePath.contains(FILE_PATTERN_ELEVE)) {
                    result.elevesFilePaths.add(filePath);
                }else if (filePath.contains(FILE_PATTERN_GROUPE)) {
                    result.groupesFilePaths.add(filePath);
                }
            });
            paths.close();
        } catch (IOException e) {
            System.out.println("Erreur: pas de complet dans le dossier "+entFolderPath);
            e.printStackTrace();
        }
        return result;
    }

    public void printStatus() {
        System.out.println("files found in "+entFolderPath+": "+filePaths.size());
        System.out.println("etablissements files found in "+entFolderPath+": "+etablissementsFilePaths.size());
        System.out.println("enseignants files found in "+entFolderPath+": "+enseignantsFilePaths.size());
        System.out.println("eleves files found in "+entFolderPath+": "+elevesFilePaths.size());
        System.out.println("groupes files found in "+entFolderPath+": "+groupesFilePaths.size());
    }

}
