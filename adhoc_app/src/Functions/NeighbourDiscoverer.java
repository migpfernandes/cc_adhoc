/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

import Common.Global;
import Models.Message.Hello;
import Models.Message.MessageType;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migpfernandes
 */
public class NeighbourDiscoverer implements Runnable {

    public final int TTL = 1;

    public NeighbourDiscoverer() {
    }

    @Override
    public void run() {
        try {
            InitDiscovery();
        } catch (IOException ex) {
            Logger.getLogger(NeighbourDiscoverer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void InitDiscovery() throws UnknownHostException, IOException {
        byte[] msg;

        InetAddress group = InetAddress.getByName(Global.MCAST_ADDR);

        while (true) {
            Hello helloMessage = new Hello(Global.machineName, MessageType.Request, "");
            msg = helloMessage.GetBytes();

            DatagramPacket message = new DatagramPacket(msg, msg.length, group, Global.APP_PORT);

            Global.adhocSocket.SendMessage(message);
            try {
                Thread.sleep( Global.HELLO_INTERVAL * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(NeighbourDiscoverer.class.getName()).log(Level.SEVERE, null, ex);
            }
            Global.peers.InvalidePeerList();
        }
    }
}
