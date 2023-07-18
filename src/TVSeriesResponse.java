import com.google.gson.annotations.SerializedName;

public class TVSeriesResponse {
    @SerializedName("d")
    private TVSeries[] series;

    public TVSeries[] getSeries() {
            return series;
    }
}
