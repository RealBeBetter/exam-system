package com.company.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @ author： 雨下一整晚Real
 * @ date： 2021年05月10日 15:34
 */
public class JDBCUtils {
    // 定义字符串
    private static String user;
    private static String password;
    private static String url;
    private static String driver;

    /*
     * 文件的读取，只需要用到一次，所以用到静态代码块
     * */
    static {
        // 读取资源文件，获取值
        try {
            // 1. 创建properties集合类
            Properties properties = new Properties();

            // 获取src路径下文件的方法-> ClassLoader 类加载器
            // ClassLoader classLoader = JDBCUtils.class.getClassLoader();
            // URL res = classLoader.getResource("src\\jdbc.properties");
            // String path = res.getPath();
            // System.out.println(path);

            // 2.加载文件
            properties.load(new FileReader("src\\com\\company\\resources\\jdbc.properties"));

            // 3.获取数值，赋值
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");

            // 4.注册驱动
            Class.forName(driver);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     * 获取连接，连接对象
     * */
    public static Connection getConnection() throws SQLException {
        System.out.println("数据库连接中......");
        return DriverManager.getConnection(url, user, password);
    }

    /*
     * 释放资源
     * */
    public static void close(PreparedStatement pstmt, Connection conn) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void close (ResultSet rs, PreparedStatement pstmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(pstmt, conn);
        System.out.println("资源释放成功！");
    }
}
