/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Implementation;
import com.ensimag.api.bank.IUser;

/**
 *
 * @author marchaem
 */
public class User implements IUser{
    private String name;
    private String age;
    private String FirstName;
    
    
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFirstName() {
        return FirstName;
    }

    @Override
    public String getAge() {
        return age;
    }
    
}
