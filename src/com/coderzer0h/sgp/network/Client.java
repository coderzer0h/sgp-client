/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderzer0h.sgp.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

/**
 *
 * @author kalockhart
 */
public class Client{
    
    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    
    public Client() throws ClientCreateException {
        try {
        datagramSocket = new DatagramSocket();
        inetAddress = InetAddress.getByName("localhost");
        } catch(Exception ex) {
            throw new ClientCreateException();
        }
    }
    
    public void Connect() {

        try {
        MessageBufferPacker mp = MessagePack.newDefaultBufferPacker();
        mp.packInt(1);
        mp.packString("CONNECT");
        DatagramPacket sendPacket = new DatagramPacket(mp.toByteArray(), mp.toByteArray().length, this.inetAddress, 31337);
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
