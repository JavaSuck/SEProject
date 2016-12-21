package Client;

import javax.swing.*;
import Client.Client;
public class Main {

    public static void main(String[] args) {
        Client app = new Client();


        app.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        app.pack();
        app.setLocationRelativeTo(null);
        app.setVisible(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setResizable(false);
    }

}