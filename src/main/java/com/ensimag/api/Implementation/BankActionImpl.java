/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Implementation;

import com.ensimag.api.bank.IBankAction;
import com.ensimag.api.bank.IBankNode;
import com.ensimag.api.bank.IUser;
import com.ensimag.api.message.IResult;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author marchaem
 */

public class BankActionImpl implements IBankAction{

    public enum actionType {
        AJOUT,
        CREDIT,
        DEBIT,
        AUTO_DECOU,
        SUPR,
        DELIVER;  
    }
    private final IResult result;
    private actionType type;
    
    public BankActionImpl() {
        this.result = null;
    }

    public BankActionImpl(IResult result, actionType type) {
        this.result = result;
    }

    public IResult getResult() {
        return result;
    }
    
    

    
    
    @Override
    //a changer
    public Serializable execute(IBankNode node) throws Exception {
        String temp;
        String[] temp2;
        switch (this.type)
    {
      case AJOUT:
          node.openAccount((IUser) new User(result.getData()));
          return new ResultImpl ("le compte"+result.getData()+" a bien ete créé ",result.getMessageId());
      case CREDIT: //ici les dats du result doive etre "aconteid montant"
          temp= (String)result.getData();
          temp2 = temp.split(" ");
          node.getAccount(Long.parseLong(temp2[0])).add(Integer.parseInt(temp2[1]));
          return new ResultImpl ("le compte"+temp2[0]+" a bien été crédité de :"+temp2[1]+"\n le nouveau solde du compte est :"+node.getAccount(Long.parseLong(temp2[0])).getTotal(),result.getMessageId());
      case DEBIT:
          temp= (String)result.getData();
          temp2 = temp.split(" ");
          node.getAccount(Long.parseLong(temp2[0])).add(- Integer.parseInt(temp2[1]));
          return new ResultImpl ("le compte"+temp2[0]+" a bien été debité de :"+temp2[1]+"\n le nouveau solde du compte est :"+node.getAccount(Long.parseLong(temp2[0])).getTotal(),result.getMessageId());
      case AUTO_DECOU:  
          temp= (String)result.getData();
          temp2 = temp.split(" ");
          node.getAccount(Long.parseLong(temp2[0])).setAllowedOverdraw( Integer.parseInt(temp2[1]));
          return new ResultImpl ("le compte"+temp2[0]+" a bien une aurorisation de decouvert de :"+temp2[1],result.getMessageId());
      case SUPR: 
          node.closeAccount((long) result.getData());
          return new ResultImpl ("le compte"+result.getData()+" a bien ete supimé ",result.getMessageId());
      case DELIVER:
          System.out.println(result.getData().toString());
      default:  
          System.out.println("type d'action inconue");
    }
        
        return new BankActionImpl();
    }
    
}
