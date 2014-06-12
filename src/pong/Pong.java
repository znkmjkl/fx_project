package pong;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import static javafx.scene.media.AudioClip.INDEFINITE;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pong extends Application {

    public static Circle circle;
    public static Pane canvas;
    private static final AudioClip MUSIC = new AudioClip(Pong.class.getResource("/mp3.mp3").toString());
    private static final AudioClip CLICK = new AudioClip(Pong.class.getResource("/click.mp3").toString());
    private static final AudioClip wallBounce = new AudioClip(Pong.class.getResource("/wallBounce.mp3").toString());    
    private static final AudioClip ballBounce = new AudioClip(Pong.class.getResource("/ballBounce.mp3").toString());
    private static final AudioClip gameMUSIC = new AudioClip(Pong.class.getResource("/gameMP3.wav").toString());
    private static final AudioClip applause = new AudioClip(Pong.class.getResource("/applause.mp3").toString());
    public boolean musicON = true;
    public boolean musicStatus = true;
    public String mode = "easy";
    public int speed;   
    public boolean GameStatus = true;
    public Timeline loop;
    public boolean gameOver = false;
    public int winScore = 10;
    
    
    /* 
        GRA Z KOMPUTEREM 
                        */
//    public void gameComp(final Stage primaryStage){
//        canvas = new Pane();
//        if (musicON){
//            Pong.gameMUSIC.setCycleCount(INDEFINITE);           
//            Pong.gameMUSIC.play();
//        }
//        
//                
//        final int width = 600; int height = 360;
//        final int scorePanelHeight = 80;
//        
//        final Scene scene = new Scene(canvas, width, height+scorePanelHeight);
//        
//        final int paddleHeight = 100;
//        final int paddleWidth = 20;
//        
//        final Label gameArea = new Label();
//        gameArea.setMinHeight(height);
//        gameArea.setMinWidth(width);
//        gameArea.setStyle("-fx-background-image: url(\"/ppBoard.png\");");
//        
//        final Text rematchText = new Text("REMATCH?(Y/n)");
//        rematchText.setLayoutX(width/2-120);
//        rematchText.setLayoutY(height/2-50);
//        rematchText.setFill(Color.CORAL);
//        rematchText.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
//        rematchText.setVisible(false);
//        
//        final Text points1 = new Text("0");
//        final Text points2 = new Text("0");
//        
//        final Label info = new Label("Press SPACE to start");
//        
//        double fontSize = scorePanelHeight/2.2;
//        
//        points1.setLayoutX(0.06*width+(fontSize*0.25));       
//        points1.setLayoutY(height+scorePanelHeight*0.65);
//        points1.setStyle("-fx-font-size: " + fontSize + "px;");        
//        points1.setBoundsType(TextBoundsType.VISUAL);
//        
//        Circle circle1 = new Circle(25, Color.AQUA);
//        circle1.setLayoutX(0.06*width+(fontSize*0.5));
//        circle1.setLayoutY(height+scorePanelHeight*0.5);
//        
//        points2.setLayoutX(width-(0.1*width+(fontSize*0.75)));        
//        points2.setLayoutY(height+scorePanelHeight*0.65);
//        points2.setStyle("-fx-font-size: " + fontSize + "px;");
//        points2.setBoundsType(TextBoundsType.VISUAL);
//        
//        Circle circle2 = new Circle(25, Color.AQUA);
//        circle2.setLayoutX(width-(0.1*width+(fontSize*0.5)));
//        circle2.setLayoutY(height+scorePanelHeight*0.5);
//        
//        
//        
//        info.setLayoutX(0.3*width+(fontSize*0.2));
//        info.setLayoutY(height+scorePanelHeight*0.3);
//        info.setStyle("-fx-font-size: " + fontSize/1.5 + "px;");
//        
//        final Label paddle1 = new Label();
//        final Label paddle2 = new Label();
//        
//        paddle1.setMinHeight(paddleHeight);
//        paddle1.setMinWidth(paddleWidth);
//        paddle1.setLayoutX(10);
//        paddle1.setLayoutY(gameArea.getMinHeight()/2-(paddle1.getMinHeight()/2));
//
//        
//        paddle2.setMinHeight(paddleHeight);
//        paddle2.setMinWidth(paddleWidth);
//        paddle2.setLayoutX(gameArea.getMinWidth()-(paddle2.getMinWidth()+10));
//        paddle2.setLayoutY(gameArea.getMinHeight()/2-(paddle2.getMinHeight()/2));
//        
//        paddle1.setStyle("-fx-background-color: black;");
//        paddle2.setStyle("-fx-background-color: black;");
//        
//        primaryStage.setTitle("Game");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//        circle = new Circle(10, Color.BLUE);
//        circle.relocate(gameArea.getMinWidth()/2-circle.getRadius(), gameArea.getMinHeight()/2-circle.getRadius());
//
//        canvas.getChildren().addAll(gameArea, circle, paddle1, rematchText, paddle2, circle1, points1, circle2, points2, info);
//        
//        loop = new Timeline(new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {
//            
//            double deltaX = 5;
//            double deltaY = 5;
//            
//
//            @Override
//            public void handle(final ActionEvent t) {
//                circle.setLayoutX(circle.getLayoutX() + deltaX);
//                circle.setLayoutY(circle.getLayoutY() + deltaY);
//
//                final Bounds bounds = gameArea.getBoundsInLocal();
//                
//                double minX = paddle1.getLayoutX() + paddle1.getMinWidth();
//                double maxX = paddle2.getLayoutX();
//                
//                double y1 = paddle1.getLayoutY();
//                double y2 = paddle2.getLayoutY();
//                
//                //zmienne określające, czy piłeczka trafiła w paletkę
//                final boolean atRightBorder = (circle.getLayoutX()+circle.getRadius() == (maxX)) && ((y2) <= circle.getLayoutY() && (y2+paddle1.getMinHeight()) >= circle.getLayoutY());
//                final boolean atLeftBorder = (circle.getLayoutX() == (minX+circle.getRadius())) && ((y1) <= circle.getLayoutY() && (y1+paddle1.getMinHeight()) >= circle.getLayoutY());
//                
//                final boolean atBottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
//                final boolean atTopBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());
//                
//                //Jeśli trafiła w którąś paletkę - zmiana kierunku na przeciwny
//                if (atLeftBorder) {
//                    ballBounce.play();
//                    deltaX *= -1;
//                } else if (atRightBorder) {
//                    ballBounce.play();
//                    deltaX *= -1;
//                }
//                if (computer){
//                    if(mode == "easy"){
//                        speed = 5;
//                    } else if(mode == "medium"){
//                        speed = 10;
//                    } else if(mode == "hard"){                    
//                        speed = 10;
//                    }
//                }
//                if (circle.getLayoutY() > paddle2.getLayoutY() && deltaX > 0){
//                    if (height-100 > paddle2.getLayoutY())
//                    paddle2.setLayoutY(paddle2.getLayoutY() + speed);
//                } else if (circle.getLayoutY() < paddle2.getLayoutY() && deltaX > 0){
//                    if (gameArea.getBoundsInLocal().getMinY()/2 < paddle2.getLayoutY())
//                    paddle2.setLayoutY(paddle2.getLayoutY() - speed);                    
//                } 
//                //Jeśli jest już za którąś z paletek
//                if (circle.getLayoutX() < bounds.getMinX()-circle.getRadius()*2) {
//                    circle.setLayoutX(gameArea.getMinWidth()/2-circle.getRadius());
//                    circle.setLayoutY(gameArea.getMinHeight()/2-circle.getRadius());
//                    
//                    if(Math.random() > 0.5){                        
//                        deltaX *= -1;
//                    }
//                    if(Math.random() > 0.5){
//                        deltaY *= -1;
//                    }
//                    
//                    Integer score2 = Integer.parseInt(points2.getText())+1;
//                    if (score2 >= 10)
//                        points2.setLayoutX(width-(0.1*width+(fontSize*1)));
//                    
//                    points2.setText(score2.toString());
//                    
//                    if(score2 == 10){
//                        loop.pause();
//                        info.setLayoutX(0.3*width+(fontSize*1));
//                        info.setTextFill(Color.FIREBRICK);
//                        info.setText("Computer WON!");
//                        Pong.applause.play();
//                        rematchText.setVisible(true);
//                        gameOver = true;
//                    }
//                    
//                    circle2.setFill(Color.RED);
//                    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
//                    pause.setOnFinished(new EventHandler<ActionEvent> () {
//                        @Override
//                        public void handle(ActionEvent event){
//                            circle2.setFill(Color.AQUA);
//                        }
//                    });
//                    pause.play(); 
//                } else if (circle.getLayoutX()-circle.getRadius() > bounds.getMaxX()) {
//                    circle.setLayoutX(gameArea.getMinWidth()/2-circle.getRadius());
//                    circle.setLayoutY(gameArea.getMinHeight()/2-circle.getRadius());
//                    
//                    if(Math.random() > 0.5){                        
//                        deltaX *= -1;
//                    }                    
//                    if(Math.random() > 0.5){
//                        deltaY *= -1;
//                    }
//                    Integer score1 = Integer.parseInt(points1.getText())+1;
//                    if(score1 >= 10)
//                        points1.setLayoutX(0.06*width+(fontSize*0));
//                    
//                    points1.setText(score1.toString());
//                    
//                    if(score1 == 10){
//                        loop.pause();
//                        info.setLayoutX(0.3*width+(fontSize*1));
//                        info.setTextFill(Color.FIREBRICK);                        
//                        info.setText("Player 1 WON!");
//                        Pong.applause.play();
//                        rematchText.setVisible(true);
//                        gameOver = true;
//                    }
//                    
//                    circle1.setFill(Color.RED);
//                    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
//                    pause.setOnFinished(new EventHandler<ActionEvent> () {
//                        @Override
//                        public void handle(ActionEvent event){
//                            circle1.setFill(Color.AQUA);
//                        }
//                    });
//                    pause.play(); 
//                }
//
//                if (atBottomBorder || atTopBorder) {
//                    Pong.wallBounce.play();
//                    deltaY *= -1;
//                }
//            }
//        }));
//        
//        loop.setCycleCount(Timeline.INDEFINITE);
//        
////        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
////          @Override
////          public void handle(KeyEvent event) {
////            if (event.getCode()==KeyCode.L) {
////                System.out.println("ruszylem");
////              loop.setCycleCount(Timeline.INDEFINITE);
////                 loop.play();
////            }
////          }
////        });
//
//        
//        final AnimationTimer movePaddle1Up = new AnimationTimer() {
//          @Override
//          public void handle(long timestamp) {
//              if (gameArea.getBoundsInLocal().getMinY()/2 < paddle1.getLayoutY())
//                paddle1.setLayoutY(paddle1.getLayoutY()-5);              
//          }          
//        };
//
//        final AnimationTimer movePaddle1Down = new AnimationTimer() {
//          @Override
//          public void handle(long timestamp) {
//              if (height-100 > paddle1.getLayoutY())
//                paddle1.setLayoutY(paddle1.getLayoutY()+5);
//          }
//        };
//        
//        final AnimationTimer movePaddle2Up = new AnimationTimer() {
//          @Override
//          public void handle(long timestamp) {
//              if (gameArea.getBoundsInLocal().getMinY()/2 < paddle2.getLayoutY())
//                paddle2.setLayoutY(paddle2.getLayoutY()-5);
//          }
//        };
//
//        final AnimationTimer movePaddle2Down = new AnimationTimer() {
//          @Override
//          public void handle(long timestamp) {
//              if (height-100 > paddle2.getLayoutY())
//                paddle2.setLayoutY(paddle2.getLayoutY()+5);
//          }
//        };
//
//
//        
//        
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//          @Override
//          public void handle(KeyEvent event) {
//            if (event.getCode()==KeyCode.W) {
//              movePaddle1Up.start();
//            } else if (event.getCode() == KeyCode.S) {
//              movePaddle1Down.start();
//            } else if (event.getCode() == KeyCode.SPACE) {
//                if (loop.getStatus() == Animation.Status.STOPPED || loop.getStatus() == Animation.Status.PAUSED && GameStatus == true && gameOver == false) {                   
//                    loop.play();
//                    info.setText("Press SPACE to pause");
//                } else if (loop.getStatus() == Animation.Status.RUNNING) {
//                    loop.pause();
//                    info.setText("Press SPACE to resume");
//                }
//            } else if (event.getCode() == KeyCode.ESCAPE){
//                loop.pause();
//                info.setLayoutX(0.25*width+(fontSize*0));
//                info.setText("Do you want to quit? (Y/n)");
//                GameStatus = false;
//            } else if (event.getCode() == KeyCode.Y && GameStatus == false){
//                Pong.gameMUSIC.stop();
//                start(primaryStage);
//            } else if (event.getCode() == KeyCode.N && GameStatus == false){
//                GameStatus = true;
//                info.setLayoutX(0.3*width+(fontSize*0.2));
//                info.setText("Press SPACE to resume");
//            } else if (gameOver == true && event.getCode() == KeyCode.Y){
//                rematchText.setVisible(false);
//                points1.setText("0");
//                points2.setText("0");
//                gameOver = false;
//                info.setLayoutX(0.3*width+(fontSize*0.2));
//                paddle1.setLayoutX(10);
//                paddle1.setLayoutY(gameArea.getMinHeight()/2-(paddle1.getMinHeight()/2));
//                paddle2.setLayoutX(gameArea.getMinWidth()-(paddle2.getMinWidth()+10));
//                paddle2.setLayoutY(gameArea.getMinHeight()/2-(paddle2.getMinHeight()/2));
//                info.setTextFill(Color.BLACK);
//                info.setText("Press SPACE to play");
//                loop.pause();
//            } else if (gameOver == true && event.getCode() == KeyCode.N){
//                gameOver = false;
//                Pong.gameMUSIC.stop();
//                start(primaryStage);
//            }
//          }
//        });
//
//        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
//          @Override
//          public void handle(KeyEvent event) {
//            if (event.getCode() == KeyCode.W) {
//              movePaddle1Up.stop();
//            } else if (event.getCode() == KeyCode.S) {
//              movePaddle1Down.stop();  
//            } 
//          }
//        });
//        
//    }
    

    /*
        GRA
            */
    public void game(final Stage primaryStage, final boolean computer){
        canvas = new Pane();
        
        if(musicON){
            Pong.gameMUSIC.setCycleCount(INDEFINITE);           
            Pong.gameMUSIC.play();
        }
        
        
        final int width = 600; int height = 360;
        final int scorePanelHeight = 80;
        
        final Scene scene = new Scene(canvas, width, height+scorePanelHeight);
        
        final int paddleHeight = 100;
        final int paddleWidth = 20;
        
        final Label gameArea = new Label();
        gameArea.setMinHeight(height);
        gameArea.setMinWidth(width);
        gameArea.setStyle("-fx-background-image: url(\"/ppBoard.png\");");
        
        final Text rematchText = new Text("REMATCH?(Y/N)");
        rematchText.setLayoutX(width/2-120);
        rematchText.setLayoutY(height/2-50);
        rematchText.setFill(Color.CORAL);
        rematchText.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        rematchText.setVisible(false);
        
        final Text points1 = new Text("0");
        final Text points2 = new Text("0");
        
        final Label info = new Label("Press SPACE to start");
        
        double fontSize = scorePanelHeight/2.2;
        
        points1.setLayoutX(0.06*width+(fontSize*0.25));       
        points1.setLayoutY(height+scorePanelHeight*0.65);
        points1.setStyle("-fx-font-size: " + fontSize + "px;");        
        points1.setBoundsType(TextBoundsType.VISUAL);
        
        Circle circle1 = new Circle(25, Color.AQUA);
        circle1.setLayoutX(0.06*width+(fontSize*0.5));
        circle1.setLayoutY(height+scorePanelHeight*0.5);
        
        points2.setLayoutX(width-(0.1*width+(fontSize*0.75)));        
        points2.setLayoutY(height+scorePanelHeight*0.65);
        points2.setStyle("-fx-font-size: " + fontSize + "px;");
        points2.setBoundsType(TextBoundsType.VISUAL);
        
        Circle circle2 = new Circle(25, Color.AQUA);
        circle2.setLayoutX(width-(0.1*width+(fontSize*0.5)));
        circle2.setLayoutY(height+scorePanelHeight*0.5);
        
        
        
        info.setLayoutX(0.3*width+(fontSize*0.2));
        info.setLayoutY(height+scorePanelHeight*0.3);
        info.setStyle("-fx-font-size: " + fontSize/1.5 + "px;");
        
        final Label paddle1 = new Label();
        final Label paddle2 = new Label();
        
        paddle1.setMinHeight(paddleHeight);
        paddle1.setMinWidth(paddleWidth);
        paddle1.setLayoutX(10);
        paddle1.setLayoutY(gameArea.getMinHeight()/2-(paddle1.getMinHeight()/2));
        
        paddle2.setMinHeight(paddleHeight);
        paddle2.setMinWidth(paddleWidth);
        paddle2.setLayoutX(gameArea.getMinWidth()-(paddle2.getMinWidth()+10));
        paddle2.setLayoutY(gameArea.getMinHeight()/2-(paddle2.getMinHeight()/2));
        
        paddle1.setStyle("-fx-background-color: black;");
        paddle2.setStyle("-fx-background-color: black;");
        
        primaryStage.setTitle("PING PONG - GAME");
        primaryStage.setScene(scene);
        primaryStage.show();

        circle = new Circle(10, Color.BLUE);
        circle.relocate(gameArea.getMinWidth()/2-circle.getRadius(), gameArea.getMinHeight()/2-circle.getRadius());

        canvas.getChildren().addAll(gameArea, circle, paddle1, rematchText, paddle2, circle1, points1, circle2, points2, info);
        
        loop = new Timeline(new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {

            double deltaX = 5;
            double deltaY = 5;

            @Override
            public void handle(final ActionEvent t) {
                circle.setLayoutX(circle.getLayoutX() + deltaX);
                circle.setLayoutY(circle.getLayoutY() + deltaY);

                final Bounds bounds = gameArea.getBoundsInLocal();
                
                double minX = paddle1.getLayoutX() + paddle1.getMinWidth();
                double maxX = paddle2.getLayoutX();
                
                double y1 = paddle1.getLayoutY();
                double y2 = paddle2.getLayoutY();
                
                //zmienne określające, czy piłeczka trafiła w paletkę
                final boolean atRightBorder = (circle.getLayoutX()+circle.getRadius() == (maxX)) && ((y2) <= circle.getLayoutY() && (y2+paddle1.getMinHeight()) >= circle.getLayoutY());
                final boolean atLeftBorder = (circle.getLayoutX() == (minX+circle.getRadius())) && ((y1) <= circle.getLayoutY() && (y1+paddle1.getMinHeight()) >= circle.getLayoutY());
                
                final boolean atBottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
                final boolean atTopBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());
                
                //Jeśli trafiła w którąś paletkę - zmiana kierunku na przeciwny
                if (atLeftBorder) {
                    ballBounce.play();                    
                    deltaX *= -1;
                    
                } else if (atRightBorder) {
                    ballBounce.play();                    
                    deltaX *= -1;
                }

                //Ustawienia komputera
                
                if (computer){
                    if(mode == "easy"){
                        speed = 5;
                    } else if(mode == "medium"){
                        speed = 10;
                    } else if(mode == "hard"){                    
                        speed = 10;
                    }
                    if (circle.getLayoutY() > paddle2.getLayoutY() && deltaX > 0){
                        if (height-100 > paddle2.getLayoutY())
                            paddle2.setLayoutY(paddle2.getLayoutY() + speed);
                    } else if (circle.getLayoutY() < paddle2.getLayoutY() && deltaX > 0){
                        if (gameArea.getBoundsInLocal().getMinY()/2 < paddle2.getLayoutY())
                            paddle2.setLayoutY(paddle2.getLayoutY() - speed);                    
                    } 
                }
                
                //Jeśli jest już za którąś z paletek
                if (circle.getLayoutX() < bounds.getMinX()-circle.getRadius()*2) {
                    circle.setLayoutX(gameArea.getMinWidth()/2-circle.getRadius());
                    circle.setLayoutY(gameArea.getMinHeight()/2-circle.getRadius());
                    if(Math.random() > 0.5){                        
                        deltaX *= -1;
                    }                    
                    if(Math.random() > 0.5){
                        deltaY *= -1;
                    }
                    
                    Integer score2 = Integer.parseInt(points2.getText())+1;
                    if (score2 >= 10)
                        points2.setLayoutX(width-(0.1*width+(fontSize*1)));
                    points2.setText(score2.toString());
                    
                    if(score2 == winScore){
                        loop.pause();
                        info.setLayoutX(0.3*width+(fontSize*1));
                        info.setTextFill(Color.FIREBRICK);
                        if(computer){
                            info.setText("Computer WIN!");
                        } else {
                            info.setText("Player 2 WIN!");
                        }
                        
                        Pong.applause.play();
                        rematchText.setVisible(true);
                        gameOver = true;
                    }
                    
                    circle2.setFill(Color.RED);
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                    pause.setOnFinished(new EventHandler<ActionEvent> () {
                        @Override
                        public void handle(ActionEvent event){
                            circle2.setFill(Color.AQUA);
                        }
                    });
                    pause.play(); 
                } else if (circle.getLayoutX()-circle.getRadius() > bounds.getMaxX()) {
                    circle.setLayoutX(gameArea.getMinWidth()/2-circle.getRadius());
                    circle.setLayoutY(gameArea.getMinHeight()/2-circle.getRadius());
                    if(Math.random() > 0.5){                        
                        deltaX *= -1;
                    }                    
                    if(Math.random() > 0.5){
                        deltaY *= -1;
                    }
                    
                    Integer score1 = Integer.parseInt(points1.getText())+1;
                    if(score1 >= 10)
                        points1.setLayoutX(0.06*width+(fontSize*0));                    
                    points1.setText(score1.toString());
                   
                    if(score1 == winScore){
                        loop.pause();
                        info.setLayoutX(0.3*width+(fontSize*1));
                        info.setTextFill(Color.FIREBRICK);                        
                        info.setText("Player 1 WON!");
                        Pong.applause.play();
                        rematchText.setVisible(true);
                        gameOver = true;
                    }
                    
                    circle1.setFill(Color.RED);                    
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                    pause.setOnFinished(new EventHandler<ActionEvent> () {
                        @Override
                        public void handle(ActionEvent event){
                            circle1.setFill(Color.AQUA);
                        }
                    });                    
                    pause.play(); 
                }

                if (atBottomBorder || atTopBorder) {
                    Pong.wallBounce.play();
                    deltaY *= -1;
                }
            }
        }));
        
        loop.setCycleCount(Timeline.INDEFINITE);
        
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//          @Override
//          public void handle(KeyEvent event) {
//            if (event.getCode()==KeyCode.L) {
//                System.out.println("ruszylem");
//              loop.setCycleCount(Timeline.INDEFINITE);
//                 loop.play();
//            }
//          }
//        });

        
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
              if (height-100 > paddle1.getLayoutY())
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
              if (height-100 > paddle2.getLayoutY())
                paddle2.setLayoutY(paddle2.getLayoutY()+5);
          }
        };


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            if(!(loop.getStatus() == Animation.Status.PAUSED)){
                if (event.getCode()==KeyCode.W) {
                movePaddle1Up.start();
                } else if (event.getCode() == KeyCode.S) {
                movePaddle1Down.start();
                } else if (event.getCode() == KeyCode.UP) {
                    if(!computer)
                        movePaddle2Up.start();
                } else if (event.getCode() == KeyCode.DOWN) {
                    if(!computer)
                        movePaddle2Down.start();                   
                }
            }
            if (event.getCode() == KeyCode.SPACE) {
                if (loop.getStatus() == Animation.Status.STOPPED || loop.getStatus() == Animation.Status.PAUSED && GameStatus == true && gameOver == false) {
                    loop.play();
                    info.setText("Press SPACE to pause");
                } else if (loop.getStatus() == Animation.Status.RUNNING) {
                    loop.pause();
                    info.setText("Press SPACE to resume");
                }
            } else if (event.getCode() == KeyCode.ESCAPE){
                if(!gameOver){
                    loop.pause();
                    info.setLayoutX(0.25*width+(fontSize*0));
                    info.setText("Do you want to quit? (Y/N)");
                    GameStatus = false;
                }                
            } else if (event.getCode() == KeyCode.Y && GameStatus == false){
                Pong.gameMUSIC.stop();
                start(primaryStage);
            } else if (event.getCode() == KeyCode.N && GameStatus == false){
                GameStatus = true;
                info.setLayoutX(0.3*width+(fontSize*0.2));
                info.setText("Press SPACE to resume");
            } else if (gameOver == true && event.getCode() == KeyCode.Y){
                rematchText.setVisible(false);
                points1.setText("0");
                points2.setText("0");
                gameOver = false;
                info.setLayoutX(0.3*width+(fontSize*0.2));
                paddle1.setLayoutX(10);
                points1.setLayoutX(0.06*width+(fontSize*0.25));       
                points2.setLayoutX(width-(0.1*width+(fontSize*0.75)));        
                paddle1.setLayoutY(gameArea.getMinHeight()/2-(paddle1.getMinHeight()/2));
                paddle2.setLayoutX(gameArea.getMinWidth()-(paddle2.getMinWidth()+10));
                paddle2.setLayoutY(gameArea.getMinHeight()/2-(paddle2.getMinHeight()/2));
                info.setTextFill(Color.BLACK);
                info.setText("Press SPACE to play");
                loop.pause();
            } else if (gameOver == true && event.getCode() == KeyCode.N){
                gameOver = false;
                Pong.gameMUSIC.stop();
                start(primaryStage);
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
    }
    
    
    
    
    
    //Wybieranie przeciwnika
    public void selectMode(final Stage primaryStage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);
        
        Button playerBtn = new Button("HUMAN VS HUMAN");
        playerBtn.setMinSize(200, 40);
        grid.add(playerBtn,0,0);
        
        Button computerBtn = new Button("HUMAN VS COMPUTER");
        computerBtn.setMinSize(200, 40);
        grid.add(computerBtn,0,1);
        
        Button backBtn = new Button("BACK");
        backBtn.setMinSize(200, 40);
        grid.add(backBtn, 0,4);       
        
        
        playerBtn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
            public void handle(ActionEvent e) {        
                Pong.CLICK.play();
                Pong.MUSIC.stop();
                musicStatus = true;
                game(primaryStage, false);                
           }
        });
        
        computerBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                Pong.CLICK.play();
                Pong.MUSIC.stop();
                musicStatus = true;
                game(primaryStage, true);
            }
        });
        
        backBtn.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent e){
               Pong.CLICK.play();               
               start(primaryStage);
           }
        });
        
        Scene scene = new Scene(grid, 600, 400);       
        grid.getStylesheets().add("style.css"); 
        primaryStage.setTitle("PING PONG - CHOOSE OPONENT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /*
        OPCJE
                */
    public void options(final Stage primaryStage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        
        
        Text text = new Text();                      
        text.setText("COMPUTER MODE");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font ("Verdana", 16));
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font-weight: bold;");                 
        grid.add(text,0,0);
        
        Separator separator1 = new Separator();
        grid.add(separator1,0,1);
        
        Button easyBtn = new Button("EASY");
        easyBtn.setMinSize(200, 30);         
        grid.add(easyBtn, 0,2);
        
        Button mediumBtn = new Button("MEDIUM");
        mediumBtn.setMinSize(200, 30);
        grid.add(mediumBtn, 0, 3);
        
        Button hardBtn = new Button("HARD");
        hardBtn.setMinSize(200, 30);
        grid.add(hardBtn, 0, 4);    
        
        Separator separator2 = new Separator();
        grid.add(separator2,0,6);
        
        if(mode == "easy"){
            easyBtn.setStyle(" -fx-text-fill: red;");  
        } else if (mode == "medium"){
            mediumBtn.setStyle(" -fx-text-fill: red;");  
        } else {
            hardBtn.setStyle(" -fx-text-fill: red;");  
        }
        
        easyBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Pong.CLICK.play();
                mode = "easy";
                mediumBtn.setStyle(" -fx-text-fill: white;");
                hardBtn.setStyle(" -fx-text-fill: white;");
                easyBtn.setStyle(" -fx-text-fill: red;"); 
            }
        });
        
        mediumBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Pong.CLICK.play();
                mode = "medium";
                easyBtn.setStyle(" -fx-text-fill: white;");
                hardBtn.setStyle(" -fx-text-fill: white;");
                mediumBtn.setStyle(" -fx-text-fill: red;");  
            }
        });
        
        hardBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Pong.CLICK.play();
                mode = "hard";
                easyBtn.setStyle(" -fx-text-fill: white;");
                mediumBtn.setStyle(" -fx-text-fill: white;");
                hardBtn.setStyle(" -fx-text-fill: red;");  
            }
        });
        
        Text text2 = new Text();                                             
        text2.setText("SOUNDS SETTINGS");
        text2.setTextAlignment(TextAlignment.CENTER);
        text2.setFont(Font.font ("Verdana", 16));
        text2.setFill(Color.WHITE);
        text2.setStyle("-fx-font-weight: bold;");                 
        grid.add(text2,0,5);
        
        Button musicBtn = new Button ();
        musicBtn.setMinSize(200, 30);
        if (musicON == true){
            musicBtn.setText("MUSIC: ON");
        } else {
            musicBtn.setText("MUSIC: OFF");
        }
        
        musicBtn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
            public void handle(ActionEvent e) {
                Pong.CLICK.play();
                if (musicON == true){
                    Pong.MUSIC.stop();
                    musicON = false;
                    musicBtn.setText("MUSIC: OFF");
                } else {                    
                    Pong.MUSIC.cycleCountProperty();
                    musicON = true;
                    Pong.MUSIC.setCycleCount(INDEFINITE);           
                    Pong.MUSIC.play();
                    musicBtn.setText("MUSIC: ON");
                }
           }
        });
        grid.add(musicBtn, 0, 7);
        
        
        Button backBtn = new Button("BACK");
        backBtn.setMinSize(200, 40);
        grid.add(backBtn,0,10);
        
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
            public void handle(ActionEvent e) {
               Pong.CLICK.play();
               start(primaryStage);             
           }
        });
        
        Scene scene = new Scene(grid, 600, 400);       
        grid.getStylesheets().add("style.css"); 
        primaryStage.setTitle("PING PONG - OPTIONS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void credits(final Stage primaryStage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        
        
        Text text1 = new Text();
        text1.setText("AUTHORS:");
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setUnderline(true);
        text1.setFill(Color.WHITE);
        text1.setFont(Font.font ("Verdana", 30));
        grid.add(text1,0,0);
        
        Text text2 = new Text();
        text2.setText("Mateusz Jancarz");
        text2.setTextAlignment(TextAlignment.CENTER);
        text2.setFill(Color.WHITE);
        text2.setFont(Font.font ("Verdana", 20));
        grid.add(text2,0,1);
        
        Text text3 = new Text();
        text3.setText("Michał Pachel");
        text3.setTextAlignment(TextAlignment.CENTER);
        text3.setFill(Color.WHITE);
        text3.setFont(Font.font ("Verdana", 20));
        grid.add(text3,0,2);
        
        Button backBtn = new Button("BACK");
        backBtn.setMinSize(200, 40);
        grid.add(backBtn,0,8);
        
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
            public void handle(ActionEvent e) {
               Pong.CLICK.play();
               start(primaryStage);             
           }
        });
         
        Scene scene = new Scene(grid, 600, 400);       
        grid.getStylesheets().add("style.css");
        primaryStage.setTitle("PING PONG - CREDITS");
        primaryStage.setScene(scene);
        primaryStage.show();        
    }
    //MENU
    @Override
    public void start(final Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        if (musicON == true && musicStatus == true){
            Pong.MUSIC.setCycleCount(INDEFINITE);           
            Pong.MUSIC.play();
            
            musicStatus = false;
        }
        
        
   
        
        Button playBtn = new Button("PLAY");
        playBtn.setAlignment(Pos.CENTER);
        playBtn.setMinSize(200, 40);
        grid.add(playBtn, 0,0);
        
        
        Button optionsBtn = new Button("OPTIONS");
        optionsBtn.setAlignment(Pos.CENTER);
        optionsBtn.setMinSize(200, 40);
        grid.add(optionsBtn, 0,1);
        
        Button creditsBtn = new Button("CREDITS");
        creditsBtn.setAlignment(Pos.CENTER);
        creditsBtn.setMinSize(200, 40);
        grid.add(creditsBtn, 0,2);
        
        Button infoBtn = new Button("INSTRUCTIONS");
        infoBtn.setAlignment(Pos.CENTER);
        infoBtn.setMinSize(200, 40);
        grid.add(infoBtn, 0,3);
        
        Button quitBtn = new Button("QUIT");
        quitBtn.setAlignment(Pos.CENTER);
        quitBtn.setMinSize(200, 40);
        grid.add(quitBtn, 0,5);
        
        playBtn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
            public void handle(ActionEvent e) {
               Pong.CLICK.play();
               selectMode(primaryStage);
           }
        });
        
        optionsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override   
            public void handle(ActionEvent e) {          
                Pong.CLICK.play();
                options(primaryStage);              
            }
        });
        
        creditsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override   
            public void handle(ActionEvent e) {          
               Pong.CLICK.play();
               credits(primaryStage);
               
            }
        });
        
        infoBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                Pong.CLICK.play();
            }
        });
        
        quitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override   
            public void handle(ActionEvent e) {
                Pong.CLICK.play();
                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(new EventHandler<ActionEvent> () {
                @Override
                    public void handle(ActionEvent event){
                        primaryStage.close();
                    }
                });
                pause.play(); 
               
            }
        });
        
         
        Scene scene = new Scene(grid, 600, 400);       
        grid.getStylesheets().add("style.css");
        primaryStage.setTitle("PING PONG");
        primaryStage.setScene(scene);
        primaryStage.show();
       
    }

    public static void main(final String[] args) {
        launch(args);
    }
}