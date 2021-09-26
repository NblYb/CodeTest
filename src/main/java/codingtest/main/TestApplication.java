package codingtest.main;

import codingtest.main.methods.IOMethods;
import codingtest.main.methods.MatchingMethod;
import codingtest.main.pojo.Record;
import codingtest.main.pojo.Result;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class TestApplication {

    public static void main(String[] args) throws IOException, ParseException {
        IOMethods ioMethods = new IOMethods();
        MatchingMethod matchingMethod = new MatchingMethod();
        List<Record> records = ioMethods.readFile();
        HashMap<String, List<Record>> recordGroup = matchingMethod.divideRecordsIntoHashedGroups(records);
        List<Result> results = matchingMethod.calculateResults(recordGroup);
        ioMethods.writeFile(results);
    }
}
