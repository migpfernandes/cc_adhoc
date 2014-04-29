/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import java.net.InetAddress;
import java.util.Date;

/**
 *
 * @author migpfernandes
 */
public class Peer implements Comparable{
    private static final String FIELD_SEPARATOR = "&";
    
    private String Name;
    private int ErrorsNo;
    private String NeighbourName;
    private InetAddress NeighbourIP;
    private int Leaps;
    private Date LastSuccessfulConnection;

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the ErrorsNo
     */
    public int getErrorsNo() {
        return ErrorsNo;
    }

    /**
     * @param ErrorsNo the ErrorsNo to set
     */
    public void setErrorsNo(int ErrorsNo) {
        this.ErrorsNo = ErrorsNo;
    }

    /**
     * @return the NeighbourName
     */
    public String getNeighbourName() {
        return NeighbourName;
    }

    /**
     * @param NeighbourName the NeighbourName to set
     */
    public void setNeighbourName(String NeighbourName) {
        this.NeighbourName = NeighbourName;
    }

    /**
     * @return the NeighbourIP
     */
    public InetAddress getNeighbourIP() {
        return NeighbourIP;
    }

    /**
     * @param NeighbourIP the NeighbourIP to set
     */
    public void setNeighbourIP(InetAddress NeighbourIP) {
        this.NeighbourIP = NeighbourIP;
    }

    /**
     * @return the Leaps
     */
    public int getLeaps() {
        return Leaps;
    }

    /**
     * @param Leaps the Leaps to set
     */
    public void setLeaps(int Leaps) {
        this.Leaps = Leaps;
    }

    /**
     * @return the LastSuccessfulConnection
     */
    public Date getLastSuccessfulConnection() {
        return LastSuccessfulConnection;
    }

    /**
     * @param LastSuccessfulConnection the LastSuccessfulConnection to set
     */
    public void setLastSuccessfulConnection(Date LastSuccessfulConnection) {
        this.LastSuccessfulConnection = LastSuccessfulConnection;
    }
    
    public boolean isDirectNeighbour(){
        return this.getName().equals(this.getNeighbourName());
    }

    /**
     * Construtores
     */
    public Peer(){
        this.ErrorsNo=0;
        this.Name = "";
        this.NeighbourName="";
        this.NeighbourIP=null;
        this.Leaps=0;
        this.LastSuccessfulConnection = new Date();
    }
    
    public Peer(String name,String neighbour,InetAddress ip){
        this.ErrorsNo=0;
        this.Name = name;
        this.NeighbourName=neighbour;
        this.NeighbourIP=ip;
        this.Leaps=0;
        this.LastSuccessfulConnection = new Date();
    }
    
    public Peer(Peer p){
        this.ErrorsNo=p.getErrorsNo();
        this.Name = p.getName();
        this.NeighbourName=p.getNeighbourName();
        this.NeighbourIP=p.getNeighbourIP();
        this.Leaps=p.getLeaps();
        this.LastSuccessfulConnection = p.getLastSuccessfulConnection();
    }
    
    @Override
    public String toString(){
        String pattern = "%s: %s\n";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(pattern,"Nome",this.Name));
        sb.append(String.format(pattern,"NomeVizinho",this.NeighbourName));
        sb.append(String.format(pattern,"IP",this.NeighbourIP.getHostAddress()));
        sb.append(String.format(pattern,"Leaps",this.getLeaps()));
        return sb.toString();
    }
    
    @Override
    public Peer clone(){
        return new Peer(this);
    }
    
    public String getDataToMsg(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName());
        sb.append(FIELD_SEPARATOR);
        sb.append(this.getLeaps());
        return sb.toString();
    }
    
    public static Peer fromDataInMsg(String neighbourName,InetAddress neighbourIP, String data){
        String[] fields;
        String peerName;
        int leaps;
        
        fields = data.split(FIELD_SEPARATOR);
        
        peerName = fields[0];
        leaps = Integer.parseInt(fields[1]);
        
        Peer res = new Peer(peerName, neighbourName, neighbourIP); 
        res.setLeaps(leaps);
        
        return res;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Peer ){
            return this.getName().compareTo(((Peer) o).getName());
        } else {
            throw new IllegalArgumentException("O objeto não pode ser convertido para Peer.");
        }
    }
}
