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
        System.out.println("Enter a number: ");
        int n = sc.nextInt();
        System.out.println("Enter a double: ");
        double d = sc.nextDouble();
        System.out.println("Enter a boolean: ");
        boolean b = sc.nextBoolean();
        System.out.println("Enter a char: ");
        char c = sc.next().charAt(0);
        System.out.println("Enter a long: ");
        long l = sc.nextLong();
        System.out.println("Enter a float: ");
        float f = sc.nextFloat();
        System.out.println("Enter a byte: ");
        byte by = sc.nextByte();
        System.out.println("Enter a short: ");
        short sh = sc.nextShort();
        System.out.println("Enter a string: ");
        String s3 = sc.next();
        System.out.println("Enter a string: ");
        String s4 = sc.next();
        System.out.println(s2);
        System.out.println(n);
        System.out.println(d);
        System.out.println(b);
        System.out.println(c);
        System.out.println(l);
        System.out.println(f);
        System.out.println(by);
        System.out.println(sh);
        System.out.println(s3);
        System.out.println(s4);
        System.out.println(s2);
        sc.close();
    }

    public static void test() {
        System.out.println("Hello world!");
    }

    static void test2(int a, int b) {
        System.out.printf("a = %d, b = %d\n", a, b);
    }
}
