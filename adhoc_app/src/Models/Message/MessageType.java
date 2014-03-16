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
public enum MessageType {
        Request (0),
        Reply (1);
        
        private final int typeValue;
        MessageType(int value){
            this.typeValue = value;
        }
        
        public int getValue(){return this.typeValue;}
}
