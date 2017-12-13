/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Serveur;

import com.ensimag.api.Implementation.BankImpl;
import com.ensimag.api.Implementation.BankNodeImpl;
import com.ensimag.api.bank.IBankNode;
import com.ensimag.api.node.INode;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author marchaem
 */
public class Serveur2 {
     public static void main(String[] argv) {
        try {
            System.out.println("on rentre dans serveur 2");
            
            Registry registry=LocateRegistry.getRegistry(10001);
            IBankNode bank2=new BankNodeImpl(20,new BankImpl(2),new HashMap<Long, INode>());
            registry.bind("bank2", bank2); // publie notre instance sous le nom "Add"
            
            
            IBankNode bank1=(IBankNode) registry.lookup("bank1");
            System.out.println("bank 1 a pour id : " + bank1.getId());
            bank2.addNeighboor(bank1);
            bank1.addNeighboor(bank2);
            
            
            
            
            Scanner sc=new Scanner(System.in);
            String str=sc.nextLine();
            System.out.println("après le scanner");
            while(!str.equals("exit")){
                str=sc.nextLine();
            }
            
        }
        catch(Exception e){
            
        }
        System.out.println("serveur 2 lancé");
    }    
}
    

