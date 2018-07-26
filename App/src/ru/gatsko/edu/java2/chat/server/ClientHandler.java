package ru.gatsko.edu.java2.chat.server;

import ru.gatsko.edu.java2.chat.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler {
    private final Server myServer;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String name;
    public String getName(){
        return name;
    }
    public ClientHandler(Server myServer, Socket socket){
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            new Thread(() -> {
                try {
                    myServer.subscribe(this);
                    while (true) {
                        String str = in.readUTF();
                        String nick = null;
                        System.out.println(str);
                        if (str.startsWith("/auth")) {
                            String[] parts = str.split("\\s");
                            if (parts.length > 2 && parts[2].length() > 0) {
                                nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                            } else if (parts.length > 1 && parts[1].length() > 0){
                                nick = myServer.getAuthService().getNickIfEmpty(parts[1]);
                            }
                            if (nick != null) {
                                if (!myServer.isNickBusy(nick)) {
                                    sendMsg("/authok\t" + nick);
                                    name = nick;
                                    myServer.broadcastMsg(name + " зашел в чат");
                                    break;
                                } else sendMsg("Учетная запись уже используется");
                            } else {
                                sendMsg("Неверные логин/пароль");
                            }
                        }
                    }
                    while (true) {
                        String str = in.readUTF();
                        System.out.println(name + ": " + str);
                        if (str.startsWith("/w")) {
                            String[] parts = str.split("\\s");
                            if (parts.length > 2 && "/w".equals(parts[0])) {
                                myServer.personalMsg(parts[1], String.join(" ", Arrays.copyOfRange(parts,2, parts.length)));
                            }
                        } else {
                            myServer.broadcastMsg(name + ": " + str);
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.toString());
                } finally {
                    myServer.unsubscribe(this);
                    if (name.length() > 0) {
                        myServer.broadcastMsg(name + " вышел и чата");
                    }
                    try {
                        socket.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }
    public void sendMsg(String msg){
        try{
            out.writeUTF(msg);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
