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
        Record testRecord1 = new Record();
        testRecord1.setID("1");
        testRecord1.setDateTimeUTC("2021-09-22 21:43:23");
        testRecord1.setTapType("ON");
        testRecord1.setStopId("Stop1");
        testRecord1.setCompanyId("Company1");
        testRecord1.setBusID("Bus37");
        testRecord1.setPAN("5500005555555550");

        Record testRecord2 = new Record();
        testRecord2.setID("2");
        testRecord2.setDateTimeUTC("2021-09-22 21:43:24");
        testRecord2.setTapType("OFF");
        testRecord2.setStopId("Stop2");
        testRecord2.setCompanyId("Company1");
        testRecord2.setBusID("Bus37");
        testRecord2.setPAN("5500005555555550");

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
        Record testRecord1 = new Record();
        testRecord1.setID("1");
        testRecord1.setDateTimeUTC("2021-09-22 21:43:23");
        testRecord1.setTapType("ON");
        testRecord1.setStopId("Stop1");
        testRecord1.setCompanyId("Company1");
        testRecord1.setBusID("Bus37");
        testRecord1.setPAN("5500005555555550");
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
        Record testRecord1 = new Record();
        testRecord1.setID("1");
        testRecord1.setDateTimeUTC("2021-09-22 21:43:23");
        testRecord1.setTapType("ON");
        testRecord1.setStopId("Stop1");
        testRecord1.setCompanyId("Company1");
        testRecord1.setBusID("Bus37");
        testRecord1.setPAN("5500005555555550");
        Record testRecord2 = new Record();
        testRecord2.setID("2");
        testRecord2.setDateTimeUTC("2021-09-22 21:43:24");
        testRecord2.setTapType("OFF");
        testRecord2.setStopId("Stop1");
        testRecord2.setCompanyId("Company1");
        testRecord2.setBusID("Bus37");
        testRecord2.setPAN("5500005555555550");
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