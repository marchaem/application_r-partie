package com.ensimag.api.Client;


import com.ensimag.api.Implementation.IBankActionImpl;
import com.ensimag.api.Implementation.IBankMessageImpl;
import com.ensimag.api.Implementation.MessageImpl;
import com.ensimag.api.bank.IAddInterface;
import com.ensimag.api.bank.IBankAction;
import com.ensimag.api.bank.IBankMessage;
import com.ensimag.api.bank.IBankNode;
import com.ensimag.api.message.EnumMessageType;
import com.ensimag.api.message.IAction;
import com.ensimag.api.message.IMessage;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Alain Defrance
 */
public class Client {
    public static void main(String[] argv) {
        try {
            Registry registry = LocateRegistry.getRegistry(10001);
            IBankNode stub = (IBankNode) registry.lookup("bank3");
            IBankAction action=new IBankActionImpl();
            IBankMessage message=new IBankMessageImpl(100, action, stub.getId(), 1, EnumMessageType.DELIVERY);
            System.out.println("avant on Message");
            stub.onMessage(message);
            System.out.println("après on Message");
            System.out.println(stub.getId()); // Affiche 3
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
