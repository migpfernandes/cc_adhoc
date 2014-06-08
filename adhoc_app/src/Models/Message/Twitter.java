/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models.Message;

/**
 *
 * @author migpfernandes
 */
public class Twitter {
    private String sender;
    private String destination;
    private String message;
    
    public Twitter(String sender,String destination,String message){
        this.sender = sender;
        this.destination = destination;
        this.message = message;
    }

    public Twitter(String messageData){
        String fields[];
        fields = messageData.split("\\|");
        if(fields.length == 4) {
            this.sender = fields[1];
            this.destination = fields[2];
            this.message = fields[3];
        } else {
            throw new IllegalArgumentException("Não é possível construir o objeto Twitter a partir da String recebida.");
        }
    }
    
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
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    
    /*
    MESSAGE|SENDER|DESTINATION|TEXT
    */
    
    public String GetData(){
        StringBuilder sb = new StringBuilder();
        sb.append("MESSAGE");        
        sb.append("|");
        sb.append(this.sender);
        sb.append("|");
        sb.append(this.destination);
        sb.append("|");
        sb.append(this.message);
        sb.append("|");
        return sb.toString();
    }
    
    public byte[] GetBytes(){
        return this.GetData().getBytes();
    }
}
