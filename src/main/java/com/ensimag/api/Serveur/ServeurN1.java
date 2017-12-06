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
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 *
 * @author kerboult
 */
public class ServeurN1 {
    public static void main(String[] argv) {
    try {
        //argv[0]: numero du serveur 
            System.out.println("on rentre dans le serveur" + "1");
            //on cré un bankNode d'id argv[0] ne contenan pas de banck ni de voisin , et aucun message 
            IBankNode bank=new BankNodeImpl(Long.valueOf("1"),null,null,null);
            Registry registry=LocateRegistry.getRegistry(10000);
            registry.bind("bank"+"1", bank); // publie notre instance sous le nom "Add"
            
            
            
            //on reli la bank a ses voisins
            /*for (int i =1; i<argv.length; i++){
                IBankNode bankN=(IBankNode) registry.lookup(argv[i]);
                bankN.addNeighboor(bank);
                bank.addNeighboor(bankN);
                System.out.println("conected to " + argv[i]);
            }*/
            
            
            //sistheme permetant de lire lescommende d entré sur le serveur 
            Scanner sc=new Scanner(System.in);
            String str=sc.nextLine();
            while(!str.equals("exit")){
                str=sc.nextLine();
            }
        }
        catch(Exception e){
            
        }
    }
}
