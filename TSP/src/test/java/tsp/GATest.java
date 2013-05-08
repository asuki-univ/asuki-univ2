package tsp;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import tsp.impl.Gene;
import tsp.impl.IndexConverter;


public class GATest {
    @Test
    public void testMakeGenes() {
        Gene gene = Gene.makeRandomGene(100);
        
        assertThat(gene.size(), equalTo(100));
    }
    
    @Test
    public void testActualIndices() {
        assertThat(IndexConverter.convert(new int[] { 0, 0, 0 }),
                equalTo(new int[] { 0, 1, 2 }));
        
        assertThat(IndexConverter.convert(new int[] { 0, 1, 0 }),
                equalTo(new int[] { 0, 2, 1 }));

        assertThat(IndexConverter.convert(new int[] { 1, 0, 0 }),
                equalTo(new int[] { 1, 0, 2 }));

        assertThat(IndexConverter.convert(new int[] { 1, 1, 0 }),
                equalTo(new int[] { 1, 2, 0 }));

        assertThat(IndexConverter.convert(new int[] { 2, 0, 0 }),
                equalTo(new int[] { 2, 0, 1 }));

        assertThat(IndexConverter.convert(new int[] { 2, 1, 0 }),
                equalTo(new int[] { 2, 1, 0 }));
    }
}
