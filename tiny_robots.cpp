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
        int cost;
        int estimated;
	int *estimated_function(point,point);
        interest *parent;
        interest() {
            cost = estimated = 0;
            parent = NULL;
	    // add estimated_fuction pointer
        }
        interest(int c, int e, interest *p, point curr) {
            cost = c;
            estimated = e;
	            parent = p;
            current = curr;
	    estimated_function = &(parent->estimated_function);
        }
	bool operator< (const interest &c) const {
		return this->estimated <  c.estimated;
	}
	bool operator== (const interest &c) const {
		return this->current == c.current;
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
	set<interest> ClosedSet;
	set<*interest,setcmp> StartSet;
	interest *start_point= new interest(0,admissiblssible(from,end),nullptr,from);
	StartSet.insert(start_point);
	while(!StartSet.empty())
	{
		interest *curr = *StartSet.begin(); //get curr
		StartSet.erate(StartSet.begin(),StartSet.begin()+1); //del min
		if(curr->current == end) //need overload 
			{
				return curr;
			}
		next=getNextMoves // mpla mpla ,insert father mpla mpla
		for(auto &i : next)
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
    
    vector<point> fir_route = A_star(first, meet_point[0]);
    for(i = 1; i < meet_points; i++) {
        vector<point> temp = A_star(meet_point[i - 1], meet_points[i]);
        fir_route.insert(fir_route.back(), temp.begin(), temp.end());
    }


