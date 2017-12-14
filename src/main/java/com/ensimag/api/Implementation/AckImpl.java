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
    private final long messageId;

    public AckImpl() {
        this.AckSenderId = 0;
        this.messageId = 0;
    }

    public AckImpl(long AckSenderId, long messageId) {
        this.AckSenderId = AckSenderId;
        this.messageId = messageId;
    }

   
   
    
    @Override
    public long getAckSenderId() {
        return AckSenderId;
    }

    @Override
    public long getAckMessageId() {
        return messageId;
    } 

}
