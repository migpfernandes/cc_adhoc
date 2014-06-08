/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

import Common.Global;
import Models.Message.Twitter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migpfernandes
 */
public class MessageSockets implements Runnable{
    private ArrayList<AtendePedidoTCP> clientList;
    
    @Override
    public void run() {
        try {
            clientList = new ArrayList<AtendePedidoTCP>();
            ServerSocket welcomeSocket = new ServerSocket(Global.APP_PORT);
            
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                AtendePedidoTCP attend = new AtendePedidoTCP(connectionSocket);
                //Thread thread = new Thread(new AtendePedidoTCP(connectionSocket));
                
                clientList.add(attend);
                Thread thread = new Thread(attend);
                thread.start();
                
                /*
                 AtendePedidoTCP atendedor = new AtendePedidoTCP(connectionSocket);
                
                 String pedido = in.readLine();
                 System.out.println(pedido);
                 */
                /*
                 AtendePedidoTCP atendedor = new AtendePedidoTCP(connectionSocket);
                 atendedor.start();
                
                
                 DatagramPacket pedido = Global.adhocSocket.receiveDatagram();
                 String pedidoString = new String(pedido.getData(), 0, pedido.getLength());
                 System.out.println(pedidoString);

                 ProcessMessage(pedido.getAddress(), pedidoString);
                 */
            }
        } catch (SocketException ex) {
            Logger.getLogger(NetworkListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeMessage(Twitter msg){
        for (AtendePedidoTCP canal : clientList){
            canal.WriteMessage(msg.getSender(), msg.getMessage());
        }
    }
    
}
