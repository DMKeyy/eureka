package Eureka.Controller.ui;

import Eureka.models.SoundEffects;
import Eureka.models.BadgeRep.Badge;
import Eureka.models.BadgeRep.BadgeRepository;
import Eureka.models.PlayerRep.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.List;

public class BadgeController {

    @FXML
    private Button btn_back;
    
    @FXML
    private ListView<String> badgeListView;

    @FXML
    public void initialize() {
        SoundEffects.addSound(btn_back);
        Player player = Player.getCurrentPlayer();
        List<Badge> badges = BadgeRepository.getPlayerBadges(player.getPlayerId());

        btn_back.setOnAction(e -> {
            SceneManager.changeScene(e, "Profile.fxml");
        });

        for (Badge badge : badges) {
            String info = String.format("🏅 %s\n• %s\n• Thème : %s  |  Rareté : %s\n", badge.getName(), badge.getDescription(), badge.getTheme(), badge.getRarity().toString());

            badgeListView.getItems().add(info);
        }
    }
}