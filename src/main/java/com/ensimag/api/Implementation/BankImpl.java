/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Implementation;

import com.ensimag.api.bank.IAccount;
import com.ensimag.api.bank.IBank;
import com.ensimag.api.bank.IUser;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.login.AccountNotFoundException;

/**
 *
 * @author marchaem
 */
public class BankImpl implements IBank{

    private int bankId;
    private List<IAccount> listAccount;
    
    
    @Override
    public long getBankId() {
        return bankId;
    }

   
    
    @Override
    public List<IAccount> getAccounts() throws RemoteException {
        //ajouter exceptions
        return listAccount;
    }

    @Override
    public IAccount getAccount(long number) throws AccountNotFoundException, RemoteException {
        if(listAccount==null){
            throw new AccountNotFoundException();
        }
        Iterator iter=listAccount.iterator();
        while(iter.hasNext()){
            AccountImpl acc=(AccountImpl) iter.next();
            if(acc.getAccountNumber()==number){
                return acc;
            }
            
        }
        throw new AccountNotFoundException("account not found");
    }

    @Override
    public IAccount openAccount(IUser user) throws RemoteException {
        AccountImpl acc = new AccountImpl(user,listAccount.size(), 0);
        listAccount.add(acc);
        return acc;
        //faire exception
    }

    @Override
    public boolean closeAccount(long number) throws AccountNotFoundException, RemoteException {
        
        if(this.getAccount(number).getTotal()!=0){
            return false;
        }
        listAccount.remove((this.getAccount(number)));
        return true;
        
        
    }

    
    
}
