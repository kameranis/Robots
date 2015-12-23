/* NTUA ECE AI robots
 *
 * Given two robots and a plane with destinations and obstacles
 * implement A* to get the two robots to pass through the destinations
 * and meet at the last one
 */

#include <cstdio>
#include <cstdlib>
#include <set>

using namespace std;

class point {
    public:
        int x, y;

        point() {}

        point(int a, int b) {
            x = a;
            y = b;
        }

        dist(point const & other) {
            return math.abs(this->x - other.x) + math.abs(this->y - other.y);
        }
};

class state {
    public:
        point first;
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

class interest {
    public:
        int cost;
        int estimated;
        interest *parent;
        state current;

        interest() {
            cost = estimated = 0;
            parent = NULL;
        }

        interest(int c, int e, interest *p, state curr) {
            cost = c;
            estimated = e;
            parent = p;
            current = curr;
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
        

int admissible(point curr, point meet[], bool visited[], int N) {
    int i, d;
    int minimum = INT_MAX;
    for(i = 0; i < N; i++) {
        if(!visited[i] && (d = curr.dist(meet[i])) < min) {
            min = d;
        }
    }
    return min;
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
    set<point> meet;
    point temp;
    for(i = 0; i < meet_points; i++) {
        scanf("%d %d", &temp.x, &temp[i].y);
        meet.insert(temp);
    }

    char board[N][M];
    for(i = 0; i < N; i++) {
        for(j = 0; j < M; j++) {
            board[i][j] = getchar();
        }
        getchar();
    }


