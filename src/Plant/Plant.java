package Plant;

import java.time.LocalDate;

public class Plant {

        private String name;
        private String notes;
        private LocalDate planted;
        private LocalDate watering;
        private int frequencyOfWatering;

        public Plant (String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) {
            this.name = name;
            this.notes = notes;
            this.planted = planted;
            this.watering = watering;
            this.frequencyOfWatering = frequencyOfWatering;
        }

        public Plant(String name) {
            this(name,"", LocalDate.now(), LocalDate.now(), 7);
        }

        public Plant(String name, int frequencyOfWatering) {
            this(name, "", LocalDate.now(), LocalDate.now(), frequencyOfWatering);
        }

        public Plant(String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException {
            this.name = name;
            setNotes(null);
            setPlanted(LocalDate.now());
            setWatering(LocalDate.now());
            setFrequencyOfWatering(7);
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









}
