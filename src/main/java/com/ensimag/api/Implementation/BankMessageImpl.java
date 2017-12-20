/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ensimag.api.Implementation;

import com.ensimag.api.bank.IBankAction;
import com.ensimag.api.bank.IBankMessage;
import com.ensimag.api.message.EnumMessageType;
import com.ensimag.api.message.IAction;
import com.ensimag.api.message.IMessage;

/**
 *
 * @author marchaem
 */
public class BankMessageImpl implements IBankMessage{

    private final long MessageId;
    private final IBankAction Action;
    private long senderId;
    private final long DestinationBankId;
    private final EnumMessageType MessageType;
    private final long OriginalBankSenderId ;

    public BankMessageImpl(long MessageId, IBankAction Action, long senderId, long DestinationBankId, EnumMessageType MessageType, long OriginalBankSenderId) {
        this.MessageId = MessageId;
        this.Action = Action;
        this.senderId = senderId;
        this.DestinationBankId = DestinationBankId;
        this.MessageType = MessageType;
        this.OriginalBankSenderId = OriginalBankSenderId;
    }

    public BankMessageImpl() {
        this.MessageId = 0;
        this.Action = null;
        this.senderId = 0;
        this.DestinationBankId = 0;
        this.MessageType = null;
        this.OriginalBankSenderId = 0;
    }

    
    
    @Override
    public IBankAction getAction() {
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
    public long getSenderId() {
        return senderId;
    }

    @Override
    public void setSenderId(long senderId) {

        this.senderId=senderId;

    }

    @Override
    public IBankMessage clone() {
        BankMessageImpl message;
        message = new BankMessageImpl(MessageId, Action, senderId, DestinationBankId, MessageType,OriginalBankSenderId);
        return message;            
    }

    @Override
    public long getOriginalBankSenderId() {
        return OriginalBankSenderId;
    }
 
}
