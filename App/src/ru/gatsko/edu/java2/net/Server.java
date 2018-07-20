package ru.gatsko.edu.java2.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Server {
    static Boolean isRunning = true;
    static ServerSocket serv;
    static Scanner in;
    static PrintWriter out;
    static Socket socket;

    private static void receiveHandler(){
        try {
            while (isRunning) {
                String str = in.nextLine();
                System.out.println("Client: " + str);
                if (str.equals("end")) break;
            }
        }catch (NoSuchElementException e){
        }finally{
            try{
                isRunning = false;
                serv.close();
                System.out.println("Client was disconnected");
            }catch (IOException e){
                e.printStackTrace();
            }
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
                isRunning = false;
                socket.close();
                out.close();
                in.close();
                System.out.println("Server is shutting down...");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            serv = new ServerSocket(8189);
            System.out.println("Server has been started...");
            socket = serv.accept();
            System.out.println("New client connected");
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            Thread waitMessage = new Thread(() -> receiveHandler());
            Thread sendMessage = new Thread(() -> sendHandler());
            sendMessage.start();
            waitMessage.start();
            while (waitMessage.isAlive() || sendMessage.isAlive()) Thread.sleep(1000);
            System.out.println("Bye");
        } catch (IOException | InterruptedException e) {
            System.out.println("Server intialize error");
        }

    }
}
