package rvme.data;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author McKillaGorilla
 */
public class SubRegion {
    public static final String DEFAULT_CATEGORY = "?";
    public static final String DEFAULT_DESCRIPTION = "?";
    public static final LocalDate DEFAULT_DATE = LocalDate.now();
    public static final boolean DEFAULT_COMPLETED = false;
    
    //final StringProperty category;
    //final StringProperty description;
    //final ObjectProperty<LocalDate> startDate;
    //final ObjectProperty<LocalDate> endDate;
    //final BooleanProperty completed;
    
    //hw4
    String capitalName = "";
    String leaderName = "";
    String subregionName = "";
    //hw5
    Color subregionColor = Color.GREEN;
    String flagImagePath = "";
    String leaderImagePath = "";
    Image leaderImage;
    Image flagImage;
    Double subregionBorderThickness = .01;
    ArrayList<Polygon> subregionPolygonList = new ArrayList<>();
    
    //r,g,b doubles
    public void setCapitalName(String s) {capitalName = s;}
    public void setSubregionBorderThickness(Double d) {subregionBorderThickness = d;}
    public void setSubregionColor(Color color) {subregionColor = color;}
    public void setFlagImagePath(String s) {flagImagePath = s;}
    public void setLeaderImagePath(String s) {leaderImagePath = s;}
    public void setLeaderName(String s) {leaderName = s;}
    public void setSubregionName(String s) {subregionName = s;}
    
    public ArrayList<Polygon> getPolygonList() {
        return subregionPolygonList;
    }
    public Color getSubregionColor() {return subregionColor;}
    public String getCapitalName() {return capitalName;}
    public String getLeaderName() {return leaderName;}
    public String getSubregionName() {return subregionName;}
    public String getFlagImagePath() {return flagImagePath;}
    public String getLeaderImagePath() {return leaderImagePath;}
    public Double getSubregionBorderThickness() {return subregionBorderThickness;}
    
    
    public SubRegion() {
        //category = new SimpleStringProperty("asdf");
        //description = new SimpleStringProperty("hey");
        //startDate = new SimpleObjectProperty("ho");
        //endDate = new SimpleObjectProperty("lets");
        //completed = new SimpleBooleanProperty(false);
        

        //category = new SimpleStringProperty(DEFAULT_CATEGORY);
        //description = new SimpleStringProperty(DEFAULT_DESCRIPTION);
        //startDate = new SimpleObjectProperty(DEFAULT_DATE);
        //endDate = new SimpleObjectProperty(DEFAULT_DATE);
        //completed = new SimpleBooleanProperty(DEFAULT_COMPLETED);
    }

    public SubRegion(String initSubregionName, String initCapital, String initLeader) {
        this();
        subregionName = initSubregionName;
        capitalName = initCapital;
        leaderName = initLeader;
    }
    
    public void reset() {
        setCapitalName(DEFAULT_CATEGORY);
        setLeaderName(DEFAULT_DESCRIPTION);
        setSubregionName(DEFAULT_CATEGORY);
    }
}
