package codingtest.main.methods;

import codingtest.main.pojo.Record;
import codingtest.main.pojo.Result;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.util.List;

public class IOMethods {

    String readFileName = "./src/main/resources/data.csv";

    public List<Record> readFile() throws FileNotFoundException {
        List<Record> records = new CsvToBeanBuilder(new FileReader(readFileName))
                .withType(Record.class)
                .build()
                .parse();
        records.remove(0);
        return records;
    }

    public void writeFile(List<Result> results) throws IOException {
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


}
