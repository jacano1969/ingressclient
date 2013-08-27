package pl.swiatowy.ingress.actions;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created: 25.08.1322:35
 *
 * @author swiatek25
 */
public abstract class ActionResult {
    private final InputStream resultStream;
    private final JsonObject result;

    public ActionResult(InputStream resultStream) {
        this.resultStream = resultStream;
        Gson gson = new Gson();
        result = gson.fromJson(new InputStreamReader(resultStream), JsonObject.class);
        if (result.has("error")) {
            throw new IllegalStateException("Error encountered while getting response (" + result + "). Check payload data and Config.");
        }
    }

    protected JsonObject getResult() {
        return result;
    }

    public abstract void compute();

}
