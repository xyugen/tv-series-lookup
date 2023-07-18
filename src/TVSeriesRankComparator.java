import java.util.Comparator;

public class TVSeriesRankComparator implements Comparator<TVSeries> {
    @Override
    public int compare(TVSeries series1, TVSeries series2) {
        return Integer.compare(series1.getRank(), series2.getRank());
    }
}
