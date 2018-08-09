package ru.gatsko.edu.java2.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public final class Server {
    private ServerSocket server;
    private Vector<ClientHandler> clients;
    private AuthService authService;
    public final AuthService getAuthService() {
        return authService;
    }
    private final int PORT = 8189;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try {
            server = new ServerSocket(PORT);
            Socket socket = null;
            authService = new AuthService();
            authService.start();
            clients = new Vector<>();
            while (true) {
                System.out.println("Сервер ожидает подключения");
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (final IOException e){
            System.out.println("Ошибка при работе сервера");
        }finally{
            try {
                server.close();
            } catch (final IOException e){
                e.printStackTrace();
            }
            authService.stop();
        }

    }
    public final synchronized boolean isNickBusy(final String nick) {
        for (final ClientHandler o : clients) {
            if (o.getName().equals(nick)) return true;
        }
        return false;
    }
    public final synchronized void broadcastMsg(final String msg) {
        for (final ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }
    public final synchronized void personalMsg(final String nick, final String msg) {
        for (final ClientHandler o : clients) {
            if (nick.equals(o.getName()))
                o.sendMsg("Private from " + nick + ": " + msg);
        }
    }
    public final synchronized void unsubscribe(final ClientHandler o){
        clients.remove(o);
    }
    public final synchronized void subscribe(final ClientHandler o){
        clients.add(o);
    }
}
