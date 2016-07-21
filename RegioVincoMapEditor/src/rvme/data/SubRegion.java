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
    
    
    
    final StringProperty subregion;
    final StringProperty capital;
    final StringProperty leader;
    //final StringProperty imageFlag;
    //final StringProperty imageLeader;
    
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
    public void setCapitalName(String s) {//capitalName = s;
    capital.setValue(s);}
    public void setSubregionBorderThickness(Double d) {subregionBorderThickness = d;}
    public void setSubregionColor(Color color) {subregionColor = color;}
    public void setFlagImagePath(String s) {flagImagePath = s;}
    public void setLeaderImagePath(String s) {leaderImagePath = s;}
    public void setLeaderName(String s) {//leaderName = s;
    leader.setValue(s);}
    public void setSubregionName(String s) {//subregionName = s;
    subregion.setValue(s);}
    
    public ArrayList<Polygon> getPolygonList() {
        return subregionPolygonList;
    }
    public Color getSubregionColor() {return subregionColor;}
    public String getCapitalName() {return capital.getValue();}
    public String getLeaderName() {return leader.getValue();}
    public String getSubregionName() {return subregion.getValue();}
    public String getFlagImagePath() {return flagImagePath;}
    public String getLeaderImagePath() {return leaderImagePath;}
    public Double getSubregionBorderThickness() {return subregionBorderThickness;}
    
    
    public SubRegion() {
        subregion = new SimpleStringProperty("?");
        leader = new SimpleStringProperty("?");
        capital = new SimpleStringProperty("?");
        //subregion = new SimpleStringProperty(subregionName);
        //leader = new SimpleStringProperty(leaderName);
        //capital = new SimpleStringProperty(capitalName);
        
        //actually, these are the only ones i need i think (since they are only needed for the table)
        //imageFlag = new SimpleStringProperty()
        //capital,imageFlag,imageLeader

        //category = new SimpleStringProperty(DEFAULT_CATEGORY);
        //description = new SimpleStringProperty(DEFAULT_DESCRIPTION);
        //startDate = new SimpleObjectProperty(DEFAULT_DATE);
        //endDate = new SimpleObjectProperty(DEFAULT_DATE);
        //completed = new SimpleBooleanProperty(DEFAULT_COMPLETED);
    }

    public SubRegion(String initSubregionName, String initCapital, String initLeader) {
        this();
        //subregionName = initSubregionName;
        //capitalName = initCapital;
        //leaderName = initLeader;
        subregion.setValue(initSubregionName);
        capital.setValue(initCapital);
        leader.setValue(initLeader);
    }
    
    public void reset() {
        setCapitalName(DEFAULT_CATEGORY);
        setLeaderName(DEFAULT_DESCRIPTION);
        setSubregionName(DEFAULT_DESCRIPTION);
    }
}
