package TicTacToe;
import javafx.scene.canvas.Canvas;

public class BoardGame {
    Canvas canvas;
    public BoardGame() {
        canvas = new Canvas(600, 600);
        canvas.setOnMouseClicked(event -> {
            System.out.println(event.getX() + " " + event.getY());
        });
    }


}
