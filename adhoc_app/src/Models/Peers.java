/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import com.google.gson.Gson;
import java.util.TreeMap;
import java.util.Iterator;

/**
 *
 * @author migpfernandes
 */
public class Peers extends TreeMap<String,Peer>{
    
    public Peers(){
        super();
    }
    
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    
    public static Peers fromJson(String json){
        Gson gson = new Gson();
        
        return gson.fromJson(json, Peers.class); 
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Peer p;
        
        sb.append("Peers:\n");
        
        Iterator iter = this.values().iterator();
        while (iter.hasNext()) {
            p = (Peer) iter.next();
            sb.append(p.toString());
            sb.append("\n\n");
        }
        return sb.toString();
    }
    
}
