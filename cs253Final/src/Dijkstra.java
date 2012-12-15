import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra
{
    public static void computePaths(City source, int[][] adj, ArrayList<City> cities)
    {
    	ArrayList<City> workingCities = (ArrayList<City>)cities.clone();
        source.setMinDistance(0);
        PriorityQueue<City> vertexQueue = new PriorityQueue<City>();
      	vertexQueue.add(source);

      	while (!vertexQueue.isEmpty()) {
      		City u = vertexQueue.poll();
      		City v = u;
            // Visit each edge exiting u
            for (int i = 0; i < adj[u.getID()].length; i++)
            {
            	while(i<30 && adj[u.getID()][i]==0) i++;
            	if(i==30) break;
                
                for(int j = 0; j < workingCities.size(); j++) if(workingCities.get(j).getID()==i) v = workingCities.get(j);
                int weight = adj[u.getID()][v.getID()];
                int dist = u.getMinDistance() + weight;
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