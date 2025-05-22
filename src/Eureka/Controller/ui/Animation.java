package Eureka.Controller.ui;


import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animation {
     

    private static final int INDEFINITE = 1;

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

       public static void spin(ImageView imageView) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(60), imageView);
        rotate.setByAngle(360); 
        rotate.setCycleCount(Animation.INDEFINITE); 
        rotate.setInterpolator(javafx.animation.Interpolator.LINEAR);
        rotate.play();
    }

 
public static void pulseRed(Node node) {
    
    node.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

    ScaleTransition pulse = new ScaleTransition(Duration.millis(400), node);
    pulse.setFromX(1.0);
    pulse.setToX(1.2);
    pulse.setFromY(1.0);
    pulse.setToY(1.2);
    pulse.setCycleCount(ScaleTransition.INDEFINITE);
    pulse.setAutoReverse(true);
    pulse.play();
}

}

    
