
public class City {
private String Name;
private int x, y, id;
private int[] adjacents;
	public City(String Name, int x, int y, int id)
{
	this.Name=Name;
	this.x=x;
	this.y=y;
	this.id=id;
}
	public String getName(){return Name;}
	public int getX(){return x;}
	public int getY(){return y;}
	public int getID(){return id;}
}