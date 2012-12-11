import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class nflFlightsApplet extends JApplet implements ActionListener {

	Image img;
	MediaTracker tr;
	String mString = "";
	String[] cityLabels = new String[30];
	ArrayList<City> cities = new ArrayList<City>();
	public int[][] buildMatrix() throws FileNotFoundException {
		int Matrix[][] = new int[30][30];
		Scanner filescan = new Scanner(new File("matrix.txt"));

		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {

				Matrix[i][j] = filescan.nextInt();
			}
		}

		return Matrix;
	}

	// Distance formula is

	// public String MatrixToString(int [][]matrix)
	// {
	// String MatrixString = "";
	// for (int row = 0; row < matrix.length; row++) {
	// for (int col = 0; col < matrix[row].length; col++) {
	// MatrixString += " " + matrix[row][col];
	// }
	// MatrixString += "\n";
	// }
	// return MatrixString;
	// }

	public ArrayList<City> buildArray() {
		ArrayList<City> cities = new ArrayList<City>();
		cities.add(new City("SEA", 56, 42, 0));
		cities.add(new City("SF", 17, 167, 1));
		cities.add(new City("SD", 60, 247, 2));
		cities.add(new City("ARI", 131, 244, 3));
		cities.add(new City("BUF", 518, 80, 4));
		cities.add(new City("NE", 571, 99, 5));
		cities.add(new City("NY", 547, 129, 6));
		cities.add(new City("BALT", 524, 157, 7));
		cities.add(new City("PHIL", 550, 145, 8));
		cities.add(new City("WASH", 511, 164, 9));
		cities.add(new City("CAR", 498, 220, 10));
		cities.add(new City("JACK", 493, 293, 11));
		cities.add(new City("MIA", 522, 352, 12));
		cities.add(new City("TB", 494, 333, 13));
		cities.add(new City("ATL", 461, 256, 14));
		cities.add(new City("PITT", 485, 140, 15));
		cities.add(new City("DET", 434, 105, 16));
		cities.add(new City("CLE", 458, 139, 17));
		cities.add(new City("CIN", 442, 168, 18));
		cities.add(new City("TENN", 424, 216, 19));
		cities.add(new City("NO", 391, 312, 20));
		cities.add(new City("IND", 418, 164, 21));
		cities.add(new City("CHI", 396, 134, 22));
		cities.add(new City("GB", 394, 99, 23));
		cities.add(new City("MINN", 338, 96, 24));
		cities.add(new City("KC", 330, 183, 25));
		cities.add(new City("STL", 373, 186, 26));
		cities.add(new City("DAL", 301, 272, 27));
		cities.add(new City("HOU", 320, 326, 28));
		cities.add(new City("DEN", 215, 170, 29));
		return cities;

	}

	public void paint(Graphics g) {

		
		int[][] adj = new int[30][30];
		
		
		try {
			adj = buildMatrix();
		} catch (Exception e) {

		}
		// mString = MatrixToString(adj);

		tr = new MediaTracker(this);
		img = getImage(getCodeBase(), "map.gif");
		tr.addImage(img, 0);

		g.drawImage(img, 0, 0, this);

		// Draws Flight Paths
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				int x1, x2, y1, y2, distance;
				double xd, yd;
				if (adj[i][j] != 0) {
					x1 = cities.get(i).getX();
					y1 = cities.get(i).getY();
					x2 = cities.get(j).getX();
					y2 = cities.get(j).getY();
					g.setColor(Color.RED);
					g.drawLine(x1, y1, x2, y2);
					xd = Math.pow(x2 - x1, 2);
					yd = Math.pow(y2 - y1, 2);
					distance = (int) Math.sqrt(xd + yd);
					adj[i][j] = distance;

				}
			}
		}
		g.setColor(Color.MAGENTA);
		for (int i = 0; i < cities.size(); i++) {
			int x = cities.get(i).getX() - 5;
			int y = cities.get(i).getY() - 5;
			g.fillOval(x, y, 10, 10);
		}
		JLabel comboBoxLabel1 = new JLabel("Starting City: ");
		JComboBox cityList = new JComboBox(cityLabels);
		 cityList.setSelectedIndex(0);
		 cityList.addActionListener(this);
		
		cityList.setAlignmentX(500);
		cityList.setAlignmentY(200);
		 getContentPane().add(comboBoxLabel1);
		 getContentPane().add(cityList);
		 g.setColor(Color.BLACK);
		String temp = " " + adj[5][6];
		g.drawString(temp, 500, 500);
		
		
	}

	public void init()

	{
		
		cities = buildArray();
		for (int index = 0; index < 30; index++) {

			cityLabels[index] = cities.get(index).getName();

		}
		setSize(800, 800);
//		Choice StartingCity = new Choice();
//		for (int i=0;i<30;i++)
//		{
//			StartingCity.add(cityLabels[i]);
//		}
//		add(StartingCity);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}