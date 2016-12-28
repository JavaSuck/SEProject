package Client.BackgroundCanvas;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import Client.Scene.Game;

public class BackgroundCanvas extends JLayeredPane {
    public Image mapImage;

    public final static int CANVAS_WIDTH = 816;
    public final static int CANVAS_HEIGHT = 816;

    public Point coordinateOld = new Point(0, 0);

    public final int canvasBasicOffsetX = (Game.CAMERA_WIDTH / Game.BLOCK_PIXEL - 1) / 2 * Game.BLOCK_PIXEL;
    public final int canvasBasicOffsetY = (Game.CAMERA_HEIGHT / Game.BLOCK_PIXEL - 1) / 2 * Game.BLOCK_PIXEL;
    private Timer timer;

    private int newCanvasX;
    private int newCanvasY;

    public boolean isWalkingAnimation = false;
    private boolean shouldCharacterSync = true;

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

            if (this.shouldCharacterSync) {
                setLocation(newCanvasX, newCanvasY);
                isWalkingAnimation = false;
                return;
            }

            int movePixel = 4;
            if (Math.abs(newCanvasX - oldCanvasX) > 60 || Math.abs(newCanvasY - oldCanvasY) > 60) {
                timer.setDelay(5);
            } else if (Math.abs(newCanvasX - oldCanvasX) > 48 || Math.abs(newCanvasY - oldCanvasY) > 48) {
                timer.setDelay(15);
            } else {
                timer.setDelay(16);
            }

            if (newCanvasX - oldCanvasX > 0) {
                moveCanvas(movePixel, 0);
            } else if (newCanvasX - oldCanvasX < 0) {
                moveCanvas(movePixel * -1, 0);
            } else if (newCanvasY - oldCanvasY > 0) {
                moveCanvas(0, movePixel);
            } else if (newCanvasY - oldCanvasY < 0) {
                moveCanvas(0, movePixel * -1);
            }

            if (Math.abs(newCanvasX - oldCanvasX) < 12 && Math.abs(newCanvasY - oldCanvasY) < 12) {
                isWalkingAnimation = false;
            } else {
                isWalkingAnimation = true;
            }

        };

        timer = new Timer(16, move);
        timer.setRepeats(true);
        timer.setCoalesce(true);
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

    public void update(Point coordinateNext, boolean shouldCharacterSync) {
        this.shouldCharacterSync = shouldCharacterSync;
        if (coordinateOld.x != coordinateNext.x || coordinateOld.y != coordinateNext.y) {
            coordinateOld = coordinateNext;

            newCanvasX = canvasBasicOffsetX - coordinateNext.x * Game.BLOCK_PIXEL;
            newCanvasY = canvasBasicOffsetX - coordinateNext.y * Game.BLOCK_PIXEL;
        }

    }
}
