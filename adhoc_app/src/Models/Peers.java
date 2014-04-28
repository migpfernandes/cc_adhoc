/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author migpfernandes
 */
public class Peers {

    private static final String REGISTER_SEPARATOR = "\t";
    private final Map<String, Peer> knownPeers;
    private final String machineName;

    public Peers() {
        this.knownPeers = Collections.synchronizedMap(new TreeMap<String, Peer>());
        this.machineName = "";
    }

    public Peers(String name) {
        this.knownPeers = Collections.synchronizedMap(new TreeMap<String, Peer>());
        this.machineName = name;
    }

    public Collection<Peer> getPeers() {
        return this.knownPeers.values();
    }
    
    public Collection<Peer> getDirectPeers() {
        TreeSet<Peer> res = new TreeSet<Peer>();
        for(Peer p : this.knownPeers.values()){
            if (p.isDirectNeighbour()){
                res.add(p.clone());
            }
        }
        return res;
    }

    public void RegisterPeer(String name, String neighbourname, InetAddress ipaddress, int leaps) {
        Peer store;
        if ((machineName.equals("")) || (!name.equals(machineName))) {
            if (this.knownPeers.containsKey(name)) {
                store = this.knownPeers.get(name);
                store.setLastSuccessfulConnection(new Date());
                if (store.getLeaps() > leaps) {
                    store.setLeaps(leaps);
                }
                store.setErrorsNo(0);
            } else {
                store = new Peer(name, neighbourname, ipaddress);
                store.setLeaps(leaps);
                this.knownPeers.put(name, store);
            }
        }
    }

    public void RegisterPeer(Peer p) {
        this.RegisterPeer(p.getName(), p.getNeighbourName(), p.getNeighbourIP(), p.getLeaps());
    }

    public void RegisterPeers(Peers peers) {
        Peer p;
        Iterator<Peer> it = peers.getPeers().iterator();
        while (it.hasNext()) {
            p = (Peer) it.next();
            RegisterPeer(p.getName(), p.getNeighbourName(), p.getNeighbourIP(), p.getLeaps() + 1);
        }
    }
    
    public Peer get(String name){
        if (this.contains(name)){
            return this.knownPeers.get(name);
        } else {
            return null;
        }
    }
    
    public boolean contains(String peername){
        return this.knownPeers.containsKey(peername);
    }

    public String getDataToMsg() {
        StringBuilder sb = new StringBuilder();
        Peer p;

        Iterator iter = this.getPeers().iterator();
        while (iter.hasNext()) {
            p = (Peer) iter.next();
            sb.append(p.getDataToMsg());
            if (iter.hasNext()) {
                sb.append(REGISTER_SEPARATOR);
            }
        }
        return sb.toString();
    }

    public static Peers fromDataInMsg(String neighbourName, InetAddress neighbourIP, String data) {
        Peers res = new Peers();
        Peer p;

        if (data == null) {
            return res;
        }

        String[] registers;

        registers = data.split(REGISTER_SEPARATOR);
        for (String register : registers) {
            p = Peer.fromDataInMsg(neighbourName, neighbourIP, register);
            res.RegisterPeer(p);
        }
        return res;
    }

    @Override
    public String toString() {
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
