package objects;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationAnswer {
    @Expose
    int id;
    @Expose
    String token;
    @Expose
    String error;

}
