package tsprunner;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import tsp.GA;

class TSPCanvas extends Canvas {
	private List<Point> points;
	private int[] indices;
	
	@Override
	synchronized public void paint(Graphics g) {
		System.out.println("repaint");
		
		super.paint(g);
		
		if (points == null || indices == null)
			return;
		
		Dimension d = this.getSize();
		g.clearRect(0, 0, d.width, d.height);
		
		for (int i = 0; i < points.size(); ++i) {
			Point p1 = points.get(indices[i]);
			Point p2 = points.get(indices[(i + 1) % points.size()]);
			System.out.printf("%d - %d\n", p1.x, p1.y);
			g.drawLine(p1.x / 10, p1.y / 10, p2.x / 10, p2.y / 10);
		}
	}
	
	synchronized public void setResult(List<Point> points, int[] indices) {
		this.points = points;
		this.indices = indices;
	}
}

public class GUIRunner extends AbstractRunner {
	private static Frame frame;
	private static Button buttonStep10;
	
	public static void main(String[] args) throws Exception {
		final List<Point> points = loadProblem("problem/att48.tsp");
		GA ga = new GA(points);
				
		frame = new Frame("TSP");
		frame.setSize(1024, 768);
		frame.setLayout(new FlowLayout());

		buttonStep10 = new Button("repaint");
		buttonStep10.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonStep10.repaint();
			}
		});
		frame.add(buttonStep10);
		
		
		TSPCanvas panel = new TSPCanvas();
		panel.setSize(1024, 700);
		frame.add(panel);

		frame.setVisible(true);
		
		ga.run();
		// ga.showFinalResult();

		int[] indices = ga.finalResult(); 
		panel.setResult(points, indices);
		panel.repaint();
	}
}
