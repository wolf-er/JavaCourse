package ru.gatsko.edu.java2.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private final JTextArea textArea = new JTextArea();
    private final JScrollPane scrollPane = new JScrollPane(textArea);
    private final JPanel panelBottom = new JPanel();
    private final JTextField textInput = new JTextField();
    private final JButton button = new JButton("Отправить");

    private void OnAction(){
        if (textInput.getText().length() > 0) {
            textArea.setText(textArea.getText() + textInput.getText() + '\n');
            textInput.setText("");
        }
    }

    public MainWindow(){
        setTitle("Messenger");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300,300,400,400);
        setLayout(new BorderLayout());

        textArea.setEditable(false);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
        panelBottom.setLayout(new BorderLayout());
        panelBottom.add(button, BorderLayout.EAST);
        panelBottom.add(textInput, BorderLayout.CENTER);
        textInput.addActionListener(e -> OnAction());
        button.addActionListener(e -> OnAction());
    }
}
