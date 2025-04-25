package Eureka.models;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;

public class SoundEffects {

    public static final AudioClip hoverSound = new AudioClip(SoundEffects.class.getResource("/Eureka/View/sounds/Hoverbtn.wav").toExternalForm());
    public static final AudioClip clickSound = new AudioClip(SoundEffects.class.getResource("/Eureka/View/sounds/Clickbtn.wav").toExternalForm());
    public static boolean isMuted = false;

    public static void addSound(Button button) {
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isMuted) {
                    hoverSound.stop();
                    hoverSound.play();
                }
            }
        });

        button.setOnMousePressed(_ -> {
            System.out.println("Button pressed: " + button.getText() + " | isMuted: " + isMuted);
            if (!isMuted && clickSound != null) {
                clickSound.stop();
                clickSound.play();
            }
        });
    }

    public static void mute() {
        isMuted = true;
        hoverSound.stop();
        clickSound.stop();
    }

    public static void unmute() {
        isMuted = false;
    }

    public static void toggleMute() {
        isMuted = !isMuted;
        if (isMuted) {
            hoverSound.stop();
            clickSound.stop();
        }
    }

    public static boolean isMuted() {
        return isMuted;
    }
}



