package codingtest.main;

import codingtest.main.enums.MaxCharge;
import codingtest.main.enums.NormalCharge;
import codingtest.main.enums.Status;
import codingtest.main.pojo.Record;
import codingtest.main.pojo.Result;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestApplication {

    private static HashMap<String, List<Record>> divideRecordsIntoHashedGroups(List<Record> records) {
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

    private static Result RecordToResultMapper(Record currentRecord, Record nextRecord) throws ParseException {
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

    private static List<Result> calculteResults(HashMap<String, List<Record>> recordGroup) throws ParseException {
        List<Result> results = new ArrayList<>();
        for (Map.Entry<String, List<Record>> entry: recordGroup.entrySet()) {
            int i = 0;
            while (i < entry.getValue().size()) {
                if (i == entry.getValue().size() - 1) {
                    Record currentRecord = entry.getValue().get(i);
                    Result newResult = RecordToResultMapper(currentRecord, null);
                    results.add(newResult);
                    i += 1;
                } else {
                    Record currentRecord = entry.getValue().get(i);
                    Record nextRecord = entry.getValue().get(i + 1);
                    if (nextRecord.getTapType().equals("ON")) {
                        Result newResult = RecordToResultMapper(currentRecord, null);
                        results.add(newResult);
                        i += 1;
                    } else {
                        Result newResult = RecordToResultMapper(currentRecord, nextRecord);
                        results.add(newResult);
                        i += 2;
                    }
                }
            }
        }
        return results;
    }

    private static void writeFile(List<Result> results) throws IOException {
        String CSV_SEPARATOR = ",";

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./src/main/resources/result.csv"), "UTF-8"));
        String[] columns = {
            "Started",
            "Finished",
            "DurationSecs",
            "FromStopId",
            "ToStopId",
            "ChargeAmount",
            "CompanyId",
            "BusID",
            "PAN",
            "Status"
        };
        bw.write(String.join(",", columns));
        bw.newLine();
        for (Result result : results)
        {
            StringBuffer oneLine = new StringBuffer();
            oneLine.append(result.getStarted());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(result.getFinished());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(result.getDurationSecs());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(result.getFromStopId());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(result.getToStopId());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(result.getChargeAmount());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(result.getCompanyId());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(result.getBusID());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(result.getPAN());
            oneLine.append(CSV_SEPARATOR);
            oneLine.append(result.getStatus());
            bw.write(oneLine.toString());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public static void main(String[] args) throws IOException, ParseException {
        String fileName = "./src/main/resources/data.csv";
        List<Record> records = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Record.class)
                .build()
                .parse();
        records.remove(0);
        HashMap<String, List<Record>> recordGroup = divideRecordsIntoHashedGroups(records);
        List<Result> results = calculteResults(recordGroup);
        writeFile(results);
    }
}
