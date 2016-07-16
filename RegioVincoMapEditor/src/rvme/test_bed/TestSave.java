/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvme.test_bed;

import java.io.IOException;
import javafx.scene.paint.Color;
import rvme.MapEditorApp;
import rvme.data.DataManager;
import rvme.data.SubRegion;
import rvme.file.FileManager;

/**
 *
 * @author arjun rao
 */
public class TestSave {
    
    /**Have your driver class hard code the creation of all the necessary data values
     * such that the Andorra map and data could be created. This means everything.
     * The map data, the images, the background color, the border color and thickness, etc.
     * Note that your TestSave class would artificially initialize all the data such that
     * it can then be used by your file manager to save it to /work/Andorra.json, which again,
     * would be in your own JSON format.
     */
    //might need this if i need to get it from the junit testing
    public static DataManager dataManager;
    
    public DataManager getDataManager() {return dataManager;}
    
    public static void main(String[] args) throws IOException {
        MapEditorApp app = new MapEditorApp();
        dataManager = new DataManager(app);
        FileManager fileManager = new FileManager();
        //FileManager fileManager = (FileManager)app.getFileComponent();
        
        
        //create a new JSON file on save
        //on load, loads THAT Json file
        //call method in FileManager that does the saving of data / json file, similar to HW1
        
        //hardcoding Andorra
    
        //step 1: Create Regions
        //step 2: Import All data into data

        //step 1 - need to access the path to the JSON file - can just note this and use in the datamanager
        //the datamanager/workspace should be able to access the file and then create the subregions
        //as it does for the json files from hw2
        String rawMapPath = "./HW5SampleData/raw_map_data/Andorra.json";
        //load the data from andorra.json into the polygon list in the datamanager
        //it's okay that this part isn't exactly 'hard-coded' because who's gonna put all of the andorra points
        //into this class, amirite?
        dataManager.getPolygonList().clear();
        fileManager.loadPolygonData(dataManager, rawMapPath);
        //System.out.println(dataManager.getPolygonList().size());
        //add a subregion for each polygon
        //for (int i = 0; i < dataManager.getPolygonList().size(); i++) {
            //SubRegion temp = new SubRegion();
            //temp.setSubregionBorderThickness(1.0);
            //dataManager.getSubregions().add(temp);
        //}
        //put the relevant data inside each subregion of the subregions list of the dataManager
        SubRegion sub1 = new SubRegion();
        sub1.setSubregionName("Ordino");
        sub1.setCapitalName("Ordino (town)");
        sub1.setLeaderName("Ventura Espot");
        sub1.setSubregionColor(Color.rgb(200, 200, 200));
        sub1.setFlagImagePath("./export/The World/Europe/Andorra/" + sub1.getSubregionName() + " Flag.png");
        sub1.setLeaderImagePath("./export/The World/Europe/Andorra/" + sub1.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub1);
        
        SubRegion sub2 = new SubRegion("Canillo", "Canillo (town)", "Enric Casadevall Medrano");
        sub2.setSubregionColor(Color.rgb(198, 198, 198));
        sub2.setFlagImagePath("./export/The World/Europe/Andorra/" + sub2.getSubregionName() + " Flag.png");
        sub2.setLeaderImagePath("./export/The World/Europe/Andorra/" + sub2.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub2);
        
        SubRegion sub3 = new SubRegion("Encamp", "Encamp (town)", "Miquel Alís Font");
        sub3.setSubregionColor(Color.rgb(196, 196, 196));
        sub3.setFlagImagePath("./export/The World/Europe/Andorra/" + sub3.getSubregionName() + " Flag.png");
        sub3.setLeaderImagePath("./export/The World/Europe/Andorra/" + sub3.getLeaderName() + ".png");//"Miquel Al°s Font.png"
        dataManager.getSubregions().add(sub3);
        
        SubRegion sub4 = new SubRegion("Escaldes-Engordany", "Escaldes-Engordany (town)", "Montserrat Capdevila Pallarés");
        sub4.setSubregionColor(Color.rgb(194, 194, 194));
        sub4.setFlagImagePath("./export/The World/Europe/Andorra/" + sub4.getSubregionName() + " Flag.png");
        sub4.setLeaderImagePath("./export/The World/Europe/Andorra/" + sub4.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub4);
        
        SubRegion sub5 = new SubRegion("La Massana", "La Massana (town)", "Josep Areny");
        sub5.setSubregionColor(Color.rgb(192, 192, 192));
        sub5.setFlagImagePath("./export/The World/Europe/Andorra/" + sub5.getSubregionName() + " Flag.png");
        sub5.setLeaderImagePath("./export/The World/Europe/Andorra/" + sub5.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub5);
        
        SubRegion sub6 = new SubRegion("Andorra la Vella", "Andorra la Vella (town)", "Maria Rosa Ferrer Obiols");
        sub6.setSubregionColor(Color.rgb(190, 190, 190));
        sub6.setFlagImagePath("./export/The World/Europe/Andorra/" + sub6.getSubregionName() + " Flag.png");
        sub6.setLeaderImagePath("./export/The World/Europe/Andorra/" + sub6.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub6);
        
        SubRegion sub7 = new SubRegion("Sant Julia de Loria", "Sant Julia de Loria (town)", "Josep Pintat Forné");
        sub7.setSubregionColor(Color.rgb(188, 188, 188));
        sub7.setFlagImagePath("./export/The World/Europe/Andorra/" + sub7.getSubregionName() + " Flag.png");
        sub7.setLeaderImagePath("./export/The World/Europe/Andorra/" + sub7.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub7);
        
        dataManager.setBackgroundColor(Color.rgb(220,110,0));
        dataManager.setBorderColor(Color.BLACK);
        dataManager.setMapName("Andorra");
        dataManager.setRawMapPath("./raw_map_data/Andorra.json");
        dataManager.setParentDirectory("to be set when the new button is pressed, not needed for HW5");
        dataManager.setRegionFlagImagePath("./export/The World/Europe/Andorra Flag.png");
        dataManager.setCoatOfArmsImagePath("./export/The World/Europe/Andorra Coa.png");
        dataManager.setZoom(400.0);
        //the above Coa pic was found online and saved in the data
        
        // SAVE IT TO A FILE
	fileManager.saveData(dataManager, "./work/Andorra.json");
        
    }
}
