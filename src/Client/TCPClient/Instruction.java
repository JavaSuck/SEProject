package Client.TCPClient;

import java.util.HashMap;
import java.util.Map;

public class Instruction {

    protected Map map;

    protected String[] instructionArr = {
            "TURNEAST",
            "TURNSOUTH",
            "TURNNORTH",
            "TURNWEST",
            "GET"
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
