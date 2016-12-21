package Client.BackgroundCanvas;

import javax.swing.*;
import java.awt.*;

public class BackgroundCanvas extends JPanel {
    public Image mapImage;
    public int xOffset ;
    public int yOffset ;

    private final int width = 816;
    private final int height = 816;

//    public BackgroundCanvas() {
//        setBackground(Color.RED);
//    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapImage, 0, 0, this);
    }


    public void moveCanvas(int xOffsetDelta,int yOffsetDelta){
        xOffset += xOffsetDelta;
        yOffset += yOffsetDelta;
    }
}
