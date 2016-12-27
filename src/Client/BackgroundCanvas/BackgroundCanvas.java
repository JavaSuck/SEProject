package Client.BackgroundCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import Client.Scene.Game;

public class BackgroundCanvas extends JPanel {
  public Image mapImage;

  public final static int CANVAS_WIDTH = 816;
  public final static int CANVAS_HEIGHT = 816;

  public Point coordinateOld = new Point(4, 4);

  public final int canvasBasicOffsetX = (Game.CAMERA_WIDTH / Game.BLOCK_PIXEL - 1) / 2 * Game.BLOCK_PIXEL;
  public final int canvasBasicOffsetY = (Game.CAMERA_HEIGHT / Game.BLOCK_PIXEL - 1) / 2 * Game.BLOCK_PIXEL;
  private Timer timer;

  private int newCanvasX;
  private int newCanvasY;
//  @Override
//  public Dimension getPreferredSize() {
//    return new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
//  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(mapImage, 0, 0, this);
  }

  public BackgroundCanvas() {

  }

  public void initTimer() {
    ActionListener move = e -> {
      int oldCanvasX = getLocation().x;
      int oldCanvasY = getLocation().y;
      if (newCanvasX - oldCanvasX > 0) {
        moveCanvas(1 ,0);
      } else if (newCanvasX - oldCanvasX < 0) {
        moveCanvas(-1 ,0);
      } else if (newCanvasY - oldCanvasY > 0) {
        moveCanvas(0 ,1);
      } else if (newCanvasY - oldCanvasY < 0) {
        moveCanvas(0 ,-1);
      }
    };

    timer = new Timer(4, move);
    timer.setRepeats(true);
    timer.start();
  }

  public void initCanvasPosition() {
    newCanvasX = canvasBasicOffsetX - coordinateOld.x * Game.BLOCK_PIXEL;
    newCanvasY = canvasBasicOffsetY - coordinateOld.y * Game.BLOCK_PIXEL;
    setLocation(newCanvasX, newCanvasY);
  }

  public void moveCanvas(int xOffsetDelta, int yOffsetDelta) {
    setLocation(getLocation().x + xOffsetDelta, getLocation().y + yOffsetDelta);
  }

  public void update(Point coordinate) {
    if (coordinateOld.x != coordinate.x || coordinateOld.y != coordinate.y) {
      coordinateOld = coordinate;

      newCanvasX = canvasBasicOffsetX - coordinate.x * Game.BLOCK_PIXEL;
      newCanvasY = canvasBasicOffsetX - coordinate.y * Game.BLOCK_PIXEL;

    }

  }
}
