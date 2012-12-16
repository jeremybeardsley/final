import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class nflFlightsApplet extends JApplet implements ActionListener {

	// 2d array to be used as adjacency matrix
	static int[][] adj = new int[30][30];
	// array list of cities, city objects will have the city name
	// id, and xy coords stored in them.
	static ArrayList<City> cities = new ArrayList<City>();
	List<City> path;
	private static JFrame frame;
	static MediaTracker tr;
	static Image img;
	static String tester = "";
	Graphics gr;

	@SuppressWarnings("rawtypes")
	JComboBox StartComboBox, comboBox_1;
	JLabel output;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public nflFlightsApplet() throws IOException {
		// Calls buildArray which populates the list of cities
		cities = buildArray();
		// iterates through array of cities, retrieves the
		// names as an array of strings, to create
		// labels for comboboxes
		String[] cityLabels = new String[30];
		for (int i = 0; i < 30; i++) {
			cityLabels[i] = cities.get(i).getName();
		}
		// try catch statement to attempt to build array
		// of adjacencies. Was needed when we were reading
		// from a file, but now that we hardcoded the matrix into
		// app, the try is no longer needed, but it is not hurting
		// so we left it in. If it wasn't for Linux Mint screwing
		// with permissions on windows text files, we would be
		// reading from a file. Oh well.
		try {
			adj = buildMatrix();
		} catch (Exception e) {
		}
		// Some gui stuff. For future reference, Swing is temperamental.
		// in the future, dont use swing. Code with android or something
		// that doesnt suck for gui.
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 661);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new FlowLayout(FlowLayout.CENTER, 5, 5));
		frame.setResizable(false);
		// Label and combobox for starting city.
		JLabel lblStartingCity = new JLabel("Starting City");
		frame.getContentPane().add(lblStartingCity);
		StartComboBox = new JComboBox(cityLabels);
		frame.getContentPane().add(StartComboBox);
		// ending city label and combobox
		JLabel lblEndingCity = new JLabel("Ending City");
		frame.getContentPane().add(lblEndingCity);
		comboBox_1 = new JComboBox(cityLabels);
		frame.getContentPane().add(comboBox_1);
		// Button find route, listener for this does a bunch of cool stuff
		JButton btnFindRoute = new JButton("Find Route");
		btnFindRoute.addActionListener(this);
		// Some hacked code, to allow us to paint to a specific pane in Swing
		// which does not let you do normally. Had to override paint. Whoda
		// thunk?
		frame.getContentPane().add(btnFindRoute);
		MyComponent myComponent = new MyComponent();
		myComponent.setOpaque(true);

		frame.getContentPane().add(myComponent);
		output = new JLabel();
		frame.getContentPane().add(output);
		frame.setVisible(true);

	}

	// manually coded adjacency matrix, cuz linux annoyed me.
	public int[][] buildMatrix() {
		int Matrix[][] = new int[][] {
				{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 1, 0, 1, 0, 0, 0, 0, 1 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
				{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0,
						1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1,
						1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0,
						0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0,
						0, 1, 0, 1, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0,
						0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1,
						0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0,
						0, 1, 0, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 1, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1,
						0, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
				{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 1, 0, 1, 0, 1, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
						0, 0, 1, 0, 1, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 1, 0, 1, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 1, 0, 1, 0, 1, 1, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
						1, 1, 0, 0, 0, 1, 0, 1, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 1, 1, 0, 1, 1 },
				{ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						1, 0, 1, 0, 0, 0, 0, 1, 0, 0 },
				{ 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 1, 1, 0, 1, 0, 0 } };

		return Matrix;
	}

	// builds arraylist of type city
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
	//this allows me to override paint method, and draw map, city dots, and edges
	class MyComponent extends JComponent {

		@Override
		//sets size to custom pane. Set to match size of image
		public Dimension getPreferredSize() {
			return new Dimension(687, 382);
		}

		@Override
		public void paintComponent(Graphics g) {
			//code inserts image of map
			gr = g;
			tr = new MediaTracker(this);
			img = getImage(getCodeBase(), "map.gif");
			tr.addImage(img, 0);
			g.drawImage(img, 0, 0, this);

			// Draws Flight Paths and also calculates distances between 
			//nodes and overrides the values of 1 in the adj matrix with
			//the weight of the lines. 
			for (int i = 0; i < 30; i++) {
				for (int j = 0; j < 30; j++) {
					int x1, x2, y1, y2, distance;
					double xd, yd;
					if (adj[i][j] != 0) {
						x1 = cities.get(i).getX();
						y1 = cities.get(i).getY();
						x2 = cities.get(j).getX();
						y2 = cities.get(j).getY();
						g.setColor(Color.GRAY);
						g.drawLine(x1, y1, x2, y2);
						xd = Math.pow(x2 - x1, 2);
						yd = Math.pow(y2 - y1, 2);
						distance = (int) Math.sqrt(xd + yd);
						adj[i][j] = distance;

					}
				}
			}
	
			//iterate through arraylist of cities and 
			//paints dots for all cities
			g.setColor(Color.RED);
			for (int i = 0; i < cities.size(); i++) {
				int x = cities.get(i).getX() - 5;
				int y = cities.get(i).getY() - 5;
				g.fillOval(x, y, 10, 10);
			}

		}
	}

	@Override
	//Action listener for button, this is where magic happens and the 
	//starting city and destination city are passed to the dijkstra 
	//class to find shortest distance
	public void actionPerformed(ActionEvent arg0) {

		Dijkstra.computePaths(cities.get(StartComboBox.getSelectedIndex()),
				adj, cities);
		path = Dijkstra.getShortestPathTo(cities.get(comboBox_1
				.getSelectedIndex()));
		tester = "";
		for (int i = 0; i < path.size(); i++)
			tester += path.get(i).getName() + " ";
		output.setText("");
		output.setText(tester);
	}
}
