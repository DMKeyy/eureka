package Eureka.Controller.core;


import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animation {
     

    public static void shake(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(50), node);
        tt.setFromX(-5);
        tt.setByX(10);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }

  
    public static void bounce(Node node) {
       
        TranslateTransition up = new TranslateTransition(Duration.millis(100), node);
        up.setByY(-10);
        up.setInterpolator(javafx.animation.Interpolator.EASE_OUT);

      
        TranslateTransition down = new TranslateTransition(Duration.millis(150), node);
        down.setByY(10);
        down.setInterpolator(javafx.animation.Interpolator.EASE_IN);

        ScaleTransition squish = new ScaleTransition(Duration.millis(50), node);
        squish.setToY(0.9);
        squish.setToX(1.1);
        squish.setCycleCount(2);
        squish.setAutoReverse(true);

        SequentialTransition bounceSequence = new SequentialTransition(up, down);
        bounceSequence.setCycleCount(2);

        bounceSequence.setOnFinished(event -> {
            ScaleTransition resetScale = new ScaleTransition(Duration.millis(50), node);
            resetScale.setToX(1.0);
            resetScale.setToY(1.0);
            resetScale.play();
        });

      
        ParallelTransition fullBounce = new ParallelTransition(node, bounceSequence, squish);
        fullBounce.play();
    }
}

    
