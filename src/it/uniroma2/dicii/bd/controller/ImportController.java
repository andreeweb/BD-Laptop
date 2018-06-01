package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.bean.SatelliteBean;
import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.dao.PGToolDao;
import it.uniroma2.dicii.bd.enumeration.BranchType;
import it.uniroma2.dicii.bd.enumeration.StarType;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.exception.ExceptionDialog;
import it.uniroma2.dicii.bd.interfaces.GPointDao;
import it.uniroma2.dicii.bd.interfaces.SatelliteDao;
import it.uniroma2.dicii.bd.interfaces.ToolDao;
import it.uniroma2.dicii.bd.model.*;
import it.uniroma2.dicii.bd.thread.BoundaryThread;
import it.uniroma2.dicii.bd.thread.FilamentThread;
import it.uniroma2.dicii.bd.thread.SkeletonThread;
import it.uniroma2.dicii.bd.thread.StarThread;
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
import java.util.Objects;

public class ImportController {

    // USE ONLY IN DEBUG !!!
    public static Integer checkSizeDebug;
    public static Boolean DEBUG = false;

    final private Integer maxThreadPerImport = Integer.valueOf(Config.getSingletonInstance().getProperty("maxThread"));

    /**
     *
     * Import file star
     *
     * @param filePath csv file path
     * @throws IOException
     */
    public double importStar(String filePath, SatelliteBean satelliteBean) throws Exception {

        if (ImportController.DEBUG)
            ImportController.checkSizeDebug = 1; //+1 HEADER

        // Join list and time counting
        long startTime = System.nanoTime();
        List<Thread> threadList = new ArrayList<>();

        int csvSize = this.countRows(filePath);

        int starPerThread = csvSize;
        if (maxThreadPerImport > 0)
            starPerThread = starPerThread / maxThreadPerImport;
        else
            throw new Exception("maxThreadPerImport in config must be greater than zero");

        System.out.println("CSV Size: " + csvSize);
        System.out.println("Star per Thread: " + starPerThread);

        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withSkipHeaderRecord(true)
                .withHeader("IDSTAR", "NAMESTAR", "GLON_ST", "GLAT_ST", "FLUX_ST", "TYPE_ST")
                .withIgnoreHeaderCase()
                .withTrim());

        LinkedList<Star> linkedlist = new LinkedList<Star>();

        // get satellite tools
        ToolDao toolDao = DaoFactory.getSingletonInstance().getToolDAO();
        Satellite satellite = new Satellite(satelliteBean.getName());
        satellite.setTools(toolDao.getTools(satellite));

        for (CSVRecord csvRecord : csvParser) {

            //System.out.println("Record No - " + csvRecord.getRecordNumber());

            try{

                String idStar = csvRecord.get("IDSTAR");
                String name = csvRecord.get("NAMESTAR");
                String glon = csvRecord.get("GLON_ST");
                String glat = csvRecord.get("GLAT_ST");
                String flux = csvRecord.get("FLUX_ST");
                String type = csvRecord.get("TYPE_ST");

                Star star = new Star(Integer.valueOf(idStar));
                star.setName(name);
                star.setFlux(new BigDecimal(flux));
                star.setPosition(new GPoint(Double.valueOf(glon), Double.valueOf(glat)));
                star.setType(StarType.valueOf(type));

                Tool tool = satellite.getToolForTypeStar(StarType.valueOf(type));
                star.setTool(tool);

                linkedlist.add(star);

            }catch (Exception e){

                System.out.println("Error, skipper row No - " + csvRecord.getRecordNumber());
                e.printStackTrace();
            }

            if (linkedlist.size() == starPerThread){

                Thread thread = new Thread(new StarThread(linkedlist));
                thread.start();
                threadList.add(thread);

                // reset
                linkedlist = new LinkedList<Star>();
            }

        }

        // handle leftover
        if (linkedlist.size() > 0){
            Thread thread = new Thread(new StarThread(linkedlist));
            thread.start();
            threadList.add(thread);
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

        // COMPARE THIS WITH THE CSV SIZE
        if (ImportController.DEBUG)
            System.out.println("\nDEBUG - Compare with CSV size: " + ImportController.checkSizeDebug);

        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/ 1000000000.0);

        return (endTime - startTime)/ 1000000000.0;
    }

    /**
     *
     * Import file skeleton
     *
     * @param filePath csv file path
     * @throws IOException
     */
    public double importSkeleton(String filePath) throws Exception {

        if (ImportController.DEBUG)
            ImportController.checkSizeDebug = 1; //+1 HEADER

        // Join list and time counting
        List<Thread> threadList = new ArrayList<>();
        long startTime = System.nanoTime();

        int csvSize = this.countRows(filePath);

        int distinctBranchRows = this.countDistinctBranchRows(filePath);
        int branchPerThread;
        if (maxThreadPerImport > 0)
            branchPerThread = distinctBranchRows / maxThreadPerImport;
        else
            throw new Exception("maxThreadPerImport in config must be greater than zero");

        System.out.println("CSV Size: " + csvSize);
        System.out.println("Branch per Thread: " + branchPerThread);
        System.out.println();

        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withSkipHeaderRecord(true)
                .withHeader("IDFIL", "IDBRANCH", "TYPE", "GLON_BR", "GLAT_BR", "N", "FLUX")
                .withIgnoreHeaderCase()
                .withTrim());

        // List for single thread
        LinkedList<Branch> linkedlist = new LinkedList<Branch>();
        Branch branch = new Branch(-1, null);

        for (CSVRecord csvRecord : csvParser) {

            //System.out.println("Record No - " + csvRecord.getRecordNumber());

            try {

                String idfil = csvRecord.get("IDFIL");
                String idBranch = csvRecord.get("IDBRANCH");
                String type = csvRecord.get("TYPE");
                String n = csvRecord.get("N");
                String flux = csvRecord.get("FLUX");
                String g_lon = csvRecord.get("GLON_BR");
                String g_lat = csvRecord.get("GLAT_BR");

                if (linkedlist.size() > 0){

                    // check if last branch is the same of this new iteration
                    // if not, create new branch
                    if (!linkedlist.getLast().getIdBranch().equals(Integer.valueOf(idBranch))){

                        branch = new Branch(Integer.valueOf(idBranch), new Filament(Integer.valueOf(idfil)));
                        branch.setType(BranchType.valueOf(type));
                        linkedlist.add(branch);
                    }

                }else{

                    branch = new Branch(Integer.valueOf(idBranch), new Filament(Integer.valueOf(idfil)));
                    branch.setType(BranchType.valueOf(type));
                    linkedlist.add(branch);
                }

                GPointBranch gpoint = new GPointBranch(Double.valueOf(g_lon), Double.valueOf(g_lat), Integer.valueOf(n));
                gpoint.setFlux(new BigDecimal(flux));

                // add branch point
                branch.addPoint(gpoint);

            }catch (Exception e){

                System.out.println("Error, skipper row No - " + csvRecord.getRecordNumber());
                e.printStackTrace();
            }

            if (linkedlist.size() == branchPerThread){

                Thread thread = new Thread(new SkeletonThread(linkedlist));
                thread.start();
                threadList.add(thread);

                // reset list per thread
                linkedlist = new LinkedList<Branch>();
            }
        }

        // handle leftover
        if (linkedlist.size() > 0){

            Thread thread = new Thread(new SkeletonThread(linkedlist));
            thread.start();
            threadList.add(thread);

        }

        for (Thread thread : threadList) {
            try {
                thread.join();
                //System.out.println(thread.getName() + " Finished its job");
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception thrown by : " + thread.getName());
            }
        }

        // COMPARE THIS WITH THE CSV SIZE
        if (ImportController.DEBUG)
            System.out.println("\nDEBUG - Compare with CSV size: " + ImportController.checkSizeDebug);

        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/ 1000000000.0);

        return (endTime - startTime)/ 1000000000.0;
    }

    /**
     *
     * Import file filament
     *
     * @param filePath csv file path
     * @throws IOException
     */
    public double importFilament(String filePath) throws Exception {

        if (ImportController.DEBUG)
            ImportController.checkSizeDebug = 1; //+1 HEADER

        // Join list and time counting
        long startTime = System.nanoTime();
        List<Thread> threadList = new ArrayList<>();

        int csvSize = this.countRows(filePath);

        int filamentPerThread = csvSize;
        if (maxThreadPerImport > 0)
            filamentPerThread = filamentPerThread / maxThreadPerImport;
        else
            throw new Exception("maxThreadPerImport in config must be greater than zero");

        System.out.println("CSV Size: " + csvSize);
        System.out.println("Filament per Thread: " + filamentPerThread);

        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withSkipHeaderRecord(true)
                .withHeader("IDFIL", "NAME", "TOTAL_FLUX", "MEAN_DENS", "MEAN_TEMP", "ELLIPTICITY", "CONTRAST", "SATELLITE", "INSTRUMENT")
                .withIgnoreHeaderCase()
                .withTrim());

        LinkedList<Filament> linkedlist = new LinkedList<Filament>();

        for (CSVRecord csvRecord : csvParser) {

            //System.out.println("Record No - " + csvRecord.getRecordNumber());

            try{

                String idfil = csvRecord.get("IDFIL");
                String name = csvRecord.get("NAME");
                String total_flux = csvRecord.get("TOTAL_FLUX");
                String mean_density = csvRecord.get("MEAN_DENS");
                String mean_temperature = csvRecord.get("MEAN_TEMP");
                String ellipticity = csvRecord.get("ELLIPTICITY");
                String contrast = csvRecord.get("CONTRAST");

                Filament filament = new Filament(Integer.parseInt(idfil));
                filament.setName(name);
                filament.setTotalFlux((new BigDecimal(total_flux)));
                filament.setMeanDensity(new BigDecimal(mean_density));
                filament.setMeanTemperature(Float.parseFloat(mean_temperature));
                filament.setEllipticity(Float.parseFloat(ellipticity));
                filament.setContrast(Float.parseFloat(contrast));

                String toolName = csvRecord.get("INSTRUMENT");
                String satelliteName = csvRecord.get("SATELLITE");

                Tool tool = new Tool(toolName);

                Satellite satellite = new Satellite(satelliteName);
                tool.setSatellite(satellite);

                filament.setTool(tool);

                linkedlist.add(filament);

            }catch (Exception e){

                System.out.println("Error, skipper row No - " + csvRecord.getRecordNumber());
                e.printStackTrace();
            }

            if (linkedlist.size() == filamentPerThread){

                Thread thread = new Thread(new FilamentThread(linkedlist));
                thread.start();
                threadList.add(thread);

                // reset
                linkedlist = new LinkedList<Filament>();
            }

        }

        // handle leftover
        if (linkedlist.size() > 0){
            Thread thread = new Thread(new FilamentThread(linkedlist));
            thread.start();
            threadList.add(thread);
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

        // COMPARE THIS WITH THE CSV SIZE
        if (ImportController.DEBUG)
            System.out.println("\nDEBUG - Compare with CSV size: " + ImportController.checkSizeDebug);

        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/ 1000000000.0);

        return (endTime - startTime)/ 1000000000.0;
    }

    /**
     *
     * Import file filament boundary
     *
     * @param filePath csv file path
     * @throws IOException
     */
    public double importBoundary(String filePath) throws Exception {

        if (ImportController.DEBUG)
            ImportController.checkSizeDebug = 1; //+1 HEADER

        // Join list and time counting
        List<Thread> threadList = new ArrayList<>();
        long startTime = System.nanoTime(); // calcolo tempi import

        int csvSize = this.countRows(filePath);

        int distinctFIl = this.countDistinctFilamentRows(filePath);
        int filamentPerThread;
        if (maxThreadPerImport > 0)
            filamentPerThread = distinctFIl / maxThreadPerImport;
        else
            throw new Exception("maxThreadPerImport in config must be greater than zero");

        System.out.println("CSV Size: " + csvSize);
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
        Filament filament = new Filament(-1);

        for (CSVRecord csvRecord : csvParser) {

            //System.out.println("Record No - " + csvRecord.getRecordNumber());

            try{

                String idfil = csvRecord.get("IDFIL");
                String g_lon = csvRecord.get("GLON_CONT");
                String g_lat = csvRecord.get("GLAT_CONT");

                if (linkedlist.size() > 0){

                    // check if last filament is the same of this new iteration
                    // if not, create new branch
                    if (!linkedlist.getLast().getIdfil().equals(Integer.valueOf(idfil))){

                        filament = new Filament(Integer.valueOf(idfil));
                        linkedlist.add(filament);
                    }

                }else{

                    filament = new Filament(Integer.valueOf(idfil));
                    linkedlist.add(filament);
                }


                // Get point
                GPoint gpoint = new GPoint(Double.valueOf(g_lon), Double.valueOf(g_lat));

                // add point to filament
                filament.addBoundaryPoint(gpoint);

            }catch (Exception e){

                System.out.println("Error, skipper row No - " + csvRecord.getRecordNumber());
                e.printStackTrace();
            }

            if (linkedlist.size() == filamentPerThread){

                Thread thread = new Thread(new BoundaryThread(linkedlist));
                thread.start();
                threadList.add(thread);

                // reset list per thread
                linkedlist = new LinkedList<Filament>();
            }
        }

        // handle leftover
        if (linkedlist.size() > 0){

            Thread thread = new Thread(new BoundaryThread(linkedlist));
            thread.start();
            threadList.add(thread);
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
                //System.out.println(thread.getName() + " Finished its job");
            } catch (InterruptedException e) {
                System.out.println("Interrupted Exception thrown by : " + thread.getName());
            }
        }

        // COMPARE THIS WITH THE CSV SIZE
        if (ImportController.DEBUG)
            System.out.println("\nDEBUG - Compare with CSV size: " + ImportController.checkSizeDebug);

        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/ 1000000000.0);

        return (endTime - startTime)/ 1000000000.0;

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
        return this.countDistinctRows(filePath, 0);
    }

    /**
     *
     * Count how many branch there are in the csv
     *
     * @param filePath csv file path
     * @return number of branch
     * @throws IOException file error
     */
    public int countDistinctBranchRows(String filePath) throws IOException {
        return this.countDistinctRows(filePath, 1);
    }

    /**
     *
     * Count how many distinct row there are in the csv
     *
     * @param filePath csv file path
     * @param column index
     * @return distinct row
     * @throws IOException file error
     */
    public int countDistinctRows(String filePath, Integer column) throws IOException {

        int count = 0;

        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withSkipHeaderRecord(true)
                .withIgnoreHeaderCase()
                .withTrim());

        String actualFilamentID = null;

        for (CSVRecord csvRecord : csvParser) {

            // first read set
            if (actualFilamentID == null)
                actualFilamentID = csvRecord.get(column);

            if (!actualFilamentID.equals(csvRecord.get(column))){
                count++;
                actualFilamentID = csvRecord.get(column);
            }
        }

        return count;
    }

    public static synchronized void increaseDebugCounter(Integer increase){
        ImportController.checkSizeDebug+=increase;
    }

    public static void main(String[] args) throws Exception {

        ImportController importController = new ImportController();

        importController.importStar("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/stelle_Herschel.csv", new SatelliteBean("Herschel"));

        //importController.importSkeleton("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/scheletro_filamenti_Herschel.csv");
        //importController.importSkeleton("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/scheletro_filamenti_Spitzer.csv");

        //importController.importFilament("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/filamenti_Herschel.csv");
        //importController.importFilament("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/filamenti_Spitzer.csv");

        //importController.importBoundary("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/contorni_filamenti_Herschel.csv");
        //importController.importBoundary("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/contorni_filamenti_Spitzer.csv");

        // errore
        //importController.importFilament("/Users/andrea/Sviluppo/BD/BD-Laptop/src/it/uniroma2/dicii/bd/resources/csv-test/contorni_filamenti_Herschel.csv");
    }

}