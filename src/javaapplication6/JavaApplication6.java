/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SAMSUNG
 */
public class JavaApplication6 {

    /**
     * @param args the command line arguments
     */
    
    public static ArrayList <Table> tables = new ArrayList<Table>();
    public static boolean isRunning = true;
    
    public static void main(String[] args) {
        // TODO code application logic here
        loadTableData(tables);
        
//        for(Table s : tables){
//            s.Show();
//        }
        
            
        // meminta inputan user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan query : ");
        String query = scanner.nextLine();
        System.out.println("=============================");

        // mengecek apakah di akhir ada semi colon
        if(query.charAt(query.length() -1) != ';'){
            CloseError("No (;) in the end of query");
            return;
        }

        // mengecek apakah ada semi colon di dalam query selain di akhir
        if(query.substring(0, (query.length()-1)).contains(";")){
            CloseError("Invalid (;) found in query");
            return;
        }
        query = query.substring(0, (query.length()-1));

        // mensplit query
        String[] queryParsed = query.split(" ");

        // mendapatkan mark point
        ArrayList<CheckPoint> Mark = QueryValidator.getCheckPoint(queryParsed);


        // mengecek struktur query
        boolean isValid = QueryValidator.validateStructure(Mark);
        if(!isValid){
            CloseError("Invalid Structure");
            return;
        }

        String[] out = QueryValidator.getSelect(Mark,queryParsed);
        
        
        ArrayList<Table> from = QueryValidator.getFrom(Mark,queryParsed,tables);
        
        if(from == null){
            return;
        }
        
        
        if(!out[0].equals("*")){
            for(String col : out){
                if(col.contains(".")){
                    col = col.split("\\.")[1];
                }

                for(Table t : from){
                    if(t.containColumn(col)){
                        t.outColomn.add(col);
                    }
                }
            }
        }else{
            for(Table t : from){

                t.outColomn = t.colomnName;
                
            }
        }
        
        int i = 1;
        for(Table t : from){

            System.out.println("Table "+i+" : "+t.name);
            System.out.print("Column  : ");
            for(String s : t.outColomn){
                System.out.print(" "+s+" ,");
            }
            i++;
            System.out.println(""); 
        }
        
        
            
            
            
            
        
        
        
        
    }
    static void loadTableData(ArrayList<Table> tables){
        String data = "";
        try {
            BufferedReader readData = new BufferedReader(new FileReader("src/javaapplication6/data.csv"));
            data = readData.readLine();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        String[] hasilCsv = data.split("#");
        
        for(String s : hasilCsv){
            String[] temp = s.split(";");
            Table t_table = new Table(temp[0]);
            for(int i = 1;i < temp.length ; i++){
                t_table.colomnName.add(temp[i]);
            }
            tables.add(t_table);
        }
    }
    
    public static void CloseError(String Messege){
        
        System.out.println(Messege);

    }
    
    
    
}
