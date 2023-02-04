/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.ounis.fileinistruct.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author AndroidDev
 */
public class FileINIManager {
    private String fileName;
    public String getFileName() {
        return this.fileName;
    }
    
    private ArrayList<FINILine> fileINILines;
    public ArrayList<FINILine> getFileINILines() {
        return fileINILines;
    }
    
    FileINIManager(String aFileName) {
        this.fileName = aFileName;
        fileINILines = new ArrayList<>();
        
        
    }
    
    public int loadFromFile()
    {
        int result = -1;
        String line;
        String section = null;
        try {
            BufferedReader textFile = new BufferedReader(
                  new InputStreamReader(new FileInputStream(fileName)));
            result = 0;
            for(;(line = textFile.readLine()) != null;) {
//                System.out.println(String.format("%d - %s",result, line));
                result += 1;
                if (line.startsWith(com.ounis.fileinistruct.CONST.PREF_REM)) {
                    this.fileINILines.add(new FINILineRem(result, section, line.substring(1)));
                    continue;
                } 
                if (line.startsWith(com.ounis.fileinistruct.CONST.PREF_SECT)) {
                    String buff = line;
                    buff = buff.substring(1, buff.length()-1);
                    if (!buff.equals(section))
                        section = buff;
                    this.fileINILines.add(new FINILineSection(result, section));
                    continue;
                } 
                if(line.isBlank()) {
                    this.fileINILines.add(new FINILineEmpty(result, section));
                    continue;
                } 
                this.fileINILines.add(new FINILineKeyValue(result, section, line));
            } // for
            textFile.close();
        }
        catch (IOException e) {
            System.err.println(e);
            result = -1;
        }
        
        return result;
    }
    
    public ArrayList<FINILine> getSections() {
        ArrayList<FINILine> result = null;
        
        for(FINILine fini: this.fileINILines) {
            if (fini instanceof FINILineSection) {
                if (result == null)
                    result = new ArrayList<>();
                result.add(fini);
            }
        }
        return result;
    }
    
    public ArrayList<String> getSectionsStr() {
        ArrayList<String> result = null;
        ArrayList<FINILine> finilines = this.getSections();
        if (finilines != null) {
            result = new ArrayList<>();
            for(FINILine finil: finilines)
                result.add(finil.getSection());
        }
        return null;
    }
    
    public ArrayList<FINILine> getItems4Section(String aSectionName) {
        ArrayList<FINILine> result = null;
        for(FINILine fini: this.fileINILines) {
            if (fini instanceof FINILineKeyValue) {
                if (result == null)
                    result = new ArrayList<>();
                String temp = fini.getSection();
                if (aSectionName.equals(temp)) {
                    result.add(fini);
                }
            }
        }
        return result;
    }
    
    public ArrayList<String> getItems4SectionStr(String aSectionName) {
        ArrayList<String> result = null;
        ArrayList<FINILine> finilines = getItems4Section(aSectionName);
        if (finilines != null) {
            
        }
        
        return result;
    }
    
//    na potrzeby testów
    public static void main(String... args) {
        FileINIManager fINIMan = new FileINIManager("sfall-mods.ini");
        int loadedLines = fINIMan.loadFromFile();

        if (loadedLines > -1) {
//  zrzut linia po linii        
//            for(FINILine fini: fINIMan.getFileINILines()) {
//                System.out.println(fini.toString().concat(" - ").concat(fini.getSection() != null ? fini.getSection() : "[root]"));
//            }
//   lista sekcji
//            for(FINILine fini: fINIMan.getSections())
//                System.out.println(String.format("#%d - %s",fini.getLineNum(),
//                        fini));
//   lista wartości dla danej sekcji
            for(FINILine linesect: fINIMan.getSections()) {
                String sect = linesect.toString();
                System.out.println(String.format("\nLista wartości dla sekcji: %s", sect));
                for(FINILine fini: fINIMan.getItems4Section(sect))
                        System.out.println("".concat(fini.toString()));
            }
        }
        
        
    }
}
