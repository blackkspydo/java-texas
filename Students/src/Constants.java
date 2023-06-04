import java.util.ArrayList;

public class Constants {
    public static final Float PASSING_GPA = 2.0f;
    public static final ArrayList<Courses> THIRD_SEM_COURSES = new ArrayList<Courses>(5){{
        add(Courses.DSA);
        add(Courses.CG);
        add(Courses.CA);
        add(Courses.StatsI);
        add(Courses.NM);
    }};

    public static final ArrayList<Courses> FIRST_SEM_COURSES = new ArrayList<Courses>(5){{
        add(Courses.Physics);
        add(Courses.MathematicsI);
        add(Courses.DL);
        add(Courses.C);
        add(Courses.IIT);
    }};

    public static final ArrayList<Courses> SECOND_SEM_COURSES = new ArrayList<Courses>(5){{
        add(Courses.DS);
        add(Courses.OOP);
        add(Courses.MP);
        add(Courses.StatI);
        add(Courses.MathematicsII);
    }};
}
