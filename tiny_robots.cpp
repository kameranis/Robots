/* NTUA ECE AI robots
 *
 * Given two robots and a plane with destinations and obstacles
 * implement A* to get the two robots to pass through the destinations
 * and meet at the last one
 */

#include <cstdio>
#include <cstdlib>
#include <set>
#include <vector>
#include <string>

#if defined(ADMISSIBLE)
#define CMP admissible
#else
#define CMP non_admissible
#endif

using namespace std;

class point {
    public:
        int x, y;

        point() {}

        point(int a, int b) {
            x = a;
            y = b;
        }

    	bool operator==(const point &c) const {
    		return this->x==c.x && this->y == c.y;
    	}

        int dist(point const & other) {
            return abs(this->x - other.x) + abs(this->y - other.y);
        }
/*
        int dist() {
            return abs(this->x) + abs(this->y);
        }
*/
    	bool operator<(const point &c) const{
	    	return this->x + this->y < c.x + c.y; // need a rotattion case equals
    	}
};

int admissible(point from, point to) {
    return from.dist(to);
}

int non_admissible(point from, point to) {
    int d = from.dist(to);
    return d * d + 5;
}

/*class state {
    public:
        point ;
        point second;
        bool *visited;

        state() {
            first = point();
            second = point();
            visited = NULL;
        }

        state(point f, point s, bool *v) {
            first = f;
            second = s;
            visited = v;
        }
};
*/
class interest {
    public:
        point current;
	    point goal;
        int cost;
        int estimated;
        interest *parent;

        interest(point curr,point goal) {
            cost = estimated = 0;
            parent = NULL;
    	    current=curr;
    	    this->goal=goal;
	        // add estimated_fuction pointer
        }
        
        interest(interest *p, point curr) {
            cost = p->cost+1;
	        parent = p;
            current = curr;
    	    estimated = CMP(curr,goal);
        }

    	bool operator< (const interest &c) const {
    		return this->estimated <  c.estimated;
    	}
	
        bool operator== (const interest &c) const {
		    return (this->current == c.current);
    	}
	
        vector<interest*> next()
    	{
    		vector<interest*> moves; 
    		point up,down,left,right;
    		up.x=current.x;
    		up.y=current.y+1;
    		down.x=current.x;
    		down.y=current.y-1;
    		left.x=current.x-1;
    		left.y=current.y;
    		right.x=current.x+1;
    		right.y=current.y;
    		interest *a = new interest(this,up);
    		interest *b = new interest(this,down);
    		interest *c = new interest(this,left);
    		interest *d = new interest(this,right);
    		moves.push_back(a);
    		moves.push_back(b);
    		moves.push_back(c);
    		moves.push_back(d);
    		return moves;
    	}
};

void print_table(char table[], point first, point second, int N, int M) {
    int i, j;
    system("CLS");
    for(i = 0; i < N; i++) {
        if(i == first.x && j == first.y) 
            putchar('1');
        else if(i == second.x && j == second.y)
            putchar('2');
        else
            putchar(table[i*N + j]);
    }
}
        

class setcmp{
    public:
        bool operator() (interest *lhs, interest *rhs)
	    {   
    		return lhs->estimated < rhs->estimated; 
    	}
};	

class findcmp{
    public:
    	bool operator() (const interest &lhs, const interest &rhs)
	    {
    		return lhs.current < rhs.current;
    	}
};

interest *A_star(point from, point end,bool player) {
	set<interest, findcmp> ClosedSet; 
	set<interest *, setcmp> StartSet;
	interest *start_point= new interest(nullptr, from);
	StartSet.insert(start_point);
	while(!StartSet.empty())
	{
		interest *curr = *StartSet.begin(); //get curr always the first is the min
		StartSet.erase(StartSet.begin()); //del min
		if(curr->current == end) //need overload 
		{
			return curr;
		}
		vector<interest*> next=curr->next(); // mpla mpla ,insert father mpla mpla
		for(auto i : next)
		{
			if(ClosedSet.find(*i)>0)
				continue;
			if(player && check(i)) //check if one is at this momment there
			StartSet.insert(i);
			else
			{
				//add a stall action and if it is better the algorithm will choose it;
				interest *next;
				*next=*curr;
				next->cost++; // cost also give as the momment :)
				StartSet.insert(next);
			}
		}
	}
	perror("no path exists");
	exit(2);
}

int main() {
    int N, M;
    scanf("%d %d", &N, &M);

    int i, j;
    point first, second;
    
    scanf("%d %d", &i, &j);
    first = point(i, j);
    scanf("%d %d", &i, &j);
    second = point(i, j);
    
    point last;
    scanf("%d %d", &i, &j);
    last = point(i, j);

    int meet_points;
    scanf("%d", &meet_points);
    point meet[meet_points+1];
    meet[0]=first;
    for(i = 1; i <= meet_points; i++) {
        scanf("%d %d", &meet[i].x, &meet[i].y);
    }

    char board[N][M];
    for(i = 0; i < N; i++) {
        for(j = 0; j < M; j++) {
            board[i][j] = getchar();
        }
        getchar();
    }
    // prepei na perasei apo ola t simia.  mas endiaferei sinantisi mono sto teleueteo simio, simfona me euaggelia pou rwtsise kontogianni
 /*   vector<point> fir_route = A_star(first, meet_point[0]);
    for(i = 1; i < meet_points; i++) {
        vector<point> temp = A_star(meet_point[i - 1], meet_points[i]);
        fir_route.insert(fir_route.back(), temp.begin(), temp.end());
    }*/
    vector<interest*> First;
    for(i=1;i<=meet_points ; i++)
    {
    	interest *temp = A_star(meet[i-1], meet[i], 0);
    	First.push_back(temp);
    }
}
