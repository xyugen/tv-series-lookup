import com.google.gson.annotations.SerializedName;

public class TVSeries {
    private String id;

    private int rank;

    @SerializedName("l")
    private String name;

    @SerializedName("s")
    private String cast;

    @SerializedName("y")
    private String year;

    public TVSeries(String name, String cast, String year) {
        this.name = name;
        this.cast = cast;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name.strip();
    }

    public String getCast() {
        if (cast == null || cast.strip().equals(""))
            cast = "None";
        return cast;
    }

    public String getYear() {
        if (year == null || year.strip().equals(""))
            year = "Unknown";
        return year;
    }

    public int getRank() {
        return rank;
    }
}
