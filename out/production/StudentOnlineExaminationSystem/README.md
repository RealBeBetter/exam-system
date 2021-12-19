所有密码默认均为 **123456**

测试系统环境：

- Windows10 系统
- MySQL 8.0.23
- JDK 14

导入试卷：

如果发生文件找不到，请在下面的代码中修改

src/com/company/testpaper/AddPaper.java

将其中的文件读取信息修改成相对路径

```java
BufferedReader br = new BufferedReader(new FileReader("D:\\Java\\IdeaProjects\\StudentOnlineExaminationSystem\\Test.txt"));
```

修改为：

```java
BufferedReader br = new BufferedReader(new FileReader("StudentOnlineExaminationSystem\\Test.txt"));
```

