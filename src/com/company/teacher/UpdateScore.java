package com.company.teacher;

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
 * @author 雨下一整晚Real
 * @date 2021年05月10日 15:49
 */
public class UpdateScore extends JFrame implements ActionListener {

    public static final int MAX_SCORE = 100;
    public static final int STU_NO_LENGTH = 11;

    JLabel updateId = new JLabel("请输入要修改的学生学号：");
    JLabel updateScore = new JLabel("请输入要修改的成绩：");
    JTextField jtfId = new JTextField(20);
    JTextField jtfScore = new JTextField(20);
    JButton btnOk = new JButton("确认");
    JButton btnCancel = new JButton("取消");

    public UpdateScore() {
        JPanel jPanel = new JPanel();
        updateId.setBounds(350, 50, 200, 30);
        jtfId.setBounds(350, 80, 300, 25);
        updateScore.setBounds(350, 120, 200, 30);
        jtfScore.setBounds(350, 150, 300, 25);
        btnOk.setBounds(400, 400, 75, 30);
        btnCancel.setBounds(500, 400, 75, 30);
        add(updateId);
        add(jtfId);
        add(updateScore);
        add(jtfScore);
        add(btnOk);
        add(btnCancel);

        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);

        this.add(jPanel, BorderLayout.CENTER);
        this.setVisible(true);
        this.setSize(1000, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public boolean isAllNumber(String s) {
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
        if (actionEvent.getSource() == btnOk) {
            // 判断ID以及分数是否合理

            int isEmpty = 0;
            int isLegal = 0;

            String temp = jtfScore.getText();
            String tempId = jtfId.getText();
            int tempScore = 0;

            if (tempId == null || tempId.length() == 0) {
                JOptionPane.showMessageDialog(null, "请输入学号！");
            } else if (temp == null || temp.length() == 0) {
                JOptionPane.showMessageDialog(null, "请输入分数！");
            } else {
                isEmpty = 1;
            }

            if (isEmpty == 1) {
                if (isAllNumber(tempId)) {
                    char[] id = tempId.toCharArray();
                    if (id.length == STU_NO_LENGTH) {
                        if (isAllNumber(temp)) {
                            tempScore = Integer.parseInt(jtfScore.getText());
                            if (0 <= tempScore && tempScore <= MAX_SCORE) {
                                isLegal = 1;
                            } else {
                                JOptionPane.showMessageDialog(null, "分数在0-100！");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "分数应为数字！");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "学号为11位！");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "学号应为数字！");
                }
            }

            if (isEmpty == 1 && isLegal == 1) {
                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;

                try {
                    conn = JDBCUtils.getConnection();
                    System.out.println("修改分数：数据库连接成功！");
                    String sql = "update sc set score = ? where sc.sno = ?";
                    String sqlQuery = "select sno from sc where sc.sno = ?";
                    pstmt = conn.prepareStatement(sqlQuery);
                    int isExist = 0;
                    pstmt.setString(1, tempId);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        isExist++;
                    }

                    if (isExist == 1) {
                        pstmt = null;
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, tempScore);
                        pstmt.setString(2, tempId);
                        int count = pstmt.executeUpdate();
                        if (count == 1) {
                            System.out.println("修改成功！");
                            JOptionPane.showMessageDialog(null, "修改成功！");
                            this.dispose();
                        } else {
                            System.out.println("修改失败！");
                            JOptionPane.showMessageDialog(null, "修改失败！");
                            this.dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "您输入的学号不存在！");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(rs, pstmt, conn);
                }
            }
        } else if (actionEvent.getSource() == btnCancel) {
            this.dispose();
        }
    }
}
