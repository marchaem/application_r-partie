package com.ensimag.api.Implementation;
import com.ensimag.api.bank.IAddInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Alain Defrance
 */
public class AddImpl extends UnicastRemoteObject implements IAddInterface{
   
    
    public AddImpl() throws RemoteException{
        super();
    }
    
   @Override
    public int add(int x, int y) throws RemoteException {
        return x+y;
    }
    
}    