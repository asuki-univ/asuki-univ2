package wthor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

// WThor がきちんと読めているかどうか、見た目でチェックするためのテスト用メインメソッド。
public class WThorParserMain {
    public static void main(String[] args) throws Exception {
        InputStream in = new BufferedInputStream(new FileInputStream(new File("wth/WTH_2004.wtb")));
        List<MatchData> results = WThorParser.parse(in);

        System.out.println("results.size() = " + results.size());

        for (int i = 0; i < results.size(); ++i) {
            MatchData data = results.get(i);
            System.out.printf("%4d : %2d : ", i, data.theoreticalScore);
            for (int j = 0; j < data.hands.length; ++j)
                System.out.printf("%d ", data.hands[j]);
            System.out.println();
        }
    }
}
