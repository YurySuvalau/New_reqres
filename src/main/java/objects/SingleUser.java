package objects;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class SingleUser {
    @Expose
    UsersData data;
}
