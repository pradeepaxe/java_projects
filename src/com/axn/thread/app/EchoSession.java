package com.axn.thread.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoSession implements Runnable {

	private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private boolean isRunning;
	
    
    public EchoSession(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    @Override
	
    public void run() {
        try {
            String inputLine;
            while (!Thread.interrupted() && (inputLine = bufferedReader.readLine()) != null) {
            	
                if (!bufferedReader.ready()) {
                    try {
                        Thread.sleep(1000);
                        continue;
                    } catch (InterruptedException e) {
                        close();
                        return;
                    }
                }
            	
                String outputLine = "Echo Server: " + inputLine;
                printWriter.write(outputLine);
            }
        close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    private void close() {
        try {
            isRunning = false;
            socket.close();
            System.out.println("Disconnecting client");
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
