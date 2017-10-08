/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderzer0h.sgp.network;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;

/**
 *
 * @author kalockhart
 */
public class Client{
    
    private DatagramSocket datagramSocket;
    private DatagramSocket serverSocket;
    private InetAddress inetAddress;
    private InetAddress localAddress;    
    
    private final Object stateLock = new Object();
    private ClientState state = ClientState.IDLE;
    
 
    public static class Message {
        public int messageType;
        public long sequenceNumber;
        public String message;
    }
    
    private Runnable listener;
    
    public Client() throws ClientCreateException {
        
        this.SetState(ClientState.IDLE);
        
        try {
        datagramSocket = new DatagramSocket();
        serverSocket = new DatagramSocket(41337);
        inetAddress = InetAddress.getByName("localhost");
        
        } catch(Exception ex) {
            throw new ClientCreateException();
        }
        
        listener = new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = Client.this.serverSocket;
                while(Client.this.GetState() != ClientState.SHUTDOWN) {
                   
                    
                }
            }  
        };   
   
    }
    
    public final ClientState GetState() {
        synchronized (stateLock) {
            ClientState temp;
            temp = state;
            return state;
        }
    }
    
    public final void SetState(ClientState state) {
        synchronized (stateLock) {
            this.state = state;
        }
    }
    
    public void Connect() {

        try {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MessagePacker mp = MessagePack.newDefaultPacker(out);
        Message msg = new Message();
        msg.messageType = 1;
        msg.sequenceNumber = 1;
        msg.message = "test";
        mp.packInt(msg.messageType);
        mp.packLong(msg.sequenceNumber);
        mp.packString(msg.message);
        mp.flush();
        
        
        DatagramPacket sendPacket = new DatagramPacket(out.toByteArray(), out.toByteArray().length, this.inetAddress, 31337);
        for  (int i = 0; i < 10; i++) {
            datagramSocket.send(sendPacket);
            Thread.sleep(1000);
        }
        } catch (Exception ex) {
            
        } finally {
            datagramSocket.close();
        }
    } 
}
