/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models.Message;

import Common.Global;

/**
 *
 * @author migpfernandes
 */
public class RouteRequest {

    private String sender;
    private String peers;
    private int radius;
    private int timeout;
    private String destination;
    
    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }
    

    /**
     * @return the peers
     */
    public String getPeers() {
        return peers;
    }

    /**
     * @param peers the peers to set
     */
    public void setPeers(String peers) {
        this.peers = peers;
    }
    
    
    /**
     * @return the radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    
    /**
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    
    public RouteRequest(){
        this.sender = "";
        this.destination = "";
        this.radius = -1;
        this.peers = "";
        
    }
    
    public RouteRequest(String sender,String destination,int radius,String peers){
        this.sender = sender;
        this.destination = destination;
        this.radius = radius;
        this.peers = peers;
    }
    
    public RouteRequest(String messageData){
        String fields[];
        fields = messageData.split("\\|");
        if(fields.length >= 3) {
            this.sender = fields[1];
            this.destination = fields[2];
            this.radius = Integer.parseInt(fields[3]);
            
            if (fields.length == 5) this.peers = fields[4];
            
        } else {
            throw new IllegalArgumentException("Não é possível construir o obejto HelloRequest a partir da String recebida.");
        }
    }
    
    /*
    ROUTE_REQUEST|MACHINE|DESTINATION|JUMPS|PEERS_PATHOFRETURN
    */
    
    public String GetData(){
        StringBuilder sb = new StringBuilder();
        sb.append("ROUTE_REQUEST");        
        sb.append("|");
        sb.append(Global.machineName);
        sb.append("|");
        sb.append(this.getDestination());
        sb.append("|");
        sb.append(this.getRadius());
        sb.append("|");
        sb.append(peers);
        sb.append("|");
        return sb.toString();
    }
    
    public byte[] GetBytes(){
        return this.GetData().getBytes();
    }
}
