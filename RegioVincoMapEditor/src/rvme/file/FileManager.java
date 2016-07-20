/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvme.file;

import java.util.concurrent.locks.ReentrantLock;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import rvme.data.DataManager;
import rvme.data.SubRegion;
import rvme.gui.Workspace;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;

/**
 *
 * @author McKillaGorilla
 */
public class FileManager implements AppFileComponent {
    DataManager dataManager;
    Double maxX = 0.0;
    Double maxY = 0.0;
    Double minX = 0.0;
    Double minY = 0.0;
    int temp = 0;
    
    //ProgressBar bar;
    //Button button;
    //Label processLabel;
    //int numTasks = 0;
    //ReentrantLock progressLock;
    
    
    
    // FOR JSON SAVING AND LOADING
    static final String JSON_CATEGORY = "category";
    static final String JSON_DESCRIPTION = "description";
    static final String JSON_START_DATE = "start_date";
    static final String JSON_END_DATE = "end_date";
    static final String JSON_COMPLETED = "completed";
    
    static final String JSON_NAME = "name";
    static final String JSON_OWNER = "owner";
    static final String JSON_ITEMS = "items";
    
    
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	dataManager = (DataManager)data;
	
	//All of the datamanager data
        //also need to access the path to the JSON file - can just note this and use in the datamanager
        //the datamanager/workspace should be able to access the file and then create the subregions
        //as it does for the json files from hw2
        String rawMapPath;
        String mapName;
        String parentDirec;
        String backgroundColorRed, borderColorRed;
        String backgroundColorBlue, borderColorBlue;
        String backgroundColorGreen, borderColorGreen;
        //can always use Double.parseDouble if I want these strings to return to double values
        
        if (dataManager.getMapName() == null)
            mapName = "";
        else
            mapName = dataManager.getMapName();
        if (dataManager.getRawMapPath() == null)
            rawMapPath = "";
        else
            rawMapPath = dataManager.getRawMapPath();
        if (dataManager.getParentDirectory() == null)
            parentDirec = "";
        else
            parentDirec = dataManager.getParentDirectory();
        if (dataManager.getBackgroundColor()== null) {
            backgroundColorRed = "";backgroundColorBlue = "";backgroundColorGreen = "";
            
        }
        else {
            
            backgroundColorRed = String.valueOf(dataManager.getBackgroundColor().getRed());
            backgroundColorBlue = String.valueOf(dataManager.getBackgroundColor().getBlue());
            backgroundColorGreen = String.valueOf(dataManager.getBackgroundColor().getGreen());
        }
        if (dataManager.getBorderColor()== null) {
            borderColorRed = "";borderColorBlue = "";borderColorGreen = "";
        }
        else {
            borderColorRed = String.valueOf(dataManager.getBorderColor().getRed());
            borderColorBlue = String.valueOf(dataManager.getBorderColor().getBlue());
            borderColorGreen = String.valueOf(dataManager.getBorderColor().getGreen());
        }
        
        
	// NOW BUILD THE JSON ARRAY FOR THE LIST
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	ObservableList<SubRegion> subregions = dataManager.getSubregions();
	for (SubRegion item : subregions) {
	    JsonObject itemJson = Json.createObjectBuilder()
		    .add("subregion_name", item.getSubregionName())
		    .add("capital_name", item.getCapitalName())
		    .add("leader_name", item.getLeaderName())
		    .add("flag_image_path", item.getFlagImagePath())
                    .add("leader_image_path", item.getLeaderImagePath())
                    .add("red", String.valueOf(item.getSubregionColor().getRed()))
                    .add("blue", String.valueOf(item.getSubregionColor().getBlue()))
                    .add("green", String.valueOf(item.getSubregionColor().getGreen()))
                    .add("border_thickness", String.valueOf(item.getSubregionBorderThickness())).build();
	    arrayBuilder.add(itemJson);
	}
	JsonArray itemsArray = arrayBuilder.build();
	
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add("map_name", mapName)
                .add("parent_directory", parentDirec)
                .add("background_color_red", backgroundColorRed)
                .add("background_color_blue", backgroundColorBlue)
                .add("background_color_green", backgroundColorGreen)
                .add("border_color_blue", borderColorBlue)
                .add("border_color_red", borderColorRed)
                .add("border_color_green", borderColorGreen)
                .add("raw_map_path", dataManager.getRawMapPath())
                .add("region_flag_image_path", dataManager.getRegionFlagImagePath())
                .add("coat_of_arms_image_path", dataManager.getCoatOfArmsImagePath())
                .add("map_position_x", dataManager.getMapPositionX())
                .add("map_position_y", dataManager.getMapPositionY())
                .add("flag_image_pos_x", dataManager.getFlagImagePos().getX())
                .add("flag_image_pos_y", dataManager.getFlagImagePos().getY())
                .add("coa_image_pos_x", dataManager.getCoatOfArmsImagePos().getX())
                .add("coa_image_pos_y", dataManager.getCoatOfArmsImagePos().getY())
                .add("zoom", dataManager.getZoom())
		.add("subregions", itemsArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    //note: the following method is referenced in the test classes and simply puts the polygons
    //from a file into the polygon list. Actually I should change it so it does both necessary things.
    
    public void loadPolygonData(AppDataComponent data, String filePath) throws IOException {
        
        // CLEAR THE OLD DATA OUT
	dataManager = (DataManager)data;
	dataManager.reset();
        resetVariables();
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
        System.out.println(filePath);
        
	JsonArray list = json.getJsonArray("SUBREGIONS");
        
        System.out.println("list size:" + list.size());
        
	for (int i = 0; i < list.size(); i++) {
            System.out.println("what");
	    JsonObject subregion = list.getJsonObject(i);
	    ArrayList<Polygon> temp;
            temp = loadSubregion(subregion);
            for (Polygon x: temp) {
                //Polygon y = dataManager.convertPolygon(x);
                dataManager.addPolygon(x);
                
            }
	}
        
    }
    
    
    public void loadProgressBar() {
        //I commented this stuff out bc we're not required to have a progress bar
        /**
        progressLock = new ReentrantLock();
        VBox box = new VBox();

        HBox toolbar = new HBox();
        bar = new ProgressBar(0);
        toolbar.getChildren().add(bar);
        processLabel = new Label();
        processLabel.setFont(new Font("Serif", 12));
        processLabel.setText("Progress Bar");
        box.getChildren().add(processLabel);
        box.getChildren().add(toolbar);
        
        Workspace ws = (Workspace)dataManager.getMapEditorApp().getWorkspaceComponent();
        box.setAlignment(Pos.BOTTOM_LEFT);
        ws.getStackPane().getChildren().add(box);
        
        Task<Void> task = new Task<Void>() {
            int task = numTasks++;
            double max = 200;
            double perc;
            @Override
            protected Void call() throws Exception {
                try {
                    progressLock.lock();
                for (int i = 0; i < 200; i++) {
                    
                    perc = i/max;

                    // THIS WILL BE DONE ASYNCHRONOUSLY VIA MULTITHREADING
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //updating the bar!
                            bar.setProgress(perc);
                            //processLabel.setText("Task #" + task);
                        }
                    });

                    // SLEEP EACH FRAME
                    Thread.sleep(2);
                }}
                finally {
                    // WHAT DO WE NEED TO DO HERE?
                    //unlock!
                    progressLock.unlock();
                        }
                return null;
            }
        };
        // THIS GETS THE THREAD ROLLING
        Thread thread = new Thread(task);
        thread.start();
        * 
        */
    }
    
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        // CLEAR THE OLD DATA OUT
	dataManager = (DataManager)data;
	dataManager.reset();
        
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
        
        //hw5 - this was added to load the polygon data
        loadPolygonData(dataManager, json.getString("raw_map_path"));
                
        //Put each relevant string/datafield into the datamanager
        //woops. should've just been doing this, not what I did for the rest... oh well, I'll do it right in the array
        dataManager.setMapName(json.getString("map_name"));
        
        JsonString parentDir = json.getJsonString("parent_directory");
        dataManager.setParentDirectory(parentDir.getString());
        
        JsonString bgColorRed = json.getJsonString("background_color_red");
        JsonString bgColorBlue = json.getJsonString("background_color_blue");
        JsonString bgColorGreen = json.getJsonString("background_color_green");
        dataManager.setBackgroundColor(Color.color(Double.parseDouble(bgColorRed.getString()),Double.parseDouble(bgColorGreen.getString()),Double.parseDouble(bgColorBlue.getString())));
        
        JsonString borderColorRed = json.getJsonString("border_color_red");
        JsonString borderColorBlue = json.getJsonString("border_color_blue");
        JsonString borderColorGreen = json.getJsonString("border_color_green");
        dataManager.setBorderColor(Color.color(Double.parseDouble(borderColorRed.getString()),Double.parseDouble(borderColorGreen.getString()),Double.parseDouble(borderColorBlue.getString())));
        
        Double posX = getDataAsDouble(json, "map_position_x");
        Double posY = getDataAsDouble(json, "map_position_y");
        Double zoom = getDataAsDouble(json, "zoom");
        dataManager.setPosX(posX);
        dataManager.setPosY(posY);
        dataManager.setZoom(zoom);
        
        Double flagPosX = getDataAsDouble(json, "flag_image_pos_x");
        Double flagPosY = getDataAsDouble(json, "flag_image_pos_y");
        Double coaPosX = getDataAsDouble(json, "coa_image_pos_x");
        Double coaPosY = getDataAsDouble(json, "coa_image_pos_y");
        dataManager.setCoatOfArmsImagePos(coaPosX, coaPosY);
        dataManager.setFlagImagePos(flagPosX, flagPosY);
        
        dataManager.setRegionFlagImagePath(json.getString("region_flag_image_path"));
        dataManager.setCoatOfArmsImagePath(json.getString("coat_of_arms_image_path"));
        dataManager.setRawMapPath(json.getString("raw_map_path"));
        
        JsonArray list = json.getJsonArray("subregions");
	for (int i = 0; i < list.size(); i++) {
	    JsonObject subregion = list.getJsonObject(i);
            //set everything for the current subregion
            SubRegion temp = new SubRegion(subregion.getString("subregion_name"), subregion.getString("capital_name"), subregion.getString("leader_name"));
            temp.setSubregionColor(Color.color(Double.parseDouble(subregion.getString("red")), Double.parseDouble(subregion.getString("green")),Double.parseDouble(subregion.getString("blue"))));
            temp.setFlagImagePath(subregion.getString("flag_image_path"));
            temp.setLeaderImagePath(subregion.getString("leader_image_path"));
            temp.setSubregionBorderThickness(Double.parseDouble(subregion.getString("border_thickness")));
            //next small bit sets the overall border thickness to that of the first subregion
            if (i == 0)
                dataManager.setBorderThickness(Double.parseDouble(subregion.getString("border_thickness")));
            //add it to the datamanager
	    dataManager.getSubregions().add(temp);
	}
        
        //System.out.println(dataManager.getSubregions().size());
        //System.out.println(dataManager.getPolygonList().size());
        //Workspace workspace = (Workspace)dataManager.getMapEditorApp().getWorkspaceComponent();
        //workspace.centerMap();
    }
    
    public ArrayList<Polygon> loadSubregion(JsonObject obj) {
        
        int bizz = 0;
        JsonArray list2 = obj.getJsonArray("SUBREGION_POLYGONS");
        ArrayList<Double> xyCoordinates = new ArrayList<>();
        ArrayList<Polygon> myPolygons = new ArrayList<>();
        
        for (int i = 0; i < list2.size(); i++) {
	    JsonArray myArray = list2.getJsonArray(i);
            for (int j = 0; j < myArray.size(); j++) {
                double x = getDataAsDouble(myArray.getJsonObject(j), "X");
                double y = getDataAsDouble(myArray.getJsonObject(j), "Y");
                
                //hw5 //hw6 commented this code out bc i found a better way to zoom. see center in workspace
                /*
                if (maxX < x)
                    maxX = x;
                if (maxY < y)
                    maxY = y;
                if (temp == 0) {
                    temp++;
                    minY = y; minX = x;
                }
                if (minX > x)
                    minX = x;
                if (minY > y)
                    minY = y;
                */
                
                xyCoordinates.add(x);
                xyCoordinates.add(y);
                
                
            }
            
            dataManager.scaleXYCoordinates(xyCoordinates);
            
            Polygon p = new Polygon();
            
            p.getPoints().addAll(xyCoordinates);
            
            xyCoordinates.clear();
            myPolygons.add(p);
            
            //bizz will mark how many extra polygons there are. Then it will add that number plus one
            //to the datamanager's numPolygonsList.
            //note that slovakia does not have all 1's, but andorra, for example, does.
            bizz++;
            
	}
        dataManager.getNumPolygonsList().add(bizz);
        return myPolygons;
    }
    
    public double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();	
    }
    
    public int getDataAsInt(JsonObject json, String dataName) {
        JsonValue value = json.get(dataName);
        JsonNumber number = (JsonNumber)value;
        return number.bigIntegerValue().intValue();
    }
    
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    
    

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        // GET THE DATA
	dataManager = (DataManager)data;
	
	//All of the datamanager data
        //also need to access the path to the JSON file - can just note this and use in the datamanager
        //the datamanager/workspace should be able to access the file and then create the subregions
        //as it does for the json files from hw2
        String mapName;
        boolean haveCapitals = true;
        boolean haveFlags = true;
        boolean haveLeaders = true;
        //can always use Double.parseDouble if I want these strings to return to double values
        
        if (dataManager.getMapName() == null)
            mapName = "";
        else
            mapName = dataManager.getMapName();
        
        
        
        
        ObservableList<SubRegion> subregions = dataManager.getSubregions();
        //SUBREGIONS HAVE
        //flags?
        for (SubRegion sub : subregions) {
            if (sub.getFlagImagePath() == null || sub.getFlagImagePath().equals("")) {
                haveFlags = false;
                break;
            }
            else {
                try {
                    //System.out.println(sub.getFlagImagePath());
                    Image image = new Image("file:" + sub.getFlagImagePath());
                    //System.out.println(image.getPixelReader().getColor(1,1));
                }
                catch(Exception e) {
                    haveFlags = false;
                    break;
                }
            }
        }
        for (SubRegion sub : subregions) {
            if (sub.getLeaderName()==null || sub.getLeaderName().equals("")) {
                haveLeaders = false;
                break;
            }
            if (sub.getLeaderImagePath()== null || sub.getLeaderImagePath().equals("")) {
                haveLeaders = false;
                break;
            }
            else {
                try {
                    Image image = new Image("file:" + sub.getLeaderImagePath());
                    image.getPixelReader().getColor(1,1);
                }
                catch(Exception e) {
                    haveLeaders = false;
                    break;
                }
            }
        }
        for (SubRegion sub : subregions) {
            if (sub.getCapitalName()==null || sub.getCapitalName().equals("")) {
                haveCapitals = false;
                break;
            }
        }
        System.out.println(haveFlags);
        System.out.println(haveLeaders);
        System.out.println(haveCapitals);
        System.out.println(filePath);
        
        
	// NOW BUILD THE JSON ARRAY FOR THE LIST
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        
	for (SubRegion item : subregions) {
	    JsonObject itemJson = Json.createObjectBuilder()
		    .add("name", item.getSubregionName())
		    .add("capital", item.getCapitalName())
		    .add("leader", item.getLeaderName())
                    .add("red", (int)(item.getSubregionColor().getRed()*255))
                    .add("green", (int)(item.getSubregionColor().getGreen()*255))
                    .add("blue", (int)(item.getSubregionColor().getBlue()*255)).build();
	    arrayBuilder.add(itemJson);
	}
	JsonArray itemsArray = arrayBuilder.build();
	
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add("name", mapName)
                .add("subregions_have_capitals", haveCapitals)
                .add("subregions_have_flags", haveFlags)
                .add("subregions_have_leaders", haveLeaders)
		.add("subregions", itemsArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Double getMaxX() {return maxX;}
    public Double getMaxY() {return maxY;}
    public Double getMinX() {return minX;}
    public Double getMinY() {return minY;}
    public void resetVariables() {
        maxX = 0.0;
        maxY = 0.0;
        minX = 0.0;
        minY = 0.0;
        temp = 0;
    }


}