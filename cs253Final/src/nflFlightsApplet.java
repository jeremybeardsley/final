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

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;




public class nflFlightsApplet extends JApplet implements ActionListener {

	 static int[][] adj = new int[30][30];
	 static ArrayList<City> cities = new ArrayList<City>();
	 private static JFrame frame; 
	 static MediaTracker tr;
	 static Image img;
	 Graphics gr;
	 JComboBox StartComboBox, comboBox_1;
	   
           

	public nflFlightsApplet() throws IOException {
		cities = buildArray();
		
       String[] cityLabels = new String[30];
       for (int i = 0; i < 30; i++) {
           cityLabels[i] = cities.get(i).getName();
       }

       try {
           adj = buildMatrix();
       } catch (Exception e) {}
       
       frame = new JFrame();
       frame.setBounds(100, 100, 700, 661);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
       frame.setResizable(false);
       
       
       JLabel lblStartingCity = new JLabel("Starting City");
       frame.getContentPane().add(lblStartingCity);
       
       StartComboBox = new JComboBox(cityLabels);
       frame.getContentPane().add(StartComboBox);
       
       JLabel lblEndingCity = new JLabel("Ending City");
       frame.getContentPane().add(lblEndingCity);
       
       comboBox_1 = new JComboBox(cityLabels);
       frame.getContentPane().add(comboBox_1);
       
       
       
       JButton btnFindRoute = new JButton("Find Route");
       btnFindRoute.addActionListener(this);
       
       frame.getContentPane().add(btnFindRoute);
       MyComponent myComponent = new MyComponent();
       myComponent.setOpaque(true);
       
       frame.getContentPane().add(myComponent);
       
       frame.setVisible(true);
      
	}
	
	public int[][] buildMatrix() {
        int Matrix[][] = new int[][]
        		{{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,1},
        		{1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
        		{0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        		{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
        		{0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,1,0,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,1,1,0,1,0,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,1,1,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,1,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,1,1,0,1,0,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
        		{0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0,0,0,0},
        		{0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,0,0,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,0,0,0,1,0,0,0},
        		{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,0,0,1,0},
        		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0},
        		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,1},
        		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,1,0,1},
        		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1,0,1,0,0},
        		{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1},
        		{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0},
        		{1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0}};
        
        
        return Matrix;
    }
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
 class MyComponent extends JComponent {
	    
		@Override
		   public Dimension getPreferredSize() {
		       return new Dimension(687, 382);        
		   }
		       
		   @Override
		   public void paintComponent(Graphics g) {
			   	gr = g;
		       tr = new MediaTracker(this);
				img = getImage(getCodeBase(),"map.gif");
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
//
//		       g.setColor(Color.BLACK);
		       

		   }}
@Override
public void actionPerformed(ActionEvent arg0) {
	int [] previous = findPath.findPath(cities, adj, StartComboBox.getSelectedIndex(), comboBox_1.getSelectedIndex());
	
	String tester = "";
	for (int i=0; i<30; i++)
	{
		tester += previous[i]+ ", ";
	}
		gr.drawString(tester, 50, 600);

}
}
