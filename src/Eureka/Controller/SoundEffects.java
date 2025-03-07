package Eureka.Controller;


import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;

public class SoundEffects {

    private static final AudioClip hoverSound = new AudioClip(SoundEffects.class.getResource("/Eureka/View/sounds/Hoverbtn.wav").toExternalForm());
    static final AudioClip clickSound = new AudioClip(SoundEffects.class.getResource("/Eureka/View/sounds/Clickbtn.wav").toExternalForm());

    public static void addSound(Button button) {
        button.setOnMouseEntered(e -> {
            hoverSound.stop();
            hoverSound.play();
        });

        button.setOnAction(e -> {
            clickSound.stop();
            clickSound.play();
        });
    }
}


