/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 *
 * @author migpfernandes
 */
public class Peers {

    private static final String REGISTER_SEPARATOR = "\t";
    private final ConcurrentSkipListMap<String, Peer> knownPeers;
    private final String machineName;

    public Peers() {
        //this.knownPeers = Collections.synchronizedMap(new TreeMap<String, Peer>());
        this.knownPeers = new ConcurrentSkipListMap<String, Peer>() {
        };
        this.machineName = "";
    }

    public Peers(String name) {
        this.knownPeers = new ConcurrentSkipListMap<String, Peer>();
        this.machineName = name;
    }

    public Collection<Peer> getPeers() {
        return this.knownPeers.values();
    }

    public Collection<Peer> getDirectPeers() {
        TreeSet<Peer> res = new TreeSet<Peer>();
        for (Peer p : this.knownPeers.values()) {
            if (p.isDirectNeighbour()) {
                res.add(p.clone());
            }
        }
        return res;
    }

    public void RegisterPeer(String name, String neighbourname,
            InetAddress ipaddress, int leaps) {
        Peer store;
        if ((machineName.equals("")) || (!name.equals(machineName))) {
            if (this.knownPeers.containsKey(name)) {
                store = this.knownPeers.get(name);
                store.refreshLastSuccessfulConnection();
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
        this.RegisterPeer(p.getName(), p.getNeighbourName(),
                p.getNeighbourIP(), p.getLeaps());
    }

    /**
     * Método utilizado no processamento de mensagens Hello
     *
     * @param peers
     * @param neighbourname
     */
    public void RegisterPeers(String neighbourname, Peers peers) {
        Peer p;
        TreeSet currentPeers = new TreeSet<String>();
        Iterator<Peer> it = peers.getPeers().iterator();
        while (it.hasNext()) {
            p = (Peer) it.next();
            currentPeers.add(p.getName());
            RegisterPeer(p.getName(), p.getNeighbourName(), p.getNeighbourIP(),
                    p.getLeaps() + 1);
        }

        //Atualiza lista de peers atingidos pelo vizinho em questão
        it = peers.getPeers().iterator();
        while (it.hasNext()) {
            p = (Peer) it.next();
            if ((p.getNeighbourName().equals(neighbourname)) && (!p.isDirectNeighbour())
                    && (!currentPeers.contains(p.getName()))) {
                knownPeers.remove(p.getName());
            }
        }
    }

    public Peer get(String name) {
        if (this.contains(name)) {
            return this.knownPeers.get(name);
        } else {
            return null;
        }
    }

    public boolean contains(String peername) {
        return this.knownPeers.containsKey(peername);
    }

    /**
     * Devolve uma string com uma lista de peers removendo os peers atingidos a
     * partir do vizinho
     *
     * @param neighbour
     * @return
     */
    public String getDataToMsg(String neighbour) {
        StringBuilder sb = new StringBuilder();
        Peer p;

        Iterator iter = this.getPeers().iterator();
        while (iter.hasNext()) {
            p = (Peer) iter.next();
            if (!p.getNeighbourName().equals(neighbour)) {
                
                if(sb.length()!=0) sb.append(REGISTER_SEPARATOR);
                
                sb.append(p.getDataToMsg());
            }
        }
        return sb.toString();
    }

    /**
     * Remove os peers cujo último contacto tenha sido à um período maior que o
     * DEAD_INTERVAL Caso o peer a remover seja um vizinho direto remove também
     * todas as rotas que o involvam
     */
    public void InvalidePeerList() {
        TreeSet nbRemoved = new TreeSet<String>();
        String peer_id;
        Peer p;
        Iterator it = this.knownPeers.keySet().iterator();
        while (it.hasNext()) {
            peer_id = (String) it.next();
            p = knownPeers.get(peer_id);
            if ((p.Expired()) && (p.isDirectNeighbour())) {
                knownPeers.remove(peer_id);
                nbRemoved.add(peer_id);
            }
        }

        it = this.knownPeers.keySet().iterator();
        while (it.hasNext()) {
            peer_id = (String) it.next();
            p = knownPeers.get(peer_id);
            if (nbRemoved.contains(p.getNeighbourName())) {
                knownPeers.remove(peer_id);
            }
        }
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
            sb.append("\n");
        }
        return sb.toString();
    }

}
