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
    
    public Hello(String messageData){
        String fields[];
        String typeMsg,jsonPeers;
        fields = messageData.split("\\|");
        if(fields.length >= 2) {
            typeMsg = fields[1];
            
            this.type = MessageType.fromInteger(Integer.parseInt(typeMsg));
            if (fields.length == 3) this.peers = fields[2];
            
        } else {
            throw new IllegalArgumentException("Não é possível construir o obejto HelloMessage a partir da String recebida.");
        }
    }
    
    public String GetData(){
        StringBuilder sb = new StringBuilder();
        sb.append("HELLO");        
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

    private MessageType MessageType(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
