package TicTacToe;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main extends Application {
    private Pane pane = new Pane();
    public Canvas canvas;
    public GraphicsContext graphicsContext;
    public int[][] board = {
            {2, 2, 2},
            {2, 2, 2},
            {2, 2, 2},
    };
    public int x;
    public int y;

    @Override
    public void start(Stage stage) throws Exception {
        canvas = new Canvas(600, 600);
        graphicsContext = canvas.getGraphicsContext2D();
        render();
        Socket socket = new Socket("localhost", 9999);
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        // read the message sent to this client
                        String msg = dataInputStream.readUTF();
                        System.out.println(msg);
                        int id = Integer.parseInt(String.valueOf(msg.charAt(0)));
                        int x = Integer.parseInt(String.valueOf(msg.charAt(1)));
                        int y = Integer.parseInt(String.valueOf(msg.charAt(2)));
                        board[x][y] = id;
                        render();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        });

        readMessage.start();

        canvas.setOnMouseClicked(event -> {
            x = (int) event.getX() / 200;
            y = (int) event.getY() / 200;
            try {
                dataOutputStream.writeUTF(String.valueOf(x) + String.valueOf(y));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        pane.getChildren().add(canvas);
        Scene scene = new Scene(pane, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void render() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 2) {
                    graphicsContext.drawImage(Asset.image0, i * 200, j * 200);
                }
                if (board[i][j] == 0) {
                    graphicsContext.drawImage(Asset.image1, i * 200, j * 200);
                }
                if (board[i][j] == 1) {
                    graphicsContext.drawImage(Asset.image2, i * 200, j * 200);
                }
            }
        }
    }
}