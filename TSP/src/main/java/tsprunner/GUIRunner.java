package tsprunner;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import tsp.AbstractTSPSolver;
import tsp.Point;
import tsp.TSPSolver;
import tsp.TSPState;
import tsp.impl.HillClimbing;
import tsp.impl.HillClimbingImproved;
import tsp.impl.KOptHillClimbing;
import tsp.impl.SimulatedAnnealingImproved;

class TSPCanvas extends Canvas {
    private static final long serialVersionUID = 1L;
    private TSPState state;

    @Override
    synchronized public void paint(Graphics g) {
        System.out.println("repaint");

        super.paint(g);

        if (state == null)
            return;

        Dimension d = this.getSize();
        g.clearRect(0, 0, d.width, d.height);

        List<Point> points = state.getPoints();

        for (int i = 0; i < points.size(); ++i) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % points.size());
            // System.out.printf("%d - %d\n", p1.x, p1.y);
            g.drawLine((int)(p1.x / 10) + 20, (int)(p1.y / 10) + 20, (int)(p2.x / 10) + 20, (int)(p2.y / 10) + 20);
        }
    }

    synchronized public void setState(TSPState state) {
        this.state = state;
    }
}

class StepButtonActionListener implements ActionListener {
    private final TSPSolver solver;
    private final TSPCanvas canvas;
    private final int numStepsPerOneAction;

    public StepButtonActionListener(TSPSolver solver, TSPCanvas canvas, int numStepsPerOneAction) {
        this.solver = solver;
        this.canvas = canvas;
        this.numStepsPerOneAction = numStepsPerOneAction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < numStepsPerOneAction; ++i)
            solver.step();

        System.out.println(solver.getBestState().getScore());
        canvas.setState(solver.getBestState());
        canvas.repaint();
    }
}

class SVGButtonActionListener implements ActionListener {
    private final TSPSolver solver;
    private final TSPCanvas canvas;

    public SVGButtonActionListener(TSPSolver solver, TSPCanvas canvas) {
        this.solver = solver;
        this.canvas = canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TSPState state = solver.getBestState();

        System.out.println("<svg viewBox='0 0 10000 10000'>");
        System.out.println("<path stroke='black' stroke-width='1' fill='none' d='");
        for (int i = 0; i < state.getPoints().size(); ++i) {
            Point p = state.getPoints().get(i);
            if (i == 0) {
                System.out.printf("M %f %f\n", p.x, p.y);
            } else {
                System.out.printf("L %f %f\n", p.x, p.y);
            }
        }
        System.out.println("Z'/>");
        System.out.println("</svg>");


        System.out.println(solver.getBestState().getScore());
        canvas.setState(solver.getBestState());
        canvas.repaint();
    }
}

public class GUIRunner extends AbstractRunner {
    private static Frame frame;
    private static Button buttonStep1;
    private static Button buttonStep10;
    private static Button buttonStep100;
    private static Button buttonSVG;

    public static void main(String[] args) throws Exception {
        final List<Point> points = loadProblem("problem/att48.tsp");

        // opt = 33523.70850743559
//        int[] optIndices = new int[] {
//                 1,  8, 38, 31, 44, 18,  7, 28,  6, 37,
//                19, 27, 17, 43, 30, 36, 46, 33, 20, 47,
//                21, 32, 39, 48,  5, 42, 24, 10, 45, 35,
//                 4, 26,  2, 29, 34, 41, 16, 22,  3, 23,
//                14, 25, 13, 11, 12, 15, 40, 9
//        };
//
//        List<Point> optPoints = new ArrayList<Point>();
//        for (int k : optIndices) {
//            optPoints.add(points.get(k - 1));
//        }

        //AbstractTSPSolver solver = new HillClimbingImproved(optPoints);

        // AbstractTSPSolver solver = new GA(points);
        AbstractTSPSolver solver = new HillClimbing(points);
        // AbstractTSPSolver solver = new HillClimbingWithRandom(points);

        // 34052.487524201075
        // AbstractTSPSolver solver = new HillClimbingImproved(points);

        // AbstractTSPSolver solver = new SimulatedAnnealing(points);
        //TSPSolver solver = new SimulatedAnnealingImproved(points);
        //TSPSolver solver = new KOptHillClimbing(points, 2);
        //TSPSolver solver = new KOptHillClimbing(points, 3);
        //TSPSolver solver = new KOptHillClimbing(points, 4);
        //TSPSolver solver = new KOptHillClimbing(points, 5);

        run(points, solver);
    }

    private static void run(List<Point> points, final TSPSolver solver) {
        frame = new Frame("TSP");
        frame.setSize(1024, 768);
        frame.setLayout(new FlowLayout());

        buttonStep1 = new Button("step 1");
        frame.add(buttonStep1);

        buttonStep10 = new Button("step 10");
        frame.add(buttonStep10);

        buttonStep100 = new Button("step 100");
        frame.add(buttonStep100);

        buttonSVG = new Button("SVG");
        frame.add(buttonSVG);

        final TSPCanvas panel = new TSPCanvas();
        panel.setSize(1024, 700);
        frame.add(panel);
        frame.setVisible(true);

        buttonStep1.addActionListener(new StepButtonActionListener(solver, panel, 1));
        buttonStep10.addActionListener(new StepButtonActionListener(solver, panel, 10));
        buttonStep100.addActionListener(new StepButtonActionListener(solver, panel, 100));
        buttonSVG.addActionListener(new SVGButtonActionListener(solver, panel));

        panel.setState(solver.getBestState());
        panel.repaint();
    }
}
