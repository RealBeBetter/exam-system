package com.company.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @ author： 雨下一整晚Real
 * @ date： 2021年05月10日 16:02
 */
public class StudentSelect extends JFrame implements ActionListener {
    /*
     * 提供学生入口选择，注册还是登录
     * 根据相应的选择，跳转不同的界面
     * */
    private JButton jRegister;            // 身份选择，教师
    private JButton jLogin;            // 身份选择，学生

    private JLabel welcome;
    private JButton back;

    StudentSelect() {
        super("学生在线考试系统_学生注册/登录");

        welcome = new JLabel("请选择您的操作");
        back = new JButton("返回");
        back.addActionListener(this);

        JPanel jPanel = new JPanel();
        jPanel.add(welcome);
        jPanel.add(back);
        this.add(jPanel, BorderLayout.NORTH);

        JPanel jPanel1 = new JPanel();
        JLabel jNotification = new JLabel("请选择：");
        jNotification.setBounds(430, 230, 200, 25);
        add(jNotification);
        jRegister = new JButton("注册");
        jLogin = new JButton("登录");
        jRegister.setBounds(430, 260, 60, 30);
        jLogin.setBounds(500, 260, 60, 30);
        add(jRegister);
        add(jLogin);

        jRegister.addActionListener(this);
        jLogin.addActionListener(this);


        Container con = this.getContentPane();
        con.add(jPanel1, BorderLayout.CENTER);
        con.setLocation(400, 200);

        this.setVisible(true);
        this.setSize(1000, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new StudentSelect();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == jRegister) {
            // 选择注册之后
            this.dispose();
            new StudentRegister();
        } else if (actionEvent.getSource() == jLogin) {
            // 选择登录之后进入登录界面
            this.dispose();
            new StudentLogin();
        } else if (actionEvent.getSource() == back) {
            this.dispose();
            new SelectIdentity();
        }
    }
}
