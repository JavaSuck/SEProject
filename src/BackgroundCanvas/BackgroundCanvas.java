package BackgroundCanvas;

import java.awt.*;
import javax.swing.*;

public class BackgroundCanvas {

    public int xOffset ;
    public int yOffset ;

    public void renderScene(Image map){

    }


    public void move(int xOffsetDelta,int yOffsetDelta){
        xOffset += xOffsetDelta;
        yOffset += yOffsetDelta;

    }
}
