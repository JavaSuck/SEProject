package Server.TCPServer;

import java.util.HashMap;
import java.util.Map;

public class Instruction {

    private Map map;

    private String[] instructionArr = {
            "DOWN",
            "LEFT",
            "RIGHT",
            "UP",
            "BOMB"
    };

    public Instruction(){

        map = new HashMap<String, Integer>();

        int index = 0;

        for(String inst: instructionArr)
            map.put(inst, index++);
    }

    public int index(String instruction){
        return (int)map.get(instruction);
    }

    public String get(int index) {
        try {
            return instructionArr[index];
        }
        catch(IndexOutOfBoundsException e){
            return null;
        }
    }
}
