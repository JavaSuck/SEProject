package Client.BackgroundCanvas;

import java.awt.*;

public class BackgroundCanvas {

    private int xOffset;
    private int yOffset;

    public void renderScene(Image map) {

    }


    public int move(int xOffsetDelta, int yOffsetDelta) {
        xOffset += xOffsetDelta;
        yOffset += yOffsetDelta;
        return xOffset;
    }
}
