package ru.gatsko.edu.java2.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 8189;
    private static Socket socket;
    private static Scanner in;
    private static PrintWriter out;
    private static Boolean isRunning = true;

    private static void receiveHandler(){
        try {
            while (isRunning) {
                String str = in.nextLine();
                System.out.println("Server: " + str);
                if (str.equals("end")) break;
            }
        }catch (NoSuchElementException e){
        }finally{
            System.out.println("Server was disconnected");
            isRunning = false;
        }
    }

    private static void sendHandler(){
        try {
            while (isRunning) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String str = br.readLine();
                out.println(str);
                out.flush();
                if (str.equals("end")) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Client is shutting down...");
                isRunning = false;
                socket.close();
                out.close();
                in.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            System.out.println("Connection established");
            Thread waitMessage = new Thread(() -> receiveHandler());
            Thread sendMessage = new Thread(() -> sendHandler());
            waitMessage.start();
            sendMessage.start();
            while (waitMessage.isAlive() || sendMessage.isAlive()) Thread.sleep(1000);
            System.out.println("Bye");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
