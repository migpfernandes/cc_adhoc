/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import com.google.gson.Gson;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author migpfernandes
 */
public class Peers {
    
    private final Map<String,Peer> knownPeers;
    
    public Peers(){
        this.knownPeers = Collections.synchronizedMap(new TreeMap<String,Peer>());
    }
    
    public Collection<Peer> getPeers(){
        return this.knownPeers.values();
    }
    
    public void RegisterPeer(String name,String neighbourname,InetAddress ipaddress,int leaps){
        Peer store;
        if (this.knownPeers.containsKey(name)){
            store = this.knownPeers.get(name);
            store.setLastSuccessfulConnection(new Date());
            if (store.getLeaps()>leaps) store.setLeaps(leaps);
            store.setErrorsNo(0);
        } else {
            store = new Peer(name, neighbourname, ipaddress);
            store.setLeaps(leaps);
            this.knownPeers.put(name, store);
        }
    }
    
    public void RegisterPeers(Peers peers){
        Peer p;
        Iterator<Peer> it = peers.getPeers().iterator();
        while(it.hasNext()){
            p = (Peer) it.next();
            RegisterPeer(p.getName(),p.getNeighbourName(),p.getNeighbourIP(),p.getLeaps() + 1);
        }
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
        
        Iterator iter = this.getPeers().iterator();
        while (iter.hasNext()) {
            p = (Peer) iter.next();
            sb.append(p.toString());
            sb.append("\n\n");
        }
        return sb.toString();
    }
    
}
