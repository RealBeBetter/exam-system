package com.company.view;

import com.company.utils.JDBCUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ author： 雨下一整晚Real
 * @ date： 2021年05月10日 16:01
 */
public class StudentLogin extends JFrame implements ActionListener {
    // 定义文本域接收用户名
    private JTextField jtfId;
    // 定义密码域接收密码
    private JPasswordField jpfPassword;
    // 定义按钮，确认、取消
    private JButton btnOk;
    private JButton btnCancel;

    private static String stuId = "";              // 学号

    public static String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    private String stuPassword = "";        // 密码

    private JLabel welcome;
    private JButton back;

    public StudentLogin() {
        super("学生在线考试系统_登录");

        welcome = new JLabel("学生登录界面");
        back = new JButton("返回");
        back.addActionListener(this);

        JPanel jPanel = new JPanel();
        jPanel.add(welcome);
        jPanel.add(back);
        this.add(jPanel, BorderLayout.NORTH);

        // 学号密码输入部分
        // 定义界面
        JPanel jpMain = new JPanel();
        // 定义标签
        JLabel lblId = new JLabel("学号：");
        JLabel lblPassword = new JLabel("密码：");
        jtfId = new JTextField(20);
        jpfPassword = new JPasswordField(20);
        lblId.setBounds(350, 200, 50, 30);
        jtfId.setBounds(400, 205, 200, 25);
        lblPassword.setBounds(350, 240, 50, 30);
        jpfPassword.setBounds(400, 245, 200, 25);
        add(lblId);
        add(jtfId);
        add(lblPassword);
        add(jpfPassword);

        // 登录确认取消部分
        JPanel jpBtn = new JPanel();
        btnOk = new JButton("确认");
        btnCancel = new JButton("取消");
        btnOk.setBounds(430, 290, 60, 30);
        add(btnOk);
        btnCancel.setBounds(500, 290, 60, 30);
        add(btnCancel);

        // 添加事件监听
        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);

        // 添加容器
        Container con = this.getContentPane();
        con.add(jpMain, BorderLayout.CENTER);
        con.add(jpBtn, BorderLayout.CENTER);

        // 设置属性值
        this.setSize(1000, 600);
        // 设置用户不可调节窗口大小
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("学生在线考试系统_登录");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /*
         * 登录功能逻辑：
         *  1. 先获取到学号
         *  2. 利用学号检索数据库，看是否查询得到对应的密码
         *    - 有查询结果，则代表学号正确
         *    - 无查询结果，则代表学号错误/没添加
         *  3. 有对应的密码之后，判断数据库的密码和用户输入的密码是否匹配
         *    - 若匹配，则密码正确
         *    - 若不匹配，则密码错误
         * */
        // 设置用户输入临时储存
        // 设置正确学号密码的匹配值
        String stuPassword = "";

        // 设置用户输入的存储值
        String tempId = "";
        String tempPassword = "";

        int temp = 0;

        if (actionEvent.getSource() == btnOk) {
            if ("".equals(jtfId.getText())) {
                // System.out.println("请输入学号！");
                JOptionPane.showMessageDialog(null, "请输入学号！");
            } else if ("".equals(new String(jpfPassword.getPassword()))) {
                // System.out.println("请输入密码！");
                JOptionPane.showMessageDialog(null, "请输入密码！");
            } else {
                temp = 1;
                tempId = jtfId.getText();
                tempPassword = new String(jpfPassword.getPassword());
            }

            if (temp == 1) {
                // 执行JDBC查询到学生的ID和密码
                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                try {
                    conn = JDBCUtils.getConnection();
                    // JOptionPane.showMessageDialog(null, "数据库连接成功！");
                    System.out.println("登录：数据库连接成功！");
                    String sql = "select password from student where sno = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, tempId);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        stuPassword = rs.getString(1);
                    }
                    // 如果学号输入错误/找不到对应的学号，则返回的rs为null，getString也为null
                    if (tempPassword.equals(stuPassword)) {
                        JOptionPane.showMessageDialog(null, "登录成功！");
                        // 学号正确，填入学号，后期更新成绩需要用到
                        /*setStuId(tempId);*/
                        stuId = tempId;
                        // 执行页面跳转
                        this.dispose(); // 关闭登录界面
                        new MainView(); // 弹出主页面
                    } else {
                        JOptionPane.showMessageDialog(null, "学号或密码错误！");
                        jtfId.setText("");
                        jpfPassword.setText("");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // 释放资源
                    JDBCUtils.close(rs, pstmt, conn);
                }
            }
        }

        // 取消登录操作
        if (actionEvent.getSource() == btnCancel) {
            /*JOptionPane.showMessageDialog(null, "您已退出！");*/
            this.dispose();
            new StudentSelect();
        } else if (actionEvent.getSource() == back) {
            this.dispose();
            new StudentSelect();
        }
    }

    public static void main(String[] args) {
        new StudentLogin();
    }

}
