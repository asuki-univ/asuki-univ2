package tsprunner;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractRunner {

	protected static List<Point> loadProblem(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        while (true) {
            String s = scanner.nextLine();
            if ("NODE_COORD_SECTION".equals(s))
                break;
        }
        
        List<Point> points = new ArrayList<Point>();
        while (scanner.hasNextInt()) {
            scanner.nextInt(); // skip id.
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            
            points.add(new Point(x, y));
        }

        scanner.close();
        return points;
    }
}
