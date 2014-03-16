/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adhoc_app;

import Models.Peer;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
            // TODO code application logic here
            Teste();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Adhoc_app.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
    }
}
