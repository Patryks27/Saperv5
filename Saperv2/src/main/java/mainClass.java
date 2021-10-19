import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Random;
///sdfsadfsadfsdf
public class mainClass extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Font font = Font.font("Arial", FontWeight.BOLD, 50);
    Font fonntButton = Font.font("Arial", FontWeight.BOLD, 15);
    Font fontPoint = Font.font("Arial", FontWeight.BOLD, 20);

    private int sizeOfButton = 40;
    private int elemnts = 16;
    private int bombs = 50;
    private int marginLeftRight = 10;
    private int heightOfMenu = 60;
    //Window size
    private int height = elemnts * sizeOfButton + heightOfMenu + marginLeftRight;
    private int width = (elemnts * sizeOfButton) + (marginLeftRight * 2);
    private int bombsPoints;

    Label label = new Label();
    int[][] board = new int[elemnts][elemnts];
    Button[][] button;

    public void RestartGame() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = 0;
            }
        }

        Random random = new Random();
        int licznik = 0;
        // draw bombs
        while (licznik < bombs) {
            int randomFirst = random.nextInt(elemnts);
            int randmNext = random.nextInt(elemnts);
            if (board[randomFirst][randmNext] == 0) {
                licznik++;
                board[randomFirst][randmNext] = -1;
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == -1) {
                    if (i > 0 && j > 0) {
                        if (board[i - 1][j - 1] != -1) {
                            board[i - 1][j - 1]++;
                        }
                    }
                    if (i > 0) {
                        if (board[i - 1][j] != -1) {
                            board[i - 1][j]++;
                        }
                    }
                    if (i > 0 && j < board.length - 1) {
                        if (board[i - 1][j + 1] != -1) {
                            board[i - 1][j + 1]++;
                        }
                    }
                    if (j < board.length - 1) {
                        if (board[i][j + 1] != -1) {
                            board[i][j + 1]++;
                        }
                    }
                    if (i < board.length - 1 && j < board.length - 1) {
                        if (board[i + 1][j + 1] != -1) {
                            board[i + 1][j + 1]++;
                        }
                    }
                    if (i < board.length - 1) {
                        if (board[i + 1][j] != -1) {
                            board[i + 1][j]++;
                        }
                    }
                    if (i < board.length - 1 && j > 0) {
                        if (board[i + 1][j - 1] != -1) {
                            board[i + 1][j - 1]++;
                        }
                    }
                    if (j > 0) {
                        if (board[i][j - 1] != -1) {
                            board[i][j - 1]++;
                        }
                    }

                }

            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void gameOver() {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int valeBoard = board[i][j];
                button[i][j].setDisable(true);
                if (valeBoard != -1 && valeBoard != -2) {
                    if (valeBoard != 0 && valeBoard != -10) {
                        button[i][j].setGraphic(null);
                        button[i][j].setText(Integer.toString(valeBoard));
                    }
                } else {
                    ImageView imageView = new ImageView("bombSaper.png");
                    imageView.setFitWidth(sizeOfButton - 10);
                    imageView.setPreserveRatio(true);
                    button[i][j].setGraphic(imageView);
                }
            }
        }
    }

    public boolean checkWin() {
        int licznik = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == -2) {
                    licznik++;
                }
            }
        }
        if (licznik == bombs) {
            return true;
        }
        return false;
    }

    public void showNearBlank(int i, int j) {
        button[i][j].setDisable(true);
        if (board[i][j] == 0 || board[i][j] == -10) {
            if (i > 0) {
                if (board[i - 1][j] == 0) {
                    board[i - 1][j] = -10;
                    showNearBlank(i - 1, j);
                } else if (board[i - 1][j] != -10 && !button[i - 1][j].getStyleClass().contains("exposed-number")) {
                    button[i - 1][j].setText(Integer.toString(board[i - 1][j]));
                    button[i - 1][j].getStyleClass().add("exposed-number");
                }
            }
            if (j < board.length - 1) {
                if (board[i][j + 1] == 0) {
                    board[i][j + 1] = -10;
                    showNearBlank(i, j + 1);
                } else if (board[i][j + 1] != -10 && !button[i][j + 1].getStyleClass().contains("exposed-number")) {
                    button[i][j + 1].setText(Integer.toString(board[i][j + 1]));
                    button[i][j + 1].getStyleClass().add("exposed-number");
                }
            }
            if (i < board.length - 1) {
                if (board[i + 1][j] == 0) {
                    board[i + 1][j] = -10;
                    showNearBlank(i + 1, j);
                } else if (board[i + 1][j] != -10 && !button[i + 1][j].getStyleClass().contains("exposed-number")) {
                    button[i + 1][j].setText(Integer.toString(board[i + 1][j]));
                    button[i + 1][j].getStyleClass().add("exposed-number");
                }
            }
            if (j > 0) {
                if (board[i][j - 1] == 0) {
                    board[i][j - 1] = -10;
                    showNearBlank(i, j - 1);
                } else if (board[i][j - 1] != -10 && !button[i][j - 1].getStyleClass().contains("exposed-number")) {
                    button[i][j - 1].setText(Integer.toString(board[i][j - 1]));
                    button[i][j - 1].getStyleClass().add("exposed-number");
                }
            }

            if (i > 0 && j > 0) {
                if (board[i - 1][j - 1] == 0) {
                    board[i - 1][j - 1] = -10;
                    showNearBlank(i - 1, j - 1);
                } else if (board[i - 1][j - 1] != -10 && !button[i - 1][j - 1].getStyleClass().contains("exposed-number")) {
                    button[i - 1][j - 1].setText(Integer.toString(board[i - 1][j - 1]));
                    button[i - 1][j - 1].getStyleClass().add("exposed-number");
                }
            }
            if (i > 0 && j < board.length - 1) {
                if (board[i - 1][j + 1] == 0) {
                    board[i - 1][j + 1] = -10;
                    showNearBlank(i - 1, j + 1);
                } else if (board[i - 1][j + 1] != -10 && !button[i - 1][j + 1].getStyleClass().contains("exposed-number")) {
                    button[i - 1][j + 1].setText(Integer.toString(board[i - 1][j + 1]));
                    button[i - 1][j + 1].getStyleClass().add("exposed-number");
                }
            }
            if (i < board.length - 1 && j > 0) {
                if (board[i + 1][j - 1] == 0) {
                    board[i + 1][j - 1] = -10;
                    showNearBlank(i + 1, j - 1);
                } else if (board[i + 1][j - 1] != -10 && !button[i + 1][j - 1].getStyleClass().contains("exposed-number")) {
                    button[i + 1][j - 1].setText(Integer.toString(board[i + 1][j - 1]));
                    button[i + 1][j - 1].getStyleClass().add("exposed-number");
                }
            }
            if (i < board.length - 1 && j < board.length - 1) {
                if (board[i + 1][j + 1] == 0) {
                    board[i + 1][j + 1] = -10;
                    showNearBlank(i + 1, j + 1);
                } else if (board[i + 1][j + 1] != -10 && !button[i + 1][j + 1].getStyleClass().contains("exposed-number")) {
                    button[i + 1][j + 1].setText(Integer.toString(board[i + 1][j + 1]));
                    button[i + 1][j + 1].getStyleClass().add("exposed-number");
                }
            }


        }
    }
    public boolean checkCountFlags(int i, int j){
        int licznik = 0;
        if (i > 0) {
            if (button[i-1][j].getGraphic() != null) {
                licznik++;
            }
        }
        if (j < board.length - 1) {
            if (button[i][j+1].getGraphic() != null) {
                licznik++;
            }
        }
        if (i < board.length - 1) {
            if (button[i+1][j].getGraphic() != null) {
                licznik++;
            }
        }
        if (j > 0) {
            if (button[i][j-1].getGraphic() != null) {
                licznik++;
            }
        }

        if (i > 0 && j > 0) {
            if (button[i-1][j-1].getGraphic() != null) {
                licznik++;
            }
        }
        if (i > 0 && j < board.length - 1) {
            if (button[i-1][j+1].getGraphic() != null) {
                licznik++;
            }
        }
        if (i < board.length - 1 && j > 0) {
            if (button[i+1][j-1].getGraphic() != null) {
                licznik++;
            }
        }
        if (i < board.length - 1 && j < board.length - 1) {
            if (button[i+1][j+1].getGraphic() != null) {
                licznik++;
            }
        }
        System.out.println("Licznik: " + licznik);
        if (board[i][j] == licznik){
            return true;
        }
        return false;

    }

    public boolean discoverNumber(int i, int j) {

        if(checkCountFlags(i,j)){
            if (i > 0) {
                if (board[i - 1][j] > 0 && !button[i-1][j].getStyleClass().contains("exposed-number")) {
                    button[i - 1][j].setText(Integer.toString(board[i - 1][j]));
                    button[i - 1][j].getStyleClass().add("exposed-number");
                } else if (board[i-1][j] == 0) {
                    showNearBlank(i-1, j);
                }else if(board[i-1][j] == -1){
                    return false;
                }
            }
        if (j < board.length - 1) {
            if (board[i][j+1] > 0 && !button[i][j+1].getStyleClass().contains("exposed-number")) {
                button[i][j+1].setText(Integer.toString(board[i][j+1]));
                button[i][j+1].getStyleClass().add("exposed-number");
            } else if (board[i][j+1] == 0) {
                showNearBlank(i, j+1);
            } else if(board[i][j+1] == -1){
                return false;
            }
        }
        if (i < board.length - 1) {
            if (board[i+1][j] > 0 && !button[i+1][j].getStyleClass().contains("exposed-number")) {
                button[i+1][j].setText(Integer.toString(board[i+1][j]));
                button[i+1][j].getStyleClass().add("exposed-number");
            } else if (board[i+1][j] == 0) {
                showNearBlank(i+1, j);
            }else if(board[i+1][j] == -1){
                return false;
            }
        }
        if (j > 0) {
            if (board[i][j-1] > 0 && !button[i][j-1].getStyleClass().contains("exposed-number")) {
                button[i][j-1].setText(Integer.toString(board[i][j-1]));
                button[i][j-1].getStyleClass().add("exposed-number");
            } else if (board[i][j-1] == 0) {
                showNearBlank(i, j-1);
            }else if(board[i][j-1] == -1){
                return false;
            }
        }

        if (i > 0 && j > 0) {
            if (board[i-1][j-1] > 0 && !button[i-1][j-1].getStyleClass().contains("exposed-number")) {
                button[i-1][j-1].setText(Integer.toString(board[i-1][j-1]));
                button[i-1][j-1].getStyleClass().add("exposed-number");
            } else if (board[i-1][j-1] == 0) {
                showNearBlank(i-1, j-1);
            }else if(board[i-1][j-1] == -1){
                return false;
            }
        }
        if (i > 0 && j < board.length - 1) {
            if (board[i-1][j+1] > 0 && !button[i-1][j+1].getStyleClass().contains("exposed-number")) {
                button[i-1][j+1].setText(Integer.toString(board[i-1][j+1]));
                button[i-1][j+1].getStyleClass().add("exposed-number");
            } else if (board[i-1][j+1] == 0) {
                showNearBlank(i-1, j+1);
            }else if(board[i-1][j+1] == -1){
                return false;
            }
        }
        if (i < board.length - 1 && j > 0) {
            if (board[i+1][j-1] > 0 && !button[i+1][j-1].getStyleClass().contains("exposed-number")) {
                button[i+1][j-1].setText(Integer.toString(board[i+1][j-1]));
                button[i+1][j-1].getStyleClass().add("exposed-number");
            } else if (board[i+1][j-1] == 0) {
                showNearBlank(i+1, j-1);
            }else if(board[i+1][j-1] == -1){
                return false;
            }
        }
        if (i < board.length - 1 && j < board.length - 1) {
            if (board[i+1][j+1] > 0 && !button[i+1][j+1].getStyleClass().contains("exposed-number")) {
                button[i+1][j+1].setText(Integer.toString(board[i+1][j+1]));
                button[i+1][j+1].getStyleClass().add("exposed-number");
            } else if (board[i+1][j+1] == 0) {
                showNearBlank(i+1, j+1);
            }else if(board[i+1][j+1] == -1){
                return false;
            }
        }
        }
        return true;
    }

    public void reloadWindow(int bombs, int elemnts) {
        this.bombs = bombs;
        this.elemnts = elemnts;
        height = elemnts * sizeOfButton + heightOfMenu + marginLeftRight;
        width = (elemnts * sizeOfButton) + (marginLeftRight * 2);

        label = new Label();
        board = new int[elemnts][elemnts];

    }

    public Scene buildScene(Stage primaryStage) {
        RestartGame();
        button = new Button[elemnts][elemnts];
        bombsPoints = bombs;


        double x = marginLeftRight; //5
        double y = heightOfMenu;
        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button.length; j++) {
                button[i][j] = new Button();
                button[i][j].setMinSize(sizeOfButton, sizeOfButton);
                button[i][j].setMaxSize(sizeOfButton,sizeOfButton);
                button[i][j].setLayoutX(x);
                button[i][j].setLayoutY(y);
                button[i][j].setFont(fonntButton);
                button[i][j].getStyleClass().add("play-buttons");

                switch (board[i][j]) {
                    case 1:
                        button[i][j].setTextFill(Color.BLUE);
                        break;
                    case 2:
                        button[i][j].setTextFill(Color.GREEN);
                        break;
                    case 3:
                        button[i][j].setTextFill(Color.web("#ff3300"));
                        break;
                    case 4:
                        button[i][j].setTextFill(Color.web("#000033"));
                        break;
                    case 5:
                        button[i][j].setTextFill(Color.web("#FF0000"));
                        break;
                    case 6:
                        button[i][j].setTextFill(Color.web("#cc0000"));
                        break;
                    case 7:
                        button[i][j].setTextFill(Color.web("#990000"));
                        break;
                    case 8:
                        button[i][j].setTextFill(Color.web("#660000"));
                        break;
                }
                x += sizeOfButton;
            }
            x = marginLeftRight;
            y += sizeOfButton;
        }

        Group group = new Group();

        ///////////////menu
        int heightElemnts = 20;
        int widthElements = 100;

        Rectangle rectangleMenu =
                new Rectangle(marginLeftRight, marginLeftRight, width - (marginLeftRight * 2), heightOfMenu - marginLeftRight * 2);
        rectangleMenu.setFill(Color.GRAY);
        rectangleMenu.setEffect(new Lighting());


        PoziomyTrudnosci[] tablicaPoziomy = PoziomyTrudnosci.values();
        ChoiceBox<String> choiceBoxPoziomyMenu = new <String>ChoiceBox();

        for (int i = 0; i < tablicaPoziomy.length; i++) {
            choiceBoxPoziomyMenu.getItems().add(tablicaPoziomy[i].toString());
        }
        //choiceBoxPoziomyMenu.setMinHeight(40);

        choiceBoxPoziomyMenu.setPrefHeight(heightElemnts);
        choiceBoxPoziomyMenu.setMaxWidth(widthElements);
        choiceBoxPoziomyMenu.setLayoutX(marginLeftRight * 2);
        choiceBoxPoziomyMenu.setLayoutY(((heightOfMenu - heightElemnts) / 2) - 1);

        Button buttonZmienPoziom = new Button("Zmień");
        buttonZmienPoziom.setPrefHeight(heightElemnts);
        buttonZmienPoziom.setLayoutX(marginLeftRight * 2 + widthElements);
        buttonZmienPoziom.setLayoutY(((heightOfMenu - heightElemnts) / 2) - 1);
        buttonZmienPoziom.setPrefWidth(50);
        buttonZmienPoziom.setId("buttonZmienPoziom");


        buttonZmienPoziom.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (choiceBoxPoziomyMenu.getValue() != null) {
                    for (int i = 0; i < tablicaPoziomy.length; i++) {
                        if (choiceBoxPoziomyMenu.getValue().equals(tablicaPoziomy[i].toString())) {
                            reloadWindow(tablicaPoziomy[i].getBombs(), tablicaPoziomy[i].getElements());
                            Scene scene = buildScene(primaryStage);
                            primaryStage.setScene(scene);
                        }
                    }
                }


                //primaryStage.setHeight(scene.getHeight());
                //primaryStage.setWidth(scene.getWidth());
            }
        });

        Label labelBombs = new Label("Bombs: " + Integer.toString(bombsPoints));
        labelBombs.setLayoutX(marginLeftRight * 2 + widthElements + buttonZmienPoziom.getPrefWidth() + 20);
        labelBombs.setLayoutY(((heightOfMenu - heightElemnts) / 2) - 1);
        labelBombs.setFont(fontPoint);
        labelBombs.setTextFill(Color.WHITE);

        group.getChildren().add(rectangleMenu);
        group.getChildren().add(choiceBoxPoziomyMenu);
        group.getChildren().add(buttonZmienPoziom);
        group.getChildren().add(labelBombs);
//        Rectangle rectangleWinLose =
//                new Rectangle(width / 2 - (3.3 * sizeOfButton) -20,height / 2 - font.getSize()-20,320, font.getSize()+40);
//        rectangleWinLose.setFill(Color.GRAY);

        Rectangle rectangle =
                new Rectangle(marginLeftRight - 2, heightOfMenu - 2, sizeOfButton * elemnts + 4, sizeOfButton * elemnts + 4);
        rectangle.setFill(Color.GRAY);
        group.getChildren().add(rectangle);


        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button.length; j++) {
                int i_ = i;
                int j_ = j;
                button[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton buttonClick = event.getButton();

                        //Lewy przcisk, odkrycie wartości
                        if (buttonClick == MouseButton.PRIMARY && button[i_][j_].getGraphic() == null) {

                            int valeBoard = board[i_][j_];
                            if (valeBoard != -1) {
                                if (valeBoard != 0) {
                                    if (!button[i_][j_].getStyleClass().contains("exposed-number")) {
                                        button[i_][j_].setText(Integer.toString(valeBoard));
                                        button[i_][j_].getStyleClass().add("exposed-number");
                                    } else {
                                        if(discoverNumber(i_, j_)==false ){
                                            gameOver();
                                            group.getChildren().remove(label);
                                            label = new Label("Game Over");
                                            label.setFont(font);
                                            label.setTextFill(Color.RED);
                                            label.setLayoutX(width / 2 - (3.3 * sizeOfButton));
                                            label.setLayoutY(height / 2 - font.getSize());


                                            //group.getChildren().add(rectangleWinLose);
                                            group.getChildren().add(label);
                                            button[i_][j_].setDisable(true);
                                        }
                                    }
                                } else {
                                    showNearBlank(i_, j_);
                                }
                            } else {
                                gameOver();
                                group.getChildren().remove(label);
                                label = new Label("Game Over");
                                label.setFont(font);
                                label.setTextFill(Color.RED);
                                label.setLayoutX(width / 2 - (3.3 * sizeOfButton));
                                label.setLayoutY(height / 2 - font.getSize());


                                //group.getChildren().add(rectangleWinLose);
                                group.getChildren().add(label);
                                button[i_][j_].setDisable(true);
                            }


                        } else if (buttonClick == MouseButton.SECONDARY) { //prawy przycisk, flaga

                            if (button[i_][j_].getGraphic() != null) {
                                button[i_][j_].setGraphic(null);
                                if (board[i_][j_] == -2) {
                                    board[i_][j_] = -1;
                                }
                                bombsPoints += 1;
                                // change points
                                labelBombs.setText("Bombs: " + Integer.toString(bombsPoints));
                            } else if(!button[i_][j_].getStyleClass().contains("exposed-number") ){
                                Image image = new Image("flagaSaper.png");
                                ImageView imageView = new ImageView(image);
                                imageView.setFitWidth(sizeOfButton - 15);
                                imageView.setPreserveRatio(true);
                                button[i_][j_].setGraphic(imageView);
                                if (board[i_][j_] == -1) {
                                    board[i_][j_] = -2;
                                }
                                bombsPoints -= 1;
                                if (checkWin() == true) {
                                    label = new Label("Wygrałeś!!!");
                                    label.setFont(font);
                                    label.setTextFill(Color.GREEN);
                                    label.setLayoutX(width / 2 - (3.3 * sizeOfButton));
                                    label.setLayoutY(height / 2 - font.getSize());

                                    //group.getChildren().add(rectangleWinLose);
                                    group.getChildren().add(label);
                                }
                                // change points
                                labelBombs.setText("Bombs: " + Integer.toString(bombsPoints));
                            }

                        }
                    }
                });
                group.getChildren().add(button[i][j]);
            }
        }

        Color colorBackGround = Color.web("#C0C0C0");
        Scene scene = new Scene(group, width, height, colorBackGround);
        scene.getStylesheets().add("cssJavaSaper.css");
        return scene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setResizable(false);

        Scene scene = buildScene(primaryStage);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Saper");
        primaryStage.show();


    }
}
