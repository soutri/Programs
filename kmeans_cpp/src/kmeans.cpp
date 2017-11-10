//============================================================================
// Name        : kmeans.cpp
// Author      : Venkatesh Lokare
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
using namespace std;

class point
{
public:
	double x;
	double y;

	point(double a,double b)
	{
		x=a;
		y=b;
	}
};


class kmeans
{

public:
	point *mypoints[10];
	point *clusters[3];
	int belongs[10];
	point * sums[3];
	int count[3];

	kmeans()
	{
		cout<<"enter points"<<endl;
		int x,y;
		for(int i=0;i<10;i++)
		{
			cin>>x>>y;
			mypoints[i]= new point(x,y);
		}
		for(int i=0;i<10;i++)
		{
			cout<<mypoints[i]->x<<mypoints[i]->y;
		}
	}

	void assign()
	{
		clusters[0]=mypoints[0];
		clusters[1]=mypoints[5];
		clusters[2]=mypoints[9];

	}

	void update()
	{

		for(int p=0;p<3;p++)sums[p]=new point(0,0);
		for(int i=0;i<10;i++)
		{
			int min=9999;
			int clus=0;
			int d=0;
			for (int j=0;j<3;j++)
			{
				d=(clusters[j]->x-mypoints[i]->x)*(clusters[j]->x-mypoints[i]->x)+(clusters[j]->y-mypoints[i]->y)*(clusters[j]->y-mypoints[i]->y);
				if(d<min)
				{
					min=d;
					clus=j;
				}
			}
			belongs[i]=clus;
			sums[clus]->x+=mypoints[i]->x;
			sums[clus]->y+=mypoints[i]->y;
			count[clus]++;
		}
	}

	void newclus()
	{
		for(int i=0;i<3;i++)
		{
			clusters[i]->x=sums[i]->x/3;
			clusters[i]->y=sums[i]->y/3;
		}
	}

	bool check()
	{
		for(int i=0;i<3;i++)
		if(clusters[i]->x!=sums[i]->x/3 || clusters[i]->y!=sums[i]->y/3)
			return true;
		return false;

	}

	void display()
	{
		for(int i=0;i<10;i++)
				{
					cout<<mypoints[i]->x<<" "<<mypoints[i]->y<<" "<<belongs[i]<<endl;
				}
	}
};

int main() {
	kmeans k;
	k.assign();
	while(1)
	{

		k.update();
		if(k.check())break;
		k.newclus();

	}
	k.display();
	cout << "!!!Hello World!!!" << endl; // prints !!!Hello World!!!
	return 0;
}
