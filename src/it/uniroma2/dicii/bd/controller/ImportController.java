package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.model.Filament;
import it.uniroma2.dicii.bd.utils.RunnableThread;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ImportController {

    private static final String SAMPLE_CSV_FILE_PATH = "/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/filamenti_Herschel.csv";

    public static void main(String[] args) throws IOException, DaoException {

        long startTime = System.nanoTime();

        List<Thread> threadList = new ArrayList<>();

        int count = 0;
        int block = 500;

        int csvSize = 11450;
        int csvCount = 0;

        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withSkipHeaderRecord(true)
                        .withHeader("IDFIL", "NAME", "TOTAL_FLUX", "MEAN_DENS", "MEAN_TEMP", "ELLIPTICITY", "CONTRAST")
                        .withIgnoreHeaderCase()
                        .withTrim());
        ) {

            /*for (CSVRecord csvRecord : csvParser) {
                csvSize++;
            }*/

            LinkedList<Filament> linkedlist = new LinkedList<Filament>();

            for (CSVRecord csvRecord : csvParser) {

                csvCount++;

                // Accessing values by the names assigned to each column
                String idfil = csvRecord.get("IDFIL");
                String name = csvRecord.get("NAME");
                String total_flux = csvRecord.get("TOTAL_FLUX");
                String mean_density = csvRecord.get("MEAN_DENS");
                String mean_temperature = csvRecord.get("MEAN_TEMP");
                String ellipticity = csvRecord.get("ELLIPTICITY");
                String contrast = csvRecord.get("CONTRAST");

                //System.out.println("Record No - " + csvRecord.getRecordNumber());

                Filament filament = new Filament();
                filament.setIdfil(Integer.parseInt(idfil));
                filament.setName(name);
                filament.setTotalFlux((new BigDecimal(total_flux)));
                filament.setMeanDensity(new BigDecimal(mean_density));
                filament.setMeanTemperature(Float.parseFloat(mean_temperature));
                filament.setEllipticity(Float.parseFloat(ellipticity));
                filament.setContrast(Float.parseFloat(contrast));

                linkedlist.add(filament);
                count++;

                //System.out.println("Count - " + count);

                if (count == block || csvCount == csvSize){

                    Thread thread = new Thread(new RunnableThread(linkedlist));
                    thread.start();
                    threadList.add(thread);

                    linkedlist = new LinkedList<Filament>();
                    count = 0;
                }

                /*FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();
                dao.insert(filament);*/

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
                System.out.println(thread.getName() + " Finished its job");
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception thrown by : " + thread.getName());
            }
        }

        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/ 1000000000.0);
    }

}