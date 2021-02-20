package objects;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Suite {
    @Expose
    String name;
    @Expose
    String job;
    @Expose
    String email;
    @Expose
    String password;
//    @Expose
//    int id;
//    @Expose
//    String createdAt;
}
