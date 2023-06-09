import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.addFirst(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.addLast(4);
        System.out.println(list);
        // add upto 10
        ArrayList<Integer> list2 = new ArrayList<Integer>((Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));

        // filter even numbers / only keep even
        ArrayList<Integer> evenIntegers = list2.stream().filter(i -> i % 2 == 0).collect(Collectors.toCollection(ArrayList::new));
        System.out.println(evenIntegers);



    }
}