/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Functions;

import Common.Global;
import Models.Message.Hello;
import Models.Message.RouteRequest;
import Models.Message.MessageType;
import Models.Message.RouteReply;
import Models.Peers;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author migpfernandes
 */
public class NetworkListener implements Runnable {
    public final int PORT = 9999;
    public final String MCAST_ADDR = "FF02::1";
    public final int TTL = 1;

    @Override
    public void run() {
        try {
            DatagramSocket s= new DatagramSocket(PORT);
            byte[] aReceber = new byte[1024];
            
            while(true){
                DatagramPacket pedido = new DatagramPacket(aReceber, aReceber.length);
                s.receive(pedido);
                String pedidoString = new String(pedido.getData(), 0, pedido.getLength());
                System.out.println(pedidoString);
                
                ProcessMessage(pedido.getAddress(),pedidoString);
            }
        } catch (SocketException ex) {
            Logger.getLogger(NetworkListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ProcessMessage(InetAddress senderIp, String message){
        if (message.startsWith("HELLO")){
            ProcessHello(senderIp,message);
        } else if (message.startsWith("ROUTE_REQUEST")){
            ProcessRouteRequest(senderIp,message);
        }
    }
    
    //HELLO
    public void ProcessHello(InetAddress senderIp, String message){
        Hello msg = new Hello(message);
        
        if (msg.getSender().equals(Global.machineName)) return;
        
        AddSenderPeer(msg.getSender(),senderIp);
        
        if (msg.getType() == MessageType.Request){
            try {
                SendHelloReply(senderIp);
            } catch (IOException ex) {
                Logger.getLogger(NetworkListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Global.peers.RegisterPeers(Peers.fromDataInMsg(
                    msg.getSender(),senderIp,msg.getPeers()));
        }
    }
    
    public void SendHelloReply(InetAddress senderIp) throws SocketException, IOException{
        Hello helloMessage = new Hello(Global.machineName, MessageType.Reply, 
                Global.peers.getDataToMsg());
        
        System.out.println("\n\nHelloReply: " + helloMessage.GetData());
        
        byte[] msg = helloMessage.GetBytes();
        
        DatagramSocket s = new DatagramSocket();
        
        DatagramPacket p = new DatagramPacket(msg,msg.length,senderIp,PORT);
        s.send(p);
    }
    
    
    //ROUTE REQUEST
    public void ProcessRouteRequest(InetAddress senderIp,String message){
        RouteRequest msg = new RouteRequest(message);
        if ((msg.getPeers()!= null) && (msg.getPeers().contains(Global.machineName))){
            //Não faz nada
        } else if (msg.getRadius()<=1){
            throw new NotImplementedException();
        } else if (msg.getDestination().equals(Global.machineName)){
            throw new NotImplementedException();
        } else {
            msg.setRadius(msg.getRadius()-1);
            msg.appendPeer(Global.machineName);
            Thread thread = new Thread(new NeighbourFind(msg));
           thread.start();
        }
    }
    
    private void AddSenderPeer(String name,InetAddress ipaddress){        
        Global.peers.RegisterPeer(name, name, ipaddress, 1);
    }
        
    
}
