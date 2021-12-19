package com.company.testpaper;

/**
 * @ author： 雨下一整晚Real
 * @ date： 2021年05月10日 15:50
 */
public class Test {
    // 提供试题类，包括题目的题号、文本、答案
    private int questionNum;        // 题目题号
    private String questionText;    // 试题内容
    private String standardAnswer;  // 标准答案

    // 学生答案和题号
    private String stuAnswer;

    // 检查答案是否正确
    public boolean checkAnswer() {
        if (this.stuAnswer == null) {
            return false;
        } else {
            return this.stuAnswer.equals(this.standardAnswer);
        }
    }

    public Test() {
    }

    public Test(int questionNum, String questionText, String standardAnswer, String stuAnswer) {
        this.questionNum = questionNum;
        this.questionText = questionText;
        this.standardAnswer = standardAnswer;
        this.stuAnswer = stuAnswer;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getStandardAnswer() {
        return standardAnswer;
    }

    public void setStandardAnswer(String standardAnswer) {
        this.standardAnswer = standardAnswer;
    }

    public String getStuAnswer() {
        return stuAnswer;
    }

    public void setStuAnswer(String stuAnswer) {
        this.stuAnswer = stuAnswer;
    }
}
