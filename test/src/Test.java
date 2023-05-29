import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        test();
        test2(1, 4);
        test2(6, 5);
        String s = "Hello world!";
        System.out.println(s);
        System.out.println("Enter a string: ");
        String s2 = sc.nextLine();
        System.out.println(s2);
    }

    public static void test() {
        System.out.println("Hello world!");
    }

    static void test2(int a, int b) {
        System.out.printf("a = %d, b = %d\n", a, b);
    }
}
