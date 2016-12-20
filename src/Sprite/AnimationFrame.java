package Sprite; /**
 * Created by DMOON on 2016/10/25.
 */

import java.awt.image.BufferedImage;

public class AnimationFrame {

    private BufferedImage frame;
    private int duration;

    public AnimationFrame(BufferedImage frame, int duration) {
        this.frame = frame;
        this.duration = duration;
    }

    public BufferedImage getFrame() {
        return frame;
    }

    public void setFrame(BufferedImage frame) {
        this.frame = frame;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


}