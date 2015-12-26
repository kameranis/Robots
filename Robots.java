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
class Point  implements Comparable<Point> {
    public int c, r;

    int min(int a, int b) { return a < b ? a : b; }
    int max(int a, int b) { return a > b ? a : b; }

    // Constructors
    Point(int a, int b) { r = a; c = b; }
    Point() { c = r = 0; }

    // Returns the distance between this and p
    public int dist(Point p) {
        return Math.abs(this.r-p.r) + Math.abs(this.c-p.c); 
    }

    @Override
    public int compareTo(Point a)
    { 
        int i;
        if((i=this.r-a.r)!=0) return i;
        else return this.c - a.c;
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
        return (this.r == guest.r) && (this.c == guest.c);
    }
}

/* This class is used by the priority queue to determine the next one
 * in Djikstra.
 * Holds the id number of the creature and its current tentative distance
 * in respect to (0,0) */

class Interest implements Comparable<Interest> {
    public Point pos;
    public int dist;
    public int heur;
    public Interest father;

    @Override
    public int compareTo(Interest a)
    { 
        double i;
        if((i=this.heur-a.heur)!=0) return (int) i;
        else if((i=this.dist-a.dist)!=0) return (int) i;
        else if((i=this.pos.r-a.pos.r)!=0) return (int) i;
        else return this.pos.c-a.pos.c;
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
    Interest(Point p, int d, int h, Interest f) { pos = p; dist = d; heur = h; father = f; }
    Interest() { pos = new Point(); dist = 0; heur = 0; father = null; }

    public ArrayList<Interest> next(char[][] board, int N, int M, Point finish) {
        ArrayList<Interest> ret = new ArrayList<Interest>();
        if(pos.r+1 < N && board[this.pos.r+1][this.pos.c] != 'X') {
            Point n = new Point(pos.r+1, pos.c);
            ret.add(new Interest(n, dist+1, dist+1+n.dist(finish), this));
        }
        if(pos.c+1 < M && board[this.pos.r][this.pos.c+1] != 'X') {
            Point n = new Point(pos.r, pos.c+1);
            ret.add(new Interest(n, dist+1, dist+1+n.dist(finish), this));
        }
        if(pos.r > 0 && board[this.pos.r-1][this.pos.c] != 'X') {
            Point n = new Point(pos.r-1, pos.c);
            ret.add(new Interest(n, dist+1, dist+1+n.dist(finish), this));
        }
        if(pos.c > 0 && board[this.pos.r][this.pos.c-1] != 'X') {
            Point n = new Point(pos.r, pos.c-1);
            ret.add(new Interest(n, dist+1, dist+1+n.dist(finish), this));
        }
        return ret;
    }
}

/* Main class */
public class Robots {

    static int min(int a, int b) { return a < b ? a : b; }
    static int max(int a, int b) { return a > b ? a : b; }

    static int M, N;
    static char[][] board;

    public static Interest Astar(Point start, Point fin, Point[] player ,int size,int prev){
        PriorityQueue<Interest> queue = new PriorityQueue<Interest>();
        TreeSet<Point> closed= new TreeSet<Point>();
        Interest start_i= new Interest(start,0,start.dist(fin),null);
        queue.add(start_i);
	int size_t =size;
	String Name;
	if(player==null)
		Name=new String("Player 1 ");
	else{
		Name=new String("Player 2 ");}
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
		int pos_print = next.dist + prev;
		System.out.println(Name + "considering new posisition at<" + next.pos.r +"," + next.pos.c +"> at step " + pos_print );
                if(closed.contains(next.pos))
                    continue;
                if(player != null)
                {
                    if(next.dist > size_t || !player[(next.dist)].equals(next.pos))
                        queue.add(next);
                    else
                    {
			System.out.println("** Conflict ** , thinking about stall or another move");
                        queue.add(new Interest(next.pos, next.dist+1, next.heur+1, next.father));
                    }
                }
                else
                    queue.add(next);
            }
        }
        System.out.println("You guys really fucked it up");
        return null;
    }

    public static ArrayList<Point> backtrace(Interest a)
    {
        ArrayList<Point> val = new ArrayList<Point>();
        Interest temp=a;
        while(temp!=null)
        {
            if(temp.father != null && temp.dist - temp.father.dist > 1)
                val.add(temp.father.pos);
            val.add(temp.pos);
            temp=temp.father;
        }
        Collections.reverse(val);
        return val;
    }

    static void print_route(ArrayList<Point> player1, ArrayList<Point> player2)
    {
        try {
            int s = max(player1.size(), player2.size());
            int i;
            for(i = 0; i < s; i++)
            {
                Point pl1, pl2;
                if(i < player1.size())
                    pl1 = player1.get(i);
                else
                    pl1 = player1.get(player1.size() - 1);
                if(i < player2.size())
                    pl2 = player2.get(i);
                else
                    pl2 = player2.get(player2.size() - 1);
                int n, m;
                for(n = 0; n < N; n++) {
                    for(m = 0; m < M; m++) {
                        Point c = new Point(n, m);
                        if(pl1.equals(c))
                            System.out.print(1);
                        else if(pl2.equals(c))
                            System.out.print(2);
                        else
                            System.out.print(board[n][m]);
                    }
                    System.out.println();
                }
                Thread.sleep(100);
                System.out.println();
                //Runtime.getRuntime().exec("cls");
            }
	    System.out.println("Printing final path");
	    for(i=0;i<s;i++)
	    {
		    
                Point pl1, pl2;
                if(i < player1.size())
                    pl1 = player1.get(i);
                else
                    pl1 = player1.get(player1.size() - 1);
                if(i < player2.size())
                    pl2 = player2.get(i);
                else
                    pl2 = player2.get(player2.size() - 1);
		System.out.println("Player 1 going at posisition at<" + pl1.r +"," + pl2.c +"> at step " + i );
		System.out.println("Player 2 going at posisition at<" + pl2.r +"," + pl2.c +"> at step " + i );
	    }
        }
        catch(Exception e) {}
    }

    public static void main(String[] args) {

        /* Input */
        try {
            Scanner in = new Scanner(System.in);
            // Read dimensions
            M = in.nextInt();
            N = in.nextInt();
            int r, c;
            // Get starting points
            c = in.nextInt();
            r = in.nextInt();
            Point first = new Point(r, c);
            c = in.nextInt();
            r = in.nextInt();
            Point second = new Point(r, c);

            // Get meeting point
            c = in.nextInt();
            r = in.nextInt();
            Point last = new Point(r, c);

            // Get intermediate points
            int meet_points = in.nextInt();
            Point[] meet = new Point[meet_points + 1];

            int i, j;
            for(i = 0; i < meet_points; i++) {
                c = in.nextInt();
                r = in.nextInt();
                meet[i] = new Point(c,r);
            }

            meet[meet_points] = last;

            // Get board
            board = new char[N][M];
            for(i = 0; i < N; i++) {
                board[i] = in.next().toCharArray();
            }

            ArrayList<Point> player1 = backtrace(Astar(first, meet[0], null,0,0));

            for(i = 1; i < meet_points + 1; i++) {
                player1.addAll(backtrace(Astar(meet[i-1], meet[i], null,0,player1.size())));
            }

            ArrayList<Point> player2 = backtrace(Astar(second, meet[0],player1.toArray( new Point[player1.size()] ),player1.size(),0));

            for(i = 1; i < meet_points + 1; i++) {
		ArrayList<Point> temp =(new ArrayList<Point> (player1.subList(player2.size(), player1.size()-1)));
                player2.addAll(backtrace(Astar(meet[i-1], meet[i], (temp.toArray(new Point[temp.size()])),temp.size(),player2.size())));
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

        // If file is not valid
        catch(Exception e)
        {
        }
    }
}       
