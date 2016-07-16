/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvme.test_bed;

import javafx.scene.paint.Color;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rvme.MapEditorApp;
import rvme.data.DataManager;
import rvme.data.SubRegion;
import rvme.file.FileManager;
import static rvme.test_bed.TestSave.dataManager;
import static junit.framework.TestCase.assertEquals;

/**
 *
 * @author arjun rao
 */
public class SaveAndLoadTest {
    
    public SaveAndLoadTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("* UtilsJUnit4Test: @BeforeClass method");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("* UtilsJUnit4Test: @AfterClass method");
    }

    /**
     * Test of main method, of class TestSave.
     */
    @Test
    public void testAndorra() throws Exception {
        MapEditorApp app = new MapEditorApp();
        MapEditorApp app2 = new MapEditorApp();
        dataManager = new DataManager(app);
        DataManager dataManager2 = new DataManager(app2);
        FileManager fileManager = new FileManager();
        //hardcoding Andorra
        //step 1: Create Regions
        //step 2: Import All data into data
        String rawMapPath = "./HW5SampleData/raw_map_data/Andorra.json";
        //load the data from andorra.json into the polygon list in the datamanager
        dataManager.getPolygonList().clear();
        fileManager.loadPolygonData(dataManager, rawMapPath);
        //System.out.println(dataManager.getPolygonList().size());
        
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
        dataManager.setZoom(100.0);
        //the above Coa pic was found online and saved in the data
        
        // SAVE IT TO A FILE
	fileManager.saveData(dataManager, "./work/Andorra.json");
        
        fileManager.loadData(dataManager2, "./work/Andorra.json");
        
        //compare dataManager and dataManager2, and assert stuff
        assertEquals(dataManager.getMapName(), dataManager2.getMapName());
        assertEquals(dataManager.getParentDirectory(), dataManager2.getParentDirectory());
        assertEquals(dataManager.getBackgroundColor(), dataManager2.getBackgroundColor());
        assertEquals(dataManager.getBorderColor(), dataManager2.getBorderColor());
        assertEquals(dataManager.getRawMapPath(), dataManager2.getRawMapPath());
        assertEquals(dataManager.getRegionFlagImagePath(), dataManager2.getRegionFlagImagePath());
        assertEquals(dataManager.getCoatOfArmsImagePath(), dataManager2.getCoatOfArmsImagePath());
        assertEquals(dataManager.getMapPositionX(), dataManager2.getMapPositionX());
        assertEquals(dataManager.getMapPositionY(), dataManager2.getMapPositionY());
        assertEquals(dataManager.getPolygonList().size(), dataManager2.getPolygonList().size());
        assertEquals(dataManager.getZoom(), dataManager2.getZoom());
        
        for (int j = 0; j < dataManager.getSubregions().size(); j++) {
            //so they are the same in terms of data... but they don't point to the same object.
            //that's not a problem is it? Is this the right way to use assertEquals?
            SubRegion temp1 = dataManager.getSubregions().get(j);
            SubRegion temp2 = dataManager2.getSubregions().get(j);
            assertEquals(temp1.getSubregionName(),temp2.getSubregionName());
            assertEquals(temp1.getCapitalName(),temp2.getCapitalName());
            assertEquals(temp1.getLeaderName(),temp2.getLeaderName());
            assertEquals(temp1.getFlagImagePath(),temp2.getFlagImagePath());
            assertEquals(temp1.getLeaderImagePath(),temp2.getLeaderImagePath());
            assertEquals(temp1.getSubregionColor(),temp2.getSubregionColor());
            assertEquals(temp1.getSubregionBorderThickness(),temp2.getSubregionBorderThickness());
            
        }
        
    }
    
    
    
    @Test
    public void testSanMarino() throws Exception {
        MapEditorApp app = new MapEditorApp();
        MapEditorApp app2 = new MapEditorApp();
        dataManager = new DataManager(app);
        DataManager dataManager2 = new DataManager(app2);
        FileManager fileManager = new FileManager();
        //hardcoding Andorra
        //step 1: Create Regions
        //step 2: Import All data into data
        String rawMapPath = "./HW5SampleData/raw_map_data/San Marino.json";
        //load the data from andorra.json into the polygon list in the datamanager
        dataManager.getPolygonList().clear();
        fileManager.loadPolygonData(dataManager, rawMapPath);
        //System.out.println(dataManager.getPolygonList().size());
        
        //put the relevant data inside each subregion of the subregions list of the dataManager
        SubRegion sub1 = new SubRegion();
        sub1.setSubregionName("Acquaviva");
        sub1.setCapitalName("");
        sub1.setLeaderName("Lucia Tamagnini");
        sub1.setSubregionColor(Color.rgb(225, 225, 225));
        sub1.setFlagImagePath("./export/The World/Europe/San Marino/" + sub1.getSubregionName() + " Flag.png");
        sub1.setLeaderImagePath("./export/The World/Europe/San Marino/" + sub1.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub1);
        
        SubRegion sub2 = new SubRegion("Borgo Maggiore", "", "Sergio Nanni");
        sub2.setSubregionColor(Color.rgb(200, 200, 200));
        sub2.setFlagImagePath("./export/The World/Europe/San Marino/" + sub2.getSubregionName() + " Flag.png");
        sub2.setLeaderImagePath("./export/The World/Europe/San Marino/" + sub2.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub2);
        
        SubRegion sub3 = new SubRegion("Chiesanuova", "", "Franco Santi");
        sub3.setSubregionColor(Color.rgb(175, 175, 175));
        sub3.setFlagImagePath("./export/The World/Europe/San Marino/" + sub3.getSubregionName() + " Flag.png");
        sub3.setLeaderImagePath("./export/The World/Europe/San Marino/" + sub3.getLeaderName() + ".png");//"Miquel Al°s Font.png"
        dataManager.getSubregions().add(sub3);
        
        SubRegion sub4 = new SubRegion("Domagnano", "", "Gabriel Guidi");
        sub4.setSubregionColor(Color.rgb(150, 150, 150));
        sub4.setFlagImagePath("./export/The World/Europe/San Marino/" + sub4.getSubregionName() + " Flag.png");
        sub4.setLeaderImagePath("./export/The World/Europe/San Marino/" + sub4.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub4);
        
        SubRegion sub5 = new SubRegion("Faetano", "", "Pier Mario Bedetti");
        sub5.setSubregionColor(Color.rgb(125, 125, 125));
        sub5.setFlagImagePath("./export/The World/Europe/San Marino/" + sub5.getSubregionName() + " Flag.png");
        sub5.setLeaderImagePath("./export/The World/Europe/San Marino/" + sub5.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub5);
        
        SubRegion sub6 = new SubRegion("Fiorentino", "", "Gerri Fabbri");
        sub6.setSubregionColor(Color.rgb(100, 100, 100));
        sub6.setFlagImagePath("./export/The World/Europe/San Marino/" + sub6.getSubregionName() + " Flag.png");
        sub6.setLeaderImagePath("./export/The World/Europe/San Marino/" + sub6.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub6);
        
        SubRegion sub7 = new SubRegion("Montegiardino", "", "Marta Fabbri");
        sub7.setSubregionColor(Color.rgb(75, 75, 75));
        sub7.setFlagImagePath("./export/The World/Europe/San Marino/" + sub7.getSubregionName() + " Flag.png");
        sub7.setLeaderImagePath("./export/The World/Europe/San Marino/" + sub7.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub7);
        
        SubRegion sub8 = new SubRegion("City of San Marino", "", "Maria Teresa Beccari");
        sub8.setSubregionColor(Color.rgb(50, 50, 50));
        sub8.setFlagImagePath("./export/The World/Europe/San Marino/" + sub8.getSubregionName() + " Flag.png");
        sub8.setLeaderImagePath("./export/The World/Europe/San Marino/" + sub8.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub8);
        
        SubRegion sub9 = new SubRegion("Serravalle", "", "Leandro Maiani");
        sub9.setSubregionColor(Color.rgb(25, 25, 25));
        sub9.setFlagImagePath("./export/The World/Europe/San Marino/" + sub9.getSubregionName() + " Flag.png");
        sub9.setLeaderImagePath("./export/The World/Europe/San Marino/" + sub9.getLeaderName() + ".png");
        dataManager.getSubregions().add(sub9);
        
        dataManager.setBackgroundColor(Color.rgb(220,110,0));
        dataManager.setBorderColor(Color.BLACK);
        dataManager.setMapName("San Marino");
        dataManager.setRawMapPath("./raw_map_data/San Marino.json");
        dataManager.setParentDirectory("to be set when the new button is pressed, not needed for HW5");
        dataManager.setRegionFlagImagePath("./export/The World/Europe/San Marino Flag.png");
        dataManager.setCoatOfArmsImagePath("./export/The World/Europe/San Marino Coa.png");
        dataManager.setZoom(1000.0);
        //the above Coa pic was found online and saved in the data
        
        // SAVE IT TO A FILE
	fileManager.saveData(dataManager, "./work/San Marino.json");
        
        fileManager.loadData(dataManager2, "./work/San Marino.json");
        
        //compare dataManager and dataManager2, and assert stuff
        assertEquals(dataManager.getMapName(), dataManager2.getMapName());
        assertEquals(dataManager.getParentDirectory(), dataManager2.getParentDirectory());
        assertEquals(dataManager.getBackgroundColor(), dataManager2.getBackgroundColor());
        assertEquals(dataManager.getBorderColor(), dataManager2.getBorderColor());
        assertEquals(dataManager.getRawMapPath(), dataManager2.getRawMapPath());
        assertEquals(dataManager.getRegionFlagImagePath(), dataManager2.getRegionFlagImagePath());
        assertEquals(dataManager.getCoatOfArmsImagePath(), dataManager2.getCoatOfArmsImagePath());
        assertEquals(dataManager.getMapPositionX(), dataManager2.getMapPositionX());
        assertEquals(dataManager.getMapPositionY(), dataManager2.getMapPositionY());
        assertEquals(dataManager.getPolygonList().size(), dataManager2.getPolygonList().size());
        assertEquals(dataManager.getZoom(), dataManager2.getZoom());
        
        for (int j = 0; j < dataManager.getSubregions().size(); j++) {
            //so they are the same in terms of data... but they don't point to the same object.
            //that's not a problem is it? Is this the right way to use assertEquals?
            SubRegion temp1 = dataManager.getSubregions().get(j);
            SubRegion temp2 = dataManager2.getSubregions().get(j);
            assertEquals(temp1.getSubregionName(),temp2.getSubregionName());
            assertEquals(temp1.getCapitalName(),temp2.getCapitalName());
            assertEquals(temp1.getLeaderName(),temp2.getLeaderName());
            assertEquals(temp1.getFlagImagePath(),temp2.getFlagImagePath());
            assertEquals(temp1.getLeaderImagePath(),temp2.getLeaderImagePath());
            assertEquals(temp1.getSubregionColor(),temp2.getSubregionColor());
            assertEquals(temp1.getSubregionBorderThickness(),temp2.getSubregionBorderThickness());
            
        }
    }
    
    
    
    @Test
    public void testSlovakia() throws Exception {
        MapEditorApp app = new MapEditorApp();
        MapEditorApp app2 = new MapEditorApp();
        dataManager = new DataManager(app);
        DataManager dataManager2 = new DataManager(app2);
        FileManager fileManager = new FileManager();
        //hardcoding Andorra
        //step 1: Create Regions
        //step 2: Import All data into data
        String rawMapPath = "./HW5SampleData/raw_map_data/Slovakia.json";
        //load the data from andorra.json into the polygon list in the datamanager
        dataManager.getPolygonList().clear();
        fileManager.loadPolygonData(dataManager, rawMapPath);
        //System.out.println(dataManager.getPolygonList().size());
        
        //put the relevant data inside each subregion of the subregions list of the dataManager
        SubRegion sub1 = new SubRegion();
        sub1.setSubregionName("Bratislava");
        sub1.setCapitalName("");
        sub1.setLeaderName("");
        sub1.setSubregionColor(Color.rgb(250, 250, 250));
        sub1.setFlagImagePath("");
        sub1.setLeaderImagePath("");
        dataManager.getSubregions().add(sub1);
        
        SubRegion sub2 = new SubRegion("Trnava", "", "");
        sub2.setSubregionColor(Color.rgb(249, 249, 249));
        sub2.setFlagImagePath("");
        sub2.setLeaderImagePath("");
        dataManager.getSubregions().add(sub2);
        
        SubRegion sub3 = new SubRegion("Trencin", "", "");
        sub3.setSubregionColor(Color.rgb(248, 248, 248));
        sub3.setFlagImagePath("");
        sub3.setLeaderImagePath("");
        dataManager.getSubregions().add(sub3);
        
        SubRegion sub4 = new SubRegion("Nitra", "", "");
        sub4.setSubregionColor(Color.rgb(247, 247, 247));
        sub4.setFlagImagePath("");
        sub4.setLeaderImagePath("");
        dataManager.getSubregions().add(sub4);
        
        SubRegion sub5 = new SubRegion("Zilina", "", "");
        sub5.setSubregionColor(Color.rgb(246, 246, 246));
        sub5.setFlagImagePath("");
        sub5.setLeaderImagePath("");
        dataManager.getSubregions().add(sub5);
        
        SubRegion sub6 = new SubRegion("Banska Bystrica", "", "");
        sub6.setSubregionColor(Color.rgb(245, 245, 245));
        sub6.setFlagImagePath("");
        sub6.setLeaderImagePath("");
        dataManager.getSubregions().add(sub6);
        
        SubRegion sub7 = new SubRegion("Presov", "", "");
        sub7.setSubregionColor(Color.rgb(244, 244, 244));
        sub7.setFlagImagePath("");
        sub7.setLeaderImagePath("");
        dataManager.getSubregions().add(sub7);
        
        SubRegion sub8 = new SubRegion("Kosice", "", "");
        sub8.setSubregionColor(Color.rgb(243, 243, 243));
        sub8.setFlagImagePath("");
        sub8.setLeaderImagePath("");
        dataManager.getSubregions().add(sub8);
        
        
        dataManager.setBackgroundColor(Color.rgb(220,110,0));
        dataManager.setBorderColor(Color.BLACK);
        dataManager.setMapName("Slovakia");
        dataManager.setRawMapPath("./raw_map_data/Slovakia.json");
        dataManager.setParentDirectory("to be set when the new button is pressed, not needed for HW5");
        dataManager.setRegionFlagImagePath("./export/The World/Europe/Slovakia Flag.png");
        dataManager.setCoatOfArmsImagePath("./export/The World/Europe/Slovakia Coa.png");
        dataManager.setZoom(25.0);
        //the above Coa pic was found online and saved in the data
        
        // SAVE IT TO A FILE
	fileManager.saveData(dataManager, "./work/Slovakia.json");
        
        fileManager.loadData(dataManager2, "./work/Slovakia.json");
        
        //compare dataManager and dataManager2, and assert stuff
        assertEquals(dataManager.getMapName(), dataManager2.getMapName());
        assertEquals(dataManager.getParentDirectory(), dataManager2.getParentDirectory());
        assertEquals(dataManager.getBackgroundColor(), dataManager2.getBackgroundColor());
        assertEquals(dataManager.getBorderColor(), dataManager2.getBorderColor());
        assertEquals(dataManager.getRawMapPath(), dataManager2.getRawMapPath());
        assertEquals(dataManager.getRegionFlagImagePath(), dataManager2.getRegionFlagImagePath());
        assertEquals(dataManager.getCoatOfArmsImagePath(), dataManager2.getCoatOfArmsImagePath());
        assertEquals(dataManager.getMapPositionX(), dataManager2.getMapPositionX());
        assertEquals(dataManager.getMapPositionY(), dataManager2.getMapPositionY());
        assertEquals(dataManager.getPolygonList().size(), dataManager2.getPolygonList().size());
        assertEquals(dataManager.getZoom(), dataManager2.getZoom());
        
        for (int j = 0; j < dataManager.getSubregions().size(); j++) {
            //so they are the same in terms of data... but they don't point to the same object.
            //that's not a problem is it? Is this the right way to use assertEquals?
            SubRegion temp1 = dataManager.getSubregions().get(j);
            SubRegion temp2 = dataManager2.getSubregions().get(j);
            assertEquals(temp1.getSubregionName(),temp2.getSubregionName());
            assertEquals(temp1.getCapitalName(),temp2.getCapitalName());
            assertEquals(temp1.getLeaderName(),temp2.getLeaderName());
            assertEquals(temp1.getFlagImagePath(),temp2.getFlagImagePath());
            assertEquals(temp1.getLeaderImagePath(),temp2.getLeaderImagePath());
            assertEquals(temp1.getSubregionColor(),temp2.getSubregionColor());
            assertEquals(temp1.getSubregionBorderThickness(),temp2.getSubregionBorderThickness());
            
        }
    }
}
