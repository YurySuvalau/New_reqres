package objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersData {
    @Expose
    int id;
    @Expose
    String email;
    @Expose
    @SerializedName("first_name")
    String firstName;
    @Expose
    @SerializedName("last_name")
    String lastName;
    @Expose
    String avatar;
}