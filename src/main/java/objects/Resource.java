package objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@lombok.Data
public class Resource {
    @Expose
    int id;
    @Expose
    String name;
    @Expose
    int year;
    @Expose
    String color;
    @Expose
    @SerializedName("pantone_value")
    String pantoneValue;
}
