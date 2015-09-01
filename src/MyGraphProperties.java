/*************************************************************************
 *Jim Gurgone
 *Assignment 3
 *4.1.16
 *CSC 403-510
 *
 *************************************************************************/

public class MyGraphProperties
{
	
	private static final int MAX = Integer.MAX_VALUE;
	private boolean[] marked;  
    private int[] edgeTo;     
    private static int[] distTo;     
    private static int numV;
    
//    // single source
    public MyGraphProperties(Graph G, int s) 
    {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        bfs(G, s);
        numV = G.V();
    }

    // BFS from single soruce
    private void bfs(Graph G, int s) 
    {
        Queue<Integer> q = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++) 
        	distTo[v] = MAX;
        	distTo[s] = 0;
        	marked[s] = true;
        	q.enqueue(s);

        while (!q.isEmpty()) 
        {
            int v = q.dequeue();
            for (int w : G.adj(v)) 
            {
                if (!marked[w]) 
                {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    // is there a path between s (or sources) and v?
    public boolean hasPathTo(int v) 
    {
        return marked[v];
    }

    // length of shortest path between s (or sources) and v
    public static int distTo(int v)
    {
        return distTo[v];
    }

    // shortest path bewteen s (or sources) and v; null if no such path
    public Iterable<Integer> pathTo(int v) 
    {
        if (!hasPathTo(v)) 
        	return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }

    
    public static int ecc(int v)
    {
    	
    	int dist = 0;
        for(int i = 0; i < numV; i++)
        {
        		if(distTo(i) > dist)
                	dist = distTo(i);
        }
        return dist;
}
    
    public static int diameter()
    {
    	int maxEcc = Integer.MIN_VALUE;
    	for (int i = 0;i<distTo.length;i ++)
    	{
    		if (distTo[i]>maxEcc)
    		{
    			maxEcc = distTo[i];
    		}
    	}
		return maxEcc;
    }
    
    
    public static int radius()
    {
    	int minEcc = Integer.MAX_VALUE;
    	for (int i = 0;i<distTo.length;i ++)
    	{
    		if (distTo[i]<minEcc)
    		{
    			minEcc = distTo[i];
    		}
    	}
    	return minEcc;
    }    
    
    public static int center()
    {
    	int cent = radius();
    	for (int i = 0;i<distTo.length;i ++)
    	{
    		if (distTo[i] == cent)
    		{	
    			cent =i;
    		}
    	}
    	return cent;
    }    

    // test client

	public static void main(String[] args) 
	{
        In in = new In("tinyCG");
        Graph G = new Graph(in);
        StdOut.println("Enter the source Vertex: ");
        int s = StdIn.readInt();
        MyGraphProperties mgp = new MyGraphProperties(G, s);
        int nrV1 = G.V();
        for (int v = 0; v < G.V(); v++) 
        {
            if (mgp.hasPathTo(v)) 
            {
                StdOut.printf("%d to %d (%d):  ", s, v, MyGraphProperties.distTo(v));
                for (int x : mgp.pathTo(v)) 
                {
                    if (x == s) 
                    	StdOut.print(x);
                    else        
                    	StdOut.print("-" + x);
                }
                
            }

            else 
            {
                StdOut.printf("%d to %d (-):  not connected\n", s, v);
            }
            System.out.println(MyGraphProperties.distTo(v));

        }
        int ecc = ecc(s);
        StdOut.println("The eccentricity of " + s + " is " + ecc);
        StdOut.println("The Diameter is " + diameter());
        StdOut.println("The Radius is " + radius());
        StdOut.println("The Center is " + center());

    }


}
