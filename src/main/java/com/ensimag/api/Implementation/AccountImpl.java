/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Implementation;

import com.ensimag.api.bank.IAccount;
import com.ensimag.api.bank.IUser;
import com.ensimag.api.bank.NotEnoughMoneyException;

/**
 *
 * @author marchaem
 */
public class AccountImpl implements IAccount{

    private IUser user;
    private long accountNumber;
    private int total;
    private int overdraw=0;

    public AccountImpl(IUser user, long accountNumber, int total) {
        this.user = user;
        this.accountNumber = accountNumber;
        this.total = total;
    }

    
    
    public int getOverdraw() {
        return overdraw;
    }
    
    @Override
    public IUser getAccountUser() {
        return user;
    }

    @Override
    public long getAccountNumber() {
        return accountNumber;
    }

    @Override
    public int add(int amount) {
        return total+=amount;
    }

    @Override
    public int remove(int amount) throws NotEnoughMoneyException {
        if(total-amount < -overdraw){
            throw new NotEnoughMoneyException(this);    
        }
        return total-=amount;
       
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public int setAllowedOverdraw(int overdraw) {
        this.overdraw=overdraw;
        return overdraw;
    }
    
}
