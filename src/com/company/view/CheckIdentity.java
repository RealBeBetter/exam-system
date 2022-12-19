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
 * @ date： 2021年05月10日 15:38
 */
public class CheckIdentity extends JFrame implements ActionListener {

    /**
     * 输入管理员密码之后，才能够创建教师账号/登录教师账号
     */
    private JButton btnOk;
    private JButton btnCancel;
    private JPasswordField jpfAdmin;

    private JLabel welcome;
    private JButton back;

    public CheckIdentity() {
        super("学生在线考试系统_验证您的身份");

        welcome = new JLabel("确认您的身份");
        back = new JButton("返回");
        back.addActionListener(this);

        JPanel jPanel = new JPanel();
        jPanel.add(welcome);
        jPanel.add(back);
        this.add(jPanel, BorderLayout.NORTH);

        JPanel jPanel1 = new JPanel();
        JLabel jNotification = new JLabel("请输入您的身份验证密码：");
        jNotification.setBounds(400, 200, 230, 25);
        jpfAdmin = new JPasswordField();
        jpfAdmin.setBounds(400, 230, 200, 25);
        add(jNotification);
        add(jpfAdmin);

        JPanel jPanel2 = new JPanel();
        btnOk = new JButton("确认");
        btnCancel = new JButton("取消");
        btnOk.setBounds(430, 290, 60, 30);
        btnCancel.setBounds(500, 290, 60, 30);
        add(btnOk);
        add(btnCancel);

        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);

        Container con = this.getContentPane();
        con.add(jPanel1, BorderLayout.CENTER);
        con.add(jPanel2, BorderLayout.CENTER);

        // 设置属性值
        this.setSize(1000, 600);
        // 设置用户不可调节窗口大小
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new CheckIdentity();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnOk) {
            // 确认验证
            String password = "";

            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                conn = JDBCUtils.getConnection();
                System.out.println("验证教师：数据库连接成功！");
                String sql = "select * from administrator";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    password = rs.getString(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.close(rs, pstmt, conn);
            }
            if (password.equals(new String(jpfAdmin.getPassword()))) {
                // 密码正确
                JOptionPane.showMessageDialog(null, "密码正确！");
                this.dispose();
                new TeacherSelect();
            }
        } else if (actionEvent.getSource() == btnCancel) {
            // 取消、返回则跳转回上一级页面
            new SelectIdentity();
            this.dispose();
        } else if (actionEvent.getSource() == back) {
            new SelectIdentity();
            this.dispose();
        }
    }
}
