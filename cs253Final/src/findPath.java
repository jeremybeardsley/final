import java.util.ArrayList;


public class findPath {

	public static int[] findPath(ArrayList<City> cities, int [][] adj, int startCity, int endCity)
	{
		int [] distance = new int [cities.size()];
		int [] previous = new int [cities.size()];
		for (int i=0; i<cities.size(); i++){
			distance[i] = Integer.MAX_VALUE;
			previous[i] = -1;
		}
		distance[startCity]=0;
		boolean[] visited = new boolean[cities.size()];
		for(int i = 0; i < cities.size(); i++) visited[i] = false;
		ArrayList<City> workingCities = (ArrayList<City>) cities.clone();
		
		while (workingCities.size()>0)
		{
			int u = 0;
			for(int i = 0; i < workingCities.size(); i++) {
				if(distance[workingCities.get(i).getID()] < distance[u])
					u = workingCities.get(i).getID();
			}
			visited[workingCities.get(u).getID()] = true;
			workingCities.remove(u);
			if(distance[u] == Integer.MAX_VALUE)
				break;
			
			for (int i=0; i<30; i++)
			{
				while(i<29 && visited[i]) i++;
				int alt;
				if(adj[u][i] > 0) alt = distance[u] + adj[u][i]; else alt = Integer.MAX_VALUE;
				if(alt < distance[i]) {
					distance[i] = alt;
					previous[i] = u;
					for(int j = 0;j < workingCities.size(); j++)
						if(workingCities.get(j).getID()==i)
							workingCities.add(workingCities.remove(j));
				}
			}
		}
		return previous;	
	}
}
