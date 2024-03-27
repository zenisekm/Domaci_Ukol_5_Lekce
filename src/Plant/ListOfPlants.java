package Plant;

import java.io.*;
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
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Soubor " + fileName + " nebyl nalezen\n!" + e.getLocalizedMessage());
        } catch (Exception e) {
            throw new PlantException("Nastala chyba při práci se souborem: " + fileName + "\n" + e.getLocalizedMessage());
        }
    }

        public void saveDataToFile(String fileName) throws PlantException {
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
                for (Plant plant : plantList) {
                    writer.println(plant.getName() + ","
                            + plant.getNotes() + "," + plant.getPlanted() + "," + plant.getWatering() + ","
                            + plant.getFrequencyOfWatering());
                }
            } catch (FileNotFoundException e) {
                throw new PlantException("Soubor " + fileName + " nebyl nalezen\n!" + e.getLocalizedMessage());
            } catch (IOException e) {
                throw new PlantException("Chyba výstupu při zápisu do souboru" +fileName +"\n" + e.getLocalizedMessage());
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