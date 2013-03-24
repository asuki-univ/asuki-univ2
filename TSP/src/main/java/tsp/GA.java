package tsp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class IndexConverter {
    static public int[] convert(int[] indices) {
        final int n = indices.length;
        
        boolean[] used = new boolean[n];
        int[] result = new int[n];
        
        for (int i = 0; i < n; ++i) {
            int rest = indices[i];
            for (int k = 0; k < n; ++k) {
                if (used[k])
                    continue;
                
                if (rest > 0) {
                    --rest;
                    continue;
                }
                
                used[k] = true;
                result[i] = k;
                break;
            }
        }

        return result;
    }
}

// genes[i] には、0 以上 N - i - 1 までが入る。  
class Gene {
    private static Random random = new Random();
    private int score;
    private int genes[];

    public int size() {
        return genes.length;
    }

    public static Gene makeGene(int[] genes) {
        return new Gene(genes);
    }
    
    public static Gene makeRandomGene(int n) {
        Gene gene = new Gene(n);
        for (int i = 0; i < n; ++i)
            gene.genes[i] = random.nextInt(n - i);
        return gene;
    }
    
    public static Gene makeCrossoverGene(Gene g1, Gene g2) {
        assert (g1.size() == g2.size());
        final int n = g1.size();
        Gene gen = new Gene(n);
        int k = random.nextInt(n);
        for (int i = 0; i < k; ++i)
            gen.genes[i] = g1.genes[i];
        for (int i = k; k < n; ++i)
            gen.genes[i] = g2.genes[i];
        
        return gen;
    }
    
    private Gene(int n) {
        this.genes = new int[n];
        this.score = -1;
    }
    
    private Gene(int[] genes) {
        this.genes = Arrays.copyOf(genes, genes.length);
        this.score = -1;
    }
    
    private double calculateScore(List<Point> points) {
        assert (size() == points.size());
        
        double sumDistance = 0;
        
        int[] converted = IndexConverter.convert(genes);
        for (int i = 1; i < size(); ++i) {
            Point p1 = points.get(converted[i - 1]);
            Point p2 = points.get(converted[i]);
            
            sumDistance += p1.distance(p2);
        }
        
        return -sumDistance;
    }
}

public class GA extends AbstractTSPSolver {
    public static void main(String[] args) throws Exception {
        List<Point> points = loadProblem("problem/att48.tsp");
        final int N = points.size();
        
        ArrayList<Gene> currentGeneration = new ArrayList<Gene>();
        for (int i = 0; i < 100; ++i)
            currentGeneration.add(Gene.makeRandomGene(N));
        
        for (int i = 0; i < 1000; ++i) {
            ArrayList<Gene> nextGeneration = new ArrayList<Gene>();
        }
        while (true) {
            // 現在のスコアを計算
            
            // 100 個の新しい世代を、次のようにして求める
            // 1. 5 個を、現在の遺伝子のうち最もスコアがよいものから常に残す（エリート戦略）
            // 2. 75 個を、適当に選んだ遺伝子を交叉させて作成する。このとき、確率 p で突然変異を起こす。
            // 3. 30 個を、ランダムに作成する。
        }
        
//        for (Point p : points)
//            System.out.printf("%d %d\n", p.x, p.y);
    }
}
