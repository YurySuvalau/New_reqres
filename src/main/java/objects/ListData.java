package objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ListData {
    @Expose
    int page;
    @SerializedName("per_page")
    @Expose
    int perPage;
    @Expose
    int total;
    @SerializedName("total_pages")
    @Expose
    int totalPages;
    @Expose
    List<UsersData> data;
    Support support;
}