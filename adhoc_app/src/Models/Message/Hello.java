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
public class Hello {

    private String sender;
    private MessageType type;
    private String peers;
    
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
     * @return the type
     */
    public MessageType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MessageType type) {
        this.type = type;
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

    public Hello(){
        this.type = null;
        this.peers = "";
    }
    
    public Hello(String sender,MessageType type,String peers){
        this.sender = sender;
        this.type = type;
        this.peers = peers;
    }
    
    public Hello(String messageData){
        String fields[];
        String typeMsg;
        fields = messageData.split("\\|");
        if(fields.length >= 3) {
            sender = fields[1];
            typeMsg = fields[2];
            
            this.type = MessageType.fromInteger(Integer.parseInt(typeMsg));
            if (fields.length == 4) this.peers = fields[3];
            
        } else {
            throw new IllegalArgumentException("Não é possível construir o objeto HelloMessage a partir da String recebida.");
        }
    }
    
    //MsgFormat: HELLO|MACHINENAME|REQUESTORREPLY|peerlist|
    
    public String GetData(){
        StringBuilder sb = new StringBuilder();
        sb.append("HELLO");        
        sb.append("|");
        sb.append(Global.machineName);
        sb.append("|");
        sb.append(type.getValue());
        sb.append("|");
        sb.append(peers);
        sb.append("|");
        return sb.toString();
    }
    
    public byte[] GetBytes(){
        return this.GetData().getBytes();
    }
}
