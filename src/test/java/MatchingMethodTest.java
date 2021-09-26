import codingtest.main.methods.MatchingMethod;
import codingtest.main.pojo.Record;
import codingtest.main.pojo.Result;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchingMethodTest {
    MatchingMethod matchingMethod = new MatchingMethod();

    @Test
    public void shouldMatchCompletedJourney() throws ParseException {
        List<Record> testRecords = new ArrayList<>();
        Record testRecord1 = new Record("1",
                "2021-09-22 21:43:23",
                "ON",
                "Stop1",
                "Company1",
                "Bus37",
                "5500005555555550"
        );
        Record testRecord2 = new Record("2",
                "2021-09-22 21:43:24",
                "OFF",
                "Stop2",
                "Company1",
                "Bus37",
                "5500005555555550"
        );
        testRecords.add(testRecord1);
        testRecords.add(testRecord2);
        Result testResult = new Result();
        testResult.setStarted("2021-09-22 21:43:23");
        testResult.setFinished("2021-09-22 21:43:24");
        testResult.setDurationSecs("1");
        testResult.setFromStopId("Stop1");
        testResult.setToStopId("Stop2");
        testResult.setChargeAmount("$3.25");
        testResult.setCompanyId("Company1");
        testResult.setBusID("Bus37");
        testResult.setPAN("5500005555555550");
        testResult.setStatus("complete");
        HashMap<String, List<Record>> dividedHashGroup = matchingMethod.divideRecordsIntoHashedGroups(testRecords);
        Result methodResult = matchingMethod.calculateResults(dividedHashGroup).get(0);
        Assert.assertEquals(methodResult, testResult);
    }

    @Test
    public void shouldMatchIncompleteJourney() throws ParseException {
        List<Record> testRecords = new ArrayList<>();
        Record testRecord1 = new Record("1",
                "2021-09-22 21:43:23",
                "ON",
                "Stop1",
                "Company1",
                "Bus37",
                "5500005555555550"
        );
        testRecords.add(testRecord1);
        Result testResult = new Result();
        testResult.setStarted("2021-09-22 21:43:23");
        testResult.setFromStopId("Stop1");
        testResult.setChargeAmount("$7.30");
        testResult.setCompanyId("Company1");
        testResult.setBusID("Bus37");
        testResult.setPAN("5500005555555550");
        testResult.setStatus("incomplete");
        HashMap<String, List<Record>> dividedHashGroup = matchingMethod.divideRecordsIntoHashedGroups(testRecords);
        Result methodResult = matchingMethod.calculateResults(dividedHashGroup).get(0);
        Assert.assertEquals(methodResult, testResult);
    }

    @Test
    public void shouldMatchCanceledJourney() throws ParseException {
        List<Record> testRecords = new ArrayList<>();
        Record testRecord1 = new Record("1",
                "2021-09-22 21:43:23",
                "ON",
                "Stop1",
                "Company1",
                "Bus37",
                "5500005555555550"
        );
        Record testRecord2 = new Record("2",
                "2021-09-22 21:43:24",
                "OFF",
                "Stop1",
                "Company1",
                "Bus37",
                "5500005555555550"
        );
        testRecords.add(testRecord1);
        testRecords.add(testRecord2);
        Result testResult = new Result();
        testResult.setStarted("2021-09-22 21:43:23");
        testResult.setFinished("2021-09-22 21:43:24");
        testResult.setDurationSecs("1");
        testResult.setFromStopId("Stop1");
        testResult.setToStopId("Stop1");
        testResult.setChargeAmount("$0");
        testResult.setCompanyId("Company1");
        testResult.setBusID("Bus37");
        testResult.setPAN("5500005555555550");
        testResult.setStatus("canceled");
        HashMap<String, List<Record>> dividedHashGroup = matchingMethod.divideRecordsIntoHashedGroups(testRecords);
        Result methodResult = matchingMethod.calculateResults(dividedHashGroup).get(0);
        Assert.assertEquals(methodResult, testResult);
    }
}