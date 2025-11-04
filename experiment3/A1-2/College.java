import java.util.ArrayList;
import java.util.List;


// ==========================
// 学院类 College
// ==========================
public class College {

    // College 对象持有一系列 Building 对象的引用
    private List<Building> buildings;

}


// ==========================
// 建筑类 Building
// ==========================
public class Building {

    // Building 对象持有一系列 Course 对象的引用
    private List<Course> courses;

    // 用于实现组合关系的工厂方法：以自身作为绑定目标，创建 Course 对象
    public Course createCourse() {
        Course course = new Course(this);
        courses.add(course);
        return course;
    }

}

// ==========================
// 课程类 Course
// ==========================
public class Course {

    private Building building; 

    // 构造器，确保 Student 创建时满足约束：Course 必须在某个 Building 中开设
    public Course(Building building) {
        
        if (building == null) {
            throw new IllegalArgumentException("Course 必须在某个 Building 中开设");
        }

        this.building = building;
    }

}

