/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Serveur;

import com.ensimag.api.Implementation.BankImpl;
import com.ensimag.api.Implementation.BankNodeImpl;
import com.ensimag.api.Implementation.MessageImpl;
import com.ensimag.api.bank.IBankMessage;
import com.ensimag.api.bank.IBankNode;
import com.ensimag.api.message.EnumMessageType;
import com.ensimag.api.message.IAction;
import com.ensimag.api.message.IMessage;
import com.ensimag.api.node.INode;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author marchaem
 */
public class Serveur4 {
     public static void main(String[] argv) {
        try {
            System.out.println("on rentre dans serveur 4");
            
            Registry registry=LocateRegistry.getRegistry(10001);
            IBankNode bank4=new BankNodeImpl(40,new BankImpl(3),new HashMap<Long, INode>());
            registry.bind("bank4", bank4); // publie notre instance sous le nom "Add"          
            IBankNode bank3=(IBankNode) registry.lookup("bank3");
            System.out.println("bank 4 a pour id : " + bank3.getId());
            bank4.addNeighboor(bank3);
            bank3.addNeighboor(bank4);  
            
            Scanner sc=new Scanner(System.in);
            String str=sc.nextLine();
            System.out.println("après le scanner");
            while(!str.equals("exit")){
                str=sc.nextLine();
            }
            
        }
        catch(Exception e){
            
        }
        System.out.println("serveur 4 lancé");
    }    
}
    

