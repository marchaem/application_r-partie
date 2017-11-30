package com.ensimag.api.Client;


import com.ensimag.api.Implementation.AddImpl;
import com.ensimag.api.bank.IAddInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Alain Defrance
 */
public class Client {
    public static void main(String[] argv) {
        try {
            Registry registry = LocateRegistry.getRegistry(10000);
            IAddInterface stub = (IAddInterface) registry.lookup("bank1");
            System.out.println(stub.add(1, 2)); // Affiche 3
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
