/* NTUA ECE AI robots
 *
 * Given two robots and a plane with destinations and obstacles
 * implement A* to get the two robots to pass through the destinations
 * and meet at the last one
 */

import java.util.*;
import java.io.*;
import java.io.FileNotFoundException;

class Point  implements Comparable<Point> {
    public int c, r;

    int min(int a, int b) { return a < b ? a : b; }
    int max(int a, int b) { return a > b ? a : b; }

    // Constructors
    Point(int a, int b) { r = a; c = b; }
    Point() { c = r = 0; }

    // Returns the distance between this and p
    public int dist(Point p) {
        return (Math.abs(this.r-p.r) + Math.abs(this.c-p.c) + 5)*(Math.abs(this.r-p.r) + Math.abs(this.c-p.c) + 5); 
    }

    @Override
    public int compareTo(Point a)
    { 
	    if(this.r == a.r) return this.c -a.c;
	    return this.r - a.r;
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


class Interest implements Comparable<Interest> {
    public Point pos;           // Where it is
    public int dist;            // Distance so far
    public int heur;            // Heuristic distance to be travelled
    public Interest father;     // Previous step

    @Override
    public int compareTo(Interest a)
    { 
        int i;
	    if((i = this.heur - a.heur) != 0) return i;
        if((i = this.dist - a.dist) != 0) return i;
        else return this.pos.compareTo(a.pos);
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

    // Returns a list of all the possible points we can go
    public ArrayList<Interest> next(char[][] board, int N, int M, Point finish) {
        ArrayList<Interest> ret = new ArrayList<Interest>();
        
        // Down
        if(pos.r+1 < N && board[this.pos.r+1][this.pos.c] != 'X') {
            Point n = new Point(pos.r+1, pos.c);
            ret.add(new Interest(n, dist+1, dist+1+n.dist(finish), this));
        }

        // Right
        if(pos.c+1 < M && board[this.pos.r][this.pos.c+1] != 'X') {
            Point n = new Point(pos.r, pos.c+1);
            ret.add(new Interest(n, dist+1, dist+1+n.dist(finish), this));
        }

        // Up
        if(pos.r > 0 && board[this.pos.r-1][this.pos.c] != 'X') {
            Point n = new Point(pos.r-1, pos.c);
            ret.add(new Interest(n, dist+1, dist+1+n.dist(finish), this));
        }

        // Left
        if(pos.c > 0 && board[this.pos.r][this.pos.c-1] != 'X') {
            Point n = new Point(pos.r, pos.c-1);
            ret.add(new Interest(n, dist+1, dist+1+n.dist(finish), this));
        }
        return ret;
    }
}

/* Main class */
public class Robots_non_ad {

    static int min(int a, int b) { return a < b ? a : b; }
    static int max(int a, int b) { return a > b ? a : b; }

    static int M, N;
    static char[][] board;
    static int count;

    // Given a starting and ending point, returns the last Interest of the path
    // Usually it would be used in conjuction to backtrace
    public static Interest Astar(Point start, Point fin, Point[] player, int size, int prev) throws Exception{
        PriorityQueue<Interest> queue = new PriorityQueue<Interest>();
        TreeSet<Point> closed = new TreeSet<Point>();
        Interest start_i = new Interest(start, 0, start.dist(fin), null);
        queue.add(start_i);
        String Name;
        if(player == null)
            Name = new String("Player 1 ");
        else
            Name = new String("Player 2 ");
        while(!queue.isEmpty())
        {
            Interest curr = queue.poll();       // Get next element
            count++;
            // System.out.println(Name + "considering new posisition <" + curr.pos.r +"," + curr.pos.c +"> at step " + (curr.dist+prev));
            if(curr.pos.equals(fin))            // Base case
                return curr;
            if(closed.contains(curr.pos))       // No rechecks
                continue;
            closed.add(curr.pos);

            ArrayList<Interest> next_moves = curr.next(board,N,M,fin);
            for(Interest next : next_moves)     // For every move
            {
                int pos_print = next.dist + prev;
                if(closed.contains(next.pos))   // No rechecks
                    continue;
                if(player == null || next.dist >= size || !player[(next.dist)].equals(next.pos))
                    queue.add(next);
                else        // Collision
                {
                    // System.out.println("** Conflict ** , thinking about stall or another move");
                    queue.add(new Interest(next.pos, next.dist+1, next.heur+1, next.father));
                }
            }
        }
        // Should not get here
        // It means that we couldn't reach teh destination
        System.out.println(closed.size());
        System.out.println(fin.c + " " + fin.r);
        System.out.println("You guys really fucked it up");
        throw new Exception();
        //  return null;
    }

    // Takes the end point of a path and returns the whole path in an ArrayList
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

    // Prints the route of our two robots
    static void print_route(ArrayList<Point> player1, ArrayList<Point> player2)
    {
        try {
            int s = max(player1.size(), player2.size());
            int i;
            System.out.println("Printing final path");
            for(i=0; i<s; i++)
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
                System.out.println("Player 1 going at posisition <" + pl1.r + "," + pl1.c + "> at step " + i );
                System.out.println("Player 2 going at posisition <" + pl2.r + "," + pl2.c + "> at step " + i );
            }
    }
    catch(Exception e)
    {
        e.printStackTrace();
        System.out.println("Exception has been raised");
    }
}

public static void main(String[] args) {

    /* Input */
    try {
        Scanner in = new Scanner(System.in);
        // Read dimensions
        N = in.nextInt();
        M = in.nextInt();
        int r, c;
        // Get starting points
        r = in.nextInt();
        c = in.nextInt();
        Point first = new Point(r-1, c-1);
        r = in.nextInt();
        c = in.nextInt();
        Point second = new Point(r-1, c-1);

        // Get meeting point
        r = in.nextInt();
        c = in.nextInt();
        Point last = new Point(r-1, c-1);

        // Get intermediate points
        int meet_points = in.nextInt();
        Point[] meet = new Point[meet_points + 1];

        int i, j;
        for(i = 0; i < meet_points; i++) {
            r = in.nextInt();
            c = in.nextInt();
            meet[i] = new Point(r-1,c-1);
        }

        meet[meet_points] = last;

        // Get board
        board = new char[N][M];
        for(i = 0; i < N; i++) {
            board[i] = in.next().toCharArray();
        }

        // Check board and Meeting points are valid
        for(i = 0; i < meet_points + 1; i++)
        {
            if(board[meet[i].r][meet[i].c] == 'X') {
                System.out.println(meet[i].r + " " + meet[i].c);
                throw new IOException();
            }
        }
        
        count = 0;
        ArrayList<Point> player1 = backtrace(Astar(first, meet[0], null, 0, 0));

        for(i = 1; i < meet_points + 1; i++) {
            player1.addAll(backtrace(Astar(meet[i-1], meet[i], null, 0, player1.size())));
        }
        System.out.println(count);

        ArrayList<Point> player2 = backtrace(Astar(second, meet[0],player1.toArray( new Point[player1.size()] ),player1.size(),0));

        for(i = 1; i < meet_points + 1; i++) {
            int bound;
            if(player2.size() < player1.size())
                bound = player2.size();
            else
                bound = player1.size()-1;
            ArrayList<Point> temp =(new ArrayList<Point> (player1.subList(bound, player1.size()-1)));
            player2.addAll(backtrace(Astar(meet[i-1], meet[i], (temp.toArray(new Point[temp.size()])),temp.size(),player2.size())));
        }
        System.out.println(count);
        
        if(player1.size() > player2.size()) 
        {
            player1.remove(player1.size()-1);
        }
        else
        {
            player2.remove(player2.size()-1);
        }
        // print_route(player1, player2);
    }

    // If file is not valid
    catch(Exception e)
    {
        e.printStackTrace();
        System.out.println("Exception is raised");
    }
}
}       
