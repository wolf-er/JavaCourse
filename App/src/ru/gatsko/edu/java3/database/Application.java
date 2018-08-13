package ru.gatsko.edu.java3.database;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Scanner;

public class Application {
    static Connection conn;
    static Statement st;


    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:dbsql");
            st = conn.createStatement();
            SqlQueries.createTable(st);
            System.out.println("DB Table was cleared");
            SqlQueries.insertGoods(conn);
            System.out.println("Data was inserted");
            Scanner in = new Scanner(System.in);
            while (true){
                String input = in.nextLine();
                if ("end".equals(input)){break;}
                if (!input.isEmpty()) {
                    if (input.startsWith("/цена") && input.split("\\s").length == 2) {
                        System.out.println(SqlQueries.selectByName(st, input.split("\\s")[1]));
                    } else if (input.startsWith("/сменитьцену") && input.split("\\s").length == 3) {
                        System.out.println(SqlQueries.updatePrice(st, input.split("\\s")[1], input.split("\\s")[2]));
                    } else if (input.startsWith("/товарыпоцене") && input.split("\\s").length == 3) {
                        System.out.println(SqlQueries.selectByPriceRange(st, input.split("\\s")[1], input.split("\\s")[2]));
                    } else {
                        System.out.println("Неизвестная команда");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
