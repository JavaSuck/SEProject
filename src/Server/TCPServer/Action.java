package Server.TCPServer;

import java.util.HashMap;
import java.util.Map;

public class Action {

    private Map map;

    private String[] actionArray = {
            "DOWN",
            "LEFT",
            "RIGHT",
            "UP",
            "BOMB",
    };

    public Action() {
        map = new HashMap<String, Integer>();
        int index = 0;
        for (String action : actionArray)
            map.put(action, index++);
    }

    public int index(String action) {
        return (int) map.get(action);
    }

    public String get(int index) {
        try {
            return actionArray[index];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
