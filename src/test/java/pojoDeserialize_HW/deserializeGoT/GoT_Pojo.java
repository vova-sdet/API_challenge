package pojoDeserialize_HW.deserializeGoT;

import java.util.List;

public class GoT_Pojo {

    private String message;
    private List<GotCharacter> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GotCharacter> getData() {
        return data;
    }

    public void setData(List<GotCharacter> data) {
        this.data = data;
    }
}
