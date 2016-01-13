
package graphapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author Anty
 */

public class GraphApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int opt=4;
        Scanner scan = new Scanner(System.in);
        int n;
        System.out.println("Enter nodes");
        n = scan.nextInt();
        
        graph G = new graph(n);
        do
        {
            System.out.println(" ");
            System.out.println("1.Insert Edge");
            System.out.println("2.Display Graph");
            System.out.println("3.DFS");
            System.out.println("4.BFS");
            System.out.println("5.Bellman Ford");
            System.out.println("10.exit");
            
            opt = scan.nextInt();
            switch(opt)
            {
                case 1: int n1,n2,w;
                        System.out.println("Enter node 1 ");
                        n1 = scan.nextInt();
                        System.out.println("Enter node 2 ");
                        n2 = scan.nextInt();
                        System.out.println("Enter weight : ");
                        w = scan.nextInt();
                        G.addEdge(n1,n2,w);
                        break;
                case 2: for(int i=0; i<n;i++)
                        {
                            Iterator iter = G.adjList.get(i).edgeList.iterator();
                            System.out.print("\n" + (i+1) +".");
                            while(iter.hasNext())
                            {
                                edge loopEdge = (edge)iter.next();
                                System.out.print(loopEdge.destinationNode+1 + "\t");
                            }
                        }
                        break;
                case 3: G.dfs();
                        break;
                case 4: G.bfs();
                        break;
                case 5: G.bellmanFord(0);
                        G.bFDisplay();
                        break;
            }
        }while(opt!=10);
    }
    
}
class graph
{
    
    ArrayList<vertex> adjList ;
    graph(int n)
    {
         adjList = new ArrayList<vertex>(n);
         for(int i=0;i<n;i++)
             adjList.add(new vertex(i));
    }
    public void addEdge(int n1, int n2, int w)
    {
        adjList.get(n1-1).edgeList.add(new edge(n2-1,w));
    }
    public void dfs()
    {
        //Initializashun   
        Iterator iter = adjList.iterator();
        while(iter.hasNext())
        {
            vertex currVertex = (vertex) iter.next();
            currVertex.prev = -1;
            currVertex.visited = false;
        }
        
        //Start
        iter = adjList.iterator();
        while(iter.hasNext())
        {
            vertex currVertex = (vertex)iter.next();
            if(!(currVertex).visited)
            {  
                System.out.print("\nForest = ");
                dfs_visit((currVertex).id);
                
           
            }
        }
    }
    public void dfs_visit(int nodeID)
    {
        adjList.get(nodeID).visited = true;
        Iterator iter = adjList.get(nodeID).edgeList.iterator();
        System.out.print("\t" + (nodeID+1));
        while(iter.hasNext())
        {
            edge currEdge = (edge) iter.next();
            vertex edgePointsTo = adjList.get(currEdge.destinationNode);
            if(!edgePointsTo.visited)
            {
                edgePointsTo.prev = nodeID;
                dfs_visit(edgePointsTo.id);
            }
        }
    }
    
    public void bfs()
    {
        //Initializashun
        Queue Q = new LinkedList();
        Iterator iter = adjList.iterator();
        while(iter.hasNext())
        {
            vertex currVertex = (vertex) iter.next();
            currVertex.prev = -1;
            currVertex.visited = false;
        }
        Q.add(0);
        System.out.println("\n");
        while(!Q.isEmpty())
        {
            int currNodeID = (Integer)Q.remove();
            iter = adjList.get(currNodeID).edgeList.iterator();
            System.out.print("\t"+(currNodeID+1));
            while(iter.hasNext())
            {
                vertex currVertex = (vertex)adjList.get(((edge)iter.next()).destinationNode);
                if(!currVertex.visited){
                    Q.add(currVertex.id);
                    currVertex.visited = true;
                }  
            }
        }
        
    }
    public void bellmanFord(int source)
    {
        for(vertex v:adjList)
        {
            v.distance = util.INT_MAX;
            v.prev = -1;
        }
        adjList.get(source).distance = 0;
        
        for(int i=0; i<adjList.size();i++)
        {
            vertex u = adjList.get(i);
            for(edge E:u.edgeList)
            {
                vertex v = adjList.get(E.destinationNode);
                if(u.distance + E.weight < v.distance)
                {
                    v.distance = u.distance + E.weight;
                    v.prev = u.id;
                }
            }
        }
        
        
    }
    public void bFDisplay()
    {
        System.out.println("Vertex\tDistance"); 
        for(int i=0;i<adjList.size();i++)
        {
            System.out.println(adjList.get(i).id+"\t"+adjList.get(i).distance );
        }
    }
}
class vertex
{
    int id;
    int distance;
    boolean visited;
    int prev;
    ArrayList<edge> edgeList;
    vertex(int id)
    {
        edgeList = new ArrayList<>();
        this.id = id;
        visited = false;
        prev = -1;
    }
}
class edge
{
    int weight;
    int destinationNode;
    edge(){
        weight = 0;
        destinationNode = 0;
    }
    edge(int destination, int weight)
    {
        destinationNode = destination;
        this.weight = weight;
    }
}
class util
{
    public static final int  INT_MAX = 300;
}