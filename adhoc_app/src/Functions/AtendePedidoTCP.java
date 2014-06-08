/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

import Common.Global;
import Models.Message.Twitter;
import Models.Peer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author migpfernandes
 */
public class AtendePedidoTCP implements Runnable {

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public AtendePedidoTCP(Socket s) throws IOException {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new PrintWriter(s.getOutputStream(), true);
    }

    public void run() {
        try {
            String pedido = "";

            while ((!(socket.isClosed())) && (pedido != null) && (!(pedido.toUpperCase().equals("EXIT")))) {
                pedido = in.readLine();
                
                if ((pedido != null) && !(pedido.toUpperCase().equals("EXIT"))){
                    ProcessaPedido(pedido);
                }
            }
            if (!(socket.isClosed())) {
                socket.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(AtendePedidoTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ProcessaPedido(String pedido) throws IOException {
        String destination;
        String message;
        int index = 4;
        if (PedidoValido(pedido)) {
            while (pedido.charAt(index) != ' ') {
                index++;
            }
            destination = pedido.substring(4, index);
            message = pedido.substring(index + 1);

            Twitter msg = new Twitter(Global.machineName, destination, message);
            SendMessage(msg);
        } else {
            out.println("O comando introduzido não foi reconhecido!");
        }
    }

    public boolean PedidoValido(String pedido) {
        Pattern pattern;
        pattern = Pattern.compile("TW #[a-zA-Z0-9]+ .*");

        Matcher matcher = pattern.matcher(pedido);

        return matcher.find();
    }

    public void SendMessage(Twitter message) throws IOException {
        Peer p;
        byte[] msg = message.GetBytes();

        p = Global.peers.get(message.getDestination());
        if (p == null) {
            NeighbourFind nf = new NeighbourFind(message.getDestination(), Global.LEAP_COUNT);
            nf.run();

            if (nf.peerFound()) {
                p = Global.peers.get(message.getDestination());
            }
        }

        if (p == null) {
            out.println(String.format("O peer '%s' não foi encontrado!",message.getDestination()));
        } else {
            DatagramPacket packet = new DatagramPacket(msg, msg.length, p.getNeighbourIP(), Global.APP_PORT);
            Global.adhocSocket.SendMessage(packet);
        }
    }
    
    public void WriteMessage(String from,String message){
        out.println(String.format("From #%s: %s", from,message));
    }
}
