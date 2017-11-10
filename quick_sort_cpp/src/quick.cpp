//============================================================================
// Name        : quick.cpp
// Author      : Venkatesh Lokare
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================
#include <pthread.h>
#include <iostream>
using namespace std;

pthread_t threads[100];

struct args
{
	int *arr;
	int low;
	int high;
	int tid;
};

int part(int *arr,int lower,int higher)
	{
		int i,j,temp,key;
		key=arr[lower];
		i=lower+1;
		j=higher;

		while(1)
		{
			while(i<higher && key>arr[i])
				i++;
			while(arr[j]>key)
				j--;
			if(i<j)
			{
				temp=arr[i];
				arr[i]=arr[j];
				arr[j]=temp;
			}
			else
			{
				temp=arr[lower];
				arr[lower]=arr[j];
				arr[j]=temp;
				return j;

			}
		}
	}

	void* quicksort(void* argument)//int arr[],int lower,int higher)
	{
		struct args *argu= (struct args*) argument;
		int *arr=argu->arr;
		int lower=argu->low;
		int higher = argu->high;
		if(lower<higher)
		{
			int mid=part(arr,lower,higher);
			cout<<"Pivot element with index "<<mid<<" has been found out by thread "<<argu->tid<<endl;
			args *argu1= new args();
				argu1->arr=arr;
				argu1->high=mid-1;
				argu1->low=lower;
				argu1->tid=2*argu->tid;

				//quicksort(arr,0,n-1);
			pthread_create(&threads[argu1->tid],NULL,quicksort,(void*)argu1);
			//quicksort(arr,lower,mid-1);

			args *argu2= new args();
			argu2->arr=arr;
			argu2->high=mid-1;
			argu2->low=lower;
			argu2->tid=2*argu->tid+1;

							//quicksort(arr,0,n-1);
			pthread_create(&threads[argu2->tid],NULL,quicksort,(void*)argu2);
			//quicksort(arr,mid+1,higher);

			pthread_join(threads[argu1->tid],NULL);
			pthread_join(threads[argu2->tid],NULL);
		}
	}


int main() {

	int arr[5];//={5,4,3,2,1};
	int n,i;

	cout<<"enter nos of input"<<endl;
	cin>>n;
	cout<<n;
	cout<<"enter nos"<<endl;
	for(int i=0;i<n;i++)
	{
		cin>>arr[i];
	}
	cout<<"ans";
		for(int i=0;i<n;i++)
		{
			cout<<arr[i];
		}

	args *argu= new args();
	argu->arr=arr;
	argu->high=n-1;
	argu->low=0;
	argu->tid=0;

	//quicksort(arr,0,n-1);
	pthread_create(&threads[argu->tid],NULL,quicksort,(void*)argu);
	pthread_join(threads[argu->tid],NULL);
	cout<<"ans";
			for(int i=0;i<n;i++)
			{
				cout<<arr[i];
			}

	cout << "!!!Hello World!!!" << endl; // prints !!!Hello World!!!
	return 0;
}
