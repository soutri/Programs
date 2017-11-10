//booths.cpp


#include<mpi.h>
#include<stdio.h>
#include<bitset>
#include<math.h>
#include<iostream>
#include<stdio.h>

using namespace std;

class booths{

public:
       int multiplicant,multiplier;
       int result=0;
       booths()
       {
         multiplicant=0;
	     multiplier=0;
       }
       void printValues();
       void multiply();
       void returnResult();	
};


void booths::multiply()
{       int rank;
        MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	
	int leftShiftValue=0;

	if(rank==1)
	{
		leftShiftValue=0;
	}
	else 
	{
		leftShiftValue=pow(2,rank-1);     //extra zeros to the right while adding in multiplication
	}
	
	

	int temp=multiplicant;
	switch(multiplier)
	{
	 case 0: 
		result=0;
		break;
	 case 1:
		result=multiplicant;            // multiply by one(same);
		break;
	 case -1:
		result=~(multiplicant)+1;       //two's complement
		break;
	 case 2:
		result=temp<<1;                 // multiply by 2
		break;
	 case -2:
		result=~(temp<<1)+1;			//multiply by -2 i.e. multiply by 2 and calculate 2's complement
		break;
	}

   result=result<<leftShiftValue;

cout<<"\nResult "<<rank<<" :"<<result<<"\n";

}

void booths::returnResult()
{
	int rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Send(&result,1,MPI_INT,0,rank*100+1,MPI_COMM_WORLD); //tags 101,201,301,401

}
void booths::printValues()
{ 
	int rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	cout<<"Process "<<rank<<" M: "<<multiplicant<<" m: "<<multiplier<<"\n";
}



int main(int argc, char* argv[])
{
int nodes,myid;
MPI_Init(&argc,&argv);
MPI_Comm_size( MPI_COMM_WORLD, &nodes);
MPI_Comm_rank(MPI_COMM_WORLD, &myid);


if(myid==0)
{

int pairArray[4];
int pairCount=0;

int multiplicant=0,multiplier=0;

cout<<"\nEnter first number: ";
cin>>multiplicant;

cout<<"\nEnter second number: ";
cin>>multiplier;
int k=multiplier<<1;
bitset<9> mbits=k;

for(int i=7;i>=1;i=i-2)
{

	if(mbits[i+1]==0 && mbits[i]==0 && mbits[i-1]==0)
        {
        	pairArray[pairCount]=0;        	  
	}
        else if(mbits[i+1]==0 && mbits[i]==0 && mbits[i-1]==1)
        {
        	pairArray[pairCount]=1;
	}
	else if(mbits[i+1]==0 && mbits[i]==1 && mbits[i-1]==0)
        {
        	pairArray[pairCount]=1;
	}
	else if(mbits[i+1]==0 && mbits[i]==1 && mbits[i-1]==1)
        {
        	pairArray[pairCount]=2;
	}
	else if(mbits[i+1]==1 && mbits[i]==0 && mbits[i-1]==0)
        {
        	pairArray[pairCount]=-2; 
	}
	else if(mbits[i+1]==1 && mbits[i]==0 && mbits[i-1]==1)
        {
        	pairArray[pairCount]=-1;  
	}
	else if(mbits[i+1]==1 && mbits[i]==1 && mbits[i-1]==0)
        {
        	pairArray[pairCount]=-1;  
	}
	else if(mbits[i+1]==1 && mbits[i]==1 && mbits[i-1]==1)
        {
        	pairArray[pairCount]=0;
	}

	pairCount++;
}

/////////////recorded bit array is populated //////////////////
cout<<"Recoded Multiplier:\n";
for(int i=0;i<4;i++)
{
cout<<"\t"<<pairArray[i];
MPI_Send(&multiplicant,1,MPI_INT,i+1,(i+1)*10+1,MPI_COMM_WORLD);//tags 11,21,31,41
MPI_Send(&pairArray[3-i],1,MPI_INT,i+1,(i+1)*10+2,MPI_COMM_WORLD);//tags 12,22,32,42

}
cout<<"\n";
///////////////////////////////////////////////////////////////
int finalResult=0;
int temp=0;
MPI_Status status;
for(int i=1;i<5;i++)
{
	MPI_Recv(&temp,1,MPI_INT,i,i*100+1,MPI_COMM_WORLD,&status);  //tags 101,201,301,401
        finalResult=finalResult+temp;
}
 cout<<"Answer: "<<finalResult;

}

else if(myid==1)
{
MPI_Status status;
booths obj;
MPI_Recv(&obj.multiplicant,1,MPI_INT,0,11,MPI_COMM_WORLD,&status);
MPI_Recv(&obj.multiplier,1,MPI_INT,0,12,MPI_COMM_WORLD,&status);
obj.printValues();
obj.multiply();
obj.returnResult();
}
else if(myid==2)
{
MPI_Status status;
booths obj;
MPI_Recv(&obj.multiplicant,1,MPI_INT,0,21,MPI_COMM_WORLD,&status);
MPI_Recv(&obj.multiplier,1,MPI_INT,0,22,MPI_COMM_WORLD,&status);
obj.printValues();
obj.multiply();
obj.returnResult();
}
else if(myid==3)
{
MPI_Status status;
booths obj;
MPI_Recv(&obj.multiplicant,1,MPI_INT,0,31,MPI_COMM_WORLD,&status);
MPI_Recv(&obj.multiplier,1,MPI_INT,0,32,MPI_COMM_WORLD,&status);
obj.printValues();
obj.multiply();
obj.returnResult();
}
else if(myid==4)
{
MPI_Status status;
booths obj;
MPI_Recv(&obj.multiplicant,1,MPI_INT,0,41,MPI_COMM_WORLD,&status);
MPI_Recv(&obj.multiplier,1,MPI_INT,0,42,MPI_COMM_WORLD,&status);
obj.printValues();
obj.multiply();
obj.returnResult();
}



MPI_Finalize();

return 0;
}

