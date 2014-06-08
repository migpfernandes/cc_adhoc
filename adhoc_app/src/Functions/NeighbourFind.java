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
import java.net.SocketException;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migpfernandes
 */
public class NeighbourFind implements Runnable {

    private final String PEERSEPARATOR = "\t";
    private final int TIMEOUT = 10;

    private final RouteRequest request;

    public NeighbourFind(String neighbour, int leaps) {
        this.request = new RouteRequest(Global.machineName, "", neighbour, leaps, Global.machineName);
    }

    public NeighbourFind(RouteRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        try {
            if (!(Global.peers.contains(request.getPeerToFind()))) {
                int i = 0;
                TreeSet<Peer> peers = new TreeSet<Peer>(Global.peers.getDirectPeers());
                String msgPeers[] = request.getPeers().split(PEERSEPARATOR);

                for (Peer p : peers) {
                    if ((request.getPeers() == null) || (!Arrays.contains(msgPeers, p.getName()))) {
                        this.request.setDestination(p.getName());
                        byte[] msg = this.request.GetBytes();
                        DatagramPacket message = new DatagramPacket(msg, msg.length, p.getNeighbourIP(), Global.APP_PORT);
                        Global.adhocSocket.SendMessage(message);
                    }
                }
                
                while((i<TIMEOUT) && (!peerFound())){
                    i++;
                    Thread.sleep(1000);
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(NeighbourFind.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(NeighbourFind.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean peerFound() {
        return Global.peers.contains(request.getPeerToFind());
    }

}
