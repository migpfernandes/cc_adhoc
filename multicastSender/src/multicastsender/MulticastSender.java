/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package multicastsender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.util.Enumeration;

/**
 *
 * @author migpfernandes
 */
public class MulticastSender {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      String ip = "FF02::1";
      
      int port = 9999;
      try {
         InetAddress group = InetAddress.getByName(ip);
         //SocketAddress group = new InetSocketAddress(ip, 9999);
         MulticastSocket s = new MulticastSocket(port);
         
         String msgText = "Hello";
         byte[] msg = msgText.getBytes();
         
         s.setTimeToLive(1); // Set TTL for all datagrams….
         DatagramPacket message = new DatagramPacket( msg, msg.length, group,9999);
         
         
         Enumeration<NetworkInterface> ifs =
                NetworkInterface.getNetworkInterfaces();
         while (ifs.hasMoreElements()) {
            NetworkInterface nic = ifs.nextElement();
            System.out.println("NIC: " + nic.getName());
            //s.joinGroup(group,nic);
            if (!nic.isLoopback()){
            s.setNetworkInterface(nic);
            s.joinGroup(group);
            s.send(message);
            }
         }
         

         
         
      } catch (IOException e) {
         System.out.println(e.toString());
      }
    }
}
