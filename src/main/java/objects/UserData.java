package objects;

import com.google.gson.annotations.Expose;
import lombok.Data;

import java.util.ArrayList;

@Data
public class UserData {
    @Expose
    ArrayList<Resource> data;
}
