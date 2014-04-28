/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Functions;

import Common.Global;
import Models.Message.RouteRequest;
import Models.Peer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migpfernandes
 */
public class NeighbourFind implements Runnable {
    public final int PORTSENDER = 9998;
    public final int PORTDESTINATION = 9999;
    
    private RouteRequest request;
    
    public NeighbourFind(String neighbour,int leaps){
        this.request = new RouteRequest(Global.machineName,"",neighbour,leaps, Global.machineName);
    }
    
    public NeighbourFind(RouteRequest request){
        this.request = request;
    }
    
    @Override
    public void run() {
        try {
            
            
            TreeSet<Peer> peers = new TreeSet<Peer>(Global.peers.getDirectPeers());
            DatagramSocket s = new DatagramSocket(PORTSENDER);
            
            for(Peer p : peers){
                if(!(request.getPeers().contains(p.getName()))){
                    this.request.setDestination(p.getName());
                    byte[] msg = this.request.GetBytes();
                    DatagramPacket message = new DatagramPacket(msg, msg.length, p.getNeighbourIP(), PORTDESTINATION);
                    s.send(message);
                }
            }
            s.close();
            
        } catch (SocketException ex) {
            Logger.getLogger(NeighbourFind.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NeighbourFind.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
