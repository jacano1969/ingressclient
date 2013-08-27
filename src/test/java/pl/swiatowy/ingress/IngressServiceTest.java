package pl.swiatowy.ingress;

import org.junit.Test;
import pl.swiatowy.ingress.actions.RawActionResult;
import pl.swiatowy.ingress.actions.comm.CommCallResult;
import pl.swiatowy.ingress.actions.comm.PlayerAP;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created: 26.08.1301:30
 *
 * @author swiatek25
 */
public class IngressServiceTest {
    @Test
    public void getPlayerAP_fromCommEntries() {
        IngressService service = getIngressService();
        Calendar from = new GregorianCalendar(2013, 7, 25, 13, 00, 00);
        Calendar to = new GregorianCalendar(2013, 7, 25, 17, 00, 00);
        long start = System.currentTimeMillis();
        PlayerAP playerAP = service.getPlayerAP("soltar", from.getTime(), to.getTime());
        System.out.println(playerAP.getActionSummary());
        System.out.println(playerAP.getTotalPoints());
        long end = System.currentTimeMillis();
        printDuration(end - start);
    }

    @Test
    public void testPlayerStats_global() {
        IngressService service = getIngressService();
        RawActionResult statisticsGlobal = service.getPlayerStatisticsGlobal("soltar");
        System.out.println(statisticsGlobal.getRawResult());
    }

    @Test
    public void getCommEntries_from_to_preset() {
        IngressService service = getIngressService();
        Calendar from = new GregorianCalendar(2013, 7, 27, 21, 27, 12);
        Calendar to = new GregorianCalendar(2013, 7, 27, 21, 27, 13);
        long start = System.currentTimeMillis();
        List<CommCallResult.CommEntry> entries = service.getCommEntries(from.getTime(), to.getTime());
        long end = System.currentTimeMillis();
        for (CommCallResult.CommEntry entry : entries) {
            System.out.println(entry);
        }
        printDuration(end - start);
    }

    @Test
    public void getCommEntries_from_to_current_date() {
        IngressService service = getIngressService();
        Calendar from = new GregorianCalendar(2013, 7, 27, 22, 00, 00);
        long start = System.currentTimeMillis();
        List<CommCallResult.CommEntry> entries = service.getCommEntries(from.getTime());
        long end = System.currentTimeMillis();
        for (CommCallResult.CommEntry entry : entries) {
            System.out.println(entry);
        }
        printDuration(end - start);
    }

    private static void printDuration(long duration) {
        System.out.println(String.format("Execution took %ds", TimeUnit.MILLISECONDS.toSeconds(duration)));
    }

    private IngressService getIngressService() {
        return new IngressService();
    }
}
