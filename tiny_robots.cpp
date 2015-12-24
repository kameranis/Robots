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
            return math.abs(this->x - other.x) + math.abs(this->y - other.y);
        }
};

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
	int *estimated_function(point,point)=nullptr;
        interest *parent;
        interest(int (*estimator) (point,point),point goal) {
            cost = estimated = 0;
            parent = NULL;
	    estimated_function = & estimator ;
	    this->goal=goal;
	    // add estimated_fuction pointer
        }
        interest(interest *p, point curr) {
            cost = p->cost+1;
	    parent = p;
            current = curr;
	    estimated_function = &(parent->estimated_function);
	    estimated = *estimated_function(curr,goal);
        }
	bool operator< (const interest &c) const {
		return this->estimated <  c.estimated;
	}
	bool operator== (const interest &c) const {
		return this->current == c.current;
	}
	vector<interest> next()
	{
		vector<interest> moves; 
		point up,down,left,right;
		up.x=current.x;
		up.y=current.y+1;
		down.x=current.x;
		down.y=current.y-1;
		left.x=current.x-1;
		left.y=current.y;
		right.x=current.x+1;
		right.y=current.y;
		interest a(this,up);
		interest b(this,down);
		interest c(this,left);
		interest d(this,right);
		moves.push_back(a);
		moves.push_back(b);
		moves.push_back(c);
		moves.push_back(d);
		return moves;
	}
};


void ClearScreen() {
    printf("%s", string(100, '\n'a));
}

void print_table(char table[], state *curr, int N, int M) {
    int i, j;
    ClearScreen();
    for(i = 0; i < N; i++) {
        if(i == curr->first.x && j == curr->first.y) 
            putchar('1');
        else if(i == curr->second.x && j == curr->second.y)
            putchar('2');
        else
            putchar(table[i*N + j]);
    }
}
        

int admissible(point from, point to) {
    return from.dist(to);
}
class setcmp{
	bool operator() (const &interest  lhs, const &interest rhs)
	{
		return lhs->estimated < rhs->estimated;
	}
};	

interest A_star(point from, point end) {
	set<interest, a func > ClosedSet; // TODO: DIATAKSI fuction gia fast searching visited
	set<*interest,setcmp> StartSet;
	interest *start_point= new interest(0,admissiblssible(from,end),nullptr,from);
	StartSet.insert(start_point);
	while(!StartSet.empty())
	{
		interest *curr = *StartSet.begin(); //get curr always the first is the min
		StartSet.erate(StartSet.begin(),StartSet.begin()+1); //del min
		if(curr->current == end) //need overload 
			{
				return curr;
			}
		vecor<interest> next=curr->next(); // mpla mpla ,insert father mpla mpla
		for(auto i : next)
		{
			if(ClosedSet.count(*i)>0)
				continue;
			StartSet.insert(i);
		}
	}
	perror("no path exist");
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
    scanf("%d", meet_points);
    point meet[meet_point];
    for(i = 0; i < meet_points; i++) {
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
    vector<point> fir_route = A_star(first, meet_point[0]);
    for(i = 1; i < meet_points; i++) {
        vector<point> temp = A_star(meet_point[i - 1], meet_points[i]);
        fir_route.insert(fir_route.back(), temp.begin(), temp.end());
    }


