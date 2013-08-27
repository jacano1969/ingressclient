package pl.swiatowy.ingress.actions;

import java.io.InputStream;

/**
 * For implementing new calls
 *
 * @author swiatek25
 */
public class RawActionResult extends ActionResult {
    private String rawResult;

    public RawActionResult(InputStream resultStream) {
        super(resultStream);
    }

    @Override
    public void compute() {
        rawResult = getResult().toString();
    }

    public String getRawResult() {
        return rawResult;
    }
}
