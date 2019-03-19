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
public class CheckPoint {
    public int index;
    public String status;
    public ArrayList<String> next;
    
    public CheckPoint(int index, String status){
        this.index = index;
        this.status = status;
        next = new ArrayList<String>();
        
        if(status.toLowerCase().equals("from")){
            next.add("join");
            next.add("xjoin");
        }else if(status.toLowerCase().equals("join")){
            next.add("on");
        }else if(status.toLowerCase().equals("xjoin")){
            next.add("xjoin");
        }else if(status.toLowerCase().equals("on")){
            next.add("join");
        }else if(status.toLowerCase().equals("select")){
            next.add("from");
        }
    }
    
    public static boolean isCheckPoint(String word){

        if(word.toLowerCase().equals("from")){
            return true;
        }else if(word.toLowerCase().equals("join")){
            return true;
        }else if(word.toLowerCase().equals("on")){
            return true;
        }else if(word.toLowerCase().equals("select")){
            return true;
        }
        
        return false;
    }
    
    
}