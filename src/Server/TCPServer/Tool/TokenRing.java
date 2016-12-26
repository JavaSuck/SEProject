package Server.TCPServer.Tool;

/**
 * Created by lucienlo on 2016/12/7.
 */
public class TokenRing {

    private boolean[] token_ring;

    public TokenRing(int size) {
        token_ring = new boolean[size];
    }

    public int getToken() {

        int index = 0;
        while (index < token_ring.length * 5) {

            if (!this.token_ring[index % token_ring.length]) {

                token_ring[index % 5] = true;
//                int token_number = index + 1;
                int token_number = index;

                return token_number;
            }

            index++;

        }

        return -1;

    }

    public void token_discard(int client_token) {
        this.token_ring[client_token] = false;
    }
}