package pojo;

public interface StudentMapper {
    // 根据 id 查询学生信息
    public Student findStudentById(int id) throws Exception;
    //注解方式
    //@Select("SELECT * FROM student WHERE student_id = #{id}")
    //	public Student findStudentById(int id) throws Exception;

    // 添加学生信息
    public void insertStudent(Student student) throws Exception;

    // 删除学生信息
    public void deleteStudent(int id) throws Exception;

    // 修改学生信息
    public void updateStudent(Student student) throws Exception;
}
