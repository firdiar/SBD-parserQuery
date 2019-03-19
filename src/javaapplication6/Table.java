/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SAMSUNG
 */
public class Table {
    public String alias;
    public String name;
    public ArrayList<String> colomnName;
    public ArrayList<String> outColomn;
    
    public Table(String name){
        this.name = name;
        this.alias = name;
        this.colomnName = new ArrayList<>();
        this.outColomn = new ArrayList<>();;
    }
    
    public void Show(){
        System.out.println("Table Name : "+name);
        for(String col : colomnName){
            System.out.println(" - "+col);
        }
    }
    public Boolean containColumn(String column){
        
        for(String s : colomnName){
            if(column.toLowerCase().equals(s)){
                return true;
            }
        }
        return false;
    }
    
    public static Table getTable(ArrayList <Table> tables , String alias){

        for(Table t : tables){

            if(t.name.equals(alias)){
                return (Table)t;
            }
        }
        //System.out.println(alias + " table not found ");
        return null;
    }

}
