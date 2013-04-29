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

import tsp.AbstractTSPSolver;
import tsp.GA;
import tsp.HillClimbing;
import tsp.HillClimbingWithRandom;
import tsp.SimulatedAnnealing;

class TSPCanvas extends Canvas {
    private static final long serialVersionUID = 1L;
    private List<Point> points;

	@Override
	synchronized public void paint(Graphics g) {
		System.out.println("repaint");

		super.paint(g);

		if (points == null)
			return;

		Dimension d = this.getSize();
		g.clearRect(0, 0, d.width, d.height);

		for (int i = 0; i < points.size(); ++i) {
			Point p1 = points.get(i);
			Point p2 = points.get((i + 1) % points.size());
			// System.out.printf("%d - %d\n", p1.x, p1.y);
			g.drawLine(p1.x / 10, p1.y / 10, p2.x / 10, p2.y / 10);
		}
	}

	synchronized public void setResult(List<Point> points) {
		this.points = points;
	}
}

public class GUIRunner extends AbstractRunner {
	private static Frame frame;
	private static Button buttonStep1;
	private static Button buttonStep10;

	public static void main(String[] args) throws Exception {
		final List<Point> points = loadProblem("problem/att48.tsp");

		// AbstractTSPSolver solver = new GA(points);
		// AbstractTSPSolver solver = new HillClimbing(points);
		// AbstractTSPSolver solver = new HillClimbingWithRandom(points);
		AbstractTSPSolver solver = new SimulatedAnnealing(points);

		run(points, solver);
	}

	private static void run(List<Point> points, final AbstractTSPSolver solver) {
        frame = new Frame("TSP");
        frame.setSize(1024, 768);
        frame.setLayout(new FlowLayout());

        buttonStep1 = new Button("step 1");
        frame.add(buttonStep1);

        buttonStep10 = new Button("step 10");
        frame.add(buttonStep10);

        final TSPCanvas panel = new TSPCanvas();
        panel.setSize(1024, 700);
        frame.add(panel);
        frame.setVisible(true);

        buttonStep1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solver.step();
                System.out.println(solver.getFinalScore());
                //solver.showFinalResult();
                panel.repaint();
            }
        });

        buttonStep10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 10; ++i) {
                    solver.step();
                }

                System.out.println(solver.getFinalScore());
                panel.repaint();
            }
        });

        panel.setResult(solver.getResult());
        panel.repaint();
	}
}
