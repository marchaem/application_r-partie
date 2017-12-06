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

    
    public User() {
        
    }
    
    
    public User(String name, String age, String FirstName) {
        this.name = name;
        this.age = age;
        this.FirstName = FirstName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
    
    
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
