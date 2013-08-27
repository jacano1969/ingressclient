package pl.swiatowy.ingress.actions.comm;

import com.sun.istack.internal.Nullable;
import pl.swiatowy.ingress.actions.ActionParam;

import java.util.Date;

/**
 * Created: 25.08.1322:56
 *
 * @author swiatek25
 */
public class CommCallParam implements ActionParam {

    private Date from;
    private Date to;
    private int numberOfEntries = 200;

    public CommCallParam(Date from) {
        this(from, null);
    }

    public CommCallParam(Date from, @Nullable Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public void setNumberOfEntries(int numberOfEntries) {
        this.numberOfEntries = numberOfEntries;
    }
}
