package pl.swiatowy.ingress.actions.comm;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import pl.swiatowy.ingress.actions.ActionResult;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created: 25.08.1322:36
 *
 * @author swiatek25
 */
public class CommCallResult extends ActionResult {
    private List<CommEntry> commLines;

    public CommCallResult(InputStream resultStream) {
        super(resultStream);
        commLines = new ArrayList<>();
    }

    @Override
    public void compute() {
        JsonArray requestResult = getResult().get("result").getAsJsonArray();
        for (JsonElement item : requestResult) {
            JsonArray array = item.getAsJsonArray();
            JsonObject plext = array.get(2).getAsJsonObject().get("plext").getAsJsonObject();
            String type = plext.get("plextType").getAsString();
            if (!"SYSTEM_NARROWCAST".equals(type)) {
                String text = plext.get("text").getAsString();
                if (!text.contains("decayed")) {
                    JsonArray markup = plext.get("markup").getAsJsonArray();
                    String action = markup.get(1).getAsJsonArray().get(1).getAsJsonObject().get("plain").getAsString();
                    String player = markup.get(0).getAsJsonArray().get(1).getAsJsonObject().get("plain").getAsString();
                    commLines.add(new CommEntry(new Date(array.get(1).getAsLong()), text, action.trim(), player));
                }
            }
        }
    }

    public List<CommEntry> getCommLines() {
        return commLines;
    }

    public static final class CommEntry implements Comparable<CommEntry> {
        private Date date;
        private final String text;
        private final String player;
        private final String action;

        public CommEntry(Date date, String text, String action, String player) {
            this.date = date;
            this.text = text;
            this.player = player;
            this.action = action;
        }

        public Date getDate() {
            return date;
        }

        public String getText() {
            return text;
        }

        public String getAction() {
            return action;
        }

        public String getPlayer() {
            return player;
        }

        @Override
        public String toString() {
            return date + " : " + text;
        }

        @Override
        public int compareTo(CommEntry o) {
            return date.compareTo(o.getDate());
        }
    }
}
