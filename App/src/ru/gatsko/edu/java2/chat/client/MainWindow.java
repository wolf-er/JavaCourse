package ru.gatsko.edu.java2.chat.client;

import javafx.scene.layout.BorderWidths;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class MainWindow extends JFrame {
    private final JTextArea textArea = new JTextArea();
    private final JScrollPane scrollPane = new JScrollPane(textArea);
    private final JPanel panelBottomLogin = new JPanel();
    private final JPanel panelBottomFields = new JPanel();
    private final JPanel panelBottomFieldNames = new JPanel();
    private final JPanel panelBottomFieldValues = new JPanel();
    private final JLabel passwordLabel = new JLabel("Пароль");
    private final JLabel loginLabel = new JLabel("Логин");
    private final JPanel panelBottomAuthorised = new JPanel();
    private final JTextField textInput = new JTextField();
    private final JTextField loginInput = new JTextField();
    private final JTextField passwordInput = new JTextField();
    private final JButton button = new JButton("Отправить");
    private final JButton buttonLogin = new JButton("Войти");
    private final String SERVAR_ADDR = "localhost";//"31.131.31.130";
    private final int SERVER_PORT = 8189;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private void receiveHandler(){
        try {
            while (true) {
                String str = in.readUTF();
                if (str.startsWith("/authok")) {
                    String nick = str.split("\t")[1];
                    setAuthorised(nick);
                    break;
                }
                textArea.append(str + '\n');
            }
            while (true) {
                String str = in.readUTF();
                textArea.append(str + '\n');
            }
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        } finally{
            try{
                textArea.append("Соединение с сервером закрыто\n");
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            setUnauthorised();
        }
    }

    private void onAction(){
        try {
            if (!textInput.getText().isEmpty()) {
                out.writeUTF(textInput.getText());
                textInput.setText("");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void onLogin(){
        try {
            if (loginInput.getText().contains(" ")){
                textArea.append("Логин не должен содержать пробелы\n");
            } else if (!loginInput.getText().isEmpty()) {
                out.writeUTF("/auth " + loginInput.getText() + " " + passwordInput.getText());
            } else {
                textArea.append("Введите логин\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setUnauthorised(){
        setVisible(false);
        setTitle("Unauthorised Messenger");
        loginInput.setText("");
        passwordInput.setText("");
        remove(panelBottomAuthorised);
        add(panelBottomLogin, BorderLayout.SOUTH);
        loginInput.requestFocus();
        setVisible(true);
    }

    public void setAuthorised(String nick){
        setVisible(false);
        setTitle("Messenger: " + nick);
        remove(panelBottomLogin);
        add(panelBottomAuthorised, BorderLayout.SOUTH);
        textInput.requestFocus();
        setVisible(true);
    }

    public MainWindow(){
        try {
            socket = new Socket(SERVAR_ADDR, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setBounds(300,300,400,400);
            setLayout(new BorderLayout());
            textArea.setEditable(false);
            add(scrollPane, BorderLayout.CENTER);

            panelBottomAuthorised.setLayout(new BorderLayout());
            panelBottomAuthorised.add(button, BorderLayout.EAST);
            panelBottomAuthorised.add(textInput, BorderLayout.CENTER);
            textInput.addActionListener(e -> onAction());
            button.addActionListener(e -> onAction());
            buttonLogin.addActionListener(e -> onLogin());

            panelBottomFields.setLayout( new GridLayout ( 2 , 2 ));
            panelBottomFields.add(loginLabel);
            panelBottomFields.add(passwordLabel);
            panelBottomFields.add(loginInput);
            panelBottomFields.add(passwordInput);
            panelBottomLogin.setLayout(new BorderLayout());
            panelBottomLogin.add(buttonLogin, BorderLayout.EAST);
            panelBottomLogin.add(panelBottomFields, BorderLayout.CENTER);


            add(panelBottomAuthorised, BorderLayout.SOUTH);
            setUnauthorised();


            Thread waitMessage = new Thread(() -> receiveHandler());
            waitMessage.setDaemon(true);
            waitMessage.start();

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    try {
                        socket.close();
                        out.close();
                        in.close();
                    } catch (IOException ex) {}
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
