/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Implementation;

import com.ensimag.api.message.IResult;
import java.io.Serializable;

/**
 *
 * @author marchaem
 */
public class ResultImpl implements IResult{
    
    private final Serializable Data;
    private final long MessageId;

    public ResultImpl() {
        this.Data = null;
        this.MessageId = 0;
    }

    public ResultImpl(Serializable Data, long MessageId) {
        this.Data = Data;
        this.MessageId = MessageId;
    }
    
    
    
    @Override
    public Serializable getData() {
        return Data;
    }

    @Override
    public long getMessageId() {
        return MessageId;
    }
    
}
