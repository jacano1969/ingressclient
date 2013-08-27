package pl.swiatowy.ingress.actions;

import com.google.gson.JsonObject;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AutoRetryHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import pl.swiatowy.ingress.Config;
import pl.swiatowy.ingress.actions.comm.CommCallParam;
import pl.swiatowy.ingress.actions.comm.CommCallResult;
import pl.swiatowy.ingress.actions.stats.PlayerStatsParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created: 25.08.1321:17
 *
 * @author swiatek25
 */
public enum Action implements Config {
    COMM_CALL("http://www.ingress.com/rpc/dashboard.getPaginatedPlextsV2") {
        @Override
        JsonObject getData(ActionParam param) {
            CommCallParam params = CommCallParam.class.cast(param);
            JsonObject data = new JsonObject();
            data.addProperty("method", getRpcAddress());
            data.addProperty("desiredNumItems", params.getNumberOfEntries());
            data.addProperty("minLatE6", 52453499);
            data.addProperty("minLngE6", 13058281);
            data.addProperty("maxLatE6", 52595958);
            data.addProperty("maxLngE6", 13790588);
            data.addProperty("minTimestampMs", params.getFrom().getTime());
            data.addProperty("maxTimestampMs", params.getTo() == null ? -1 : params.getTo().getTime());
            data.addProperty("factionOnly", false);
            return data;
        }

        @Override
        public CommCallResult getResult(HttpEntity resultEntity) throws IOException {
            CommCallResult commCallResult = new CommCallResult(resultEntity.getContent());
            commCallResult.compute();
            return commCallResult;
        }
    },
    GET_PLAYER_STATS_CALL("http://www.ingress.com/rpc/dashboard.getPlayerProfile") {
        @Override
        JsonObject getData(ActionParam param) {
            PlayerStatsParam params = (PlayerStatsParam) param;
            JsonObject data = new JsonObject();
            data.addProperty("method", getRpcAddress());
            JsonObject playerInfo = new JsonObject();
            playerInfo.addProperty("plain", params.getPlayer());
            playerInfo.addProperty("guid", "6f7e488a1ded4d6bb03de9a897ebad20.c");
            playerInfo.addProperty("team", "ENLIGHTENED");
            data.add("playerProfile", playerInfo);
//                        data.addProperty("playerProfile", params.getPlayer());
            //[["PLAYER",{"plain":"soltar","guid":"6f7e488a1ded4d6bb03de9a897ebad20.c","team":"ENLIGHTENED"}]
            return data;
        }

        @Override
        public RawActionResult getResult(HttpEntity resultEntity) throws IOException {
            return new RawActionResult(resultEntity.getContent());
        }
    };

    private static final Header[] REQUIRED_HEADER = new Header[]{
            new BasicHeader("Cookie", VALID_COOKIE),
            new BasicHeader("X-CSRFToken", CSRF_TOKEN),
            new BasicHeader("Host", "www.ingress.com"),
            new BasicHeader("Origin", "http://www.ingress.com"),
            new BasicHeader("Referer", "http://www.ingress.com/intel")
    };

    private final String rpcAddress;

    abstract JsonObject getData(ActionParam param);

    public abstract <T extends ActionResult> T getResult(HttpEntity resultEntity) throws IOException;

    Action(String rpcAddress) {
        this.rpcAddress = rpcAddress;
    }

    public String getRpcAddress() {
        return rpcAddress;
    }

    private HttpPost getRequestPayload(ActionParam param) {
        HttpPost post = new HttpPost(rpcAddress);
        post.setHeaders(REQUIRED_HEADER);
        try {
            JsonObject data = getData(param);
            System.out.println(data.toString());
            post.setEntity(new StringEntity(data.toString()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    public ActionResult execute(ActionParam params) {
        HttpClient httpclient = new AutoRetryHttpClient();
        try {
            HttpResponse response = httpclient.execute(getRequestPayload(params));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                throw new IllegalStateException("Request failed, check Config and use valid values. Return message: " + statusLine);
            }
            HttpEntity entity = response.getEntity();
            ActionResult result = getResult(entity);
            EntityUtils.consume(entity);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //[["PLAYER",{"plain":"soltar","guid":"6f7e488a1ded4d6bb03de9a897ebad20.c","":"ENLIGHTENED"}]
        JsonObject player = new JsonObject();
        JsonObject playerInfo = new JsonObject();
        playerInfo.addProperty("plain", "soltar");
        playerInfo.addProperty("guid", "6f7e488a1ded4d6bb03de9a897ebad20.c");
        playerInfo.addProperty("team", "ENLIGHTENED");
        player.add("PLAYER", playerInfo);
        System.out.println(player);
    }
}
