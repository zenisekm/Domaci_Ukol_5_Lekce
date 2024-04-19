package Plant;

import jdk.jfr.Category;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ListOfPlants implements Serializable {
    private List<Plant> plantList;

    public ListOfPlants() {
        plantList = new ArrayList<>();
    }

    public void addPlant(Plant plant) {
        plantList.add(plant);
    }

    public List<Plant> getPlantList() {
        return plantList;
    }

    public Plant getPlant(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < plantList.size()) {
            return plantList.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index ouf of bounds: " + index);

        }
    }

    public void removePlant(Plant plant) {
        plantList.remove(plant);
    }


    public void loadDataFromFile(String fileName) throws PlantException {
        int lineCounter = 0;
        plantList.clear();
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                lineCounter++;
                String line = scanner.nextLine();
                String[] parts = line.split("\t");
                if (parts.length != 5) {
                    throw new PlantException(
                            "Nesprávný počet položek na řádku číslo: " + lineCounter + ": " + line + "!");
                }
                String name = parts[0];
                String notes = parts[1];
                int frequencyOfWatering = Integer.parseInt(parts[2]);
                LocalDate planted = LocalDate.parse(parts[3]);
                LocalDate watering = LocalDate.parse(parts[4]);
                Plant plant = new Plant(name, notes, planted, watering, frequencyOfWatering);
                plantList.add(plant);
            }
                } catch (FileNotFoundException e) {
                    throw new PlantException("Soubor " + fileName + " nebyl nalezen!\n" + e.getLocalizedMessage());
                } catch (NumberFormatException e) {
                    throw new PlantException("Chyba při čtení číselné hodnoty na řádku číslo: " + lineCounter + ":\n" + e.getLocalizedMessage());
                } catch (IllegalArgumentException e) {
                    throw new PlantException("Chyba při čtení kategorie na řádku číslo: " + lineCounter + ":\n" + e.getLocalizedMessage());
                } catch (DateTimeParseException e) {
                    throw new PlantException("Chyba při čtení data na řádku číslo: " + lineCounter + ":\n" + e.getLocalizedMessage());
                }

    }


    public void saveUpdatedListToFile(String fileName) throws PlantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Plant plant : plantList) {
                writer.println(plant.getName() + "\t"
                        + plant.getNotes() + "\t" + plant.getPlanted() + "\t" + plant.getWatering() + "\t"
                        + plant.getFrequencyOfWatering());
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Soubor " + fileName + " nebyl nalezen\n!" + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new PlantException("Chyba při zápisu aktualizovaného seznamu do souboru: " + e.getLocalizedMessage());
        }
    }


    public static void getWateringInfoFromFile() {
        String filename = Settings.getFilename();
        ListOfPlants plants = new ListOfPlants();
        try {
            plants.loadDataFromFile(filename);
            System.out.println("Informace o zálivce:");
            for (Plant plant : plants.getPlantList()) {
                System.out.println(plant.getWateringInfo());
            }
        } catch (PlantException e) {
            System.err.println(
                    "Nastala chyba při načítání obsahu květin ze souboru: " +
                            filename + ":\n" + e.getLocalizedMessage() + "\n"
            );

        }
    }

    public static void createNewFile() {
        String oldFile = Settings.getFilename();
        String newFile = "NoveKvetiny.txt";

        ListOfPlants plants = new ListOfPlants();
        try {
            plants.loadDataFromFile(oldFile);
            plants.saveUpdatedListToFile(newFile);
            System.out.println("Seznam květin byl úspěšně aktualizován " + newFile);
            System.out.println("Obsah nového seznamu květin: ");
            try (BufferedReader reader = new BufferedReader(new FileReader(newFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.replace(",", "\t");
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.err.println("Nastala chyba při čtení obsahu nového souboru: " + e.getMessage());
            }
        } catch (PlantException e) {
            System.err.println("Nastala chyba při zpracování dat: " + e.getMessage());
        }
    }


    public static void plantsByName() {
        String filename = Settings.getFilename();
        ListOfPlants plants = new ListOfPlants();


        try {
            plants.loadDataFromFile(filename);
            List<Plant> sortedPlants = plants.getPlantList();
            Collections.sort(sortedPlants);
            System.out.println("Rostliny seřazené podle jména:");
            for (Plant plant : sortedPlants) {
                System.out.println(plant.getWateringInfo());
            }
        } catch (PlantException e) {
            System.err.println("Nastala chyba: " + e.getMessage());
        }
    }


    public static void sortByNameAndFrenquency() {
        String filename = Settings.getFilename();
        ListOfPlants plants = new ListOfPlants();

        try {

            plants.loadDataFromFile(filename);

            List<Plant> sortedByName = plants.getPlantList();
            Collections.sort(sortedByName);

            System.out.println("Rostliny seřazené podle názvu:");
            for (Plant plant : sortedByName) {
                System.out.println(plant.getWateringInfo());
            }


            List<Plant> sortedByLastWatering = plants.getPlantList();
            Collections.sort(sortedByLastWatering, new Plant.LastWateringComparator());


            System.out.println("\nRostliny seřazené podle data poslední zálivky:");
            for (Plant plant : sortedByLastWatering) {
                System.out.println(plant.getWateringInfo());
            }
        } catch (PlantException e) {
            System.err.println("Nastala chyba: " + e.getMessage());
        }

    }












}






















































