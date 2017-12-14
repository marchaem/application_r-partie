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

            IBankNode bank1=new BankNodeImpl(10,new BankImpl(1),new HashMap<Long, INode>());
            LocateRegistry.getRegistry(10001).bind("bank1", bank1); // publie notre instance sous le nom "bank1"
                        
            //System.out.println(bank1.toString());
            
            Scanner sc=new Scanner(System.in);
            String str=sc.nextLine();
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
    
