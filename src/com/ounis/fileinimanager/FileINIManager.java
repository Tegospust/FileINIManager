package com.ounis.fileinimanager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.ounis.fileinistruct.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author AndroidDev
 */
public class FileINIManager {
    private String fileName;
    public String getFileName() {
        return this.fileName;
    }
    
    /**
     * strukura reprezentująca wiersze we wczytanym pliku .ini
     */
    private ArrayList<FINILine> fileINILines;
    
    /**
     * kopia fileINILines
     * @return lista obiektów FINILine, lub null 
     */
    public final List<FINILine> getFileINILines() {
//        tu powinna być zwracana kopia
        List<FINILine> copy = null;
        if (fileINILines != null) {
            copy = new ArrayList<>();
            for(FINILine finil:fileINILines)
                copy.add(finil);
        }
        return copy;
    }
    
    private ArrayList<FINILine> updatedKeys;
    public ArrayList<FINILine> getUpdatedKeys() {
        return updatedKeys;
    }
    
    /**
     * <font size="5" color="#ff0000">konstruktor</font>
     * @param aFileName 
     */
    FileINIManager(String aFileName) {
        this.fileName = aFileName;
        fileINILines = new ArrayList<>();
    }
    
    /**
     * wczytanie pliku o nazwie this.fileName
     * @return int - liczba wczytanych wierszy lub -1
     */
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
                    this.fileINILines.add(new FINILineRem(result, section, line.substring(0)));
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

    /**
     * 
     * Zapis linii do pliku
     * 
     * @param aFileName - nazwa pliku
     * @throws Exception - rzuca ten wyjątek w przypadku niepowodzenia
     */
    public void save2File(String aFileName) throws Exception {
        

        FileWriter file = 
                new FileWriter(aFileName, false);
        String lnsep = System.getProperty("line.separator");        
        String line2save = "";
        for(FINILine finil: fileINILines) {
            if (finil instanceof FINILineKeyValue) {
                line2save = finil.toString();
            }
            else 
                line2save = finil.getLine();
//            System.out.println(line2save);
            file.write(line2save.concat(lnsep));
        }
        file.flush();
        file.close();
        
      }
    
    /**
     * 
     * pobiera wartość klucza w sekcji
     * 
     * @param aSectName - sekcja
     * @param aKey - klucz
     * @return - wartość klucza w sekcji String, niepowodzenie: null
     */
    public String getValue4SectKey(String aSectName, String aKey) {
        String result = null;
        Iterator<FINILine> iter = this.fileINILines.iterator();
        while(iter.hasNext()) {
            FINILine finil = iter.next();
            if (finil instanceof FINILineKeyValue) {
                if(aSectName.equals(finil.getSection()) && 
                        aKey.equals(((FINILineKeyValue) finil).getKey())) {
                            result = ((FINILineKeyValue) finil).getValue();
                            break;
                }
            }
        }
        return result;
    }
    
    /**
     * lista sekcji w formie ArrayList&lt;FINILine&gt;<br>
     * 
     * https://www.baeldung.com/java-instanceof
     * 
     * @return ArrayList&lt;FINILine&gt;
     * 
     */
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
    
    /** lista sekcji w formie ArrayList&lt;String&gt;
     * 
     * @return ArrayList&lt;String&gt;
     */
    public ArrayList<String> getSectionsStr() {
        ArrayList<String> result = null;
        ArrayList<FINILine> finilines = this.getSections();
        if (finilines != null) {
            result = new ArrayList<>();
            for(FINILine finil: finilines) {
                String s = finil.getSection();
                result.add(s);
            }
        }
        return result;
    }
    
    /**
     * lista pozyzji dla sekcji <b>getItem4Section</b> w formie ArrayList&lt;FINILine&gt;
     * 
     * @param aSectionName nazwa sekcji
     * @return ArrayList&lt;FINILine&gt;
     */
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
//        public ArrayList<FINILine> getKeys4Section(String aSectionName) {
//        ArrayList<FINILine> result = null;
//        for(FINILine fini: this.fileINILines) {
//            if (fini instanceof FINILineKeyValue) {
//                if (result == null)
//                    result = new ArrayList<>();
//                String temp = fini.getSection();
//                if (aSectionName.equals(temp)) {
//                    result.add(fini);
//                }
//            }
//        }
//        return result;
//    }
    /**
     * lista pozycji dla sekcji <b>aSectionName</b> w formie ArrayList&lt;String&gt;
     * 
     * @param aSectionName nazwa sekcji
     * @return ArrayList;ltString;gt
     */
    public ArrayList<String> getItems4SectionStr(String aSectionName) {
        ArrayList<String> result = null;
        ArrayList<FINILine> finilines = getItems4Section(aSectionName);
        if (finilines != null) {
            for(FINILine finil: finilines) {
                if (result == null)
                    result = new ArrayList<>();
                result.add(finil.toString());
            }
        }
        
        return result;
    }
    
    /**
     * lista kluczy w sekcji w formie ArrayList&lt;String&gt;<br>
     * 
     * @param aSectionName
     * @return 
     */
    public ArrayList<String> getKeys4SectionStr(String aSectionName) {
        ArrayList<String> result = null;
        ArrayList<FINILine> finilines = getItems4Section(aSectionName);
        if (finilines != null) {
            for(FINILine finil: finilines) {
                if (result == null)
                    result = new ArrayList<>();
                if (finil instanceof FINILineKeyValue)
                    result.add(((FINILineKeyValue) finil).getKey());
            }
        }
        return result;
    }
    
    /**
     * 
     * zwraca linie komentarza wokół zadanej linii fromNumLine
     * na głębokość deepOfSearch w górę lub w dół
     * 
     * @param fromNumLine - numer linii od której zaczyan się pobieranie linii komentarzy
     * @param deepOfSearch - głębokość szukania góra/dół
     * @return - wiersze odseparowane "\n" lub null
     */
    String findNearestRemark(int fromNumLine, int deepOfSearch) {
        ArrayList<String> remarks = null;
        int listindex = -1;
        int start, end = 0;
        
        
        for(int idx = 0;idx < fileINILines.size();idx++) {
            if (fileINILines.get(idx).getLineNum() == fromNumLine) {
                listindex = idx;
                break;
            }
        }
//        stara wersja:
        if (!(listindex == -1)) {
            start = listindex - deepOfSearch - 1 < 0 ? 0 : listindex-deepOfSearch;
            end = listindex+deepOfSearch > fileINILines.size()-1 ? fileINILines.size()-1 : listindex+deepOfSearch;
            for(FINILine finil: fileINILines) {
                if (finil.getLineNum() >= start && finil.getLineNum() <= end) 
                    if (!(finil instanceof FINILineEmpty)) {
                        if (remarks == null)
                            remarks = new ArrayList();
                        remarks.add(finil.getLine());
                    }
                    
            }
        }        

        
        String buff = "";
        if (remarks != null)
            for(String s: remarks)
                buff = buff.concat(s.concat("\n"));
        return buff;
    }
    
    /**
     * 
     * ustalanie FINILine po numerze linii
     * 
     * @param aLineNum
     * @return 
     */   
    public FINILine iniLineBy(int aLineNum) {
        FINILine result = null;
        Iterator<FINILine> iter = fileINILines.iterator();
        while(iter.hasNext()) {
            FINILine finil = iter.next();
            if(finil.getLineNum() == aLineNum) {
                result = finil;
                break;
            }
        }
        return result;
    }
    
    /**
     * aktualizowanie wartości klucza po numerze linii
     * 
     * @param lineNum
     * @return true gdy powodzenie
     */
    public boolean updateValue(final int lineNum, String aNewValue) {
        boolean result = false;
        FINILine finil = iniLineBy(lineNum);
        if (finil instanceof FINILineKeyValue) {
            if (finil.getLineNum() == lineNum) {
                ((FINILineKeyValue) finil).setValue(aNewValue);
                updatedKeys.add(finil);
                result = true;
            }
        }
        return result;
    }
    /**
     * 
     * @param aSection
     * @param aKey
     * @param aNewValue
     * @return 
     */
    public boolean updateValue(String aSection, String aKey, String aNewValue) {
        boolean result = false;
        Iterator<FINILine> iter = fileINILines.iterator();
        FINILine finil = null;
        while(iter.hasNext()) {
            finil = iter.next();
            if(finil instanceof FINILineKeyValue) {
                if(aSection.equals(finil.getSection()) && aKey.equals(((FINILineKeyValue) finil).getKey())) {
                    ((FINILineKeyValue) finil).setValue(aNewValue);
                    result = true;
                    break;
                }
            }
            finil = null;
        }
        return result;
    }
    
    
/**
 * <font size="5" color="#ff0000">na potrzeby testów</font>
 * 
 */  
   public static void main(String... args) {
        FileINIManager fINIMan = new FileINIManager( "sfall-mods.ini"); //"G:\\Gry\\Fallout 2\\mods\\InventoryFilter.dat\\InvenFilter.ini"); 
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
