package deserializeBB;

import java.util.List;
import java.util.Map;

public class BreakingBad_Pojo {

    private List<Map<String, Object>> listOfCharacters;

    public List<Map<String, Object>> getListOfCharacters() {
        return listOfCharacters;
    }

    public void setListOfCharacters(List<Map<String, Object>> listOfCharacters) {
        this.listOfCharacters = listOfCharacters;
    }
}
