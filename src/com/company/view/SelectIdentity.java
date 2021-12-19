package com.company.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @ author： 雨下一整晚Real
 * @ date： 2021年05月10日 15:37
 */
public class SelectIdentity extends JFrame implements ActionListener {
    // 提供用户身份选择
    /*
     * 按照用户分类，分为教师和学生
     * 教师能够添加试题，查询、修改学生成绩
     * 学生能够新建自己的个人信息，设置密码以及修改密码，查询成绩
     * 实现逻辑：
     * 一级界面提供身份选择，二级界面选择登录还是注册，之后根据权限判断，判断身份
     * 1. 如果是教师注册，需要输入管理员密码
     *   - 如果密码不对，不给予注册教师身份
     *   - 如果身份正确，给与注册进入下一步
     * 2. 如果是学生注册，不需要进行判断
     * */
    private JButton jIdentityTeacher;            // 身份选择，教师
    private JButton jIdentityStudent;            // 身份选择，学生
    JFrame jFrame = new JFrame();

    public SelectIdentity () {
        super("学生在线考试系统_确认您的身份");

        JPanel jPanel1 = new JPanel();
        JLabel jNotification = new JLabel("请选择您的身份：");
        jNotification.setBounds(430, 240, 200, 25);
        add(jNotification);

        JPanel jPanel2 = new JPanel();
        jIdentityTeacher = new JButton("教师");
        jIdentityStudent = new JButton("学生");
        jIdentityTeacher.setBounds(430, 270, 60, 30);
        jIdentityStudent.setBounds(500, 270, 60, 30);
        add(jIdentityTeacher);
        add(jIdentityStudent);

        jIdentityTeacher.addActionListener(this);
        jIdentityStudent.addActionListener(this);


        Container con = this.getContentPane();
        con.add(jPanel1, BorderLayout.NORTH);
        con.add(jPanel2, BorderLayout.CENTER);
        con.setLocation(400, 200);

        this.setVisible(true);
        this.setSize(1000, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        /*try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
            new CheckIdentity();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        new SelectIdentity();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == jIdentityTeacher) {
            /*
             * 假设是教师，需要输入管理员密码确认
             * 密码输入错误跳回选择界面
             * */
            this.dispose();
            new CheckIdentity();
        } else if (actionEvent.getSource() == jIdentityStudent) {
            // 跳转学生界面
            this.dispose();
            new StudentSelect();
        }
    }
}
