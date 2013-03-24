package tsp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
		for (int i = k; i < n; ++i)
			gen.genes[i] = g2.genes[i];

		return gen;
	}

	private Gene(int n) {
		this.genes = new int[n];
	}

	private Gene(int[] genes) {
		this.genes = Arrays.copyOf(genes, genes.length);
	}

	public double calculateScore(List<Point> points) {
		assert (size() == points.size());

		double sumDistance = 0;

		int[] converted = IndexConverter.convert(genes);
		for (int i = 1; i < size(); ++i) {
			Point p1 = points.get(converted[i - 1]);
			Point p2 = points.get(converted[i]);

			sumDistance += p1.distance(p2);
		}

		sumDistance += points.get(converted[size() - 1]).distance(points.get(converted[0]));		
		return sumDistance;
	}
	
	public int[] actualIndices() {
		return IndexConverter.convert(genes);
	}
}

class GeneWithScore implements Comparable<GeneWithScore> {
	public final Gene gene;
	public final double score;

	public GeneWithScore(Gene gene, double score) {
		this.gene = gene;
		this.score = score;
	}

	@Override
	public int compareTo(GeneWithScore rhs) {
		GeneWithScore lhs = this;

		if (lhs.score != rhs.score)
			return lhs.score < rhs.score ? -1 : 0;

		// We don't compare gene itself now.
		return 0;
	}
}

public class GA extends AbstractTSPSolver {
	public static final int NUM_GENES = 1000;
	public static final int NUM_MAX_GENERATIONS = 1000;

	final List<Point> points;
	ArrayList<GeneWithScore> finalGeneration;
	
	public GA(List<Point> points) {
		this.points = points;
		this.finalGeneration = null;
	}
	
	public void run() {
		Random random = new Random();
		
		ArrayList<GeneWithScore> currentGeneration = new ArrayList<GeneWithScore>();        
		fillGenerationWithRandomGenes(currentGeneration);
		Collections.sort(currentGeneration);
		
		for (int currentGenerationNumber = 0; currentGenerationNumber < NUM_MAX_GENERATIONS; ++currentGenerationNumber) {
			ArrayList<GeneWithScore> nextGeneration = new ArrayList<GeneWithScore>();

			// 100 個の新しい世代を、次のようにして求める
			// 1. 5 個を、現在の遺伝子のうち最もスコアがよいものから常に残す（エリート戦略）
			// 2. 65 個を、適当に選んだ遺伝子を交叉させて作成する。このとき、各遺伝子は確率 p で突然変異を起こす。
			// 3. 30 個を、ランダムに作成する。
			for (int i = 0; i < 5; ++i)
				nextGeneration.add(currentGeneration.get(i));

			int k = (int) (NUM_GENES * 0.3);
			for (int i = 0; i < k; ++i) {
				int r1 = random.nextInt(currentGeneration.size());
				int r2 = random.nextInt(currentGeneration.size());
				Gene gene = Gene.makeCrossoverGene(currentGeneration.get(r1).gene, currentGeneration.get(r2).gene);
				double score = gene.calculateScore(points);
				nextGeneration.add(new GeneWithScore(gene, score));
			}
			
			fillGenerationWithRandomGenes(nextGeneration);
			Collections.sort(nextGeneration);

			currentGeneration = nextGeneration;
			System.out.printf("generation id = %d : score = %f\n", currentGenerationNumber + 1, currentGeneration.get(0).score);
		}
		
		finalGeneration = currentGeneration;
	}

	public void showFinalResult() {
		int[] indices = finalResult();
		System.out.println("Best score: " + finalGeneration.get(0).score);

		for (int i = 0; i < points.size(); ++i)
			System.out.println(points.get(indices[i]));
	}
	
	public int[] finalResult() {
		if (finalGeneration == null) {
			System.out.println("Not calculated yet");
			return null;
		}
			
		Collections.sort(finalGeneration);
		GeneWithScore bestGeneWithScore = finalGeneration.get(0);
		
		return bestGeneWithScore.gene.actualIndices();
	}
	
	private void fillGenerationWithRandomGenes(ArrayList<GeneWithScore> generation) {
		while (generation.size() < NUM_GENES) {
			Gene gene = Gene.makeRandomGene(points.size());
			double score = gene.calculateScore(points);
			generation.add(new GeneWithScore(gene, score));
		}
	}
	
	
}
