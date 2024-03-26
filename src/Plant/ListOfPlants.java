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


    public void loadPlantsFromFile(String filename) throws PlantException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            plantList = (List<Plant>) inputStream.readObject();
            System.out.println("List of plants loaded from file: " + filename);
        } catch (FileNotFoundException e) {
            throw new PlantException("File '" + filename + "' not found.");
        } catch (IOException | ClassNotFoundException e) {
            throw new PlantException("Error reading plants from file: " + e.getMessage());
        }
    }




        };



