package pong;

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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pong extends Application {

    public static Circle circle;
    public static Pane canvas;
    private static final AudioClip MUSIC = new AudioClip(Pong.class.getResource("/mp3.mp3").toString());
    private static final AudioClip CLICK = new AudioClip(Pong.class.getResource("/click.mp3").toString());
    public boolean musicON = true;
    public boolean musicStatus = true;
    public String mode = "easy";
    
    public void game(final Stage primaryStage){
        canvas = new Pane();
        final Scene scene = new Scene(canvas, 600, 400);
        
        int width = 600; int height = 360;
        
        final Label gameArea = new Label();
        gameArea.setMinHeight(height);
        gameArea.setMinWidth(width);
        gameArea.setStyle("-fx-background-color: green;");
        
        final Label points1 = new Label("0");
        final Label points2 = new Label("0");
        
        points1.setLayoutX(10);
        points1.setLayoutY(360);
        points1.setStyle("-fx-font-size: 36px;");
        
        points2.setLayoutX(570);
        points2.setLayoutY(360);
        points2.setStyle("-fx-font-size: 36px;");
        
        final Label paddle1 = new Label();
        final Label paddle2 = new Label();
        
        paddle1.setMinHeight(100);
        paddle1.setMinWidth(20);
        paddle1.setLayoutX(10);
        paddle1.setLayoutY(140);
        
        paddle2.setMinHeight(100);
        paddle2.setMinWidth(20);
        paddle2.setLayoutX(570);
        paddle2.setLayoutY(140);
        
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
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event){
                if (event.getCode() == KeyCode.ESCAPE){
                    selectMode(primaryStage);
                }
            }
        });
        
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
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
        grid.add(backBtn,0,4);       
        
        
        playerBtn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
            public void handle(ActionEvent e) {        
                Pong.CLICK.play();
                game(primaryStage);
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
    //OPCJE
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
            easyBtn.setStyle("-fx-border-color:red;"
                + " -fx-text-fill: red;");  
        } else if (mode == "medium"){
            mediumBtn.setStyle("-fx-border-color:red;"
                + " -fx-text-fill: red;");  
        } else {
            hardBtn.setStyle("-fx-border-color:red;"
                + " -fx-text-fill: red;");  
        }
        
        easyBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Pong.CLICK.play();
                mode = "easy";
                mediumBtn.setStyle("-fx-border-color:black;"
                + " -fx-text-fill: white;");
                hardBtn.setStyle("-fx-border-color:black;"
                + " -fx-text-fill: white;");
                easyBtn.setStyle("-fx-border-color:red;"
                + " -fx-text-fill: red;"); 
            }
        });
        
        mediumBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Pong.CLICK.play();
                mode = "medium";
                easyBtn.setStyle("-fx-border-color:black;"
                + " -fx-text-fill: white;");
                hardBtn.setStyle("-fx-border-color:black;"
                + " -fx-text-fill: white;");
                mediumBtn.setStyle("-fx-border-color:red;"
                + " -fx-text-fill: red;");  
            }
        });
        
        hardBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Pong.CLICK.play();
                mode = "hard";
                easyBtn.setStyle("-fx-border-color:black;"
                + " -fx-text-fill: white;");
                mediumBtn.setStyle("-fx-border-color:black;"
                + " -fx-text-fill: white;");
                hardBtn.setStyle("-fx-border-color:red;"
                + " -fx-text-fill: red;");  
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
                    Pong.MUSIC.play();
                    musicON = true;
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
        grid.add(backBtn,0,5);
        
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