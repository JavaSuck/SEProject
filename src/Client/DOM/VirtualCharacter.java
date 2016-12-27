package Client.DOM;

import Client.Sprite.Animation;
import Client.Sprite.Sprite;
import Server.CDC.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;


public class VirtualCharacter extends Sprite {

  private Direction currentDirection = Direction.DOWN;

  private String name;
  private int positionX, positionY;
  private final int walkingFrameAmount = 6;

  private int delay = 12;

  Animation[] walk = new Animation[4];

  // current animation
  private Animation animation;

  public VirtualCharacter(String imageName) {
    loadSprite(imageName);
    for (int i = 0; i < 4; i++) {
      BufferedImage[] walking = {getSprite(i, 1), getSprite(i, 0), getSprite(i, 2)};
      walk[i] = new Animation(walking, delay);
    }
    animation = walk[currentDirection.getValue()];
  }

  public Point getPosition() {
    return new Point(positionX, positionY);
  }

  public void walk(Direction direction) {
    animation = walk[direction.getValue()];
    currentDirection = direction;
    start();
  }

  public void start() {
    animation.start();
  }

  public void stop() {
    animation.stop();
    animation.reset();
  }

  public void updateAnimation() {
    animation.update();
  }

  public void updateCharacter(Direction dir, int x, int y, boolean isCharacterSync) {
    setLocation(x, y);
    currentDirection = dir;
    positionX = x;
    positionY = y;

//    if(isCharacterSync) {
      walk(dir);
//    } else {
//      stop();
//    }

  }

  public Direction getCurrentDirection() {
    return currentDirection;
  }

  public BufferedImage getAnimationFrame() {
    return animation.getSprite();
  }

}
