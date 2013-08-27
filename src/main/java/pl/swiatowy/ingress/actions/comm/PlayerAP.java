package pl.swiatowy.ingress.actions.comm;

import java.util.Map;

/**
 * Created: 27.08.1300:09
 *
 * @author swiatek25
 */
public class PlayerAP {
    private final long totalPoints;
    private final Map<CommAction, Integer> actionSummary;

    public PlayerAP(Map<CommAction, Integer> playerCommStatistics, long totalPoints) {
        this.actionSummary = playerCommStatistics;
        this.totalPoints = totalPoints;
    }

    public long getTotalPoints() {
        return totalPoints;
    }

    public Map<CommAction, Integer> getActionSummary() {
        return actionSummary;
    }
}
