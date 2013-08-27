package pl.swiatowy.ingress.actions.stats;

import pl.swiatowy.ingress.actions.ActionParam;

/**
 * Created: 27.08.1322:04
 *
 * @author swiatek25
 */
public class PlayerStatsParam implements ActionParam {
    private String player;

    public PlayerStatsParam(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
