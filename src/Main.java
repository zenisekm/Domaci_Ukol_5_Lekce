import Plant.ListOfPlants;
import Plant.Plant;
import Plant.PlantException;
import Plant.TextData;
import org.w3c.dom.Text;

public class Main {
    public static void main(String[] args) {

        String filename = TextData.getFilename();
        ListOfPlants plants = new ListOfPlants();

           try {
               plants.loadDataFromFile(filename);
           } catch (PlantException e) {
               System.err.println(
                       "Nastala chyba při načítání obsahu košíku ze souboru:"
                               + filename +
                               ":\n "
                               + e.getLocalizedMessage() + "\n");


           }
    }
}
