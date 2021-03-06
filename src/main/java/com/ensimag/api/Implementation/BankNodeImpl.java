/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Implementation;

import com.ensimag.api.bank.IAccount;
import com.ensimag.api.bank.IBank;
import com.ensimag.api.bank.IBankAction;
import com.ensimag.api.bank.IBankMessage;
import com.ensimag.api.bank.IBankNode;
import com.ensimag.api.bank.IUser;
import com.ensimag.api.message.EnumMessageType;
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
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final HashMap<Long,IBankMessage> messageReceived;
    private final List<Object> messageSent;

    /**
     *
     * @param nodeId
     * @param bank
     * @param neighbours
     * @throws java.rmi.RemoteException
     */
    public BankNodeImpl(long nodeId,IBank bank, HashMap<Long,INode> neighbours) throws RemoteException{
        this.nodeId=nodeId;
        this.bank=bank;                            
        this.neighbours = neighbours;
        this.messageReceived=new HashMap<>();
        this.messageSent=new ArrayList<>();        
    }

    @Override
    public  String toString(){
        return "nodeId : "+ nodeId +"\nbank : "+bank.toString()+"\nneighbours : "+ neighbours.toString()+"\nmessageReceived : "+messageReceived.toString()+"\nmessageSent"+messageSent.toString(); 
    }
    

    

    public IBank getBank() {
        return bank;
    }

    public HashMap<Long,IBankMessage> getMessageReceived() {
        return messageReceived;
    }

    public long getNodeId() {
        return nodeId;
    }
    
    
    @Override
    public void addNeighboor(INode<IBankMessage> neighboor) throws RemoteException {
        neighbours.put(neighboor.getId(),neighboor);
        System.out.println("on a ajouté comme voisin" + neighboor.toString());
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

    public HashMap<Long, INode> getNeighbours() {
        return neighbours;
    }
    
    

    @Override
    public void onMessage(IBankMessage message) throws RemoteException {
                
          
        
        
        System.out.println("on rentre dans onMessage");
        if(message.getMessageType()==EnumMessageType.SINGLE_DEST){  //on a un seul destinataire          
            if(messageReceived.containsKey(message.getMessageId())){  //si j'ai déjà reçu le message
                System.out.println("premier if si on a déjà reçu le message");

                  IAck ack=(AckImpl) new AckImpl(this.bank.getBankId(),message.getMessageId());
                //on appelle on ack au voisin qui a envoyé le message qu'on vient de recevoir
                  neighbours.toString();
                  System.out.println("on est ici");
                  message.toString();
                  ack.toString();
                  System.out.println("on est là");
                  neighbours.get(message.getSenderId()).onAck(ack);
                return;        
            }
            System.out.println("avant messageReceived.Put");
            messageReceived.put(message.getMessageId(),message);
            if(message.getDestinationBankId()==bank.getBankId()){
                System.out.println("deuxième if si on est déjà le destinataire");
               try{
                   List<IResult<? extends Serializable>> listeResult=getResultForMessage(message.getMessageId());
                   System.out.println("apres get result");
                   IResult<Serializable> result = (IResult<Serializable>) listeResult.get(0);
                   System.out.println("apres listResult.get(0)");

                   Boolean delivered = deliverResult(result);
                   System.out.println("après delivered");
                   if(delivered==true){
                       System.out.println("on a transféré le message contenant le résultat");
                   }

               } 
               catch(Exception e){
                   e.printStackTrace();
               }
               
            }
            else{
                System.out.println("dans le else quand on veut envoyer le message aux voisins");
                Set cles=neighbours.keySet();
                Iterator it = cles.iterator();
                IBankMessage message2=message.clone();
                message2.setSenderId(this.nodeId);
                while(it.hasNext()){
                    System.out.println("dans le while");
                    IBankNode node=(IBankNode) neighbours.get(it.next());              
                    node.onMessage(message2);
                    messageSent.add(message2.getMessageId());
                }
            
            
            }
        }
        else if(message.getMessageType()==EnumMessageType.BROADCAST){  //tout le monde doit executé
            if(messageReceived.containsKey(message.getMessageId())){// si le message a deja été recu 
            IAck ack=(AckImpl) new AckImpl(nodeId,message.getMessageId());
            //on appelle on ack au voisin qui a envoyé le message qu'on vient de recevoir 
           // neighbours.get(message.getSenderId()).onAck(ack);
            return;
        } 
            System.out.println("Broadcast");
            if(messageReceived.containsKey(message.getMessageId())){  //si j'ai déjà reçu le message
                System.out.println("premier if si on a déjà reçu le message");
                //a voir si bankiD ou pas dans le new (ou nodeId)
                IAck ack=(AckImpl) new AckImpl(this.bank.getBankId(),message.getMessageId());
                //on appelle on ack au voisin qui a envoyé le message qu'on vient de recevoir
                  neighbours.get(message.getSenderId()).onAck(ack);
                return;        
            }
            else{
                System.out.println("on a pas encore exécuté");
               try{
                    messageReceived.put(message.getMessageId(),message);
                    Serializable result = message.getAction().execute(this);
                    Set cles=neighbours.keySet();
                    Iterator it = cles.iterator();
                    IBankMessage message2=message.clone();
                    message2.setSenderId(this.nodeId);
                    System.out.println("senderId vaut : "+ message2.getSenderId());
                    while(it.hasNext()){
                        IBankNode node=(IBankNode) neighbours.get(it.next());              
                        node.onMessage(message2);
                        messageSent.add(message2.getMessageId());
                    }            
                }
               catch(Exception e){             
               }            
            }
        }
        
        else if(message.getMessageType()==EnumMessageType.DELIVERY){
            System.out.println("dans le delivery");
            System.out.println(" "+message.getDestinationBankId());
            if(message.getDestinationBankId()==nodeId){  try {
                System.out.println("dans le delivery quand on est l'envoyeur inital");
                //on veut délivrer la réponse à la banque
                Serializable result =message.getAction().execute(this);
                } catch (Exception ex) {
                    Logger.getLogger(BankNodeImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            return;
            }
            System.out.println("envoie au voisin dans le delivery");
            IMessage message2 = message.clone();
            message2.setSenderId(this.getId());
            long messageId=messageReceived.get(message.getMessageId()).getSenderId();
            System.out.println("messageId vaut :" + messageId);
            System.out.println("neighbours vaut " + neighbours.toString());
            neighbours.get(messageId).onMessage((IBankMessage) message2);
            
            
        }
            
             
               
    }

    @Override
    public void onAck(IAck ack) throws RemoteException {
        /*
        
        reste a faire la diferentiation des cas en fonction du type du message 
        
        */
        
        long SenderId = ack.getAckSenderId();
        if (messageSent.size()<1){// si on en resoi un en trop 
            System.out.println("acusé recu en trop ");
            return ;
        }
        if(messageSent.contains(ack.getAckMessageId())){//si on resoit un ack 
            messageSent.remove(ack.getAckMessageId());
            if (messageReceived.get(ack.getAckMessageId()).getOriginalBankSenderId()==nodeId){// si on est l'émeteur du message de basse 
                System.out.println("message bien recu pas touts les noeus");// SUE FAIRE ICI 
            }
            if (messageSent.size()<1){//si on a recu l'ack de tout le monde
                IAck ack2=(AckImpl) new AckImpl(this.bank.getBankId(),ack.getAckMessageId());
                neighbours.get(messageReceived.get(ack.getAckMessageId()).getSenderId()).onAck(ack);
            }
        }else{//si on resoi unn ack d une origine indesirable
            System.out.println("accusé recu d'un inconnu ");
        }
        
    }

    @Override
    public void removeNeighboor(INode<IBankMessage> neighbour) throws RemoteException {
        neighbours.remove(neighbour.getId());
    }

    @Override
    public List<IResult<? extends Serializable>> getResultForMessage(long messageId) throws RemoteException {
        //ici le cas ou il n y a que 1 resulta a envoyé 
        List<IResult<? extends Serializable>> results= new ArrayList<>();
        try {
           IResult result =  new ResultImpl(messageReceived.get(messageId).getAction().execute(this),messageId);
           results.add(result);
           return results;
        } catch (Exception ex) {
            Logger.getLogger(BankNodeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    @Override
    public Boolean deliverResult(IResult<Serializable> result) throws RemoteException {
       try{
           IBankAction action = new BankActionImpl(result,BankActionImpl.actionType.DELIVER);
           
           IBankMessage message= (IBankMessage) new BankMessageImpl(result.getMessageId(),action,this.nodeId,messageReceived.get(result.getMessageId()).getOriginalBankSenderId(),EnumMessageType.DELIVERY,this.nodeId);
           long id=messageReceived.get(result.getMessageId()).getSenderId();
           System.out.println("message Id vaut : " + id + " "); //+ neighbours.get(messageReceived.get(result.getMessageId()).getSenderId()).getId());
           System.out.println(neighbours.toString());
           neighbours.get(id).toString();
            System.out.println("avant onMessage");
           neighbours.get(id).onMessage(message);
           System.out.println("apres neighbours.get");
       }catch(Exception ex){
           ex.printStackTrace();
           return false;
       }
       return true;
    }

    
}
