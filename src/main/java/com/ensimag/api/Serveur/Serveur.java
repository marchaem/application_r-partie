package com.ensimag.api.Serveur;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * @author Alain Defrance
 */
public class Serveur {
    public static void main(String[] argv) {
        try {
            Registry registry = LocateRegistry.createRegistry(10001);
            Scanner sc=new Scanner(System.in);
            String str=sc.nextLine();          
            while(!str.equals("exit")){
                str=sc.nextLine();
            }
            // 10000 est le port sur lequel sera publié le service. Nous devons le préciser à la fois sur le registry et à la fois à la création du stub.
            // Génère un stub vers notre service.
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("on lance le rmi registry");
    }
}
