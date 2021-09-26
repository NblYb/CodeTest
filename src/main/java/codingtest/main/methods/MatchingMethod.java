package codingtest.main.methods;

import codingtest.main.enums.MaxCharge;
import codingtest.main.enums.NormalCharge;
import codingtest.main.enums.Status;
import codingtest.main.pojo.Record;
import codingtest.main.pojo.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MatchingMethod {

    public HashMap<String, List<Record>> divideRecordsIntoHashedGroups(List<Record> records) {
        HashMap<String, List<Record>> recordGroup = new HashMap<>();
        for (Record record : records) {
            String key = record.getPAN() + record.getCompanyId() + record.getBusID();
            if (recordGroup.containsKey(key)) {
                recordGroup.get(key).add(record);
            } else {
                List<Record> newList = new ArrayList<>();
                newList.add(record);
                recordGroup.put(key, newList);
            }
        }
        return recordGroup;
    }

    public List<Result> calculateResults(HashMap<String, List<Record>> recordGroup) throws ParseException {
        List<Result> results = new ArrayList<>();
        for (Map.Entry<String, List<Record>> entry: recordGroup.entrySet()) {
            int i = 0;
            while (i < entry.getValue().size()) {
                if (i == entry.getValue().size() - 1) {
                    Record currentRecord = entry.getValue().get(i);
                    Result newResult = recordToResultMapper(currentRecord, null);
                    results.add(newResult);
                    i += 1;
                } else {
                    Record currentRecord = entry.getValue().get(i);
                    Record nextRecord = entry.getValue().get(i + 1);
                    if (nextRecord.getTapType().equals("ON")) {
                        Result newResult = recordToResultMapper(currentRecord, null);
                        results.add(newResult);
                        i += 1;
                    } else {
                        Result newResult = recordToResultMapper(currentRecord, nextRecord);
                        results.add(newResult);
                        i += 2;
                    }
                }
            }
        }
        return results;
    }

    private Result recordToResultMapper(Record currentRecord, Record nextRecord) throws ParseException {
        Result newResult = new Result();
        newResult.setStarted(currentRecord.getDateTimeUTC());
        newResult.setFromStopId(currentRecord.getStopId());
        if (nextRecord != null) {
            newResult.setFinished(nextRecord.getDateTimeUTC());
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date start = dateFormatGmt.parse(currentRecord.getDateTimeUTC());
            Date end = dateFormatGmt.parse(nextRecord.getDateTimeUTC());
            newResult.setDurationSecs(String.valueOf((end.getTime() - start.getTime())/1000));
            newResult.setToStopId(nextRecord.getStopId());
            if (!currentRecord.getStopId().equals(nextRecord.getStopId())) {
                newResult.setChargeAmount(NormalCharge.codeOf(currentRecord.getStopId(), nextRecord.getStopId()));
                newResult.setStatus(Status.COMPLETED.getValue());
            } else {
                newResult.setChargeAmount(NormalCharge.CANCELED.getFiled());
                newResult.setStatus(Status.CANCELED.getValue());
            }
        } else {
            newResult.setChargeAmount(MaxCharge.codeOf(currentRecord.getStopId()));
            newResult.setStatus(Status.INCOMPLETE.getValue());
        }
        newResult.setFromStopId(currentRecord.getStopId());
        newResult.setCompanyId(currentRecord.getCompanyId());
        newResult.setBusID(currentRecord.getBusID());
        newResult.setPAN(currentRecord.getPAN());
        return newResult;
    }
}
