package Eureka.Controller;

import javafx.scene.image.ImageView;
import java.util.List;

public class PenduDrawer {
    private final List<ImageView> bodyParts;
    private int attemptsLeft;

    public PenduDrawer(List<ImageView> bodyParts, int maxAttempts) {
        this.bodyParts = bodyParts;
        this.attemptsLeft = maxAttempts;
        reset();
    }

    public void drawNextPart() {
        int i = bodyParts.size() - attemptsLeft - 1;
        if (i >= 0 && i < bodyParts.size()) {
            bodyParts.get(i).setVisible(true);
        }
    }

    public void reset() {
        for (ImageView part : bodyParts) {
            part.setVisible(false);
        }
        attemptsLeft = bodyParts.size();
    }

    public void setAttemptsLeft(int attempts) {
        this.attemptsLeft = attempts;
        drawNextPart();
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public boolean isGameOver() {
        return attemptsLeft <= 0;
    }

    public void drawFull() {
        for (ImageView part : bodyParts) {
            part.setVisible(true);
        }
        this.attemptsLeft = 0;
    }

}
