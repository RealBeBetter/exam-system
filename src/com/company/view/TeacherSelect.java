package com.company.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 用户验证身份为教师之后，需要选择登录/注册
 * 根据教师的选择进行跳转相应的界面
 *
 * @author 雨下一整晚Real
 * @date 2021年05月10日 15:39
 */
public class TeacherSelect extends JFrame implements ActionListener {
    private final JButton jRegister;
    private final JButton jLogin;

    private final JButton back;

    public TeacherSelect() {
        super("学生在线考试系统_教师注册/登录");

        JLabel welcome = new JLabel("教师操作选择");
        back = new JButton("返回");
        back.addActionListener(this);

        JPanel jPanel = new JPanel();
        jPanel.add(welcome);
        jPanel.add(back);

        JPanel jPanel1 = new JPanel();
        JLabel jNotification = new JLabel("请选择：");
        jNotification.setBounds(430, 230, 200, 25);
        add(jNotification);

        JPanel jPanel2 = new JPanel();
        jRegister = new JButton("注册");
        jLogin = new JButton("登录");
        jRegister.setBounds(430, 260, 60, 30);
        jLogin.setBounds(500, 260, 60, 30);
        add(jRegister);
        add(jLogin);

        jRegister.addActionListener(this);
        jLogin.addActionListener(this);


        Container con = this.getContentPane();
        con.add(jPanel1, BorderLayout.NORTH);
        con.add(jPanel2, BorderLayout.CENTER);
        con.add(jPanel, BorderLayout.NORTH);
        con.setLocation(400, 200);

        this.setVisible(true);
        this.setSize(1000, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new TeacherSelect();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == back) {
            this.dispose();
            new SelectIdentity();
        } else if (actionEvent.getSource() == jRegister) {
            // 选择注册之后
            this.dispose();
            new TeacherRegister();
        } else if (actionEvent.getSource() == jLogin) {
            // 选择登录之后进入登录界面
            this.dispose();
            new TeacherLogin();
        }
    }
}
