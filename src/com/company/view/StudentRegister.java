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
 * @ date： 2021年05月10日 16:02
 */
public class StudentRegister extends JFrame implements ActionListener {
    // 提供用户注册界面

    private JLabel stuName;
    private JLabel stuId;
    private JLabel stuPassword;
    private JLabel stuSex;
    private JLabel stuAge;
    private JLabel stuDepartment;   // 学院
    private JLabel stuMajor;        // 专业

    private JLabel idTip;
    private JLabel passwordTip;
    private JLabel ageTip;

    private JTextField jtfName;
    private JTextField jtfId;
    private JPasswordField jpfPassword;
    private JComboBox jcbSex;
    private JComboBox jcbDepartment;
    private JComboBox jcbAge;
    private JComboBox jcbMajor;

    private JButton btnOk;
    private JButton btnCancel;

    private JLabel welcome;
    private JButton back;


    // 范围选择
    String[] stringAge = new String[] {"-请选择-", "10", "11", "12", "13", "14", "15", "16",
            "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
            "28", "29", "30", "31", "32", "33", "34", "35"};
    String[] stringMajor = new String[] {"-请选择-", "计算机科学与技术", "软件工程", "物联网", "网络工程"};
    String[] stringDepartment = new String[] {"-请选择-", "计算机学院"};
    String[] stringSex = new String[] {"-请选择-", "男", "女"};

    public StudentRegister () {
        super("学生在线考试系统_学生注册");

        welcome = new JLabel("学生注册界面");
        back = new JButton("返回");
        back.addActionListener(this);

        stuName = new JLabel("姓名：");
        jtfName = new JTextField(20);
        stuId = new JLabel("学号：");
        jtfId = new JTextField(20);
        stuPassword = new JLabel("密码：");
        jpfPassword = new JPasswordField(20);
        stuSex = new JLabel("性别：");
        stuAge = new JLabel("年龄：");
        stuDepartment = new JLabel("学院：");
        stuMajor = new JLabel("专业：");

        idTip = new JLabel("（学号为11位的整数）");
        passwordTip = new JLabel("（密码不得少于6位）");
        ageTip = new JLabel("（年龄范围：10-35）");

        btnOk = new JButton("确认");
        btnCancel = new JButton("取消");

        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);

        JPanel jPanel = new JPanel();
        /*welcome.setBounds(430, 20, 200, 30);
        welcome.setFont(new Font("黑体", Font.PLAIN, 16));
        back.setBounds(440, 50, 80, 25);*/
        jPanel.add(welcome);
        jPanel.add(back);
        jcbSex = new JComboBox(stringSex);
        jcbDepartment = new JComboBox(stringDepartment);
        jcbAge = new JComboBox(stringAge);
        jcbMajor = new JComboBox(stringMajor);

        JPanel jPanel1 = new JPanel();
        stuName.setBounds(350, 100, 50, 30);
        jtfName.setBounds(400, 105, 200, 25);
        stuId.setBounds(350, 140, 50, 30);
        jtfId.setBounds(400, 145, 200, 25);
        idTip.setBounds(620, 140, 200, 30);
        stuPassword.setBounds(350, 180, 50, 30);
        jpfPassword.setBounds(400, 185, 200, 25);
        passwordTip.setBounds(620, 180, 200, 30);
        stuSex.setBounds(350, 220, 50, 30);
        jcbSex.setBounds(400, 225, 200, 25);
        stuAge.setBounds(350, 260, 50, 30);
        jcbAge.setBounds(400, 265, 200, 25);
        ageTip.setBounds(620, 260, 200, 30);
        stuDepartment.setBounds(350, 300, 50, 30);
        jcbDepartment.setBounds(400, 305, 200, 25);
        stuMajor.setBounds(350, 340, 50, 30);
        jcbMajor.setBounds(400, 345, 200, 25);
        add(stuName);
        add(jtfName);
        add(stuId);
        add(jtfId);
        add(idTip);
        add(stuPassword);
        add(jpfPassword);
        add(passwordTip);
        add(stuSex);
        add(jcbSex);
        add(stuAge);
        add(jcbAge);
        add(ageTip);
        add(stuDepartment);
        add(jcbDepartment);
        add(stuMajor);
        add(jcbMajor);
        JPanel jPanel2 = new JPanel();
        btnOk.setBounds(430, 470, 60, 30);
        btnCancel.setBounds(500, 470, 60, 30);
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


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == back) {
            this.dispose();
            new StudentSelect();
        } else if (actionEvent.getSource() == btnOk) {
            String tempName = jtfName.getText();
            // 无输入的时候为""
            System.out.println(tempName);
            String tempId = jtfId.getText();
            String tempPassword = new String(jpfPassword.getPassword());
            String tempAge = "";
            String tempSex = "";
            String tempMajor = "";
            String tempDepartment = "";

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
                        JOptionPane.showMessageDialog(null, "学号不是11位！");
                        jtfId.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "学号不是数字！");
                    jtfId.setText("");
                }
            }

            if (isEmpty == 1 && isLegal == 1) {
                if (jcbSex.getSelectedIndex() != 0) {
                    tempSex = stringSex[jcbSex.getSelectedIndex()];
                    if (jcbAge.getSelectedIndex() != 0) {
                        tempAge = stringAge[jcbAge.getSelectedIndex()];
                        if (jcbDepartment.getSelectedIndex() != 0) {
                            tempDepartment = stringDepartment[jcbDepartment.getSelectedIndex()];
                            if (jcbMajor.getSelectedIndex() != 0) {
                                tempMajor = stringMajor[jcbMajor.getSelectedIndex()];
                                isSelect = 1; // 表示列表项全都做出了选择
                            } else {
                                JOptionPane.showMessageDialog(null, "请选择专业！");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "请选择学院！");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "请选择年龄！");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请选择性别！");
                }
            }

            // 非空才开始传入数据
            if (isEmpty == 1  && isLegal == 1 && isSelect == 1) {
                // 确认注册，需要传入数据
                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                // JDBC数据操作
                try {
                    conn = JDBCUtils.getConnection();
                    System.out.println("学生注册：数据库连接成功！");

                    // 先根据传入的数据是否存在，判断条件：主键Id
                    String selectId = "select sno from student where sno = ?";
                    pstmt = conn.prepareStatement(selectId);
                    pstmt.setString(1, tempId);
                    rs = pstmt.executeQuery();
                    int tempJudge = 0;
                    while (rs.next()) {
                        tempJudge ++;
                    }
                    if (tempJudge == 1) {
                        JOptionPane.showMessageDialog(null, "用户已存在！");
                    } else {
                        pstmt = null;
                        rs = null;
                        String sql = "insert into student(sname, sno, password, ssex," +
                                "sage, major, department) values(?, ?, ?, ?, ?, ?, ?)";
                        /*String sql2 = "insert into sc(sno) values(?)";*/
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, tempName);
                        pstmt.setString(2, tempId);
                        pstmt.setString(3, tempPassword);
                        pstmt.setString(4, tempSex);
                        pstmt.setString(5, tempAge);
                        pstmt.setString(6, tempMajor);
                        pstmt.setString(7, tempDepartment);

                        // 返回影响的行数
                        int flag = pstmt.executeUpdate();

                        if (flag == 1) {
                            System.out.println("注册成功");
                            rs = null;
                            pstmt = null;
                            String sqlInert = "insert into sc(sno, score) values(?, ?) ";
                            pstmt = conn.prepareStatement(sqlInert);
                            pstmt.setString(1, tempId);
                            pstmt.setInt(2, 0);
                            int count = pstmt.executeUpdate();
                            if (count == 1) {
                                System.out.println("SC表格数据创建成功！");
                                JOptionPane.showMessageDialog(null, "注册成功！");
                            }
                            this.dispose();
                            new StudentLogin();
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
            // 取消注册，返回上一级
            this.dispose();
            new StudentSelect();
        }
    }


    public static void main(String[] args) {
        new StudentRegister();
    }

}
