/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import com.google.gson.Gson;
import java.net.InetAddress;
import java.util.Date;

/**
 *
 * @author migpfernandes
 */
public class Peer {
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
    
    @Override
    public String toString(){
        String pattern = "%s: %s\n";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(pattern,"Nome",this.Name));
        sb.append(String.format(pattern,"NomeVizinho",this.NeighbourName));
        sb.append(String.format(pattern,"IP",this.NeighbourIP.getHostAddress()));
        return sb.toString();
    }
    
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    
    public static Peer fromJson(String json){
        Peer p;
        Gson gson = new Gson();
        p = gson.fromJson(json, Peer.class); 
        return p;
    }
}