public class Classroom {
    public String student;
    public String grade;
    public String teacher;
    public String subject;
    public String room;
    public String period;

    public Classroom() {
        this.student = "";
        this.grade = "";
        this.teacher = "";
        this.subject = "";
        this.room = "";
        this.period = "";
    }

    public Classroom(String student, String grade, String teacher, String subject, String room, String period) {
        this.student = student;
        this.grade = grade;
        this.teacher = teacher;
        this.subject = subject;
        this.room = room;
        this.period = period;
    }

    public static void main(String[] args) {
        Classroom c = new Classroom("John", "12", "Mr. Smith", "AP Computer Science", "123", "1");
        System.out.println(c);

    }

    public String toString() {
        return student + " is in " + teacher + "'s " + subject + " class in room " + room + " during period " + period + ".";
    }

    public String getStudent() {
        return student;
    }

}
