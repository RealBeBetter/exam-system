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
 * @date 2021年05月10日 15:41
 */
public class AddQuestion extends JFrame implements ActionListener {
    String[] stringAnswer = {"-请选择-", "A", "B", "C", "D"};

    int questionFlag = 0;

    JLabel questionNum = new JLabel("现有题号从：" + getQuestionFlag() + " 开始：");
    JLabel questionText = new JLabel("请输入题目内容：");
    JLabel questionAnswer = new JLabel("请选择题目答案：");

    JTextArea jtfText = new JTextArea(20, 40);
    JComboBox jcbAnswer = new JComboBox(stringAnswer);

    JButton btnOk = new JButton("确认");
    JButton btnCancel = new JButton("取消");

    public AddQuestion() {
        JPanel jPanel1 = new JPanel();
        questionNum.setBounds(350, 50, 200, 30);
        // jtfNum.setBounds(350, 80, 300, 25);
        add(questionNum);
        // add(jtfNum);
        JPanel jPanel2 = new JPanel();
        questionText.setBounds(350, 120, 200, 30);
        jtfText.setBounds(350, 150, 300, 200);
        add(questionText);
        add(jtfText);
        JPanel jPanel3 = new JPanel();
        questionAnswer.setBounds(350, 370, 200, 30);
        jcbAnswer.setBounds(350, 400, 300, 25);
        add(questionAnswer);
        add(jcbAnswer);
        btnOk.setBounds(400, 450, 75, 30);
        btnCancel.setBounds(500, 450, 75, 30);
        add(btnOk);
        add(btnCancel);
        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);

        this.add(jPanel1, BorderLayout.NORTH);
        this.add(jPanel2, BorderLayout.CENTER);
        this.add(jPanel3, BorderLayout.SOUTH);

        this.setVisible(true);
        this.setSize(1000, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public static int getQuestionFlag() {
        int questionFlag = 0;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rsQuery = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sqlQuery = "select MAX(num) from test";
            System.out.println("查询当前最大题号：数据库连接成功！");
            preparedStatement = conn.prepareStatement(sqlQuery);
            rsQuery = preparedStatement.executeQuery();
            while (rsQuery.next()) {
                questionFlag = rsQuery.getInt(1);
                questionFlag++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rsQuery, preparedStatement, conn);
        }
        return questionFlag;
    }

    /*public boolean isAllNumber (String s) {
        char[] str = s.toCharArray();
        for (char c : str) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }*/

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // 添加试题

        if (actionEvent.getSource() == btnOk) {
            String text = jtfText.getText();
            String answer = stringAnswer[jcbAnswer.getSelectedIndex()];
            int isEmpty = 0;
            int isSelect = 0;
            // int isLegal = 0;
            if ("".equals(text)) {
                JOptionPane.showMessageDialog(null, "请输入题目！");
            } else {
                isEmpty = 1;
            }
            if (jcbAnswer.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "请选择答案！");
            } else {
                isSelect = 1;
            }

            if (isEmpty == 1 && isSelect == 1) {
                // 开始连接数据库传入参数
                Connection conn = null;
                PreparedStatement preparedStatement = null;
                // ResultSet rsQuery = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;

                /*try {
                    conn = JDBCUtils.getConnection();
                    String sqlQuery = "select MAX(num) from test";
                    System.out.println("查询当前最大题号：数据库连接成功！");
                    preparedStatement = conn.prepareStatement(sqlQuery);
                    rsQuery = preparedStatement.executeQuery();
                    while (rsQuery.next()) {
                        questionFlag = rsQuery.getInt(1);
                        questionFlag++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(rsQuery, preparedStatement, conn);
                }*/

                try {
                    conn = JDBCUtils.getConnection();
                    String sql = "insert into test(num, question, answer) " +
                            "values(?, ?, ?) ";
                    System.out.println("添加题目：数据库连接成功！");
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, questionFlag);
                    pstmt.setString(2, text);
                    pstmt.setString(3, answer);
                    int count = 0;
                    count = pstmt.executeUpdate();
                    if (count == 1) {
                        System.out.println("题目添加成功！");
                        JOptionPane.showMessageDialog(null, "题目添加成功！");
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "题目添加失败！");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    JDBCUtils.close(rs, pstmt, conn);
                }
            } else {
                JOptionPane.showMessageDialog(null, "请输入题目或者选择答案！");
            }

        } else if (actionEvent.getSource() == btnCancel) {
            this.dispose();
        }
    }
}
