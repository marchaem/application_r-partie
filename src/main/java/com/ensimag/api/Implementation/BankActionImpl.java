/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Implementation;

import com.ensimag.api.bank.IBankAction;
import com.ensimag.api.bank.IBankNode;
import com.ensimag.api.message.IResult;
import java.io.Serializable;

/**
 *
 * @author marchaem
 */
public class BankActionImpl implements IBankAction{

    
    private final IResult result;

    public BankActionImpl() {
        this.result = null;
    }

    public BankActionImpl(IResult result) {
        this.result = result;
    }

    public IResult getResult() {
        return result;
    }
    
    

    
    
    @Override
    //a changer
    public Serializable execute(IBankNode node) throws Exception {
        System.out.println("on a exécuté");
        return new BankActionImpl();
    }
    
}
