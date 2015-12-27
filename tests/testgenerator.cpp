#include <iostream>
#include <stdlib.h>
#include <time.h>       /* time */
#include <fstream>
#include <cmath>

using namespace std;

int main(int argc, char *argv[]){

    ofstream fout (argv[3]) ;
    srand (time(NULL));
    int N = atoi(argv[1]);
    int M = atoi(argv[2]);
    /*char **a=new  char*[n];
      char **b=new  char*[n];
      for(int i=0;i<n;i++){
      a[i]=new char[m];
      b[i]=new char[m];
      }*/
    char a[N][M],b[N][M];
    int countX=0;
    int ar;
    for (int i=0;i<N;i++)
        for (int j=0;j<M;j++)
            if((ar=rand()%10) < 9) {
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
        startx1=rand() % N;
        starty1=rand() % M;
        startx2=rand() % N;
        starty2=rand() % M;
    }
    while(a[startx1][starty1]!='O'  || a[startx2][starty2]!='O' || (startx1==startx2 && starty1!=starty2));

    //final position
    int endx,endy;
    do{
        endx=rand() % N;
        endy=rand() % M;
    }
    while (a[endx][endy]!='O');



    // meeting points
    int meet = rand() % (N/2);
    int meeting1[meet], meeting2[meet];

    for (int i = 0; i < meet; i++){
        do{
            meeting1[i]=rand() % N;
            meeting2[i]=rand() % M;
        }
        while(b[meeting1[i]][meeting2[i]]!='O' || (meeting1[i]==endx && meeting2[i]==endy));
        b[meeting1[i]][meeting2[i]]='C';
    }


    // printing

    fout << N << " " << M << endl;
    fout << startx1+1<< " "<< starty1+1 << endl;
    fout << startx2+1<< " "<< starty2+1 << endl;
    fout << endx+1 << " " << endy+1 << endl;
    fout << meet << endl;
    for (int i=0;i<meet;i++) fout << meeting1[i]+1 << " " << meeting2[i]+1 << endl;
    for (int i=0;i<N;i++){
        for (int j=0;j<M;j++)
            fout << a[i][j];
        fout << endl;
    }
    fout << endl << endl;


    return 0;
}
