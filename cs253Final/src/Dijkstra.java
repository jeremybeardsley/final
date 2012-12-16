import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra
{
    @SuppressWarnings("unchecked")
	public static void computePaths(City source, int[][] adj, ArrayList<City> cities)
    {
    	//make copy of arraylist to work with
    	ArrayList<City> workingCities = (ArrayList<City>)cities.clone();
        //sets min distance to 0 because from source to source is 
    	//distance of 0
    	source.setMinDistance(0);
        //creates priority queue to hold the path taken to end result 
    	PriorityQueue<City> vertexQueue = new PriorityQueue<City>();
      	//adds starting city to queue as first item
    	vertexQueue.add(source);

      	while (!vertexQueue.isEmpty()) {
      		City u = vertexQueue.poll();
      		City v = u;
            // Visit each edge exiting u
            for (int i = 0; i < adj[u.getID()].length; i++)
            {
            	//looks for all adjacent nodes to chosen city
            	while(i<30 && adj[u.getID()][i]==0) i++;
            	if(i==30) break;
                //iterates through array of cities, and if ID equals current index
            	//jumps to next city in list
                for(int j = 0; j < workingCities.size(); j++) if(workingCities.get(j).getID()==i) v = workingCities.get(j);
                //gets weight from adj matrix
                int weight = adj[u.getID()][v.getID()];
                //calculates distance
                int distanceThroughU = u.getMinDistance() + weight;
                if (distanceThroughU < v.getMinDistance()) {
                	vertexQueue.remove(v);
                	v.setMinDistance(distanceThroughU);
                	v.previous = u;
                	vertexQueue.add(v);
                }
            }
        }
    }

    public static List<City> getShortestPathTo(City target)
    {
        List<City> path = new ArrayList<City>();
        for (City vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }
}