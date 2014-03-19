/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Functions;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 *
 * @author migpfernandes
 */
public class NeighbourDiscoverer {
    public final int PORT = 9999;
    public final String MCAST_ADDR = "FF02::1";
    public final int TTL = 1;
    
    public NeighbourDiscoverer(){
    }
    
    public void InitDiscovery() throws UnknownHostException, IOException{
        // join a Multicast group and send the group salutations ... 
        String msg = "Hello";
        InetAddress group = InetAddress.getByName(MCAST_ADDR);
        MulticastSocket s = new MulticastSocket(); 
        //s.joinGroup(group);
        s.setTimeToLive(TTL);
        DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(), group, PORT);
        s.send(hi);
        s.close();
        
        /*// get their responses!
        byte[] buf = new byte[1000];
        DatagramPacket recv = new DatagramPacket(buf, buf.length); 
        s.receive(recv);
        
        // OK, I'm done talking - leave the group...
        s.leaveGroup(group);
    */
     }
    
}
