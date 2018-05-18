package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.model.Filament;
import it.uniroma2.dicii.bd.model.GPoint;
import it.uniroma2.dicii.bd.thread.BoundaryThread;
import it.uniroma2.dicii.bd.thread.FilamentThread;
import it.uniroma2.dicii.bd.utils.Config;
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

    // -1 because the last thread start if there is a leftover
    final private int maxThreadPerImport = Integer.valueOf(Config.getSingletonInstance().getProperty("maxThread")) - 1;

    public void importFilament(String filePath) throws IOException {

        // Join list and time counting
        long startTime = System.nanoTime();
        List<Thread> threadList = new ArrayList<>();

        int csvSize = this.countRows(filePath);
        int filamentPerThread = csvSize / maxThreadPerImport;

        System.out.println("CSV Size: " + csvSize);
        System.out.println("Filament per Thread: " + filamentPerThread);

        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withSkipHeaderRecord(true)
                .withHeader("IDFIL", "NAME", "TOTAL_FLUX", "MEAN_DENS", "MEAN_TEMP", "ELLIPTICITY", "CONTRAST")
                .withIgnoreHeaderCase()
                .withTrim());

        LinkedList<Filament> linkedlist = new LinkedList<Filament>();

        for (CSVRecord csvRecord : csvParser) {

            //System.out.println("Record No - " + csvRecord.getRecordNumber());

            String idfil = csvRecord.get("IDFIL");
            String name = csvRecord.get("NAME");
            String total_flux = csvRecord.get("TOTAL_FLUX");
            String mean_density = csvRecord.get("MEAN_DENS");
            String mean_temperature = csvRecord.get("MEAN_TEMP");
            String ellipticity = csvRecord.get("ELLIPTICITY");
            String contrast = csvRecord.get("CONTRAST");

            Filament filament = new Filament();
            filament.setIdfil(Integer.parseInt(idfil));
            filament.setName(name);
            filament.setTotalFlux((new BigDecimal(total_flux)));
            filament.setMeanDensity(new BigDecimal(mean_density));
            filament.setMeanTemperature(Float.parseFloat(mean_temperature));
            filament.setEllipticity(Float.parseFloat(ellipticity));
            filament.setContrast(Float.parseFloat(contrast));

            linkedlist.add(filament);

            if (linkedlist.size() == filamentPerThread){

                Thread thread = new Thread(new FilamentThread(linkedlist));
                thread.start();
                threadList.add(thread);

                System.out.println(thread.getName() + " Start");

                // reset
                linkedlist = new LinkedList<Filament>();
            }

        }

        // handle leftover
        if (linkedlist.size() > 0){
            Thread thread = new Thread(new FilamentThread(linkedlist));
            thread.start();
            threadList.add(thread);

            System.out.println(thread.getName() + " Start");
        }

        // JOIN thread
        for (Thread thread : threadList) {
            try {
                thread.join();
                //System.out.println(thread.getName() + " Finished its job");
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception thrown by : " + thread.getName());
            }
        }

        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/ 1000000000.0);
    }

    public void importBoundary(String filePath) throws IOException {

        // Join list and time counting
        List<Thread> threadList = new ArrayList<>();
        long startTime = System.nanoTime(); // calcolo tempi import

        // import settings
        String actualFilamentID = null;
        Filament filament = new Filament();

        int total = 1; // +1 header csv

        int distinctFIl = this.countDistinctFilamentRows(filePath);

        int filamentPerThread = distinctFIl / maxThreadPerImport;
        System.out.println("Filament per Thread: " + filamentPerThread);

        System.out.println();

        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withSkipHeaderRecord(true)
                .withHeader("IDFIL", "GLON_CONT", "GLAT_CONT")
                .withIgnoreHeaderCase()
                .withTrim());

        // List for single thread
        LinkedList<Filament> linkedlist = new LinkedList<Filament>();

        for (CSVRecord csvRecord : csvParser) {

            //System.out.println("Record No - " + csvRecord.getRecordNumber());

            String idfil = csvRecord.get("IDFIL");

            // first import set
            if (actualFilamentID == null)
                actualFilamentID = idfil;

            if (!actualFilamentID.equals(idfil)){ // new filament

                total+=filament.getBoundary().size(); // check size
                linkedlist.add(filament);

                if (linkedlist.size() == filamentPerThread){

                    Thread thread = new Thread(new BoundaryThread(linkedlist));
                    thread.start();
                    threadList.add(thread);

                    System.out.println(thread.getName() + " Start");

                    // reset
                    linkedlist = new LinkedList<Filament>();
                }

                filament = new Filament();
            }

            filament.setIdfil(Integer.parseInt(idfil));
            actualFilamentID = idfil;

            // Get point
            double glon = Double.valueOf(csvRecord.get("GLON_CONT"));
            double glat = Double.valueOf(csvRecord.get("GLAT_CONT"));

            GPoint gpoint = new GPoint(glon, glat);

            // add point to filament
            filament.addBoundaryPoint(gpoint);
        }

        // handle leftover
        if (linkedlist.size() > 0){

            total+=filament.getBoundary().size();

            Thread thread = new Thread(new BoundaryThread(linkedlist));
            thread.start();
            threadList.add(thread);

            System.out.println(thread.getName() + " Start");
        }

        System.out.println("total " + total);

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

    public static void main(String[] args) throws IOException {

        ImportController importController = new ImportController();

        //importController.importBoundary("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/contorni_filamenti_Herschel.csv");
        //importController.importBoundary("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/contorni_filamenti_Spitzer.csv");
        //importController.importFilament("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/filamenti_Herschel.csv");
        //importController.importFilament("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/filamenti_Spitzer.csv");


    }

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

    /**
     *
     * Count how many filament there are in the csv
     *
     * @param filePath csv file path
     * @return number of filament
     * @throws IOException file error
     */
    public int countDistinctFilamentRows(String filePath) throws IOException {

        int filament = 0;

        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withSkipHeaderRecord(true)
                .withIgnoreHeaderCase()
                .withTrim());

        String actualFilamentID = null;

        for (CSVRecord csvRecord : csvParser) {

            // first read set
            if (actualFilamentID == null)
                actualFilamentID = csvRecord.get(0);

            if (!actualFilamentID.equals(csvRecord.get(0))){
                filament++;
                actualFilamentID = csvRecord.get(0);
            }
        }

        return filament;
    }

}