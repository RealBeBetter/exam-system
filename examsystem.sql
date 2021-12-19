create database examsystem;
use examsystem;
create table administrator
(
    password varchar(20) not null            -- 管理员密码
)comment = '后期使用JDBC时候，不允许添加';
insert into administrator values("123456");
create table test
(
    num int auto_increment primary key not null, 			
    question varchar(500) not null,    		 
    answer varchar(10) not null			     
);
create table student
(
    sname varchar(10) not null,				 -- 姓名
    sno varchar(20) primary key not null,  	 -- 学号
    password varchar(20) not null,	  		 -- 密码
    ssex varchar(4) not null,				 -- 性别
    sage varchar(4) not null,				 -- 年龄
    major varchar(10) not null,				 -- 专业
    department varchar(10) not null			 -- 系别
);
create table teacher
(
    tno varchar(20) not null primary key, 		-- 教师工号
    password varchar(20) not null,				-- 教师密码
    tname varchar(10) not null,					-- 教师姓名
    tsex varchar(4) not null					-- 教师性别
);
create table sc
(
    sno varchar(20) not null primary key,		-- 学生学号
    score int not null,							-- 课程得分
    foreign key(sno) references student(sno)
);
