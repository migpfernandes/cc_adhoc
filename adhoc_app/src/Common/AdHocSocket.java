/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 *
 * @author migpfernandes
 */
public class AdHocSocket {
    
    public final int PORT = 9999;
    public final String MCAST_ADDR = "FF02::1";    
    public final int TTL = 1;
    
    private MulticastSocket mSocket;
    
    public AdHocSocket() throws UnknownHostException, IOException{
        InetAddress group = InetAddress.getByName(MCAST_ADDR);
        mSocket = new MulticastSocket(PORT);
        mSocket.setTimeToLive(TTL);
        
        mSocket.joinGroup(group);
    }
    
    public void SendMessage(DatagramPacket message) throws IOException{
        mSocket.send(message);
    }
    
    public DatagramPacket receiveDatagram() throws IOException{
        byte[] aReceber = new byte[1024];
        DatagramPacket pedido = new DatagramPacket(aReceber, aReceber.length);
        mSocket.receive(pedido);
        return pedido;
    }
}
