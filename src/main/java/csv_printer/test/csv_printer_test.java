package csv_printer.test;

import csv_printer.CSVFormat;
import csv_printer.CSVParser;
import csv_printer.CSVPrinter;
import csv_printer.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class csv_printer_test {

    @Test
    public void testWrite() throws Exception {
        FileOutputStream fos = new FileOutputStream("E:/target/abc.csv");
        OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");

        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("姓名", "年龄", "家乡");
        CSVPrinter csvPrinter = new CSVPrinter(osw, csvFormat);//        csvPrinter = CSVFormat.DEFAULT.withHeader("姓名", "年龄", "家乡").print(osw);

        for (int i = 0; i < 10; i++) {
            csvPrinter.printRecord("张三", 20, "湖北");
        }


        csvPrinter.flush();
        csvPrinter.close();

    }


    @Test
    public void testRead() throws IOException {
        InputStream is = new FileInputStream("E:/cjsworkspace/cjs-excel-demo/target/abc.csv");
        InputStreamReader isr = new InputStreamReader(is, "GBK");
        Reader reader = new BufferedReader(isr);

        CSVParser parser = CSVFormat.EXCEL.withHeader("name", "age", "jia").parse(reader);//        CSVParser csvParser = CSVParser.parse(reader, CSVFormat.DEFAULT.withHeader("name", "age", "jia"));
        List<CSVRecord> list = parser.getRecords();
        for (CSVRecord record : list) {
            System.out.println(record.getRecordNumber() + ":" + record.get("name") + ":" + record.get("age") + ":" + record.get("jia"));
        }

        parser.close();
    }

    /**
     * Parsing an Excel CSV File
     */
    @Test
    public void testParse() throws Exception {
        Reader reader = new FileReader("C:/Users/Administrator/Desktop/abc.csv");
        CSVParser parser = CSVFormat.EXCEL.parse(reader);
        for (CSVRecord record : parser.getRecords()) {
            System.out.println(record);
        }
        parser.close();
    }

    /**
     * Defining a header manually
     */
    @Test
    public void testParseWithHeader() throws Exception {
        Reader reader = new FileReader("C:/Users/Administrator/Desktop/abc.csv");
        CSVParser parser = CSVFormat.EXCEL.withHeader("id", "name", "code").parse(reader);
        for (CSVRecord record : parser.getRecords()) {
            System.out.println(record.get("id") + ","
                    + record.get("name") + ","
                    + record.get("code"));
        }
        parser.close();
    }

    /**
     * Using an enum to define a header
     */
    enum MyHeaderEnum {
        ID, NAME, CODE;
    }


//    @Test
//    public void testParseWithEnum() throws Exception {
//
//
//        Reader reader = new FileReader("C:/Users/Administrator/Desktop/abc.csv");
//        CSVParser parser = CSVFormat.EXCEL.withHeader(MyHeaderEnum.class).parse(reader);
//        for (CSVRecord record : parser.getRecords()) {
//            System.out.println(record.get(MyHeaderEnum.ID) + ","
//                    + record.get(MyHeaderEnum.NAME) + ","
//                    + record.get(MyHeaderEnum.CODE));
//        }
//        parser.close();
//    }

    private List<Map<String, String>> recordList = new ArrayList<>();

    @Before
    public void init() {
        for (int i = 0; i < 5; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", "zhangsan");
            map.put("code", "001");
            recordList.add(map);
        }
    }

    @Test
    public void writeMuti() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch doneSignal = new CountDownLatch(2);

        executorService.submit(new exprotThread("E:/0.csv", recordList, doneSignal));
        executorService.submit(new exprotThread("E:/1.csv", recordList, doneSignal));

        doneSignal.await();
        System.out.println("Finish!!!");
    }

    class exprotThread implements Runnable {
        private String filename;
        private List<Map<String, String>> list;
        private CountDownLatch countDownLatch;

        public exprotThread(String filename, List<Map<String, String>> list, CountDownLatch countDownLatch) {
            this.filename = filename;
            this.list = list;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                CSVPrinter printer = new CSVPrinter(new FileWriter(filename), CSVFormat.EXCEL.withHeader("NAME", "CODE"));
                for (Map<String, String> map : list) {
                    printer.printRecord(map.values());
                }
                printer.close();
                countDownLatch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
