package test;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import pojo.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMyBatis {

    public static void main(String[] args) throws IOException {
        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 然后根据 sqlSessionFactory 得到 session
        //SqlSession 的作用类似于一个 JDBC 中的 Connection 对象，代表着一个连接资源的启用。具体而言，它的作用有 3 个：
        //获取 Mapper 接口。
        //发送 SQL 给数据库。
        //控制数据库事务。
        SqlSession session = sqlSessionFactory.openSession();

        // 找到身份证身份证号码为 1111 的学生
        StudentAndCard student=session.selectOne("findStudentByCard",1111);
        // 获得其姓名并输出
        System.out.println(student.getName());

        StudentWithCard student1=session.selectOne("findStudentByCard1",1111);
        System.out.println(student1.getStudent1().getName());


//        // 增加学生
//        Student student1 = new Student();
//        student1.setId(4);
//        student1.setStudentID(4);
//        student1.setName("新增加的学生");
//        session.insert("addStudent", student1);
//
//        // 删除学生
//        Student student2 = new Student();
//        student2.setId(1);
//        session.delete("deleteStudent", student2);
//
//        // 获取学生
//        Student student3 = session.selectOne("getStudent", 2);
//
//        // 修改学生
//        student3.setName("修改的学生");
//        session.update("updateStudent", student3);
//
//        // 最后通过 session 的 selectList() 方法调用 sql 语句 listStudent
//        List<Student> listStudent = session.selectList("listStudent");
//        for (Student student : listStudent) {
//            System.out.println("ID:" + student.getId() + ",NAME:" + student.getName());
//        }
//        // 提交修改
//        session.commit();
//        // 关闭 session
//        session.close();
    }

    /**
     * 模糊查询
     */
    @Test
    public void test() throws IOException {

        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 然后根据 sqlSessionFactory 得到 session
        SqlSession session = sqlSessionFactory.openSession();

        // 模糊查询
        List<Student> students = session.selectList("findStudentByName", "三颗心脏");
        for (Student student : students) {
            System.out.println("ID:" + student.getId() + ",NAME:" + student.getName());
        }
    }
/**一对多**/
    @Test
    public void test3() throws IOException {

        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactoryBuilder（构造器）：它会根据配置或者代码来生成 SqlSessionFactory，采用的是分步构建的 Builder 模式。
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 然后根据 sqlSessionFactory 得到 session，SqlSession（会话）：一个既可以发送 SQL 执行返回结果，也可以获取 Mapper 的接口。在现有的技术中，一般我们会让其在业务逻辑代码中“消失”，而使用的是 MyBatis 提供的 SQL Mapper 接口编程技术，它能提高代码的可读性和可维护性。
        SqlSession session = sqlSessionFactory.openSession();

        // 查询上Java课的全部学生
        List<Student2> students2 = session.selectList("listStudentByClassName", "Java课");
        for (Student2 student : students2) {
            System.out.println("ID:" + student.getId() + ",NAME:" + student.getName());
        }
    }
    /**多对多**/
    @Test
    public void test4() throws IOException {

        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 然后根据 sqlSessionFactory 得到 session
        SqlSession session = sqlSessionFactory.openSession();

        // 查询上Java课的全部学生
        List<Student2> students2 = session.selectList("findStudentsByCourseName", "Java课");
        for (Student2 student : students2) {
            System.out.println("ID:" + student.getId() + ",NAME:" + student.getName());
        }
    }


    /**
    Mapper 动态代理？一般创建 Web 工程时，从数据库取数据的逻辑会放置在 DAO 层（Date Access Object，数据访问对象）。
   使用 MyBatis 开发 Web 工程时，通过 Mapper 动态代理机制，可以只编写数据交互的接口及方法定义，和对应的 Mapper 映射文件，
   具体的交互方法实现由 MyBatis 来完成。这样大大节省了开发 DAO 层的时间。**/
    @Test
    public void test5() throws Exception {

        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 然后根据 sqlSessionFactory 得到 session
        SqlSession session = sqlSessionFactory.openSession();
        // 获取 Mapper 代理
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        // 执行 Mapper 代理独享的查询方法
        Student student = studentMapper.findStudentById(1);
        System.out.println("学生的姓名为：" + student.getName());
        session.close();
    }
}

