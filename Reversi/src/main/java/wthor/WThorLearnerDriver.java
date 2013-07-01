package wthor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WThorLearnerDriver {
    private static final String WTHOR_PARAM_FILENAME = "params.dat";

    public static void main(String[] args) throws Exception {
        String[] filenames = new String[] {
                "wth/WTH_2000.wtb", "wth/WTH_2001.wtb", "wth/WTH_2002.wtb", "wth/WTH_2003.wtb", "wth/WTH_2004.wtb",
                "wth/WTH_2005.wtb", "wth/WTH_2006.wtb", "wth/WTH_2007.wtb", "wth/WTH_2008.wtb", "wth/WTH_2009.wtb",
                "wth/WTH_2010.wtb", "wth/WTH_2011.wtb", "wth/WTH_2012.wtb",
        };

        List<MatchData> matchData = new ArrayList<MatchData>();
        for (String filename : filenames) {
            InputStream in = new BufferedInputStream(new FileInputStream(new File(filename)));
            matchData.addAll(WThorParser.parse(in));
        }

        System.out.println("results.size() = " + matchData.size());

        WThorParams loadedParams = new File(WTHOR_PARAM_FILENAME).exists() ? new WThorParams(WTHOR_PARAM_FILENAME) : new WThorParams();

        WThorLearner learner = new WThorLearner(matchData, loadedParams);
        learner.run(100);
        learner.getParams().save(WTHOR_PARAM_FILENAME);
    }
}
