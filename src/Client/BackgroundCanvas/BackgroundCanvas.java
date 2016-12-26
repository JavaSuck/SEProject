package Client.BackgroundCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BackgroundCanvas extends JPanel {
  public Image mapImage;

  private final int width = 816;
  private final int height = 816;
  public final int canvasBasicOffsetX = (720 / 48 - 1) / 2 * 48;
  public final int canvasBasicOffsetY = (720 / 48 - 1) / 2 * 48;
  private Timer timer;

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(width, height);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(mapImage, 0, 0, this);
  }


  public void initCanvasPosition() {
    setLocation(canvasBasicOffsetX - 4 * 48, canvasBasicOffsetY - 4 * 48);
  }

  public void moveCanvas(int xOffsetDelta, int yOffsetDelta) {
    setLocation(getLocation().x + xOffsetDelta, getLocation().y + yOffsetDelta);
  }

  public void update(Point coordinate) {
    int newCanvasX = coordinate.x * 48;
    int newCanvasY = coordinate.y * 48;

    ActionListener move = e -> {
      int oldCanvasX = getLocation().x;
      int oldCanvasY= getLocation().y;
      if (newCanvasX - oldCanvasX > 0)
      moveCanvas(1 ,1);
    };
    timer = new Timer(16, move);
    timer.setRepeats(true);
//    timer.start();
  }
}
