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
 * @ date： 2021年05月10日 15:52
 */
public class TeacherRegister extends JFrame implements ActionListener {
    // 提供用户注册界面

    private JLabel tName;
    private JLabel tId;
    private JLabel tPassword;
    private JLabel tSex;

    private JLabel idTip;
    private JLabel passwordTip;

    private JTextField jtfName;
    private JTextField jtfId;
    private JPasswordField jpfPassword;
    private JComboBox jcbSex;

    private JButton btnOk;
    private JButton btnCancel;

    private JLabel welcome;
    private JButton back;

    // 范围选择
    String[] stringSex = new String[] {"-请选择-", "男", "女"};

    public TeacherRegister () {
        super("学生在线考试系统_教师注册");

        welcome = new JLabel("教师注册界面");
        back = new JButton("返回");
        back.addActionListener(this);

        tName = new JLabel("姓名：");
        jtfName = new JTextField(20);
        tId = new JLabel("工号：");
        jtfId = new JTextField(20);
        tPassword = new JLabel("密码：");
        jpfPassword = new JPasswordField(20);
        tSex = new JLabel("性别：");
        jcbSex = new JComboBox(stringSex);

        idTip = new JLabel("（工号为11位的整数）");
        passwordTip = new JLabel("（密码不得少于6位）");

        btnOk = new JButton("确认");
        btnCancel = new JButton("取消");

        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);

        JPanel jPanel = new JPanel();
        jPanel.add(welcome);
        jPanel.add(back);
        JPanel jPanel1 = new JPanel();
        tName.setBounds(350, 150, 50, 30);
        jtfName.setBounds(400, 155, 200, 25);
        tId.setBounds(350, 190, 50, 30);
        jtfId.setBounds(400, 195, 200, 25);
        idTip.setBounds(620, 190, 200, 30);
        tPassword.setBounds(350, 230, 50, 30);
        jpfPassword.setBounds(400, 235, 200, 25);
        passwordTip.setBounds(620, 230, 200, 30);
        tSex.setBounds(350, 270, 50, 30);
        jcbSex.setBounds(400, 275, 200, 25);
        add(tName);
        add(jtfName);
        add(tId);
        add(jtfId);
        add(idTip);
        add(tPassword);
        add(jpfPassword);
        add(passwordTip);
        add(tSex);
        add(jcbSex);

        JPanel jPanel2 = new JPanel();
        btnOk.setBounds(430, 330, 60, 30);
        btnCancel.setBounds(500, 330, 60, 30);
        add(btnOk);
        add(btnCancel);

        this.add(jPanel, BorderLayout.NORTH);
        this.add(jPanel1, BorderLayout.CENTER);
        this.add(jPanel2, BorderLayout.SOUTH);

        this.setVisible(true);
        this.setSize(1000, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public boolean isAllNumber (String s) {
        char[] str = s.toCharArray();
        for (char c : str) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new TeacherRegister();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == back) {
            this.dispose();
            new TeacherSelect();
        } else if (actionEvent.getSource() == btnOk) {
            // 设置临时变量存储输入值，判断
            String tempName = jtfName.getText();
            // 无输入的时候为""
            System.out.println(tempName);
            String tempId = jtfId.getText();
            String tempPassword = new String(jpfPassword.getPassword());
            String tempSex = "";

            int isEmpty = 0;
            int isLegal = 0;
            int isSelect = 0;

            if ("".equals(tempName)) {
                JOptionPane.showMessageDialog(null, "请输入姓名！");
            } else if ("".equals(tempId)) {
                JOptionPane.showMessageDialog(null, "请输入学号！");
            } else if ("".equals(tempPassword)) {
                JOptionPane.showMessageDialog(null, "请输入密码！");
            } else {
                // 表示要填写的元素全部都有填写
                isEmpty = 1;
            }

            if (isEmpty == 1) {
                if (isAllNumber(tempId)){
                    char[] strId = tempId.toCharArray();
                    if (strId.length == 11) {
                        char[] strPassword = tempPassword.toCharArray();
                        if (strPassword.length >= 6) {
                            isLegal = 1;
                        } else {
                            JOptionPane.showMessageDialog(null, "密码不满足条件！");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "工号不是11位！");
                        jtfId.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "工号不是数字！");
                    jtfId.setText("");
                }
            }

            if (isEmpty == 1 && isLegal == 1) {
                if (jcbSex.getSelectedIndex() != 0) {
                    tempSex = stringSex[jcbSex.getSelectedIndex()];
                    isSelect = 1;
                } else {
                    JOptionPane.showMessageDialog(null, "请选择性别！");
                }
            }

            if (isEmpty == 1 && isLegal == 1 && isSelect == 1) {
                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;

                try {
                    conn = JDBCUtils.getConnection();
                    System.out.println("教师注册：数据库连接成功！");

                    // 先根据传入的数据是否存在，判断条件：主键Id
                    String selectId = "select tno from teacher where tno = ?";
                    pstmt = conn.prepareStatement(selectId);
                    pstmt.setString(1, tempId);
                    rs = pstmt.executeQuery();
                    String tempJudge = "";
                    while (rs.next()) {
                        tempJudge = rs.getString(1);
                    }

                    if (tempJudge.equals(tempId)) {
                        JOptionPane.showMessageDialog(null, "用户已存在！");
                    } else {
                        pstmt = null;
                        rs = null;
                        String sql = "insert into teacher(tno, password, tname, tsex) " +
                                "values(?, ?, ?, ?)";
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, tempId);
                        pstmt.setString(2, tempPassword);
                        pstmt.setString(3, tempName);
                        pstmt.setString(4, tempSex);

                        // 返回影响的行数
                        int flag = pstmt.executeUpdate();

                        if (flag == 1) {
                            System.out.println("注册成功");
                            JOptionPane.showMessageDialog(null, "注册成功！");
                            this.dispose();
                            new TeacherLogin();
                        } else {
                            System.out.println("注册失败");
                            JOptionPane.showMessageDialog(null, "注册失败！");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(rs, pstmt, conn);
                }
            }

        } else if (actionEvent.getSource() == btnCancel) {
            this.dispose();
            new SelectIdentity();
        }
    }
}
