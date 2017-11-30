/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author marchaem
 */
public interface IAddInterface extends Remote{
    public int add(int x, int y) throws RemoteException;
}
