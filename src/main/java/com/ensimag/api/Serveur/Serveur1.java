/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Serveur;
import com.ensimag.api.Implementation.BankImpl;
import com.ensimag.api.bank.IBankNode;
import com.ensimag.api.Implementation.BankNodeImpl;
import com.ensimag.api.node.INode;
import static java.rmi.Naming.bind;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author marchaem
 */
public class Serveur1 {
    public static void main(String[] argv) {
        try {
            System.out.println("on rentre dans serveur1");
            Registry registry=LocateRegistry.getRegistry(10000);

            IBankNode bank1=new BankNodeImpl(10,new BankImpl(1),new HashMap<Long, INode>());
            registry.bind("bank1", bank1); // publie notre instance sous le nom "bank1"

            
            Scanner sc=new Scanner(System.in);
            String str=sc.nextLine();
            IBankNode bank2=(IBankNode) registry.lookup("bank2");
            while(!str.equals("exit")){  
                
                str=sc.nextLine();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("serveur 1 lancé");
    }    
}
    
