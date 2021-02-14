package objects;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class SingleResource {
    @Expose
    Resource data;
}
