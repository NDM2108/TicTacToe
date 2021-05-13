package TicTacToe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    private Pane pane = new Pane();
    @Override
    public void start(Stage stage) throws Exception {
        BoardGame boardGame = new BoardGame();
        pane.getChildren().add(boardGame.canvas);
        Scene scene = new Scene(pane, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
