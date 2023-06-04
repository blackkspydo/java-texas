import java.util.ArrayList;

public class Student {
    //static variables
    private static Integer numStudents = 0;
    //variables
    private  Float gpa;
    private  Boolean isPass;
    private  String name;
    private final Integer ID;
    private ArrayList<Courses> courses = new ArrayList<Courses>(5);

    //constructor
    public Student(String name, Float gpa) {
        this.name = name;
        this.gpa = gpa;
        this.isPass = calculateIsPass(this.gpa);
        this.ID = Student.getNewID();
    }

    //getters
    public Integer getID() {
        return this.ID;
    }

    public Float getGpa() {
        return gpa;
    }
    public Boolean getIsPass() {
        return isPass;
    }
    public String getName() {
        return name;
    }

    public ArrayList<Courses> getCourses() {
        return courses;
    }

    //setters
    public void setGpa(Float gpa) {
        this.gpa = gpa;
        this.isPass = calculateIsPass(this.gpa);
    }

    public void setName(String name) {
        this.name = name;
    }


    //methods
    public boolean calculateIsPass (Float gpa) {
        return gpa >= Constants.PASSING_GPA;
    }

    public void addCourse(Courses course) {
        this.courses.add(course);
    }

    public void addCourses(ArrayList<Courses> courses) {
        this.courses.addAll(courses);
    }

    @Override
    public String toString(){
        String courses = "";
        return """
                ID: %d
                Name: %s
                GPA: %.2f
                Pass: %s
                Courses: %s
                """.formatted(ID, this.name, this.gpa, this.isPass, this.courses.toString());
    }

    private static Integer getNewID() {
        return ++numStudents;
    }


}
