package TicTacToe;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Asset {
    public static Image image0;

    static {
        try {
            image0 = new Image((new FileInputStream("src/TicTacToe/caro_0-01.png")), 200, 200, false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Image image1;

    static {
        try {
            image1 = new Image((new FileInputStream("src/TicTacToe/caro_1-01.png")), 200, 200, false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Image image2;

    static {
        try {
            image2 = new Image((new FileInputStream("src/TicTacToe/caro_2-01.png")), 200, 200, false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
