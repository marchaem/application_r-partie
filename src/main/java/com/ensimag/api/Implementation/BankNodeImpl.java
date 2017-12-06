/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Implementation;

import com.ensimag.api.bank.IAccount;
import com.ensimag.api.bank.IBank;
import com.ensimag.api.bank.IBankMessage;
import com.ensimag.api.bank.IBankNode;
import com.ensimag.api.bank.IUser;
import com.ensimag.api.message.IAck;
import com.ensimag.api.message.IAction;
import com.ensimag.api.message.IMessage;
import com.ensimag.api.message.IResult;
import com.ensimag.api.node.INode;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.security.auth.login.AccountNotFoundException;

/**
 *
 * @author marchaem
 */

//IMPORTANT : Seuls les nodes sont à ajouter sur le RMI registry
public class BankNodeImpl extends UnicastRemoteObject  implements IBankNode{
    
    private final long nodeId;
    private final IBank bank;
    private final HashMap<Long,INode> neighbours;
    private final List<Object> messageReceived;
    private final List<Object> messageSent;

    /**
     *
     * @param nodeId
     * @param bank
     * @param neighbours
     * @param <error>
     * @param messageReceived
     * @throws java.rmi.RemoteException
     */
    public BankNodeImpl(long nodeId,IBank bank, HashMap<Long,INode> neighbours) throws RemoteException{
        this.nodeId=nodeId;
        this.bank=bank;                            
        this.neighbours = neighbours;
        this.messageReceived=new ArrayList<Object>();
        this.messageSent=new ArrayList<Object>();        
    }

    

    public IBank getBank() {
        return bank;
    }

    public List<Object> getMessageReceived() {
        return messageReceived;
    }

    public long getNodeId() {
        return nodeId;
    }
    
    
    @Override
    public void addNeighboor(INode<IBankMessage> neighboor) throws RemoteException {
        neighbours.put(neighboor.getId(),neighboor);
    }

    @Override
    public List<IAccount> getAccounts() throws RemoteException {
       
        return bank.getAccounts();
    }

    @Override
    public IAccount getAccount(long number) throws AccountNotFoundException, RemoteException {
        return bank.getAccount(number);
    }

    @Override
    public IAccount openAccount(IUser user) throws RemoteException {
       return bank.openAccount(user);
    }

    @Override
    public boolean closeAccount(long number) throws AccountNotFoundException, RemoteException {
        return bank.closeAccount(number);
    }

    @Override
    public long getId() throws RemoteException {
        System.out.println("lool");
        return nodeId;
    }

    @Override
    public void onMessage(IBankMessage message) throws RemoteException {
        
        
        if(messageReceived.contains(message.getMessageId())){
            //a voir si bankiD ou pas dans le new (ou nodeId)
            IAck ack=(AckImpl) new AckImpl(this.bank.getBankId(),message);
            //on appelle on ack au voisin qui a envoyé le message qu'on vient de recevoir 
            neighbours.get(message.getSenderId()).onAck(ack);
            return;
        }        
            messageReceived.add(message);        
        //on est le destinataire du message
        if(message.getDestinationBankId()==bank.getBankId()){
           try{
               Serializable result = message.getAction().execute(this);
               
           } 
           catch(Exception e){
               //gestion à faire
           }
           
        }
        else{
            Set cles=neighbours.keySet();
            Iterator it = cles.iterator();
            IBankMessage message2=message.clone();
            message2.setSenderId(this.nodeId);
            while(it.hasNext()){
                IBankNode node=(IBankNode) neighbours.get(it.next());              
                node.onMessage(message2);
                messageSent.add(message2);
            }
            
            
        }
    }

    @Override
    public void onAck(IAck ack) throws RemoteException {
        
    }

    @Override
    public void removeNeighboor(INode<IBankMessage> neighbour) throws RemoteException {
        neighbours.remove(neighbour);
    }

    @Override
    public List<IResult<? extends Serializable>> getResultForMessage(long messageId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean deliverResult(IResult<Serializable> result) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
