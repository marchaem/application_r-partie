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
import java.util.Iterator;
import java.util.List;
import javax.security.auth.login.AccountNotFoundException;

/**
 *
 * @author marchaem
 */
public class BankNodeImpl implements IBankNode{
    
    private final long nodeId;
    private final IBank bank;
    private final List<INode> neighbours;
    private final List<Object> messageReceived;

    public BankNodeImpl(long nodeId, IBank bank, List<INode> neighbours, List<Object> messageReceived) {
        this.nodeId = nodeId;
        this.bank = bank;
        this.neighbours = neighbours;
        this.messageReceived = messageReceived;
    }
    
    
    
    @Override
    public void addNeighboor(INode<IBankMessage> neighboor) throws RemoteException {
        neighbours.add(neighboor);
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
        return nodeId;
    }

    @Override
    public void onMessage(IBankMessage message) throws RemoteException {
        
        
        if(messageReceived.contains(message.getMessageId())){
            return;
        }
        if(message.getDestinationBankId()==bank.getBankId()){
           try{
               Serializable result = message.getAction().execute(this);
           } 
           catch(Exception e){
               //gestion à faire
           }
           
        }
        else{
            Iterator it = neighbours.iterator();
            while(it.hasNext()){
                IBankNode node=(IBankNode) it.next();
                IBankMessage message2=message.clone();
                message2.setSenderId(this.nodeId);
                node.onMessage(message2);                
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
