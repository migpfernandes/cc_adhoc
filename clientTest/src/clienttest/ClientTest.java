/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clienttest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migpfernandes
 */
public class ClientTest {
    public final static String MCAST_ADDR = "FF02::1";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String msgText = "Hello";
            byte[] msg = msgText.getBytes();
            InetAddress group = InetAddress.getByName(MCAST_ADDR);
            MulticastSocket mSocket = new MulticastSocket();
            mSocket.setTimeToLive(1); // Set TTL for all datagrams….
            DatagramPacket message = new DatagramPacket( msg, msg.length, group, 9999);
            mSocket.send(message);
            mSocket.close();
            
            /*
            try {
            // TODO code application logic here
            
            InetAddress multicastAddress = InetAddress.getByName(MCAST_ADDR);
            
            MulticastSocket mSock = new MulticastSocket(9999); // for receiving
            mSock.joinGroup(multicastAddress);
            
            // Receive a datagram
            DatagramPacket packet = new DatagramPacket(new byte[1024],1024);
            
            mSock.receive(packet);
            mSock.close();
            } catch (UnknownHostException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
        } catch (IOException ex) {
            Logger.getLogger(ClientTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
