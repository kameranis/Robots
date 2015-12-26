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
class Point {
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
    public Interest father

    @Override
    public int compareTo(Interest a)
    { 
        double i;
        if((i=this.heur-a.heur)!=0) return (int) i;
        else if((i=this.dist-a.dist)!=0) return (int) i;
        else if((i=this.pos.x-a.pos.x)!=0) return (int) i;
        else return this.pos.y - a.pos.y;
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
}

/* Main class */
public class Robots {

    static int min(int a, int b) { return a < b ? a : b; }
    static int max(int a, int b) { return a > b ? a : b; }
    public static interest Astar(Point start,Point fin, boolean player){
            PriorityQueue<Interest> queue = new PriorityQueue<Interest>();
	    TreeSet<Point> closed= new TreeSet<Point>();
	    Interest start_i= new Interest(start,0,start.dist(fin),null);
	    queue.add(start_i);
	    while(!queue.isEmpty())
	    {
		interest curr = queue.peek();
		queue.remove(curr);
		if(curr==fin)
			return fin;
		if(closed.contains(curr))
			continue;
		ArrayList<Interest> next_moves= curr.next(board,N,M,fin);
		for(Interest next : next_moves)
		{
			if(closed.contains(next.pos))
				continue;
			queue.add(next);

		}
	    }
    }
    public static void main(String[] args) {

        /* Input */
        try {
            Scanner in = new Scanner(System.in);
            // Read dimensions
            int N = in.nextInt();
            int M = in.nextInt();

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
            char[][] board new char[N][M];
            for(i = 0; i < N; i++) {
                for(j = 0; j < M; j++)
                    board[i][j] = in.next().trim().charAt(0);
            }

	}
        // If file is not valid
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}       
