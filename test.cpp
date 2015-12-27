#include <iostream>
#include <stdlib.h>
#include <time.h>       /* time */
#include <fstream>
#include <cmath>
using namespace std;

int main(){

    ofstream fout ("input.txt") ;
  srand (time(NULL));
  int n,m;
  n=rand()%700;
  m=rand()%700;
 /*char **a=new  char*[n];
  char **b=new  char*[n];
  for(int i=0;i<n;i++){
      a[i]=new char[m];
      b[i]=new char[m];
  }*/
  char a[m][n],b[m][n];
  int countX=0;
  int ar;
  for (int i=0;i<m;i++)
    for (int j=0;j<n;j++)
      if((ar=rand()%155) < 151) {
        a[i][j]='O';
        b[i][j]='O';
      }
      else {
        a[i][j]='X';
        b[i][j]='X';
        countX++;
      }
// initial positions
  int startx1,starty1,startx2,starty2;
  do{
    startx1=rand()%m;
    starty1=rand()%n;
    startx2=rand()%m;
    starty2=rand()%n;
  }
  while(a[startx1][starty1]!='O'  || a[startx2][starty2]!='O' || (startx1==startx2 && starty1!=starty2));

  //final position
  int endx,endy;
  do{
    endx=rand()%m;
    endy=rand()%n;
  }
  while (a[endx][endy]!='O');



// meeting points
int meet = rand()% (n/2);
int meeting1[600],meeting2[600];

for (int i=0; i<meet;i++){
  do{
    meeting1[i]=rand()%m;
    meeting2[i]=rand()%n;
  }
  while(b[meeting1[i]][meeting2[i]]!='O' && (meeting1[i]==endx && meeting2[i]==endy));
  b[meeting1[i]][meeting2[i]]='C';
}


// printing

fout << n << " " << m << endl;
fout << starty1+1<< " "<< startx1+1<<endl;
fout << starty2+1<< " "<< startx2+1<<endl;
fout << endy+1 << " " << endx+1 << endl;
fout << meet<<endl;
for (int i=0;i<meet;i++) fout << meeting2[i]+1<< " "<<meeting1[i]+1<<endl;
for (int i=0;i<m;i++){
  for (int j=0;j<n;j++)
    fout <<a[i][j];
  fout << endl;
}
fout << endl << endl;


return 0;
}
