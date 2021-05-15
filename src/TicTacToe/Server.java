package TicTacToe;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public int[][] board = {{2, 2, 2}, {2, 2, 2}, {2, 2, 2}};
    public int clientCanPlay = 0;
    ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    void execute() throws IOException {
        ServerSocket ss = new ServerSocket(9999);
        for (int id = 0; id < 2; id++) {
            Socket socket = null;
            try {
                // socket object to receive incoming client requests
                socket = ss.accept();

                System.out.println("A new client is connected : " + socket);

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                ClientHandler clientHandler = new ClientHandler(id, this, socket, dataInputStream, dataOutputStream);
                clientHandler.start();
                clientHandlers.add(clientHandler);
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }
    }

    void a(int id, String message) throws IOException {
        if(id == clientCanPlay) {
            System.out.println("client" + id + " " + message);
            for (ClientHandler clientHandler : clientHandlers) {
                clientHandler.send(message);
            }
            clientCanPlay = (clientCanPlay + 1) % 2;
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.execute();
    }

    void handler(int id, String received) throws IOException {
        if (id == clientCanPlay) {
            int x = Integer.parseInt(String.valueOf(received.charAt(0)));
            int y = Integer.parseInt(String.valueOf(received.charAt(1)));
            if (board[x][y] != id) {
                board[x][y] = id;
                int winner = checkWin();
                String message = String.valueOf(id) + received;
                sendBoard(message);
                if (winner == 0) {
                    message = "0";
                    sendBoard(message);
                }
                else if (winner == 1) {
                    message = "1";
                    sendBoard(message);
                }
                else if (winner == -1) {
                    message = "-1";
                    sendBoard(message);
                }
                clientCanPlay = (clientCanPlay + 1) % 2;
            }
        }
    }


    void sendBoard(String message) throws IOException {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.send(message);
        }
    }

    public int checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2]) return board[i][0];
        }

        for (int i = 0; i < 3; i++) {
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i]) return board[0][i];
        }

        if (board[0][0] == board[1][1] && board[0][0] == board[2][2]) return board[0][0];

        if (board[0][2] == board[1][1] && board[0][2] == board[2][0]) return board[1][1];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 2) return 2;
            }
        }
        return -1;
    }
}
