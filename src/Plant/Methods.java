package Plant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Methods {

    private static void getWateringInfoFromFile() {
        String filename = Settings.getFilename();
        ListOfPlants plants = new ListOfPlants();
        try {
            plants.loadDataFromFile(filename);
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

    private static void createNewFile() {
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

    private static void plantsByName() {
        String filename = Settings.getFilename();
        ListOfPlants plants = new ListOfPlants();


        try {
            plants.loadDataFromFile(filename);
            List<Plant> sortedPlants = plants.getPlantList();
            Collections.sort(sortedPlants);
            for (Plant plant : sortedPlants) {
                System.out.println(plant.getWateringInfo());
            }
        } catch (PlantException e) {
            System.err.println("Nastala chyba: " + e.getMessage());
        }
    }

    private static void sortByNameAndFrenquency() {
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
            Collections.sort(sortedByLastWatering, new LastWateringComparator());


            System.out.println("\nRostliny seřazené podle data poslední zálivky:");
            for (Plant plant : sortedByLastWatering) {
                System.out.println(plant.getWateringInfo());
            }
        } catch (PlantException e) {
            System.err.println("Nastala chyba: " + e.getMessage());
        }
    }


    private static void sortPlantsByLastWatering() {
        String filename = Settings.getFilename();
        ListOfPlants plants = new ListOfPlants();

        try {
            plants.loadDataFromFile(filename);


            List<Plant> sortedPlants = plants.getPlantList();
            Collections.sort(sortedPlants, new LastWateringComparator());


            System.out.println("Rostliny seřazené podle data poslední zálivky:");
            for (Plant plant : sortedPlants) {
                System.out.println(plant.getWateringInfo());
            }
        } catch (PlantException e) {
            System.err.println("Nastala chyba: " + e.getMessage());
        }
    }

    static class LastWateringComparator implements Comparator<Plant> {
        @Override
        public int compare(Plant plant1, Plant plant2) {
            return plant1.getWatering().compareTo(plant2.getWatering());


        }
}




}
