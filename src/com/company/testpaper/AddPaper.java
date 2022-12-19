package com.company.testpaper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 添加试卷
 *
 * @author 雨下一整晚Real
 * @date 2021年05月11日 22:48
 */
public class AddPaper {
    public static Map<String, String> importTestPaper() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("..\\exam-system\\Test.txt"));
        Map<String, String> testMap = new LinkedHashMap<String, String>();
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = new char[1024];
        int len;
        while ((len = br.read(chars)) != -1) {
            stringBuilder.append(new String(chars, 0, len));
        }
        br.close();
        String allText = stringBuilder.toString();
        System.out.println(allText);
        stringBuilder.delete(0, stringBuilder.length());
        String[] strings = allText.split("/");
        String[] tests = new String[strings.length];
        String[] answers = new String[strings.length];
        int flag = 0;
        for (String string : strings) {
            // 此处的 string 是每一道题的题目与答案
            String[] split = string.split("正确答案：");
            tests[flag] = split[0];
            answers[flag] = split[1];
            flag++;
        }
        String s = stringBuilder.toString();
        System.out.println(s);
        for (int i = 0; i < flag; i++) {
            testMap.put(tests[i], answers[i]);
        }
        System.out.println("-------");
        System.out.println(testMap);
        return testMap;
    }
}
