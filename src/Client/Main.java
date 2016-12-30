package Client;

import static java.lang.Thread.sleep;

import Client.Assets.Audios.Audios;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        Client app = new Client();
        app.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        app.pack();
        app.setLocationRelativeTo(null);
        app.setVisible(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setResizable(false);

        Thread rbgSound = new Thread(()->{
           new Audios().playRbgSound();
        });

        rbgSound.start();


//        new Thread(()-> {
//            try {
//                while (true) {
//                    if (!rbgSound.isAlive()) {
//                        rbgSound.start();
//                    }
//                    sleep(1000);
//                }
//            }catch (InterruptedException exp){}
//        }).start();
    }

}