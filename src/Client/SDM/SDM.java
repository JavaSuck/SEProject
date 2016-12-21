package Client.SDM;

import java.awt.*;
import java.io.*;
import javax.imageio.*;
import Client.BackgroundCanvas.BackgroundCanvas;

public class SDM {

    private BackgroundCanvas backgroundCanvas;
    private Image map;

    public SDM (BackgroundCanvas backgroundCanvas){
        this.backgroundCanvas = backgroundCanvas;
    }

    public void loadMap(){
        try {
            map = ImageIO.read(new File("imgs/map001.png"));
        }
        catch (Exception ex) {
            System.out.println("No map001.png!!");
        }

    }
    public void renderScene(){
        backgroundCanvas.renderScene(map);
    }


}
