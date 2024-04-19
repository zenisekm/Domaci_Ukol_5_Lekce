package Plant;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Plant implements Comparable <Plant> {

        private String name;
        private String notes;
        private LocalDate planted;
        private LocalDate watering;
        private int frequencyOfWatering;

       public Plant(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) {
           this.name = name;
           this.notes = notes;
           this.planted = planted;
           this.watering = watering;
           this.frequencyOfWatering = frequencyOfWatering;
       }

       public Plant(String name) {
           this(name, "", LocalDate.now(), LocalDate.now(), 7);
       }

       public Plant(String name, int frequencyOfWatering) {
           this(name, "", LocalDate.now(), LocalDate.now(), frequencyOfWatering);
       }

       public Plant(String name, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException {
           this(name, "", LocalDate.now(), LocalDate.now(), frequencyOfWatering);
           setPlanted(planted);
           setWatering(watering);
       }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public LocalDate getPlanted() {
            return planted;
        }

        public void setPlanted(LocalDate planted) {
            this.planted = planted;
        }

        public LocalDate getWatering() {
            return watering;
        }

        public int getFrequencyOfWatering() {
            return frequencyOfWatering;
        }

        public String getWateringInfo() {
            LocalDate nextWatering = watering.plusDays(frequencyOfWatering);
            return "name of plant: " + name +
                    " / last watering date: " + watering +
                    " / next watering: " + nextWatering;
        }

         public void setFrequencyOfWatering(int frequencyOfWatering)
             throws PlantException {
             if (frequencyOfWatering <= 0) {
                 throw new PlantException ("Frequency of watering can't be zero or in negative numbers");
             }
             this.frequencyOfWatering = frequencyOfWatering;
         }

         public void setWatering(LocalDate watering) throws PlantException {
            if (watering.isBefore(planted)) {
                throw new PlantException ("Watering day can't be before plant");
            }
            this.watering = watering;
         }



    public static void sortPlantsByLastWatering() {
        String filename = Settings.getFilename();
        ListOfPlants plants = new ListOfPlants();

        try {
            plants.loadDataFromFile(filename);


            List<Plant> sortedPlants = plants.getPlantList();
            Collections.sort(sortedPlants, new Plant.LastWateringComparator());


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


        @Override
        public  int compareTo(Plant other) {
            return this.name.compareTo(other.name);
        }




}
