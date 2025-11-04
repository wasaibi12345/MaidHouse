import java.util.*;

// ==========================
// 学院类 School
// ==========================
public class School {

    // 多重性：School --> 1..* Department
    private List<Department> departments;
    // 多重性：School --> 0..* Student
    private List<Student> students;

    // 构造器，确保 School 创建时满足以上约束
    public School(List<Department> departments, List<Student> students) {
        if (departments == null || departments.isEmpty()) {
            throw new IllegalArgumentException("学院 School 必须至少有一个系部 Department");
        }

        this.departments = departments;
    }

}

// ==========================
// 系部类 Department
// ==========================
public class Department {

    // 多重性：Department --> 1 School
    private School school;
    // 多重性：Department --> 1..* Instructor
    private List<Instructor> instructors;
    // 多重性：Department --> 1..* Subject
    private List<Subject> subjects;

    // 构造器，确保 Department 创建时满足以上约束
    public Department(School school, List<Instructor> instructors, List<Subject> subjects) {
        if (school == null)
            throw new IllegalArgumentException("系 Department 必须隶属于一个学院 School");
        if (instructors == null || instructors.isEmpty())
            throw new IllegalArgumentException("系 Department 必须拥有至少一个指导教师 Instructor");
        if (subjects == null || subjects.isEmpty())
            throw new IllegalArgumentException("系 Department 必须开设至少一门课程 Subject");

        this.school = school;
        this.instructors = instructors;
        this.subjects = subjects;
    }
}


// ==========================
// 指导老师类 Instructor
// ==========================
public class Instructor {
    // 多重性：Instructor --> 1..* Department
    private List<Department> departments;
    // 多重性：Instructor --> 1..3 Subject
    private List<Subject> subjects;

    // 构造器，确保 Instructor 创建时满足以上约束
    public Instructor(List<Department> departments, List<Subject> subjects) {
        if (departments == null || departments.isEmpty())
            throw new IllegalArgumentException("指导教师 Instructor 必须至少属于一个系 Department");
        if (subjects == null || subjects.isEmpty() || subjects.size() > 3)
            throw new IllegalArgumentException("指导教师 Instructor 必须教授 1~3 门课程 Subject");

        this.departments = departments;
        this.subjects = subjects;
    }

}

// ==========================
// 课程类 Subject
// ==========================
public class Subject {
    // 多重性：Subject --> 1 Department
    private Department department;
    // 多重性：Subject --> 0..* Student
    private List<Student> students;
    // 多重性：Subject --> 1..* Instructor
    private List<Instructor> instructors;

    // 构造器，确保 Subject 创建时满足以上约束
    public Subject(Department department, List<Instructor> instructors) {
        if (department == null)
            throw new IllegalArgumentException("课程 Subject 必须隶属于一个系 Department");
        if (instructors == null || instructors.isEmpty())
            throw new IllegalArgumentException("课程 Subject 必须至少由一位教师 Instructor 授课");

        this.department = department;
        this.instructors = instructors;
    }
}

// ==========================
// 学生类 Student
// ==========================
public class Student {
    // 多重性：Student --> 1 School
    private School school;
    // 多重性：Student --> 1..5 Subject
    private List<Subject> subjects;

    // 构造器，确保 Student 创建时满足以上约束
    public Student(School school, List<Subject> subjects) {
        if (school == null)
            throw new IllegalArgumentException("学生 Student 必须隶属于一个学院 School");
        if (subjects == null || subjects.isEmpty() || subjects.size() > 5)
            throw new IllegalArgumentException("学生 Student 必须参加 1~5 门课程 Subject");

        this.school = school;
        this.subjects = subjects;
    }
}