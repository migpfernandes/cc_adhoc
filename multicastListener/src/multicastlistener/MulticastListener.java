package multicastlistener;

import java.io.*;
import java.net.*;
import java.util.Enumeration;

/**
 *
 * @author migpfernandes
 */
public class MulticastListener {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      String ip = "FF02::1";
      String name = "LISTENER";
      int port = 9999;
      try {
         //InetAddress group = InetAddress.getByName(ip);
         SocketAddress group = new InetSocketAddress(ip, 9999);
         MulticastSocket s = new MulticastSocket(port);
         
         Enumeration<NetworkInterface> ifs =
                NetworkInterface.getNetworkInterfaces();
         while (ifs.hasMoreElements()) {
            NetworkInterface nic = ifs.nextElement();
            s.joinGroup(group,nic);
         }
         
         
         byte[] buffer = new byte[10*1024];
         DatagramPacket data = new DatagramPacket(buffer, buffer.length);
         while (true) {
            s.receive(data);
            System.out.println("Received: "+ 
               (new String(buffer, 0, data.getLength())));
         }
      } catch (IOException e) {
         System.out.println(e.toString());
      }
    }
    
}
