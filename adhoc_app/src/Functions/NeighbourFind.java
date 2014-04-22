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
    public final int PORT = 9999;
    
    private final String destination;
    private final int leaps;
    
    public NeighbourFind(String neighbour,int leaps){
        this.destination = neighbour;
        this.leaps = leaps;
    }
    
    @Override
    public void run() {
        try {
            RouteRequest request = new RouteRequest(Global.machineName, destination,leaps, "");
            byte[] msg = request.GetBytes();
            
            TreeSet<Peer> peers = new TreeSet<Peer>(Global.peers.getDirectPeers());
            DatagramSocket s = new DatagramSocket(PORT);
            
            for(Peer p : peers){
                DatagramPacket message = new DatagramPacket(msg, msg.length, p.getNeighbourIP(), PORT);
                s.send(message);
            }
            s.close();
            
        } catch (SocketException ex) {
            Logger.getLogger(NeighbourFind.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NeighbourFind.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
