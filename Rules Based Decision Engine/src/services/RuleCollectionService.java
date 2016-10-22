package services;

import models.Rule;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Mike on 10/9/2016.
 */
public class RuleCollectionService {

    private static RuleCollectionService instance = null;

    private Map<String, Rule> rules = new HashMap<String, Rule>();
    private final String fileLoc = "C:\\Users\\Mike\\IdeaProjects\\Rules Based Decision Engine\\rules.txt"; //Final for testing purposes


    private RuleCollectionService()
    {
        ImportRulesMade();
    }

    public void addRule(String key, Rule rule) throws Exception
    {
        if (this.rules.containsKey(key)) {
            throw new Exception("Key already exists");
        }
        this.rules.put(key, rule);
    }

    public void ImportRulesMade(){

        Scanner ruleScanner;

        try{

            ruleScanner = new Scanner(new FileReader(fileLoc));

            while(ruleScanner.hasNextLine()){
                String txtLine = ruleScanner.nextLine();
                String[] titleAction = txtLine.split(",");
                //Rule r = new Rule(titleAction[0], titleAction[1], titleAction[2], titleAction[3], titleAction[4]);
                //rules.put(titleAction[0], r);
            }

        }
        catch(FileNotFoundException e) {                                                //Exception if file is not found
            System.out.println("File not found!");
            System.exit(0);
        }
    }

    public void exportRulesMade(){
        PrintWriter outputStream = null;										//Writes the notes in notebook to the text file
        try{
            outputStream = new PrintWriter(fileLoc);
            for(int i = 0; i < rules.size(); i++){
                outputStream.println(rules.get(i).getTitle() + "," + rules.get(i).getAction());
            }
            outputStream.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error opening file!");
            System.exit(0);
        }
    }

    public static RuleCollectionService getInstance()
    {
        if (instance == null)
        {
            instance = new RuleCollectionService();
        }
        return instance;
    }

    public Map<String, Rule> getRules()
    {
        return this.rules;
    }

    @Override
    public String toString(){
        String s = new String();

        //this.rules.forEach();

        return s;
    }

}