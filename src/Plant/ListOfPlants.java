package Plant;

import jdk.jfr.Category;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

    public Plant getPlant(int index) {
        if (index >= 0 && index < plantList.size()) {
            return plantList.get(index);
        } else {
            return null;
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
                String[] parts = line.split(Settings.getDelimiter());
                if (parts.length != 5) {
                    throw new PlantException(
                            "Nesprávný počet položek na řádku číslo: " + lineCounter + ": " + line + "!");
                }
                String name = parts[0];
                String notes = parts.length > 1 ? parts[1] : "";
                int frequencyOfWatering = Integer.parseInt(parts[2]);
                LocalDate planted = LocalDate.parse(parts[3]);
                LocalDate watering = LocalDate.parse(parts[4]);
                Plant plant = new Plant(name, notes, planted, watering, frequencyOfWatering);
                plantList.add(plant);
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Soubor " + fileName + " nebyl nalezen!\n" + e.getLocalizedMessage());
        } catch (IllegalArgumentException | DateTimeParseException e) {
            String errorMessage;
            if (e instanceof NumberFormatException) {
                errorMessage = "Chyba při čtení číselné hodnoty";
            } else if (e instanceof IllegalArgumentException) {
                errorMessage = "Chyba při čtení kategorie";
            } else {
                errorMessage = "Chyba při čtení data";
            }
            throw new PlantException(errorMessage + " na řádku číslo: " + lineCounter + ":\n"
                    + e.getLocalizedMessage());
        }
    }


    public void saveUpdatedListToFile(String fileName) throws PlantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Plant plant : plantList) {
                writer.println(plant.getName() + ","
                        + plant.getNotes() + "," + plant.getPlanted() + "," + plant.getWatering() + ","
                        + plant.getFrequencyOfWatering());
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Soubor " + fileName + " nebyl nalezen\n!" + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new PlantException("Chyba při zápisu aktualizovaného seznamu do souboru: " + e.getLocalizedMessage());
        }

}
}