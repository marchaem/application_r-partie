/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Serveur;
import com.ensimag.api.bank.IBankNode;
import com.ensimag.api.Implementation.BankNodeImpl;
import static java.rmi.Naming.bind;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

/**
 *
 * @author marchaem
 */
public class Serveur1 {
    public static void main(String[] argv) {
        try {
            System.out.println("on rentre dans serveur1");
            IBankNode bank1=new BankNodeImpl(10,null,null,null);
            LocateRegistry.getRegistry(10000).bind("bank1", bank1); // publie notre instance sous le nom "Add"
            
            
            
            Scanner sc=new Scanner(System.in);
            String str=sc.nextLine();
            while(!str.equals("exit")){
                str=sc.nextLine();
            }
        }
        catch(Exception e){
            
        }
        System.out.println("serveur 1 lancé");
    }    
}
    
