package ru.gatsko.edu.java3.database;

import java.sql.*;

public class SqlQueries {
    static PreparedStatement stInsert;
    static void createTable(Statement st) throws SQLException {
        String sql = "drop table if exists goods";
        st.execute(sql);
        sql = "Create table goods(\n" +
                "id integer primary key autoincrement not null,\n" +
                "prodid text key not null,\n" +
                "title text,\n" +
                "cost integer);";
        st.execute(sql);
    }
    static void insertGoods(Connection conn) throws SQLException {
        String sql = "Insert into goods (prodid,title,cost) values (?,?,?)";
        stInsert = conn.prepareStatement(sql);
        for (int i = 1; i <= 10000; i++){
            int cost = i * 10;
            String name = "товар" + i;
            String key = "id" + i;
            stInsert.setString(1,key);
            stInsert.setString(2,name);
            stInsert.setInt(3,cost);
            stInsert.addBatch();
        }
        System.out.println(sql);
        stInsert.executeBatch();
    }
    static String selectByName(Statement st, String name) throws SQLException {
        String sql = "SELECT cost from goods where title = '" + name + "'";
        ResultSet result = st.executeQuery(sql);
        String answer = "";
        while (result.next()){
            answer += "Цена товара " + name + ": " + result.getInt(1) + "\n";
        }
        if (answer.isEmpty()) return "Такого товара нет в базе";
        return answer;
    }
    static String updatePrice(Statement st, String name, String cost) throws SQLException {
        try{
            String sql = "UPDATE goods SET cost = " + Integer.parseInt(cost) + " where title = '" + name + "'";
            Integer result = st.executeUpdate(sql);
            if (result > 0) return "Информация о ценах успешно изменена";
            return "Не нашлось товаров с таким именем";
        } catch (NumberFormatException e) {return "В качестве цены должно быть введено число";}
    }
    static String selectByPriceRange(Statement st, String priceLow, String priceHigh) throws SQLException {
        try {

            String sql = "SELECT title, cost from goods where cost >= " + Integer.parseInt(priceLow) + " and cost <= " + Integer.parseInt(priceHigh);
            ResultSet result = st.executeQuery(sql);
            String answer = "";
            while (result.next()){
                answer += result.getString(1) + ": " + result.getInt(2) + "\n";
            }
            if (answer.isEmpty()) return "Товаров с такими ценами нет в базе";
            return answer;
        } catch (NumberFormatException e) {return "В качестве цены должно быть введено число";}
    }
}
