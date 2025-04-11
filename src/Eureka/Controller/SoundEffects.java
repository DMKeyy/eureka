package Eureka.Controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;

public class SoundEffects {

    private static final AudioClip hoverSound = new AudioClip(SoundEffects.class.getResource("/Eureka/View/sounds/Hoverbtn.wav").toExternalForm());
    static final AudioClip clickSound = new AudioClip(SoundEffects.class.getResource("/Eureka/View/sounds/Clickbtn.wav").toExternalForm());

    public static void addSound(Button button) {
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            hoverSound.stop();
            hoverSound.play();
            }
        });

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            clickSound.stop();
            clickSound.play();
            }
        });
    }
}


