/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.Random;

public class JavaFXApplication1 extends Application {
    private static final int MAZE_SIZE = 20;
    private static final int CELL_SIZE = 20;

    private boolean[][] visited;
    private Pane mazePane;
    private Rectangle explorer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        visited = new boolean[MAZE_SIZE][MAZE_SIZE];
        mazePane = new Pane();

        generateMaze(0, 0);

        Scene scene = new Scene(mazePane, MAZE_SIZE * CELL_SIZE, MAZE_SIZE * CELL_SIZE);
        primaryStage.setTitle("Random Maze Explorer");
        primaryStage.setScene(scene);

        initializeExplorer();

        scene.setOnKeyPressed(event -> moveExplorer(event.getCode()));
        explorer.requestFocus(); // Establecer el foco en el explorador

        primaryStage.show();
    }

    private void generateMaze(int row, int col) {
        if (row < 0 || row >= MAZE_SIZE || col < 0 || col >= MAZE_SIZE || visited[row][col])
            return;

        visited[row][col] = true;
        mazePane.getChildren().add(new Rectangle(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE, Color.BLACK));

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        shuffleDirections(directions);

        for (int[] dir : directions) {
            int newRow = row + dir[0] * 2;
            int newCol = col + dir[1] * 2;

            if (newRow >= 0 && newRow < MAZE_SIZE && newCol >= 0 && newCol < MAZE_SIZE && !visited[newRow][newCol]) {
                int wallRow = row + dir[0];
                int wallCol = col + dir[1];
                mazePane.getChildren().add(new Rectangle(wallCol * CELL_SIZE, wallRow * CELL_SIZE, CELL_SIZE, CELL_SIZE, Color.BLACK));

                generateMaze(newRow, newCol);
            }
        }
    }

    private void shuffleDirections(int[][] directions) {
        Random rand = new Random();
        for (int i = directions.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int[] temp = directions[index];
            directions[index] = directions[i];
            directions[i] = temp;
        }
    }

    private void initializeExplorer() {
        explorer = new Rectangle(CELL_SIZE / 2, CELL_SIZE / 2, CELL_SIZE / 2, CELL_SIZE / 2);
        explorer.setFill(Color.BLUE);
        mazePane.getChildren().add(explorer);
    }

    private void moveExplorer(KeyCode keyCode) {
        int row = (int) (explorer.getY() / CELL_SIZE);
        int col = (int) (explorer.getX() / CELL_SIZE);

        switch (keyCode) {
            case UP:
                row--;
                break;
            case DOWN:
                row++;
                break;
            case LEFT:
                col--;
                break;
            case RIGHT:
                col++;
                break;
            default:
                return;
        }

        if (row >= 0 && row < MAZE_SIZE && col >= 0 && col < MAZE_SIZE && !visited[row][col]) {
            explorer.setX(col * CELL_SIZE + CELL_SIZE / 4);
            explorer.setY(row * CELL_SIZE + CELL_SIZE / 4);
            visited[row][col] = true;
        }
    }
}




