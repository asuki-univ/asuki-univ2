package wthor;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import board.Board;
import board.Stone;

public class WThorFeatureTest {

    @Test
    public void testCollectFeature1() {
        Board board = new Board(
                "WWW.B..." +
                "...B...." +
                "..B.B..." +
                ".W...W.B" +
                "B.....B." +
                ".....W.." +
                "....W..." +
                "...B....");

        WThorFeature feature = new WThorFeature();
        feature.collect(board);

        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("WWW.....B")) + WThorParams.CORNER33_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("W..W..W.B")) + WThorParams.CORNER33_OFFSET));

        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("WWW.B...B.")) + WThorParams.CORNER25_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("W...BW..W.")) + WThorParams.CORNER25_OFFSET));

        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("WWW.B...")) + WThorParams.LENGTH8_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("...B.WWW")) + WThorParams.LENGTH8_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("W...B...")) + WThorParams.LENGTH8_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("...B...W")) + WThorParams.LENGTH8_OFFSET));

        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("W.B..W..")) + WThorParams.DIAG8_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("..W..B.W")) + WThorParams.DIAG8_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("........")) + WThorParams.DIAG8_OFFSET));

        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("B..B")) + WThorParams.DIAG4_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("....")) + WThorParams.DIAG4_OFFSET));

        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("BWBBB")) + WThorParams.DIAG5_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("BBBWB")) + WThorParams.DIAG5_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("BWWBB")) + WThorParams.DIAG5_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("BBWWB")) + WThorParams.DIAG5_OFFSET));

        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("WBBWB.")) + WThorParams.DIAG6_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray(".BWBBW")) + WThorParams.DIAG6_OFFSET));

        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("W......")) + WThorParams.DIAG7_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("......W")) + WThorParams.DIAG7_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("..W....")) + WThorParams.DIAG7_OFFSET));
        assertTrue(feature.featureIndices.contains(WThorFeature.index(toStoneArray("....W..")) + WThorParams.DIAG7_OFFSET));
    }

    private static Stone[] toStoneArray(String str) {
        Stone[] ss = new Stone[str.length()];
        for (int i = 0; i < str.length(); ++i) {
            switch (str.charAt(i)) {
            case '.':
                ss[i] = Stone.EMPTY; break;
            case 'W':
                ss[i] = Stone.WHITE; break;
            case 'B':
                ss[i] = Stone.BLACK; break;
            default:
                throw new IllegalArgumentException();
            }
        }

        return ss;
    }
}
