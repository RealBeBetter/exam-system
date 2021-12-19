package com.company.view;

import com.company.testpaper.Countdown;
import com.company.testpaper.Test;
import com.company.utils.JDBCUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @ author： 雨下一整晚Real
 * @ date： 2021年05月10日 15:36
 */
public class MainView extends JFrame implements ActionListener {
    // 定义主界面
    // 定义按钮：提交、上一题、下一题、开始
    private JButton start, commit, back, next;
    // 设置单选按钮
    private JRadioButton aButton, bButton, cButton, dButton;
    // 设置按钮组
    private ButtonGroup buttonGroup;
    // 设置文本区
    private static JTextArea jTextArea;

    // 定义所需要的变量值
    private static Test[] tests;              // 设置试题
    private static int questionNum = 0;       // 设置题目数量
    private static int questionPointer = 0;   // 设置题号指针
    private static int yes = 0;
    private static int no = 0;               // 设置对错数量
    private Countdown cd;              // 倒计时

    MainView() {
        super("学生在线考试系统_答题");

        // 设置面板
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        // 设定考试时间
        final int EXAM_TIME = 1;
        // 设置标签
        JLabel label = new JLabel("总考试时间：" + 1 + "分钟");
        JLabel clock = new JLabel();
        cd = new Countdown(clock, EXAM_TIME);

        jTextArea = new JTextArea(20, 40);
        // 设置试题区不能编辑（不能修改试题内容）
        jTextArea.setEditable(false);

        aButton = new JRadioButton("A");
        bButton = new JRadioButton("B");
        cButton = new JRadioButton("C");
        dButton = new JRadioButton("D");
        buttonGroup = new ButtonGroup();

        start = new JButton("开始考试");
        back = new JButton("上一题");
        next = new JButton("下一题");
        commit = new JButton("交卷");

        // 添加事件监听
        aButton.addActionListener(this);
        bButton.addActionListener(this);
        cButton.addActionListener(this);
        dButton.addActionListener(this);

        start.addActionListener(this);
        back.addActionListener(this);
        next.addActionListener(this);
        commit.addActionListener(this);

        // 布局设置
        buttonGroup.add(aButton);
        buttonGroup.add(bButton);
        buttonGroup.add(cButton);
        buttonGroup.add(dButton);

        panel1.add(label);
        panel1.add(start);
        panel1.add(clock);


        panel2.add(jTextArea);

        panel3.add(aButton);
        panel3.add(bButton);
        panel3.add(cButton);
        panel3.add(dButton);
        panel3.add(back);
        panel3.add(next);
        panel3.add(commit);

        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.CENTER);
        this.add(panel3, BorderLayout.SOUTH);

        // 登录之后界面才可见
        this.setVisible(true);
        this.setTitle("学生在线考试系统");
        // 设置全屏显示
        /*
         * 后来发现全屏显示需要调整UI，舍弃使用
         * */
        /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());*/
        // 设置尺寸
        this.setSize(1000, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createExam() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 题目数量
            int num = 0;
            conn = JDBCUtils.getConnection();
            System.out.println("获取题库：数据库连接成功！");
            String sql = "select * from TEST";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                num ++;
            }
            /*
             * 考虑到教师未添加题目的情况
             *  num == 0
             * */
            if (num == 0) {
                JOptionPane.showMessageDialog(null, "题库为空，请联系教师解决！");
                this.dispose();
                new StudentLogin();
            }
            tests = new Test[num];
            // 题目数量赋值
            questionNum = num;
            /*
             * 出现空指针异常
             * Exception in thread "AWT-EventQueue-0" java.lang.NullPointerException
             * 出现异常的原因是因为自定义的类数组，需要对每个类进行实例化
             * Test[] tests = new Test[num] 是没有地方可以存数据的
             * 只有每个成员进行声明后才会给这个成员分配内存
             * tests[0] = new Test();
             * */
            // 获取题量的大小之后，需要对rs重新赋值
            rs = null;
            rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                tests[i] = new Test();
                tests[i].setQuestionNum(rs.getInt("NUM"));
                tests[i].setQuestionText(rs.getString("QUESTION"));
                tests[i].setStandardAnswer(rs.getString("ANSWER"));
                i ++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
    }

    // 设置单选不重复模块
    private void setSelected(String s) {
        if ("A".equals(s)) {
            buttonGroup.setSelected(aButton.getModel(), true);
        }
        if ("B".equals(s)) {
            buttonGroup.setSelected(bButton.getModel(), true);
        }
        if ("C".equals(s)) {
            buttonGroup.setSelected(cButton.getModel(), true);
        }
        if ("D".equals(s)) {
            buttonGroup.setSelected(dButton.getModel(), true);
        }
        if ("".equals(s)) {
            buttonGroup.clearSelection();
        }
    }

    // 设置试题展示模块
    public static void showQuestion() {
        jTextArea.setText("");
        jTextArea.append(tests[questionPointer].getQuestionText());
    }

    // 设置打分模块
    public static void showScore() {
        for (int i = 0; i < questionNum; i++) {
            if (tests[i].checkAnswer()) {
                yes++;
            } else {
                no++;
            }
        }
        int score  = (int) (yes * 100 / questionNum);
        // 把学生成绩传回数据库
        // 用到之前登录时候定义的 stuId
        // 答题则表示登录成功，输入Id正确
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtils.getConnection();
            System.out.println("传入成绩：数据库连接成功！");
            String sql = "update sc set score = ? where sno = ?";
            pstmt = conn.prepareStatement(sql);

            // 设置占位符值
            pstmt.setInt(1, score);
            pstmt.setString(2, StudentLogin.getStuId());

            System.out.println("登录的学号是" + StudentLogin.getStuId());

            int count  = pstmt.executeUpdate();
            System.out.println("改变了" + count + "次成绩");
        } catch (SQLException e) {
            e.printStackTrace();
        } /*finally {
            JDBCUtils.close(null, pstmt, conn);
        }*/
        JOptionPane.showMessageDialog(null,
                "答对" + yes + "题，答错"+ no +"题，分数为" + score);

        // 展示学生信息，打分
        conn = null;
        pstmt = null;
        ResultSet rs = null;
        String sname = "";
        String sid = "";
        int sscore = 0;
        try {
            conn = JDBCUtils.getConnection();
            System.out.println("分数查询：数据库连接成功！");
            String sql = "select student.sno, student.sname, sc.score " +
                    "from sc, student where sc.sno = student.sno and sc.sno = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, StudentLogin.getStuId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("展示成绩");
                sname = rs.getString("sname");
                sid = rs.getString("sno");
                sscore = rs.getInt("score");
            }
            JOptionPane.showMessageDialog(null,
                    "姓名：" + sname + '\n' + "学号：" + sid + '\n' + "分数：" + sscore);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // 按键点击
        if (actionEvent.getSource() == start) {
            createExam();         // 创建考试
            questionPointer = 0;  // 题目序号
            showQuestion();       // 展示试题
            start.setEnabled(false);  // 设置按键不可点击
            cd.start();           // 开始计时
        } else if (actionEvent.getSource() == back) {
            questionPointer --;
            if (questionPointer == -1) {
                JOptionPane.showMessageDialog(null, "已经是第一题了！");
                questionPointer ++;
            } else {
                // 当前题目未完成，需要清空选项值
                setSelected(Objects.requireNonNullElse(tests[questionPointer].getStuAnswer(), ""));
            }
            showQuestion();
        } else if (actionEvent.getSource() == next) {
            questionPointer ++;
            if (questionPointer == questionNum) {
                JOptionPane.showMessageDialog(null, "已经是最后一题了！");
                questionPointer --;
            } else {
                // 当前题目未完成，需要清空选项值
                setSelected(Objects.requireNonNullElse(tests[questionPointer].getStuAnswer(), ""));
            }
            showQuestion();
        } else if (actionEvent.getSource() == commit) {
            showScore();
            commit.setEnabled(false);
            System.exit(0);
        }

        // 设置答案选项
        if (actionEvent.getSource() == aButton) {
            tests[questionPointer].setStuAnswer("A");
        }
        if (actionEvent.getSource() == bButton) {
            tests[questionPointer].setStuAnswer("B");
        }
        if (actionEvent.getSource() == cButton) {
            tests[questionPointer].setStuAnswer("C");
        }
        if (actionEvent.getSource() == dButton) {
            tests[questionPointer].setStuAnswer("D");
        }
    }

    public static void main(String[] args) {
        new MainView();
    }

}
