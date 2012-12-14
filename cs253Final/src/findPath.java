import java.util.ArrayList;


public class findPath {

	public static int[] findPath(ArrayList<City> cities, int [][] adj, int startCity, int endCity)
	{
		int [] distance = new int [cities.size()];
		for (int i=0; i<30; i++){distance[i] = Integer.MAX_VALUE;}
		distance[startCity]=0;
		int [] previous = new int [cities.size()];
		boolean [] visited = new boolean [cities.size()];
		int size = 30;
		for(int i = 0; i < visited.length; i++) visited[i] = false;
		ArrayList<City> workingCities = (ArrayList<City>) cities.clone();
		while (size>0)
		{
			int tempIndex = 0;
			for(int i=0; i<distance.length; i++)
			{
				while(visited[i] && i <distance.length-1) i++;
				if (distance[i] < distance[tempIndex]) tempIndex=i;
									
			}
			//workingCities.remove(tempIndex);
			visited[tempIndex]=true; size--;
			for (int i=0; i<30; i++)
			{
				while(visited[i]) i++;
				int tempDis;
				if(adj[tempIndex][i]!=0) tempDis = distance[tempIndex] + adj[tempIndex][i]; else tempDis = Integer.MAX_VALUE;
				if (tempDis< distance[i])
				{
					distance[i] = tempDis;
					previous[i]= tempIndex;
					
				}
				
			}
		}
		return previous;
		
	}
}
