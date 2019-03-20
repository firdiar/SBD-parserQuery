/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

import java.util.ArrayList;

/**
 *
 * @author SAMSUNG
 */
public class QueryValidator {
    
    public static ArrayList<CheckPoint> getCheckPoint(String[] queryParsed){
        ArrayList<CheckPoint> cp = new ArrayList<CheckPoint>();
        
        for(int i = 0; i < queryParsed.length; i++){
            if(CheckPoint.isCheckPoint(queryParsed[i])){
                if((i >= 1) && queryParsed[i].toLowerCase().equals("join") && (queryParsed[i-1].toLowerCase().equals("natural")||queryParsed[i-1].toLowerCase().equals("cross"))){
                    queryParsed[i] = "x"+queryParsed[i];
                }
                
                cp.add(new CheckPoint(i , queryParsed[i]));
            }
        }
        
        return cp;
    
    }
    
    public static boolean validateStructure(ArrayList<CheckPoint> cp){
        if(cp.size() <=0){
            System.out.println("query parser is not ready for this statement");
            return false;
        }
        
        if(!cp.get(0).status.toLowerCase().equals("select")){
            System.out.println("query parser is not ready for this statement");
            return false;
        }
        if(cp.get(cp.size()-1).status.toLowerCase().equals("join")){
            System.out.println("join cant be last keys");
            return false;
        }else if(cp.get(cp.size()-1).status.toLowerCase().equals("select")){
            System.out.println("select cant be last keys");
            return false;
        }
        
        boolean isCorrect = false;
        for(int i = 0 ;  i < cp.size()-1 ; i++){
            isCorrect = false;
            //System.out.println(isCorrect);
            for(String s : cp.get(i).next){
                
                if(s.toLowerCase().equals(cp.get(i+1).status)){
                    isCorrect = true;
                    
                    break;
                }
            }
            //System.out.println(isCorrect);
            if(!isCorrect){
                System.out.println(cp.get(i).status+"->"+cp.get(i+1).status);
                break;
            }
            //System.out.println(isCorrect);
        }
        
        
        
        
        return isCorrect;
    }
    
    public static String[] getSelect(ArrayList<CheckPoint> cp , String[] queryParsed){
        String select = "";
        for(int i = cp.get(0).index+1 ; i < cp.get(1).index ; i++){
            select += queryParsed[i];
        }
        return select.split(",");
    }
    public static ArrayList<Table> getFrom(ArrayList<CheckPoint> cp , String[] queryParsed , ArrayList <Table> tables) {
        ArrayList<Table> tabs = new ArrayList<Table>();
        Table temp = new Table("temp");
        boolean isTable = false;
        boolean isAfterOn = false;
        int cpIdx = 2;
        
        for(int i = cp.get(1).index+1 ; i < queryParsed.length ; i ++){
            if(cpIdx < cp.size() && i == cp.get(cpIdx).index){
                if(cp.get(cpIdx).status.equals("on")){
                   isAfterOn = true;
                }
                cpIdx++;
                isTable = false;
                continue;
            }
            
            if(isAfterOn){
                if(!queryParsed[i].contains("=")){
                    System.out.println("error when join table at "+queryParsed[i]);
                }
                
                String[] eqv = queryParsed[i].split("=");
                eqv[0] = eqv[0].replace("(", "");
                eqv[1] = eqv[1].replace(")", "");
                
                for(int j = 0; j <2 ; j++){
                    if(eqv[j].contains(".")){
                        eqv[j] = eqv[j].split("\\.")[1];
                    }
                }
                
                Boolean isValidA = false;
                Boolean isValidB = false;
                for(Table t : tabs){
                    if(isValidA || t.containColumn(eqv[0])){
                        isValidA = true;
                    }
                    if(isValidB || t.containColumn(eqv[1])){
                        isValidB = true;
                    }
                }
                if(!isValidA){
                    System.out.println("Column ("+eqv[0]+") is not found in table");
                    return null;
                }else if(!isValidB){
                    System.out.println("Column ("+eqv[1]+") is not found in table");
                    return null;
                }
                isAfterOn = false;
                continue;
            }
            
            if(isTable){
                if(cpIdx < cp.size() && i < cp.get(cpIdx).index){
                    if(cp.get(cpIdx).status.equals("xjoin") && i < cp.get(cpIdx).index-1){
                        temp.alias = queryParsed[i];
                    }else if(!cp.get(cpIdx).status.equals("xjoin")){
                        temp.alias = queryParsed[i];
                    }
                }else{
                    temp.alias = queryParsed[i];
                }
              
                isTable = false;
                continue;
            }
            
            temp = Table.getTable(tables, queryParsed[i]);
            //System.out.println("Parse : "+queryParsed[i]);
            if(temp != null){
                //System.out.println("Found");
                tabs.add(temp);
                isTable = true;
            }else{
                //System.out.println("Not Found");
            }
        }
        
        return tabs;
    }
    
    
    
    
}


 