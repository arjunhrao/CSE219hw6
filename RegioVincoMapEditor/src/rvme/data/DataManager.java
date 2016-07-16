package rvme.data;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import saf.components.AppDataComponent;
import rvme.MapEditorApp;
import rvme.gui.Workspace;

/**
 *
 * @author McKillaGorilla
 */
public class DataManager implements AppDataComponent {
    MapEditorApp app;
    ArrayList<Polygon> polygonList = new ArrayList<>();
    //hw4
    ObservableList<SubRegion> subregions;
    //hw5 - need to add some data values
    Color backgroundColor = Color.web("#0000FF");//blue
    Color borderColor = Color.BLACK;
    String mapName;
    String parentDirectory = "";
    //doubles width and height? and zoom?
    Double borderThickness = 1.0;
    String rawMapPath = "";
    
    Double mapPositionX = 0.0;
    Double mapPositionY = 0.0;
    Double zoom = 1.0;
    
    String regionFlagImagePath = "";
    Image regionFlagImage;
    String coatOfArmsImagePath = "";
    Image coatOfArmsImage;
    
    public MapEditorApp getMapEditorApp() {return app;}
    
    public void setMapName(String s) {mapName = s;}
    public void setBorderThickness(Double d) {borderThickness = d;}
    public void setBackgroundColor(Color color) {backgroundColor = color;}
    public void setBorderColor(Color color) {borderColor = color;}
    public void setRawMapPath(String s) {rawMapPath = s;}
    public void setParentDirectory(String s) {parentDirectory = s;}
    public void setRegionFlagImagePath(String s) {regionFlagImagePath = s;}
    public void setCoatOfArmsImagePath(String s) {coatOfArmsImagePath = s;}
    public void setZoom(double d) {zoom = d;}
    public void setPosX(double d) {mapPositionX = d;}
    public void setPosY(double d) {mapPositionY = d;}
    
    
    public double getMapPositionX() {return mapPositionX;}
    public double getMapPositionY() {return mapPositionY;}
    public double getZoom() {return zoom;}
    public Color getBorderColor() {return borderColor;}
    public Color getBackgroundColor() {return backgroundColor;}
    public String getMapName() {return mapName;}
    public String getParentDirectory() {return parentDirectory;}
    public Double getBorderThickness() {return borderThickness;}
    public String getRawMapPath() {return rawMapPath;}
    public String getCoatOfArmsImagePath() {return coatOfArmsImagePath;}
    public String getRegionFlagImagePath() {return regionFlagImagePath;}
    
    
    public DataManager(MapEditorApp initApp) {
        app = initApp;
        List list = new ArrayList();
        subregions = FXCollections.observableList(list);
        //List list2 = new ArrayList();
        //polygonList = FXCollections.observableList(list2);
    }
    
    @Override
    public void reset() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        polygonList.clear();
        subregions.clear();
        
        //workspace.getRenderPane().setScaleX(1.0); //change to getMapPane? or don't need at all?
        //workspace.getRenderPane().setScaleY(1.0);

        //might need to add stuff in here
        //double h = app.getGUI().getPrimaryScene().getHeight()/2; //these were giving me nullpointers prob bc of app.getgui on a fresh, new app
        //double w = app.getGUI().getPrimaryScene().getWidth()/2;
        
        //removed a bunch of circle stuff  - can always look at the og mapviewer to see it if needed
        
        
        //workspace.getMapPane().getChildren().clear();
        
        //workspace.getWorkspace().getChildren().clear();
    }
    
    public void resetMapPane() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.getMapGroup().setScaleY(1.0);
        workspace.getMapGroup().setScaleX(1.0);
    }
    
    public void addPolygon(Polygon p) {
        polygonList.add(p);
    }
    
    public ArrayList<Polygon> getPolygonList() {
        return polygonList;
    }
    
    public void fillPolygons(Paint p) {
        for (Polygon poly: polygonList) {
            poly.setFill(p);
        }
    }
    
    //prob won't use this method
    public Polygon convertPolygon(Polygon p) {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        p.setScaleX(1/360*workspace.getRenderPane().getWidth());
        p.setScaleY(1/180*workspace.getRenderPane().getHeight());
        return p;

    }
    
    public void scaleXYCoordinates(ArrayList<Double> xy) {
        //revised as of hw5 for 1280 width and 751-120 height
        double halfX = 1280/2;
        double halfY = (751-120)/2;
        
        for (int n = 0; n < xy.size(); n++) {
                if (n%2 == 0) {//it's an x
                    //scale it
                    xy.set(n, xy.get(n)/360*1280);
                    //place it relative to origin
                    xy.set(n, halfX+xy.get(n));
                } else {
                    //scale
                    xy.set(n, xy.get(n)/180*((751-120)));
                    //place relative to origin
                    xy.set(n, halfY-xy.get(n));
                }
            }

    }
    
    
    public ObservableList<SubRegion> getSubregions() {
	return subregions;
    }
}
