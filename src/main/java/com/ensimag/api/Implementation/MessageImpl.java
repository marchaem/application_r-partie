/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Implementation;

import com.ensimag.api.bank.IBankMessage;
import com.ensimag.api.message.EnumMessageType;
import com.ensimag.api.message.IAction;
import com.ensimag.api.message.IMessage;

/**
 *
 * @author marchaem
 */
public class MessageImpl implements IMessage{
    
    private final long MessageId;
    private final IAction Action;
    private final long senderId;
    private final long DestinationBankId;
    private final EnumMessageType MessageType;

    public MessageImpl(long MessageId, IAction Action, long senderId, long DestinationBankId, EnumMessageType MessageType) {
        this.MessageId = MessageId;
        this.Action = Action;
        this.senderId = senderId;
        this.DestinationBankId = DestinationBankId;
        this.MessageType = MessageType;
    }
    
    
    
    

    @Override
    public IAction getAction() {
        return Action;
    }

    @Override
    public long getDestinationBankId() {
        return DestinationBankId;
    }

    @Override
    public long getMessageId() {
            return MessageId;
    }

    @Override
    public EnumMessageType getMessageType() {
        return MessageType;
    }

    @Override
    public long getOriginalBankSenderId() {
            return DestinationBankId; 
               }

    @Override
    public long getSenderId() {
        return senderId;
    }

    @Override
    public void setSenderId(long senderId) {
        senderId=this.senderId;
    }

    @Override
    public IMessage clone() {
        MessageImpl message;
        message = new MessageImpl(MessageId, Action, senderId, DestinationBankId, MessageType);
        return message;            
    }
    
}
