package Eureka.Controller;

import javafx.scene.image.ImageView;

public class PenduController {

    private static ImageView img_head, img_body, img_leftArm, img_rightArm, img_leftLeg, img_rightLeg, img_rightfoot, img_leftfoot;


    public static void DrawPendu(int attemptsLeft){
        switch (attemptsLeft) {
            case 7:
                img_head.setVisible(true);
                break;
            case 6:
                img_body.setVisible(true);
                break;
            case 5:
                img_leftArm.setVisible(true);
                break;
            case 4:
                img_rightArm.setVisible(true);
                break;
            case 3:
                img_leftLeg.setVisible(true);
                break;
            case 2:
                img_rightLeg.setVisible(true);
                break;
            case 1:
                img_leftfoot.setVisible(true);
                break;
            case 0:
                img_rightfoot.setVisible(true);
                break;
            default:
                break;
        }
    }
}
