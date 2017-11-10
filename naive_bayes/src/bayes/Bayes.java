package bayes;

import java.util.Scanner;
import java.io.*;

public class Bayes
{
public static void main(String args[]) throws FileNotFoundException
{
	/*	c1 and c2 denote class 1 & class 2. 
	class1 :- Customer buys computer
	class2 :- Customer doesn't buy computer	*/
	final String[][] data1 = new String[20][5];
	String delimiter = ",";
	File file=new File("/home/nikita/workspace/b12/src/data.txt");
	Scanner f = new Scanner(file);     
	int index=0;
	int n=0;
	while (f.hasNextLine()) 
	{
	    String line = f.nextLine();
	    data1[index++] = line.split(delimiter);
		n++;
	}
	f.close();
	int c1=0,c2=0;
	for(int i=0;i<14;i++){
		if(data1[i][4].equals("yes"))
				c1++;
	}
	c2=n-c1;

	final float pc1 = (float)c1/n; //General probability for class c1
	final float pc2 = (float)c2/n; //General probability for class c2
	
	System.out.println("c1= " +c1 +"\nc2="+c2+"\ntotal="+n);
	System.out.println("p(c1)="+pc1);
	System.out.println("p(c2)="+pc2);
	
	Scanner sc = new Scanner(System.in);
	final String age,income,student,credit_rating;	
	// Accept the parameter values for which class is to be predicted	
	System.out.println("Enter age: (<=30/30to40/>40)");
	age = sc.next();
	System.out.println("Enter income:(low/medium/high)");
	income = sc.next();
	System.out.println("Enter student:(yes/no)");
	student = sc.next();
	System.out.println("Enter credit_rating:(fair/excellent)");
	credit_rating = sc.next();
	//float pinc1=0,pinc2=0;		
	
	class cyes extends Thread{
		float pinc1=0;
		public void run(){
			pinc1 = pfind(data1,age,income,student,credit_rating,"yes");//probability of prediction to be class1 (will buy)
			pinc1 = pinc1 * pc1;
		}
	}
	class cno extends Thread{
		float pinc2=0;
		public void run(){
			pinc2 = pfind(data1,age,income,student,credit_rating,"no");
			pinc2 = pinc2 * pc2;
		}
	}
	cyes t1=new cyes();
	cno t2=new cno();
	t1.start();
	t2.start();
	try {
		t1.join();
		t2.join();
		System.out.println("p(yes)="+t1.pinc1);
		System.out.println("p(no)="+t2.pinc2);

	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	// compare pinc1 & pinc2 and predict the class that user will or won't buy
	if(t1.pinc1 > t2.pinc2)
		System.out.println("Will buy computer");
	else
		System.out.println("Will not buy computer");
	sc.close();
}
public static float pfind(String data1[][],String age,String income,String student,String credit_rating,String class1)
{
	float ans = 0;
	try{
		int a=0 , b=0 , c=0 , d=0 , total=0;		
		/* 	Function finds probability for every individual parameter of provided class value 
			and using naive baye's theorem it calculates total probability */
		for(int i=0;i<14;i++){
			if(data1[i][0].equals(age) && data1[i][4].equals(class1))
					a++;// a = count of values in training set having age , class same as passed in argument
			if(data1[i][1].equals(income) && data1[i][4].equals(class1))
				b++;// b = count of values in training set having income , class same as passed in argument
			if(data1[i][2].equals(student) && data1[i][4].equals(class1))
				c++;// c = count of values in training set having student , class same as passed in argument
			if(data1[i][3].equals(credit_rating) && data1[i][4].equals(class1))
				d++;// d = count of values in training set having credit_rating , class same as passed in argument
			if(data1[i][4].equals(class1))
				total++;//total no results
		}		
		ans = (float)a / (float)total  * (float)b /(float)total * (float)c /(float)total * (float)d /(float)total ;//calculating total probability by naive bayes
	}
	catch(Exception e)
	{
		System.out.println("Exception:"+ e);
	}
	return ans;
}
}

/*---------------------------------------------------------
//data.txt
----------------------------------------------------------
//output
c1= 9
c2=5
total=14
p(c1)=0.64285713
p(c2)=0.35714287
Enter age: (<=30/30to40/>40)
<=30
Enter income:(low/medium/high)
medium
Enter student:(yes/no)
yes
Enter credit_rating:(fair/excellent)
fair
p(yes)=0.028218694
p(no)=0.0068571432
Will buy computer*/