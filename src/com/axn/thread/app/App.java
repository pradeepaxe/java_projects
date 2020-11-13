package com.axn.thread.app;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class App {
	public static void main(String[]args) throws InterruptedException {
			long timeToWait=1000*60*5;
			long startTime=System.currentTimeMillis();
			
			try (
					ServerSocket serverSocket = new ServerSocket(1000);
					Socket socket = serverSocket.accept();
					
			){
					EchoSession echoSession  = new EchoSession(socket);
					Thread thread = new Thread(echoSession);
					thread.start();
					
					while(thread.isAlive()) {
						if(threadTimer(startTime,  timeToWait, thread)){
							thread.interrupt();
							thread.join();
						}
					}
			}catch(IOException e) {
				System.err.println("Error cretaing client");
			}
			
	}
	 private static boolean threadTimer(long startTime, long timeToWait, Thread thread) {
	        return ((System.currentTimeMillis() - startTime) > timeToWait) && thread.isAlive();
	    }
	 
}
