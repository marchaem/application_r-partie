/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Implementation;

import com.ensimag.api.message.IAck;
import com.ensimag.api.message.IMessage;

/**
 *
 * @author marchaem
 */
public class AckImpl implements IAck{
    
    private final long AckSenderId;
    private final IMessage message;

    public AckImpl() {
        this.AckSenderId = 0;
        this.message = null;
    }

    public AckImpl(long AckSenderId, IMessage message) {
        this.AckSenderId = AckSenderId;
        this.message = message;
    }

   
        
    
    @Override
    public long getAckSenderId() {
        return AckSenderId;
    }

    @Override
    public long getAckMessageId() {
        return message.getMessageId();
    }
    
}
