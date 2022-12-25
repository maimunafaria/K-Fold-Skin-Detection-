package KFOLD;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)throws Exception
    {
    	System.out.println("Enter the Value of k:");
    	Scanner sc= new Scanner(System.in);
    	int k=sc.nextInt();
    	Train tr= new Train();
    	Test test = new Test();
    	for(int i=0;i<k;i++) {
    		tr.Train(k,i);
    		test.test(k,i);
    	}
    }
}