/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coderzer0h.sgp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author kalockhart
 */
public class NetworkStack implements Runnable{

    @Override
    public void run() {
        try {
            DatagramSocket dg = new DatagramSocket();
            InetAddress addr = InetAddress.getByName("localhost");
            byte[] sendData = new byte[1024];
            sendData = "Testing".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, addr, 11337);
            for (int i = 0; i < 10; i++ ) {
                dg.send(sendPacket);    
            }
            
            dg.close();
        }catch(Exception e) {
            
        }
    }
    
}
