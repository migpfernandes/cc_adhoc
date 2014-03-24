/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Functions;

import Common.Global;
import Models.Message.MessageType;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Models.Message.Hello helloMessage = new Models.Message.Hello(MessageType.Request,"");
        
        byte[] msg = helloMessage.GetBytes();
        
        InetAddress group = InetAddress.getByName(MCAST_ADDR);
        MulticastSocket mSocket = new MulticastSocket(); 
        mSocket.setTimeToLive(TTL);
        
        DatagramPacket message = new DatagramPacket(msg, msg.length, group, PORT);
        mSocket.send(message);
        
        mSocket.close();
     }
    
}
