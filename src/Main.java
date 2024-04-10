import Plant.ListOfPlants;
import Plant.Plant;
import Plant.PlantException;
import Plant.Settings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {




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


}





