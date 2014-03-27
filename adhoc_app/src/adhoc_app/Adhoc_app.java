/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adhoc_app;

import Common.Global;
import Models.Peers;
import Functions.NeighbourDiscoverer;
import Functions.NetworkListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migpfernandes
 */
public class Adhoc_app {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            while (Global.machineName.equals("")){
                System.out.println("Introduza o nome da máquina:");
                Global.machineName = System.console().readLine();
                if (Global.machineName.equals("")) System.out.println("Nome inválido!\n\n");
            }
            
            Global.peers = new Peers(Global.machineName);
            
            NeighbourDiscoverer nd = new NeighbourDiscoverer();
            
            Thread thread = new Thread(new NetworkListener());
            thread.start(); 
            
            nd.InitDiscovery();
            
        } catch (IOException ex) {
            Logger.getLogger(Adhoc_app.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
    try {
        InetAddress candidateAddress = null;
        // Iterate all NICs (network interface cards)...
        for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
            NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
            // Iterate all IP addresses assigned to each card...
            for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                if (!inetAddr.isLoopbackAddress()) {

                    if (inetAddr.isSiteLocalAddress()) {
                        // Found non-loopback site-local address. Return it immediately...
                        return inetAddr;
                    }
                    else if (candidateAddress == null) {
                        // Found non-loopback address, but not necessarily site-local.
                        // Store it as a candidate to be returned if site-local address is not subsequently found...
                        candidateAddress = inetAddr;
                        // Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
                        // only the first. For subsequent iterations, candidate will be non-null.
                    }
                }
            }
        }
        if (candidateAddress != null) {
            // We did not find a site-local address, but we found some other non-loopback address.
            // Server might have a non-site-local address assigned to its NIC (or it might be running
            // IPv6 which deprecates the "site-local" concept).
            // Return this non-loopback candidate address...
            return candidateAddress;
        }
        // At this point, we did not find a non-loopback address.
        // Fall back to returning whatever InetAddress.getLocalHost() returns...
        InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
        if (jdkSuppliedAddress == null) {
            throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
        }
        return jdkSuppliedAddress;
    }
    catch (Exception e) {
        UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
        unknownHostException.initCause(e);
        throw unknownHostException;
    }
}
    
    /*
    private static void Teste() throws UnknownHostException{
        Peer p2;
        String json;
        InetAddress address = InetAddress.getLocalHost();
        Peer p = new Peer("Mac","Vizinho",address);
        System.out.println("inicial: " + p.toString() + "\n");
        
        json = p.toJson();
        p2 = Peer.fromJson(json);
        System.out.println("json: " + json + "\n");
        System.out.println("final: " + p2.toString() + "\n");
        
        p2.setName("Mac2");
        Peers peers = new Peers();
        peers.put(p.getName(), p);
        peers.put(p2.getName(),p2);
        
        json = peers.toJson();
        System.out.println("colection json: " + json + "\n");

        Peers peers2 = Peers.fromJson(json);
        System.out.println(peers2.toString());
    }
    */
}
