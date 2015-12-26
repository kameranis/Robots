/* Bats NTUA ECE PL1 spring semester 2014
 * In a rectangle there are bats (B), walls (-) and a spider (A)
 * Using only straight lines from one bat to another, without going through a wall
 * how far is the spider from (0,0)
 *
 * 2 decimals required
 */

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

// Used to save bats, walls and spider
class Point  implements Comparable<Points> {
    public int c, r;

    int min(int a, int b) { return a < b ? a : b; }
    int max(int a, int b) { return a > b ? a : b; }

    // Constructors
    Point(int a, int b) { r = a; c = b; }
    Point() { c = r = 0; desc = ' '; }

    // Returns the distance between this and p
    public double dist(Point p) {
        return Math.abs(this.r-p.r) + Math.abs(this.c-p.c); 
    }
    
    @Override
    public int compareTo(Point a)
    { 
        int i;
        if((i=this.x-a.x)!=0) return i;
        else return this.y - a.y;
    }

    @Override
    public boolean equals(Object other)
    {
        if(this == other) return true;
        if(other == null || (this.getClass() != other.getClass()))
        {
            return false;
        }
        Point guest = (Point) other;
        return (this.x == other.x) && (this.y == other.y);
    }
}

/* This class is used by the priority queue to determine the next one
 * in Djikstra.
 * Holds the id number of the creature and its current tentative distance
 * in respect to (0,0) */

class Interest implements Comparable<Interest> {
    public Point pos;
    public double dist;
    public double heur;
    public Interest father;

    @Override
    public int compareTo(Interest a)
    { 
        double i;
        if((i=this.heur-a.heur)!=0) return (int) i;
        else if((i=this.dist-a.dist)!=0) return (int) i;
        else if((i=this.pos.x-a.pos.x)!=0) return (int) i;
        else return this.pos.y-a.pos.y;
    }

    @Override
    public boolean equals(Object other)
    {
        if(this == other) return true;
        if(other == null || (this.getClass() != other.getClass()))
        {
            return false;
        }
        Interest guest = (Interest) other;
        return (this.pos.equals(guest.pos)) && (this.dist == guest.dist) && (this.heur == guest.heur);
    }

    /* Constructors */
    Interest(Point p, double d, double h, Interest f) { pos = p; dist = d; heur = h; father = f; }
    Interest() { pos = Point(); dist = 0; heur = 0; father = null; }

    public ArrayList<Interest> next(char[][] board, int N, int M, Point finish) {
        ArrayList<Interest> ret = new ArrayList<Interest>();
        if(pos.x+1 < N && board[this.pos.x+1][this.pos.y] != 'x') {
            Point n = Point(pos.x+1, pos.y);
            ret.push(Interest(n, d+1, d+1+n.dist(finish), this));
        }
        if(pos.y+1 < M && board[this.pos.x][this.pos.y+1] != 'x') {
            Point n = Point(pos.x, pos.y+1);
            ret.push(Interest(n, d+1, d+1+n.dist(finish), this));
        }
        if(pos.x > 0 && board[this.pos.x-1][this.pos.y] != 'x') {
            Point n = Point(pos.x-1, pos.y);
            ret.push(Interest(n, d+1, d+1+n.dist(finish), this));
        }
        if(pos.y > 0 && board[this.pos.x][this.pos.y-1] != 'x') {
            Point n = Point(pos.x, pos.y-1);
            ret.push(Interest(n, d+1, d+1+n.dist(finish), this));
        }
        return ret;
    }
}


public class d




/* Main class */
public class Robots {

    static int min(int a, int b) { return a < b ? a : b; }
    static int max(int a, int b) { return a > b ? a : b; }

    static int M, N;
    static char[][] board;

    public static interest Astar(Point start, Point fin, ArrayList<Point> player){
        PriorityQueue<Interest> queue = new PriorityQueue<Interest>();
	    TreeSet<Point> closed= new TreeSet<Point>();
	    Interest start_i= new Interest(start,0,start.dist(fin),null);
	    queue.add(start_i);
	    while(!queue.isEmpty())
	    {
		    Interest curr = queue.poll();
		    if(curr.pos.equals(fin))
			    return curr;
    		if(closed.contains(curr))
    			continue;
	    	ArrayList<Interest> next_moves= curr.next(board,N,M,fin);
		    for(Interest next : next_moves)
		    {
    			if(closed.contains(next.pos))
    				continue;
                if(player != null)
                {
                    if(next.dist > player.size() || !player[next.dist].equals(next.pos))
                        queue.add(next)
                    else
                    {
                       queue.add(Interest(next.pos, next.dist+1, next.heur+1, next.father);
                    }
                }
                else
        			queue.add(next);
    		}
	    }
    }
    public static ArrayList<Point> backtrace(Interest a)
    {
	    ArrayList<Point> val =new ArrayList<Point>();
	    Interest temp=a;
	    while(temp!=null)
	    {
		val.add(temp.pos);
		temp=temp.father;
	    }
	    Collections.reverse(val);
	    return val;
    }
    public static void main(String[] args) {

        /* Input */
        try {
            Scanner in = new Scanner(System.in);
            // Read dimensions
             N = in.nextInt();
             M = in.nextInt();

            // Get starting points
            Point first = Point(in.nextInt(), in.nextInt());
            Point second = Point(in.nextInt(), in.nextInt());

            // Get meeting point
            Point last = Point(in.nextInt(), in.nextInt());

            // Get intermediate points
            int meet_points = in.nextInt();
            Point[] meet = new Point[meet_points + 1];

            int i, j;
            for(i = 0;, i < meet_points; i++) {
                meet[i].x = in.nextInt();
                meet[i].y = in.nextInt();
            }

            meet[meet_points] = last;

            // Get board
            board new char[N][M];
            for(i = 0; i < N; i++) {
                for(j = 0; j < M; j++)
                    board[i][j] = in.next().trim().charAt(0);
            }

            ArrayList<Point> player1 = backtrace(A_star(first, meet[0], null));

            for(i = 1; i < meet_points + 1; i++) {
                player1.addall(backtrace(A_star(meet[i-1], meet[i], null)));
            }

            ArrayList<Point> player2 = backtrace(A_star(second, meet[0], player1));
            
            for(i = 1; i < meet_points + 1; i++) {
                player2.addall(backtrace(A_star(meet[i-1], meet[i], player1.subList(player2.size(), player1.size()-1))));
            }
            if(player1.size() > player2.size()) 
            {
                player1.remove(player1.size()-1);
            }
            else
            {
                player2.remove(player2.size()-1);
            }
            print_route(player1, player2);
        }

	}

        // If file is not valid
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}       
