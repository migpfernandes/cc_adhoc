/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Common;

import Models.Peers;

/**
 *
 * @author migpfernandes
 */
public class Global {
    public static Peers peers;
    public static String machineName="";
    public static AdHocSocket adhocSocket;
    
    public static int APP_PORT = 9999;
    public static String MCAST_ADDR = "FF02::1";
    
    // Tempo entre envio de HELLO BROADCAST
    public static int HELLO_INTERVAL = 10;
    
    //Tempo a partir do qual um peer é considerado incontactavel
    public static int DEAD_INTERVAL = 30; 
}
