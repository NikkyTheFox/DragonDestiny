package pl.edu.pg.eti.dragondestiny.playedgame.playedgame.object;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class NotificationMessage {
    private NotificationEnum notificationOption;
    private String name;
    private int number;
    private Boolean bool;

    public NotificationMessage(NotificationEnum option) {
        notificationOption = option;
    }

    public NotificationMessage(NotificationEnum option, String playerLogin) {
        notificationOption = option;
        name = playerLogin;
    }

    public NotificationMessage(NotificationEnum option, String playerLogin, int num) {
        notificationOption = option;
        number = num;
        name = playerLogin;
    }

    public NotificationMessage(NotificationEnum option, String playerLogin, Boolean won) {
        notificationOption = option;
        bool = won;
        name = playerLogin;
    }

    public NotificationMessage(NotificationEnum option, String playerLogin, int num, Boolean won) {
        notificationOption = option;
        number = num;
        name = playerLogin;
        bool = won;
    }
}
