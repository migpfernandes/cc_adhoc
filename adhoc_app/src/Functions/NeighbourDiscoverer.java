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
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migpfernandes
 */
public class NeighbourDiscoverer implements Runnable {

    public final int PORT = 9999;
    public final String MCAST_ADDR = "FF02::1";
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
        Hello helloMessage = new Hello(Global.machineName, MessageType.Request, "");

        byte[] msg = helloMessage.GetBytes();

        InetAddress group = InetAddress.getByName(MCAST_ADDR);
        MulticastSocket mSocket = new MulticastSocket();
        mSocket.setTimeToLive(TTL);

        DatagramPacket message = new DatagramPacket(msg, msg.length, group, PORT);

        //Testar sem este bloco
        Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
        while (ifs.hasMoreElements()) {
            NetworkInterface nic = ifs.nextElement();

            if (!nic.isLoopback()) {
                mSocket.setNetworkInterface(nic);
                mSocket.joinGroup(group);
                mSocket.send(message);
            }
        }
        mSocket.close();
    }

}
