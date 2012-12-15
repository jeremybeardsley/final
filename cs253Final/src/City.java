
public class City implements Comparable{
private String Name;
private int x, y, id;
private int[] adjacents;
private int minDistance;
City previous;
	public City(String Name, int x, int y, int id)
{
	this.Name=Name;
	this.x=x;
	this.y=y;
	this.id=id;
	minDistance = Integer.MAX_VALUE - 1000;
}
	public String getName(){return Name;}
	public int getX(){return x;}
	public int getY(){return y;}
	public int getID(){return id;}
	public void setMinDistance(int m) { minDistance = m; }
	public int getMinDistance() { return minDistance; }
	public void setPrevious(City c) { previous = c; }
	public City getPrevious() { return previous; }
	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}