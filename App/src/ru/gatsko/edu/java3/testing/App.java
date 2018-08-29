package ru.gatsko.edu.java3.testing;
import java.util.Arrays;

import java.sql.*;
import java.util.Scanner;


public class App {
    static Connection conn;
    static Statement st;

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:dbsql");
            st = conn.createStatement();
            String sql = "drop table if exists students";
            st.execute(sql);
            sql = "Create table students(\n" +
                    "id integer primary key autoincrement not null,\n" +
                    "surname text key not null,\n" +
                    "grade text);";
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Table created");
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public int[] extract(int[] array){
        int last4 = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4) last4 = i;
        }
        if (last4 >= 0) return (Arrays.copyOfRange(array, last4 + 1, array.length));
        throw new RuntimeException();
    }

    public boolean check14(Integer[] array){ return Arrays.asList(array).contains(1) && Arrays.asList(array).contains(4); }

    String insertStudent(Connection conn, String surname, String grade) throws SQLException {
        PreparedStatement stInsert;
        String sql = "Insert into students (surname,grade) values (?,?)";
        stInsert = conn.prepareStatement(sql);
        stInsert.setString(1,surname);
        stInsert.setString(2,grade);
        stInsert.executeBatch();
        return "Success";
    }
    String selectBySurname(Statement st, String surname) throws SQLException {
        String sql = "SELECT grade from students where surname = '" + surname + "'";
        ResultSet result = st.executeQuery(sql);
        String answer = "";
        while (result.next()){
            answer += "Студент " + surname + ": " + result.getInt(1) + "\n";
        }
        if (answer.isEmpty()) return "Такого студента нет в базе";
        return answer;
    }
    String updateGrade(Statement st, String surname, String grade) throws SQLException {
        String sql = "UPDATE students SET grade = '" + grade + "' where surname = '" + surname + "'";
        Integer result = st.executeUpdate(sql);
        if (result > 0) return "Оценка успешно изменена";
        return "Не нашлось студента с такой фамилией";
    }
}

