package Server.CDC;

public class Main {

  public static void main(String[] args) {
    CDC cdc = new CDC();

    Player p = (Player)cdc.playerController.players.get(1);
    System.out.println(p.coordinate);
    cdc.playerController.walk(1, 2);
    System.out.println(p.coordinate);

  }

}