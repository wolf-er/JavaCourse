package ru.gatsko.edu.java3.testing;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLTest {
    static Connection conn;
    static Statement st;
    static App sql = new App();
    @Before
    public void Init() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:dbsql");
        st = conn.createStatement();
        conn.setAutoCommit(false);
    }
    @Test
    public void TestInsert() throws SQLException {
        Assert.assertEquals("Success", sql.insertStudent(conn,"Иванов","A"));
        Assert.assertEquals("Success", sql.insertStudent(conn,"Петров","E"));
    }
    @Test
    public void TestSelect() throws SQLException {
        Assert.assertNotNull(sql.selectBySurname(st,"Иванов"));
        Assert.assertNotNull(sql.selectBySurname(st,"Сидоров"));
    }
    @Test
    public void TestUpdate() throws SQLException {
        Assert.assertNotNull(sql.updateGrade(st,"Иванов","G"));
        Assert.assertNotNull(sql.updateGrade(st,"Сидоров", "H"));
    }
    @After
    public void Last() throws SQLException {
        conn.rollback();
    }

}
