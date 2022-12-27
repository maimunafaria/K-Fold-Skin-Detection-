import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)throws Exception
    {
        System.out.println("Enter the Value of k:");
        Scanner sc= new Scanner(System.in);
        int k=sc.nextInt();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<555; i++) list.add(i);
        Collections.shuffle(list);
       // for (int i=0; i<555; i++) System.out.println(list.get(i));
        Train tr= new Train();
        Test test = new Test();
        for(int i=0;i<k;i++) {
            tr.Train(k,i,list);
            test.test(k,i,list);
        }
    }
}