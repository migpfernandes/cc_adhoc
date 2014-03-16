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
public class Hello {

    private MessageType type;
    private String peers;
    
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
    
    public Hello(MessageType type,String peers){
        this.type = type;
        this.peers = peers;
    }
    
    public String GetData(){
        StringBuilder sb = new StringBuilder();
        sb.append("HELLO");        
        sb.append("|");
        sb.append(type.getValue());
        sb.append("|");
        sb.append(peers);
        return sb.toString();
    }
    
}
