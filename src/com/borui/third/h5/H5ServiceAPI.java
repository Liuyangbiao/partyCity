package com.borui.third.h5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class H5ServiceAPI {

    
    
    public static String sendRequest(String data){
    	String retStr = null;
    			
    	H5Utils.h5Trace("Client startup...");    
        
    	 Socket socket = null;  
         try {  
             socket = new Socket(H5Config.H5_SERVER_IP_ADDR, H5Config.H5_SERVER_PORT);    
                  
             DataInputStream input = new DataInputStream(socket.getInputStream());
             
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());    
             out.writeUTF(data);    
                 
             retStr = input.readUTF();     
             H5Utils.h5Trace("respons from server: " + retStr);   
               
             out.close();  
             input.close();  
         } catch (Exception e) {  
        	 H5Utils.h5Trace("exception:" + e.getMessage());   
         } finally {  
             if (socket != null) {  
                 try {  
                     socket.close();  
                 } catch (IOException e) {  
                     socket = null;   
                     H5Utils.h5Trace("excetpion in finally:" + e.getMessage());   
                 }  
             }  
         }  
         
    	return retStr;
    }

}
