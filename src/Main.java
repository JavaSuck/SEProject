import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        GameApp app = new GameApp();

        app.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        app.pack();
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }

}