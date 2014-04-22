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
public class RouteReply {

    private String sender;
    private String peers;
    private String destination;
    private int leaps;
    
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
    
    
    /**
     * @return the leaps
     */
    public int getLeaps() {
        return leaps;
    }

    /**
     * @param leaps the leaps to set
     */
    public void setLeaps(int leaps) {
        this.leaps = leaps;
    }
    
    
    public RouteReply(){
        this.sender = "";
        this.destination = "";
        this.leaps = -1;
        this.peers = "";
        
    }
    
    public RouteReply(String sender,String destination,int leaps,String peers){
        this.sender = sender;
        this.destination = destination;
        this.leaps = leaps;
        this.peers = peers;
    }
    
    public RouteReply(String messageData){
        String fields[];
        fields = messageData.split("\\|");
        if(fields.length >= 3) {
            this.sender = fields[1];
            this.destination = fields[2];
            this.leaps = Integer.parseInt(fields[3]);
            
            if (fields.length == 5) this.peers = fields[4];
            
        } else {
            throw new IllegalArgumentException("Não é possível construir o objeto HelloReply a partir da String recebida.");
        }
    }
    
    /*
    ROUTE_REQUEST|MACHINE|DESTINATION|JUMPS|PEERS_PATHOFRETURN
    */
    
    public String GetData(){
        StringBuilder sb = new StringBuilder();
        sb.append("ROUTE_REPLY");        
        sb.append("|");
        sb.append(Global.machineName);
        sb.append("|");
        sb.append(this.getDestination());
        sb.append("|");
        sb.append(this.getLeaps());
        sb.append("|");
        sb.append(peers);
        sb.append("|");
        return sb.toString();
    }
    
    public byte[] GetBytes(){
        return this.GetData().getBytes();
    }
}
