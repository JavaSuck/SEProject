package Client.SDM;

import java.awt.*;
import java.io.*;
import javax.imageio.*;
import Client.BackgroundCanvas.BackgroundCanvas;

public class SDM {

    private BackgroundCanvas backgroundCanvas;


    public SDM (BackgroundCanvas backgroundCanvas){
        this.backgroundCanvas = backgroundCanvas;
    }

    public void loadMap(){
        try {
            backgroundCanvas.mapImage = ImageIO.read(new File("src/Client/Assets/Images/map001.png"));
        }
        catch (Exception ex) {
            System.out.println("No map001.png!!");
        }

    }
}
