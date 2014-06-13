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
    private String answerType;
    private String peerToFind;
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
     * @return the answerType
     */
    public String getAnswerType() {
        return answerType;
    }

    /**
     * @param answerType the answerType to set
     */
    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }
    
    /**
     * @return the peerToFind
     */
    public String getPeerToFind() {
        return peerToFind;
    }

    /**
     * @param peerToFind the peerToFind to set
     */
    public void setPeerToFind(String peerToFind) {
        this.peerToFind = peerToFind;
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
        this.peers = "";
        this.answerType = "";
        this.leaps = 1;
        this.peerToFind = "";
    }
    
    public RouteReply(String sender,String destination,String peerToFind,int leaps,String peers,String answer){
        this.sender = sender;
        this.destination = destination;
        this.peerToFind = peerToFind;
        this.answerType = answer;
        this.leaps = leaps;
        this.peers = peers;
    }
    
    public RouteReply(String messageData){
        String fields[];
        fields = messageData.split("\\|");
        if(fields.length >= 6) {
            this.sender = fields[1];
            this.destination = fields[2];
            this.peerToFind = fields[3];
            this.answerType = fields[4];
            this.leaps = Integer.parseInt(fields[5]);
            
            if (fields.length == 7) this.peers = fields[6];
            
        } else {
            throw new IllegalArgumentException("Não é possível construir o objeto HelloReply a partir da String recebida.");
        }
    }
    
    /*
    ROUTE_REPLY|MACHINE|DESTINATION|PEER2FIND|ANSWER|LEAPS|PEERS_PATHOFRETURN
    */
    
    public String GetData(){
        StringBuilder sb = new StringBuilder();
        sb.append("ROUTE_REPLY");        
        sb.append("|");
        sb.append(Global.machineName);
        sb.append("|");
        sb.append(this.getDestination());
        sb.append("|");
        sb.append(this.getPeerToFind());
        sb.append("|");
        sb.append(this.getAnswerType());
        sb.append("|");
        sb.append(this.getLeaps());
        sb.append("|");
        sb.append(this.peers);
        sb.append("|");
        return sb.toString();
    }
    
    public byte[] GetBytes(){
        return this.GetData().getBytes();
    }
}
