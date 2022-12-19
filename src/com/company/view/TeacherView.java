package com.company.view;

import com.company.student.Student;
import com.company.teacher.AddQuestion;
import com.company.teacher.UpdateScore;
import com.company.testpaper.AddPaper;
import com.company.utils.JDBCUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * 教师界面
 *
 * @author 雨下一整晚Real
 * @date 2021年05月10日 15:42
 */
public class TeacherView extends JFrame implements ActionListener {

    private final JButton back;

    String teacherName = "";
    String teacherId = "";
    String teacherSex = "";

    private JLabel functionTip;
    private JButton btnAddQuestion;
    private JButton btnAddPaper;
    private JButton btnQueryScore;
    private JButton btnUpdateScore;

    /**
     * 学生信息表格查询
     */
    private final DefaultTableModel model;
    private final JTable jTable;
    Student[] students;

    public TeacherView() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            System.out.println("教师界面：数据库连接成功");
            String sql = "select tname,tsex from teacher where tno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, TeacherLogin.teacherId);
            rs = pstmt.executeQuery();
            teacherId = TeacherLogin.teacherId;
            while (rs.next()) {
                teacherName = rs.getString("tname");
                teacherSex = rs.getString("tsex");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }

        JLabel welcome = new JLabel("教师主页面");
        back = new JButton("返回");
        back.addActionListener(this);
        JPanel jPanel1 = new JPanel();
        jPanel1.add(welcome);
        jPanel1.add(back);

        JLabel name = new JLabel("姓名：");
        JLabel id = new JLabel("工号：");
        JLabel sex = new JLabel("性别：");
        JLabel tName = new JLabel(teacherName);
        JLabel tId = new JLabel(teacherId);
        JLabel tSex = new JLabel(teacherSex);
        JPanel jPanel2 = new JPanel();
        name.setBounds(350, 70, 50, 30);
        tName.setBounds(400, 70, 200, 30);
        id.setBounds(350, 110, 50, 30);
        tId.setBounds(400, 110, 200, 30);
        sex.setBounds(350, 150, 50, 30);
        tSex.setBounds(400, 150, 200, 30);
        add(name);
        add(tName);
        add(id);
        add(tId);
        add(sex);
        add(tSex);
        functionTip = new JLabel("请选择您的操作：");
        btnQueryScore = new JButton("查询成绩");
        btnUpdateScore = new JButton("修改成绩");
        btnAddQuestion = new JButton("添加试题");
        btnAddPaper = new JButton("添加试卷");
        functionTip.setBounds(250, 200, 200, 30);
        btnQueryScore.setBounds(250, 240, 100, 30);
        btnUpdateScore.setBounds(380, 240, 100, 30);
        btnAddQuestion.setBounds(510, 240, 100, 30);
        btnAddPaper.setBounds(640, 240, 100, 30);
        add(functionTip);
        add(btnQueryScore);
        add(btnUpdateScore);
        add(btnAddQuestion);
        add(btnAddPaper);
        btnQueryScore.addActionListener(this);
        btnUpdateScore.addActionListener(this);
        btnAddQuestion.addActionListener(this);
        btnAddPaper.addActionListener(this);

        JPanel jPanel3 = new JPanel();
        String[] columnName = {"姓名", "学号", "性别", "年龄", "成绩", "学院", "专业"};
        String[] columnWidth = {"80", "120", "40", "40", "40", "90", "90"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columnName);

        jTable = new JTable(model);
        jTable.setBounds(280, 300, 500, 300);
        TableColumnModel tableColumnModel = jTable.getColumnModel();
        for (int i = 0; i < 7; i++) {
            TableColumn tableColumn = tableColumnModel.getColumn(i);
            tableColumn.setPreferredWidth(Integer.parseInt(columnWidth[i]));
        }
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, cr);
        add(jTable);
        jTable.setVisible(false);

        this.add(jPanel3, BorderLayout.SOUTH);
        this.add(jPanel1, BorderLayout.NORTH);
        this.add(jPanel2, BorderLayout.CENTER);

        this.setVisible(true);
        this.setTitle("学生在线考试系统_教师界面");
        this.setSize(1000, 700);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == back) {
            this.dispose();
            new TeacherLogin();
        } else if (actionEvent.getSource() == btnQueryScore) {
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                int row = 0;
                conn = JDBCUtils.getConnection();
                String sqlQuery = "select sname, student.sno, ssex, sage, department, major, sc.score " +
                        "from student, sc " +
                        "where student.sno=sc.sno";
                String sqlRow = "select count(*) from student";
                pstmt = conn.prepareStatement(sqlRow);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    row = rs.getInt(1);
                }
                if (row == 0) {
                    JOptionPane.showMessageDialog(null, "没有学生记录！");
                } else {
                    model.setRowCount(0);
                    int countRow = 0;
                    String[] columnName = {"姓名", "学号", "性别", "年龄", "成绩", "学院", "专业"};
                    model.addRow(columnName);
                    jTable.setVisible(true);
                    pstmt = null;
                    rs = null;
                    students = new Student[row];
                    pstmt = conn.prepareStatement(sqlQuery);
                    rs = pstmt.executeQuery();
                    int i = 0;
                    while (rs.next()) {
                        students[i] = new Student();
                        students[i].setStudentName(rs.getString("sname"));
                        students[i].setStudentId(rs.getLong("sno"));
                        students[i].setStudentSex(rs.getString("ssex"));
                        students[i].setStudentAge(rs.getInt("sage"));
                        students[i].setStudentScore(rs.getInt("score"));
                        students[i].setStudentDepartment(rs.getString("department"));
                        students[i].setStudentMajor(rs.getString("major"));
                        i++;
                    }
                    for (int j = 0; j < i; j++) {
                        model.addRow(new Object[]{students[j].getStudentName(), students[j].getStudentId(),
                                students[j].getStudentSex(), students[j].getStudentAge(), students[j].getStudentScore(),
                                students[j].getStudentDepartment(), students[j].getStudentMajor()});
                    }
                    countRow = jTable.getRowCount();
                    jTable.setEnabled(false);
                    System.out.println(countRow);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.close(rs, pstmt, conn);
            }
        } else if (actionEvent.getSource() == btnUpdateScore) {
            new UpdateScore();
        } else if (actionEvent.getSource() == btnAddQuestion) {
            // 添加试题
            new AddQuestion();
        } else if (actionEvent.getSource() == btnAddPaper) {
            // 添加试卷
            AddPaper addPaper = new AddPaper();
            // 开始连接数据库传入参数
            Connection conn = null;
            PreparedStatement preparedStatement = null;
            ResultSet rsQuery;

            Map<String, String> testPaper = null;
            try {
                testPaper = AddPaper.importTestPaper();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                int flag = 0;
                conn = JDBCUtils.getConnection();
                String sqlQuery = "select MAX(num) from test";
                System.out.println("查询最大题号：数据库连接成功！");
                preparedStatement = conn.prepareStatement(sqlQuery);
                rsQuery = preparedStatement.executeQuery();
                while (rsQuery.next()) {
                    flag = rsQuery.getInt(1);
                    // 最大的已存在，还需要 +1
                    flag++;
                }
                String sqlInsert = null;
                Set<String> keySet = testPaper.keySet();
                for (String question : keySet) {
                    sqlInsert = "insert into test values(?, ?, ?)";
                    preparedStatement = conn.prepareStatement(sqlInsert);
                    preparedStatement.setInt(1, flag);
                    preparedStatement.setString(2, question);
                    preparedStatement.setString(3, testPaper.get(question));
                    int count = preparedStatement.executeUpdate();
                    if (count == 1) {
                        System.out.println("题目添加成功！");
                    } else {
                        System.out.println("题目添加失败！");
                    }
                    flag++;
                }
                System.out.println("题库添加完毕！");
                JOptionPane.showMessageDialog(null, "题库添加完成！");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.close(preparedStatement, conn);
            }
        }
    }
}
