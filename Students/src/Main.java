import java.util.*;

public class Main {
    public static void main(String[] args) {


        //Students init
        Student kishor = new Student("Kishor", 4.0f);
        Student miraj = new Student("Miraj", 3.9f);
        Student bimala = new Student("Bimala", 1.8f);
        Student ruppu = new Student("Ruppu", 2.5f);
        kishor.addCourses(Constants.THIRD_SEM_COURSES);
        miraj.addCourses(Constants.THIRD_SEM_COURSES);
        bimala.addCourses(Constants.SECOND_SEM_COURSES);
        ruppu.addCourses(Constants.FIRST_SEM_COURSES);
        //Students init end

        //Populating Student
        // List inits
        List<Student> firstSemStudents = new ArrayList<Student>(List.of(ruppu));
        List<Student> secondSemStudents = new ArrayList<Student>(List.of(bimala));
        List<Student> thirdSemStudents = new ArrayList<Student>(List.of(kishor, miraj));

        //Hashmaps
        HashMap<Integer, Student> studentsData = new HashMap<Integer, Student>();
        firstSemStudents.forEach(student -> {
            studentsData.put(student.getID(), student);
        });
        secondSemStudents.forEach(student -> {
            studentsData.put(student.getID(), student);
        });
        thirdSemStudents.forEach(student -> {
            studentsData.put(student.getID(), student);
        });

        //Students print
//        System.out.println(firstSemStudents);
//        System.out.println(secondSemStudents);
//        System.out.println(thirdSemStudents);

        // querying hashmaps
        Student m = getStudent(1, studentsData);
        System.out.println(m);

        // all courses
        Set<Courses> allCourses = new HashSet<Courses>();
        for(Student student: studentsData.values()){
            allCourses.addAll(student.getCourses());
        }
        System.out.println(allCourses);
    }

    public static Student getStudent(Integer ID, HashMap<Integer, Student> hashmap) {
        Student student = hashmap.get(ID);
        if (student == null) throw new Error("Student not found");
        return student;
    }

}