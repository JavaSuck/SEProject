package Client.Assets.Audios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Paths;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Path;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Created by lucienlo on 2016/12/30.
 */
public class Audios {


  public void playBombSound(){
    playSound("bomb.wav");
  }

  public void playRbgSound(){
    playSound("rbg.wav");
  }

  public void playDeadSound(){
    playSound("dead.wav");
  }

  private void playSound(String soundName) {

    String basicPath = (this.getClass().getResource("/").toString()+"Client/Assets/Audios/").substring(5);
    String soundPath = basicPath + soundName;

    try {
      InputStream in=new FileInputStream(soundPath);
      AudioStream as=new AudioStream(in);
      AudioPlayer.player.start(as);
    }
    catch (Exception e){
      e.printStackTrace();
    }

  }

}
