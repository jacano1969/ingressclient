package pl.swiatowy.ingress.actions.comm;

/**
 * Created: 26.08.1323:57
 *
 * @author swiatek25
 */
public enum CommAction {
    DEPLOY_RESONATOR("deployed an", 125),
    DESTROY_RESONATOR("destroyed an", 75),
    DESTROY_CONTROL_FIELD("destroyed a Control Field @", 750),
    CREATE_CONTROL_FIELD("created a Control Field @", 1250),
    CREATE_LINK("linked", 313),
    DESTROY_LINK("destroyed the Link", 187),
    CAPTURE_PORTAL("captured", 500);

    private final String actionName;
    private int points;

    CommAction(String actionName, int points) {
        this.actionName = actionName;
        this.points = points;
    }

    public String getActionName() {
        return actionName;
    }

    public int getPoints() {
        return points;
    }

    public static int getPoints(String actionName) {
        for (CommAction action : values()) {
            if (action.actionName.equals(actionName)) {
                return action.points;
            }
        }
        return 0;
    }

    public static CommAction fromActionName(String actionName) {
        for (CommAction action : values()) {
            if (action.actionName.equals(actionName)) {
                return action;
            }
        }
        return null;
    }
}
