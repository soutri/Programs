package bayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyTry {

	static String[][] data= new String[20][5];
	static int n=0;
	static String age="<=30";
	static String income="medium";
	static String student="yes";
	static String cred="fair";
	
	public static void main(String args[]) throws FileNotFoundException, InterruptedException
	{
		File file=new File("/home/venky/workspace/cpp/bayes/src/bayes/b12d.txt");
		Scanner sc= new Scanner(file);
		while (sc.hasNext())
		{
			data[n++]=sc.nextLine().split(",");
		}
		sc.close();
		
		float c1=0,c2;
		for(int i=0;i<n;i++)
		{
			if(data[i][4].equals("yes"))
				c1++;
		}
		c2=n-c1;
		float pc1=c1/n;
		float pc2=c2/n;
		
		class cyes extends Thread
		{
			float pyes=0;
			public void run()
			{
				pyes=pfind("yes");
			}
			
		}
		
		class cno extends Thread
		{
			float pno=0;
			public void run()
			{
				pno=pfind("no");
			}
		}
		
		cyes t1= new cyes();
		cno t2= new cno();
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		float pbuys=t1.pyes*pc1;
		float pnouys=t2.pno*pc2;
		
		System.out.print(pbuys+" "+pnouys);
		
	}
	
	static float pfind(String classtype )
	{
		float a=0,b=0,c=0,d=0,total=0;
		for(int i=0;i<n;i++)
		{
			if(data[i][4].equals(classtype))
			{
				if(data[i][0].equals(age))a++;
				if(data[i][1].equals(income))b++;
				if(data[i][2].equals(student))c++;
				if(data[i][3].equals(cred))d++;
				total++;
			}
			
		}
		return (a/total)*(b/total)*(c/total)*(d/total);
	}
}
