package com.company.testpaper;

import com.company.view.MainView;

import javax.swing.*;
import java.text.NumberFormat;

/**
 * @ author： 雨下一整晚Real
 * @ date： 2021年05月10日 15:54
 */
public class Countdown extends Thread{
    // 设置考试倒计时
    // 剩余时间
    private JLabel leftTime;
    // 考试设置时间，总时间
    private int totalTime;

    public Countdown(JLabel lT, int tT) {
        this.leftTime = lT;
        this.totalTime = tT * 60;
    }

    @Override
    public void run() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置数值的整数部分允许的最小位数
        numberFormat.setMinimumIntegerDigits(2);
        // 定义时分秒
        int h, m, s;
        while (totalTime > 0) {
            h = totalTime / 3600;
            m = totalTime % 3600 / 60;
            s = totalTime % 60;
            StringBuilder stringBuilder;
            stringBuilder = new StringBuilder();
            // 增加到leftTime标签
            stringBuilder.append("考试剩余时间为：").append(numberFormat.format(h)).append(":").append(numberFormat.format(m)).append(":").append(numberFormat.format(s));
            leftTime.setText(stringBuilder.toString());
            System.out.println("lefttime ：" + leftTime);
            try {
                //延时一秒
                Thread.sleep(1000);
            } catch (Exception e) {
                // ignore error
            }
            // 单位是s，延时1s则总时长-1
            totalTime --;
        }
        if (totalTime == 0) {
            JOptionPane.showMessageDialog(null, "考试结束");
            // 考试结束的时候自动交卷，触发打分方法
            MainView.showScore();
            // 推迟执行，防止过早关闭
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
}
