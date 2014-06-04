package pong;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pong extends Application {

    public static Circle circle;
    public static Pane canvas;

    @Override
    public void start(final Stage primaryStage) {

        canvas = new Pane();
        final Scene scene = new Scene(canvas, 400, 300);
        
        int width = 400; int height = 250;
        
        final Label gameArea = new Label();
        gameArea.setMinHeight(height);
        gameArea.setMinWidth(width);
        gameArea.setStyle("-fx-background-color: green;");
        
        final Label points1 = new Label("0");
        final Label points2 = new Label("0");
        
        points1.setLayoutX(10);
        points1.setLayoutY(250);
        points1.setStyle("-fx-font-size: 36px;");
        
        points2.setLayoutX(370);
        points2.setLayoutY(250);
        points2.setStyle("-fx-font-size: 36px;");
        
        final Label paddle1 = new Label();
        final Label paddle2 = new Label();
        
        paddle1.setMinHeight(50);
        paddle1.setMinWidth(10);
        paddle1.setLayoutX(10);
        paddle1.setLayoutY(90);
        
        paddle2.setMinHeight(50);
        paddle2.setMinWidth(10);
        paddle2.setLayoutX(380);
        paddle2.setLayoutY(90);
        
        paddle1.setStyle("-fx-background-color: black;");
        paddle2.setStyle("-fx-background-color: black;");
        
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        circle = new Circle(10, Color.BLUE);
        circle.relocate(100, 100);

        canvas.getChildren().addAll(gameArea, circle, paddle1, paddle2, points1, points2);
        
        final Timeline loop = new Timeline(new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {

            double deltaX = -5;
            double deltaY = 5;

            @Override
            public void handle(final ActionEvent t) {
                circle.setLayoutX(circle.getLayoutX() + deltaX);
                circle.setLayoutY(circle.getLayoutY() + deltaY);

                final Bounds bounds = gameArea.getBoundsInLocal();
                
                double minX = paddle1.getLayoutX() + paddle1.getMinWidth();
                double maxX = paddle2.getLayoutX();
                double y1 = paddle1.getLayoutY() + paddle1.getMinHeight()/2;
                double y2 = paddle2.getLayoutY() + paddle2.getMinHeight()/2;
                
                final boolean atRightBorder = (circle.getLayoutX() == (maxX - circle.getRadius())) && ((y2+25) >= circle.getLayoutY() && (y2-25) <= circle.getLayoutY());
                final boolean atLeftBorder = (circle.getLayoutX() == (minX + circle.getRadius())) && ((y1+25) >= circle.getLayoutY() && (y1-25) <= circle.getLayoutY());
                final boolean atBottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
                final boolean atTopBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());

                if (atLeftBorder) {
                    deltaX *= -1;
                } else if (atRightBorder) {
                    deltaX *= -1;
                }
                
                if (circle.getLayoutX()+circle.getRadius()*2 < bounds.getMinX()) {
                    circle.setLayoutX(200);
                    deltaX *= -1;
                    
                    Integer score2 = Integer.parseInt(points2.getText())+1;
                    points2.setText(score2.toString());
                } 
                
                if (circle.getLayoutX()-circle.getRadius()*2 > bounds.getMaxX()) {
                    circle.setLayoutX(200);
                    deltaX *= -1;
                    
                    Integer score1 = Integer.parseInt(points1.getText())+1;
                    points1.setText(score1.toString());
                }

                
                if (atBottomBorder || atTopBorder) {
                    deltaY *= -1;
                }
            }
        }));

        
        final AnimationTimer movePaddle1Up = new AnimationTimer() {
          @Override
          public void handle(long timestamp) {
              if (gameArea.getBoundsInLocal().getMinY()/2 < paddle1.getLayoutY())
                paddle1.setLayoutY(paddle1.getLayoutY()-5);
              
          }
          
        };

        final AnimationTimer movePaddle1Down = new AnimationTimer() {
          @Override
          public void handle(long timestamp) {
              if (height-50 > paddle1.getLayoutY())
                paddle1.setLayoutY(paddle1.getLayoutY()+5);
          }
        };
        
        final AnimationTimer movePaddle2Up = new AnimationTimer() {
          @Override
          public void handle(long timestamp) {
              if (gameArea.getBoundsInLocal().getMinY()/2 < paddle2.getLayoutY())
                paddle2.setLayoutY(paddle2.getLayoutY()-5);
          }
        };

        final AnimationTimer movePaddle2Down = new AnimationTimer() {
          @Override
          public void handle(long timestamp) {
              if (height-50 > paddle2.getLayoutY())
                paddle2.setLayoutY(paddle2.getLayoutY()+5);
          }
        };


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            if (event.getCode()==KeyCode.W) { // don't use toString here!!!
              movePaddle1Up.start();
            } else if (event.getCode() == KeyCode.S) {
              movePaddle1Down.start();
            } else if (event.getCode() == KeyCode.UP) {
              movePaddle2Up.start();  
            } else if (event.getCode() == KeyCode.DOWN) {
              movePaddle2Down.start();  
            }
          }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.W) {
              movePaddle1Up.stop();
            } else if (event.getCode() == KeyCode.S) {
              movePaddle1Down.stop();  
            } else if (event.getCode() == KeyCode.UP) {
              movePaddle2Up.stop();    
            } else if (event.getCode() == KeyCode.DOWN) {
              movePaddle2Down.stop();  
            }
          }
        });
        
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}