package com.example.javav3;

import javafx.application.Application;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Random;


public class Pong extends Application{

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;

    private static final int PLAYERHEIGHT = 100;
    private static final int PLAYERWIDTH = 15;

    private static final double BALL_R = 15;

    private double ballXPos = WIDTH/2;
    private double ballYPos = HEIGHT/2;

    private int ballYSpeed = 1;
    private int ballXSpeed = 1;

    private double player1YPosition = HEIGHT/2;
    private double player2YPosition = HEIGHT/2;

    private int player1XPosition = 0;
    private double player2XPosition = WIDTH-PLAYERWIDTH;

    private int score1 = 0; //AI
    private int score2 = 0; //You

    private boolean gameStarted;

    public static void main(String[]args){
        launch(args);

    }

    @Override
    public void start(Stage primaryStage)throws Exception{

        primaryStage.setTitle("PONG V1");
        //background size
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10),e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        //mouse control events
        canvas.setOnMouseMoved(e -> player1YPosition = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);

        primaryStage.setScene(new Scene(new StackPane(canvas)));
        primaryStage.show();
        tl.play();
    }

    private void run(GraphicsContext gc){

        //set graphics and set background color
        gc.setFill(Color.RED);
        gc.fillRect(0,0,WIDTH,HEIGHT);

        //set text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));

        //incriment ball x position by its speed and y too
        if(gameStarted){
           ballXPos += ballXSpeed;
           ballYPos += ballYSpeed;

           if(ballXPos < WIDTH - (WIDTH/4)){
               player2YPosition = ballYPos - (PLAYERHEIGHT/2);
           } else {
               player2YPosition = ballYPos > player2YPosition + PLAYERHEIGHT/2 ? player2YPosition += 1 : player2YPosition - 1;
           }

           gc.fillOval(ballXPos,ballYPos,BALL_R, BALL_R);

        } else {
            //setStartText
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("play",WIDTH/2,HEIGHT/2);

            //reset ball start position
            ballXPos = WIDTH/2;
            ballYPos = HEIGHT/2;

            //reset ball speed & direction
            ballXSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
        }

        //make sure ball stays inside canvas
        if(ballYPos > HEIGHT || ballYPos < 0){
            ballYSpeed *= -1;
        }

        //if you miss ball AI gets point
        if(ballXPos < player1XPosition - PLAYERWIDTH){
            score2++;
            gameStarted = false;
        }

        ///if AI misses ball You get point
        if(ballXPos > player2XPosition + PLAYERWIDTH){
            score1++;
            gameStarted = false;
        }

        //Increase speed and change its direction after ball hits player
        if(((ballXPos + BALL_R > player2XPosition) && ballYPos >= player2YPosition && ballYPos <= player2YPosition + PLAYERHEIGHT) || ((ballXPos < player1XPosition + PLAYERWIDTH) && ballYPos >= player1YPosition && ballYPos <= player1YPosition + PLAYERHEIGHT)){
            ballYSpeed += 1*Math.signum(ballYSpeed);
            ballXSpeed += 1*Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }

        //draw score
        gc.fillText(score1 + "\t\t\t\t\t\t\t\t\t\t" + score2, WIDTH/2, 100);

        //draw player 1 and 2
        gc.fillRect(player2XPosition, player2YPosition, PLAYERWIDTH, PLAYERHEIGHT);
        gc.fillRect(player1XPosition, player1YPosition, PLAYERWIDTH, PLAYERHEIGHT);

        String musicFile = "StayTheNight.mp3";     // For example

//        Media sound = new Media(new File(musicFile).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();

        //add media file in src
        //TODO elevator music
    }
}


