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
import Models.Peer;
import Models.Peers;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author migpfernandes
 */
public class NetworkListener implements Runnable {

    private static final int PORT = 9999;
    private static final String MCAST_ADDR = "FF02::1";
    private static final int TTL = 1;
    private static final String PEERSEPARATOR = "\t";

    @Override
    public void run() {
        try {
            DatagramSocket s = new DatagramSocket(PORT);
            byte[] aReceber = new byte[1024];

            while (true) {
                DatagramPacket pedido = new DatagramPacket(aReceber, aReceber.length);
                s.receive(pedido);
                String pedidoString = new String(pedido.getData(), 0, pedido.getLength());
                System.out.println(pedidoString);

                ProcessMessage(pedido.getAddress(), pedidoString);
            }
        } catch (SocketException ex) {
            Logger.getLogger(NetworkListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ProcessMessage(InetAddress senderIp, String message) {
        try {
            if (message.startsWith("HELLO")) {
                ProcessHello(senderIp, message);
            } else if (message.startsWith("ROUTE_REQUEST")) {
                ProcessRouteRequest(message);
            } else if (message.startsWith("ROUTE_REPLY")) {
                ProcessRouteReply(message);
            }
        } catch (IOException ex) {
            Logger.getLogger(NetworkListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //HELLO
    public void ProcessHello(InetAddress senderIp, String message) throws IOException {
        Hello msg = new Hello(message);

        if (msg.getSender().equals(Global.machineName)) {
            return;
        }

        AddSenderPeer(msg.getSender(), senderIp);

        if (msg.getType() == MessageType.Request) {
            SendHelloReply(senderIp);
        } else {
            Global.peers.RegisterPeers(Peers.fromDataInMsg(
                    msg.getSender(), senderIp, msg.getPeers()));
        }
    }

    public void SendHelloReply(InetAddress senderIp) throws SocketException, IOException {
        Hello helloMessage = new Hello(Global.machineName, MessageType.Reply,
                Global.peers.getDataToMsg());

        byte[] msg = helloMessage.GetBytes();

        DatagramSocket s = new DatagramSocket();

        DatagramPacket p = new DatagramPacket(msg, msg.length, senderIp, PORT);
        s.send(p);
    }

    //ROUTE REQUEST
    public void ProcessRouteRequest(String message) throws IOException {
        String peers[];
        RouteRequest msg = new RouteRequest(message);
        
        if ((msg.getPeers() != null)
                && (Arrays.contains(msg.getPeers().split(PEERSEPARATOR), Global.machineName))) {
            //Não faz nada
            System.out.println("Não pode passar aqui.");
        } else if ((msg.getRadius() < 1) || (allPeersQueried(msg.getPeers()))) {
            System.out.println("Terminou pesquisa sem encontrar.");
            RespondToRouteRequest(msg, false);
        } else if (Global.peers.contains(msg.getPeerToFind())) {
            System.out.println("Encontrou o que procurava.");
            RespondToRouteRequest(msg, true);
        } else {
            System.out.println("Propagar pesquisa.");
            msg.setRadius(msg.getRadius() - 1);
            msg.appendPeer(Global.machineName);
            Thread thread = new Thread(new NeighbourFind(msg));
            thread.start();
        }
    }

    private void RespondToRouteRequest(RouteRequest request, boolean foundPeer) throws SocketException, IOException {
        String destination, destinations;
        String answer;
        int leaps;
        Peer peer;

        destinations = getDestinations(request.getPeers());
        destination = PopDestination(request.getPeers());

        if (foundPeer) {
            answer = "OK";
            peer = Global.peers.get(request.getPeerToFind());
            leaps = peer.getLeaps() + 1;
        } else {
            answer = "NF";
            leaps = 1;
        }

        peer = Global.peers.get(destination);

        RouteReply reply = new RouteReply(Global.machineName, destination, request.getPeerToFind(),
                leaps, destinations, answer);

        byte[] msg = reply.GetBytes();

        DatagramSocket s = new DatagramSocket();

        DatagramPacket p = new DatagramPacket(msg, msg.length, peer.getNeighbourIP(), PORT);
        s.send(p);
    }

    private boolean allPeersQueried(String peers) {
        boolean res = true;
        Peer p;

        if (peers != null) {
            String ps[] = peers.split(PEERSEPARATOR);
            Iterator<Peer> it = Global.peers.getPeers().iterator();
            while ((res) && (it.hasNext())) {
                p = (Peer) it.next();
                res = (!Arrays.contains(ps, p.getName()));
            }
        }
        return res;
    }

    //ROUTE REPLY
    private void ProcessRouteReply(String message) throws SocketException, IOException {
        String destination;
        RouteReply reply = new RouteReply(message);

        if (reply.getAnswerType().equals("OK")) {
            Peer peer = Global.peers.get(reply.getSender());
            Global.peers.RegisterPeer(reply.getPeerToFind(), peer.getName(), peer.getNeighbourIP(), reply.getLeaps());
        }

        if ((reply.getPeers() != null) && !(reply.getPeers().isEmpty())) {
            destination = PopDestination(reply.getPeers());
            reply.setPeers(getDestinations(reply.getPeers()));
            reply.setDestination(destination);
            reply.setSender(Global.machineName);
            reply.setLeaps(reply.getLeaps() + 1);

            Peer peer = Global.peers.get(destination);

            byte[] msg = reply.GetBytes();

            DatagramSocket s = new DatagramSocket();

            DatagramPacket p = new DatagramPacket(msg, msg.length, peer.getNeighbourIP(), PORT);
            s.send(p);
        } else if (reply.getAnswerType().equals("NF")) {
            System.out.println("O peer " + reply.getPeerToFind() + " não foi encontrado.");
        }
    }

    private void AddSenderPeer(String name, InetAddress ipaddress) {
        Global.peers.RegisterPeer(name, name, ipaddress, 1);
    }

    private static String PopDestination(String destinations) {
        String fields[];

        fields = destinations.split(PEERSEPARATOR);

        return fields[fields.length - 1];
    }

    private static String getDestinations(String destinations) {
        String fields[];
        String result = "";
        int i = 0;

        fields = destinations.split(PEERSEPARATOR);
        while (i < fields.length - 1) {
            if (result.isEmpty()) {
                result = fields[i];
            } else {
                result = result + PEERSEPARATOR + fields[i];
            }
            i++;
        }
        return result;
    }
}
