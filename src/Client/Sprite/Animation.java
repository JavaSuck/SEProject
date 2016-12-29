package Client.Sprite;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;


public class Animation {

    private int frameCount;                 // Counts ticks for change
    private int frameDelay;                 // frame delay 1-12 (You will have to play around with this)
    private AtomicInteger currentFrame = new AtomicInteger(0);               // animations current frame
    private int frameInterval;              // animation direction (i.e counting forward or backward)
    private int totalFrames;                // total amount of frames for your animation
    private boolean stopped;                // has animations stopped
    private boolean isRepeat = true;
    private Timer timer;

    private List<AnimationFrame> frames = new ArrayList<AnimationFrame>();    // Arraylist of frames

    public Animation(BufferedImage[] frames, int frameDelay) {

        this.frameDelay = frameDelay;
        this.stopped = true;

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }

        this.frameCount = 0;
        this.frameDelay = frameDelay;
        this.currentFrame.set(0);
        this.frameInterval = 1;
        this.totalFrames = this.frames.size();

        ActionListener taskPerformer = evt -> {
            if (!stopped) {
                int currentFrameIndex = currentFrame.get();
                currentFrameIndex++;
                if (isRepeat) {
                    currentFrame.set(currentFrameIndex % totalFrames);
                } else {
                    if (currentFrameIndex == totalFrames) {
                        timer.stop();
                    } else {
                        currentFrame.set(currentFrameIndex % totalFrames);
                    }
                }
            }
        };

        timer = new Timer(frameDelay, taskPerformer);
        timer.setRepeats(true);
    }

    public void setIsRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public void start() {
        if (!stopped) {
            return;
        }

        if (frames.size() == 0) {
            return;
        }

        timer.start();

        stopped = false;

    }

    public void stop() {
        if (frames.size() == 0) {
            return;
        }

        timer.stop();
//        currentFrame.set(0);

        stopped = true;
    }

    public void restart() {
        if (frames.size() == 0) {
            return;
        }

        stopped = false;
        currentFrame.set(0);
    }

    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame.set(0);
    }

    private void addFrame(BufferedImage frame, int duration) {
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new AnimationFrame(frame, duration));
        currentFrame.set(0);
    }

    public BufferedImage getSprite() {
        return frames.get(currentFrame.get()).getFrame();
    }

    public void update() {
//        if (!stopped) {
//            frameCount++;
//
//            if (frameCount > frameDelay) {
//                frameCount = 0;
//                currentFrame += frameInterval;
//                if (currentFrame < 0) {
//                    currentFrame = totalFrames - 1;
//                }
//                currentFrame = currentFrame % totalFrames;
//                if (currentFrame > totalFrames - 1) {
//                    currentFrame = 0;
//                } else
//            }
//        }

    }

    public int getCurrentFrame() {
        return currentFrame.get();
    }

    public int getTotalFrames() {
        return totalFrames;
    }
}