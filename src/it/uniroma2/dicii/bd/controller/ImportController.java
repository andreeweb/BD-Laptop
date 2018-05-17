package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.Filament;
import it.uniroma2.dicii.bd.model.GPoint;
import it.uniroma2.dicii.bd.thread.BoundaryThread;
import it.uniroma2.dicii.bd.thread.ImportThread;
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

public class ImportController {

    public void importFilament(String filePath){

    }

    public void importBoundary(String filePath) throws IOException {

        // import settings
        String actualFilamentID = null;
        Filament filament = new Filament();
        int maxThreadPerImport = 15;
        int countFilament = 0;
        int alreadyImported = 0;

        int csvSize = this.countRows(filePath);
        System.out.println("Size: " + csvSize);

        int filamentPerThread = (csvSize / 20) / maxThreadPerImport;
        System.out.println("Filament per Thread: " + filamentPerThread);

        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withSkipHeaderRecord(true)
                .withHeader("IDFIL", "GLON_CONT", "GLAT_CONT")
                .withIgnoreHeaderCase()
                .withTrim());

        LinkedList<Filament> linkedlist = new LinkedList<Filament>();

        for (CSVRecord csvRecord : csvParser) {

            alreadyImported++;
            countFilament++;

            // Accessing values by the names assigned to each column
            String idfil = csvRecord.get("IDFIL");

            // first import set
            if (actualFilamentID == null)
                actualFilamentID = idfil;

            if (!actualFilamentID.equals(idfil)){

                if (countFilament == filamentPerThread || alreadyImported == csvSize){

                    Thread thread = new Thread(new BoundaryThread(linkedlist));
                    thread.start();

                    linkedlist = new LinkedList<Filament>();
                    countFilament = 0;
                }
            }

            filament.setIdfil(Integer.parseInt(idfil));

            // Get point
            float glon = Float.valueOf(csvRecord.get("GLON_CONT"));
            float glat = Float.valueOf(csvRecord.get("GLAT_CONT"));

            GPoint gpoint = new GPoint(glon, glat);

            // add point to filament
            filament.addBoundaryPoint(gpoint);

            linkedlist.add(filament);
        }
    }

    public static void main(String[] args) throws IOException {

        ImportController importController = new ImportController();
        importController.importBoundary("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/contorni_filamenti_Herschel.csv");

    }

    /*private static final String SAMPLE_CSV_FILE_PATH = "/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/filamenti_Herschel.csv";

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

                if (count == block || csvCount == csvSize){

                    Thread thread = new Thread(new ImportThread(linkedlist));
                    thread.start();
                    threadList.add(thread);

                    linkedlist = new LinkedList<Filament>();
                    count = 0;
                }

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
    }*/

    /**
     *
     * Count how many rows there are in the csv.
     *
     * @param filePath csv file path
     * @return size, number of rows
     * @throws IOException file error
     */
    public int countRows(String filePath) throws IOException {

        int csvSize = 0;

        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withSkipHeaderRecord(true)
                .withIgnoreHeaderCase()
                .withTrim());

        for (CSVRecord csvRecord : csvParser) {
            csvSize++;
        }

        return csvSize;
    }

}