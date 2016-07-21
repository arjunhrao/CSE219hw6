/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvme.gui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import properties_manager.PropertiesManager;
import saf.components.AppWorkspaceComponent;
import rvme.MapEditorApp;
import rvme.PropertyType;
import rvme.controller.MapController;
import rvme.data.DataManager;
import rvme.data.SubRegion;
import static saf.settings.AppPropertyType.*;
import static saf.settings.AppStartupConstants.FILE_PROTOCOL;
import static saf.settings.AppStartupConstants.PATH_IMAGES;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import rvme.file.FileManager;

/**
 *
 * @author McKillaGorilla
 */
public class Workspace extends AppWorkspaceComponent {
    MapEditorApp app;
    Pane renderPane = new Pane();
    MapController mapController;
    double xOrigin;
    double yOrigin;
    Boolean hasLines;
    int counter = 0;
    boolean firstLoad = true;
    boolean firstZoomCheck = true;
    boolean secondZoomCheck = true;
    Color tempColor = Color.BLACK;
    int tempIndex = -1;
    
    //HW4
    SplitPane splitPane = new SplitPane();
    FlowPane editToolbar = new FlowPane();
    Group mapGroup = new Group();
    //couple for //hw5
    StackPane stackPane = new StackPane();
    Pane barPane = new Pane();
    Pane imagePane = new Pane();
    TableView<SubRegion> subregionsTable;
    TableColumn subregionNameColumn;
    TableColumn capitalNameColumn;
    TableColumn leaderNameColumn;
    Button renameMapButton;
    Button addImageButton;
    Button removeImageButton;
    Button changeBackgroundColorButton;
    Button changeBorderColorButton;
    Button randomizeMapColorsButton;
    Button changeMapDimensionsButton;
    Button playAnthemButton;
    Button pauseAnthemButton;
    ColorPicker changeBackgroundColorPicker;
    ColorPicker changeBorderColorPicker;
    Slider borderThickness;
    Slider zoomSlider;
    
    boolean selected;
    int lastSelectedIndex;
    
    public Slider getBorderThicknessSlider() {return borderThickness;}
    public Slider getZoomSlider() {return zoomSlider;}
    public Group getMapGroup() {return mapGroup;}
    
    
    
    //Note: The new map implementation should be the hardest part and the first part you make sure works.
    //The rest should be easier.
    //Note that you can iterate through the subregions and set each polygon in their
    //list of polygons on action, i.e. give them a listener. Do this for the table as well.
    //Also make sure that clicking elsewhere deselects.
    //Implement the images and their movement (with the link given), then this selection thing, then new map.
    //Or a different order, up to me.
    
    
    public Workspace(MapEditorApp initApp) {
        app = initApp;
        workspace = new Pane();
        app.getGUI().getAppPane().setCenter(splitPane);
        
        initHW4Layout();
        
        xOrigin = app.getGUI().getPrimaryScene().getWidth()/2;
        yOrigin = (app.getGUI().getPrimaryScene().getHeight()-60)/2;
        
        //dont want this here since HW4
        //removeButtons();
        hasLines = false;
        
        //create controller
        mapController = new MapController(app);
        
        //initialize processing of eventhandlers - create new method
        //processEvents();
        processHW4Events();
    }
    
    public void initHW4Layout() {
        workspace.getStyleClass().add(CLASS_BORDERED_PANE);
        
        FlowPane fp = (FlowPane)app.getGUI().getAppPane().getTop();
        
        //give the edit toolbar the right style
        editToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
        //give the edit toolbar the appropriate controls
        //newButton = initChildButton(fileToolbarPane,	NEW_ICON.toString(),	    NEW_TOOLTIP.toString(),	false);
        //ADDING BUTTONS
        renameMapButton = app.getGUI().initChildButton(editToolbar, RENAME_MAP.toString(), RENAME_MAP_TT.toString(), false);
        addImageButton = app.getGUI().initChildButton(editToolbar, ADD_IMAGE.toString(), ADD_IMAGE_TT.toString(), false);
        removeImageButton = app.getGUI().initChildButton(editToolbar, REMOVE.toString(), REMOVE_TT.toString(), false);
        //changeBackgroundColorButton = app.getGUI().initChildButton(editToolbar, CHANGE_BACKGROUND_COLOR.toString(), CHANGE_BACKGROUND_COLOR_TT.toString(), false);
        //changeBorderColorButton = app.getGUI().initChildButton(editToolbar, CHANGE_BORDER_COLOR.toString(), CHANGE_BORDER_COLOR_TT.toString(), false);
        //Turns out the above are supposed to be color pickers.
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        changeBackgroundColorPicker = new ColorPicker();
        changeBorderColorPicker = new ColorPicker();
        Label bgColorLabel = new Label(props.getProperty(CHANGE_BACKGROUND_COLOR_TT) + ": ");
        Label borderColorLabel = new Label(props.getProperty(CHANGE_BORDER_COLOR_TT) + ": ");
        HBox colorPickingToolbar = new HBox();
        colorPickingToolbar.getChildren().addAll(bgColorLabel, changeBackgroundColorPicker, borderColorLabel, changeBorderColorPicker);
        
        randomizeMapColorsButton = app.getGUI().initChildButton(editToolbar, RANDOMIZE_MAP_COLORS.toString(), RANDOMIZE_MAP_COLORS_TT.toString(), false);
        changeMapDimensionsButton = app.getGUI().initChildButton(editToolbar, CHANGE_MAP_DIMENSIONS.toString(), CHANGE_MAP_DIMENSIONS_TT.toString(), false);
        playAnthemButton = app.getGUI().initChildButton(editToolbar, PLAY_ANTHEM.toString(), PLAY_ANTHEM_TT.toString(), false);
        pauseAnthemButton = app.getGUI().initChildButton(editToolbar, PAUSE_ANTHEM.toString(), PAUSE_ANTHEM_TT.toString(), false);
        //add the colorpicking stuff after all of the buttons but before the sliders
        editToolbar.getChildren().add(colorPickingToolbar);
        editToolbar.setHgap(1.0);
        editToolbar.setVgap(5.0);

        //ADDING SLIDERS and their labels
        borderThickness = new Slider();
        zoomSlider = new Slider();
        borderThickness.setMin(.005);
        borderThickness.setMax(.1);
        zoomSlider.setMin(1.0);
        zoomSlider.setMax(750.0);
        
        borderThickness.setShowTickLabels(true);
        borderThickness.setShowTickMarks(true);
        borderThickness.setMajorTickUnit(.025);
        borderThickness.setMinorTickCount(5);
        borderThickness.setBlockIncrement(.05);
        
        zoomSlider.setShowTickLabels(true);
        zoomSlider.setShowTickMarks(true);
        zoomSlider.setMajorTickUnit(249.0);
        zoomSlider.setMinorTickCount(5);
        zoomSlider.setBlockIncrement(50.0);
        
        DataManager dataManager = (DataManager)app.getDataComponent();
        borderThickness.setValue(dataManager.getBorderThickness());
        zoomSlider.setValue(dataManager.getZoom());
        System.out.println("bt:" + dataManager.getBorderThickness() + " ---- zoom:" + dataManager.getZoom());
        
        Label borderThicknessLabel = new Label("Border Thickness:");
        Label zoomLabel = new Label("Zoom:");
        HBox sliders = new HBox();
        sliders.getChildren().addAll(borderThicknessLabel, borderThickness, zoomLabel, zoomSlider);
        editToolbar.getChildren().addAll(sliders);
        //now add the toolbar to the flowpane. SUCCESS! Although we might want to consider spacing and button size...
        
        fp.getChildren().addAll(editToolbar);
        editToolbar.setMinWidth(800.0);
        
        //set up the split pane
        initTableAndImage(); //also adds the table to the split pane
        
        //add the split pane to the workspace
        //splitPane.setStyle("-fx-background-color: blue;");
        //workspace.getChildren().addAll(splitPane);
        //workspace.setStyle("-fx-background-color: lightblue;");
        
    }
    
    public void initTableAndImage() {
        //be able to get the properties
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        
        
        // NOW SETUP THE TABLE COLUMNS
        //this is how it should be
        //gonna use strings, not xml, for now
        //subregionNameColumn = new TableColumn(props.getProperty(PropertyType.CATEGORY_COLUMN_HEADING));
        subregionNameColumn = new TableColumn(props.getProperty(PropertyType.SUBREGIONNAME_COLUMN_HEADING));
        capitalNameColumn = new TableColumn(props.getProperty(PropertyType.CAPITAL_COLUMN_HEADING));
        leaderNameColumn = new TableColumn(props.getProperty(PropertyType.LEADER_COLUMN_HEADING));
        
        
        subregionsTable = new TableView();
        // AND LINK THE COLUMNS TO THE DATA - figure out how to do this later with the PropertyValueFactory
        subregionNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("subregion"));
        capitalNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("capital"));
        leaderNameColumn.setCellValueFactory(new PropertyValueFactory<String, String>("leader"));
        subregionsTable.getColumns().add(subregionNameColumn);
        subregionsTable.getColumns().add(capitalNameColumn);
        subregionsTable.getColumns().add(leaderNameColumn);
        
        //PropertyValueFactory<String, String> xd = new PropertyValueFactory<String, String>("subregionName");
        
        DataManager dataManager = (DataManager)app.getDataComponent();
        //SubRegion x = new SubRegion("New York", "Albany", "Andrew Cuomo");
        //ObservableList<SubRegion> subregions = new ObservableList<SubRegion>();
        //note that the above comment was a result of forgetting to instantiate the subregions list in the datamanager
        //commented out for //hw5 dataManager.getSubregions().add(x);
        //SubRegion nj = new SubRegion("New Jersey", "Trenton", "Chris Christie");
        //commented out for //hw5 dataManager.getSubregions().add(nj);
        
        //System.out.println(x.getCapitalName());
        subregionsTable.setItems(dataManager.getSubregions());
        System.out.println("initTable(): subregions size: " + dataManager.getSubregions());
        
        //splitPane.getItems().addAll(subregionsTable);
        //splitPane.setDividerPosition(10, 300);
        
        //set up the fake image and add it to the mappane so that it shows up on the left and roughly centered
        //String imagePath = FILE_PROTOCOL + PATH_IMAGES + "FakeMapImage.png";
        //Image fakeMapImage = new Image(imagePath);
        //ImageView mapIm = new ImageView(fakeMapImage);
        //mapIm.relocate(15,100);
        //mapPane.getChildren().add(mapIm);
        
        //add to splitpane
        
        //stackPane.getChildren().add(imagePane);
        //stackPane.getChildren().add(barPane);
        splitPane.getItems().add(stackPane);
        splitPane.getItems().add(subregionsTable);
        
    }
    
    public Pane getBarPane() {return barPane;}
    public StackPane getStackPane() {return stackPane;}
    
    
    public MapController getMapController() {return mapController;}
    
    public void processHW4Events() {
        
        changeMapDimensionsButton.setOnAction(e -> {
            //pop up a dimensions dialog
            mapController.processMapDimensions();
        });
        subregionsTable.setOnMouseClicked(e -> {
            DataManager dataManager = (DataManager)app.getDataComponent();
            
            if (e.getClickCount() == 2) {
                SubRegion it = subregionsTable.getSelectionModel().getSelectedItem();
                mapController.processEditSubregion(it);
                //subregionsTable.getSelectionModel().select(it);
            }
            
            if (selected) {
                if(lastSelectedIndex == subregionsTable.getSelectionModel().getSelectedIndex()){
                    subregionsTable.getSelectionModel().clearSelection();
                    selected = false;
                    updateBorderColor();
                    
                }else{
                    lastSelectedIndex=subregionsTable.getSelectionModel().getSelectedIndex();
                    
                }
            }else{
                lastSelectedIndex=subregionsTable.getSelectionModel().getSelectedIndex();
                selected = true;
            }
            if (selected) {
            }
            if (!selected) {
            }
            
            if (selected) {
                int currentSubRegionIndex = subregionsTable.getSelectionModel().getSelectedIndex();
                reloadMapFromTable(currentSubRegionIndex);
                
            }
            
        });
        //now also processes HW6 events!!
        changeBackgroundColorPicker.setOnAction(e -> {
            //pop up a dimensions dialog
            DataManager dataManager = (DataManager)app.getDataComponent();
            Color c = changeBackgroundColorPicker.getValue();
            dataManager.setBackgroundColor(c);
            reloadWorkspace();
        });
        changeBorderColorPicker.setOnAction(e -> {
            //pop up a dimensions dialog
            DataManager dataManager = (DataManager)app.getDataComponent();
            Color c = changeBorderColorPicker.getValue();
            dataManager.setBorderColor(c);
            reloadWorkspace();
        });
        //sliders - did setOnMouseDragged as well as MouseClicked so that any way you manipulate the slider,
        //the right value changes.
        borderThickness.setOnMouseDragged(e -> {
            //pop up a dimensions dialog
            DataManager dataManager = (DataManager)app.getDataComponent();
            dataManager.setBorderThickness(borderThickness.getValue());
            reloadWorkspace();
        });
        borderThickness.setOnMouseClicked(e -> {
            //pop up a dimensions dialog
            DataManager dataManager = (DataManager)app.getDataComponent();
            dataManager.setBorderThickness(borderThickness.getValue());
            reloadWorkspace();
        });
        
        zoomSlider.setOnMouseClicked(e -> {
            //pop up a dimensions dialog
            DataManager dataManager = (DataManager)app.getDataComponent();
            dataManager.setZoom(zoomSlider.getValue());
            reloadWorkspace();
        });
        zoomSlider.setOnMouseDragged(e -> {
            //pop up a dimensions dialog
            DataManager dataManager = (DataManager)app.getDataComponent();
            dataManager.setZoom(zoomSlider.getValue());
            reloadWorkspace();
        });
        
        randomizeMapColorsButton.setOnAction(e -> {
            //pop up a dimensions dialog
            mapController.processRandomizeMapColors();
        });
        
        //app.getGUI().getNewButton().setOnAction(e -> {
            //mapController.processNewRequest();
        //});
        
    }
    
    public void reloadMapFromTable(int index) {
        DataManager dataManager = (DataManager)app.getDataComponent();
        SubRegion current = dataManager.getSubregions().get(index);
        tempIndex = index;
        Color tempBorderColor = dataManager.getBorderColor();
        String hexBorderColorString = String.format( "#%02X%02X%02X",
            (int)( tempBorderColor.getRed() * 255 ),
            (int)( tempBorderColor.getGreen() * 255 ),
            (int)( tempBorderColor.getBlue() * 255 ) );
        mapGroup.getChildren().clear();
        for (Polygon poly: dataManager.getPolygonList()) {
          //use the right color
          //HW6: gonna need to change the value of the border to something red I think? although if the other
          //polygons are drawn after, then this might be a problem...
          if (current.getPolygonList().contains(poly)) {
              //System.out.println("Current subregion selection has this polygon.");
              poly.setStroke(Color.RED);
              
          }
          else {
            poly.setStroke(Paint.valueOf(hexBorderColorString));
          }
          //use the right width
          poly.setStrokeWidth(dataManager.getSubregions().get(0).getSubregionBorderThickness());
          
          poly.toFront();
          
          mapGroup.getChildren().addAll(poly);
          
        }
        
        for (Polygon polygon: dataManager.getPolygonList()) {
            if (current.getPolygonList().contains(polygon)) {
            //System.out.println("*****Current subregion selection has this polygon.");
            polygon.toFront();
            }
        }
        
        changePolygonColors();
        //System.out.println("Paint:" + current.getPolygonList().get(0).getFill());
        
        //dataManager.getPolygonList()
        //dataManager.getPolygonList().get(k+adder).setFill(Paint.valueOf(hexColorString2));
        
        
    }
    
    @Override
    public boolean newDialog() {
        try {
            //mapController.setFileButtons();
            return mapController.processNewMapDialog();
        } catch (IOException ex) {
            System.out.println("An error occurred loading the file.");
        }
        return false;
    }
    
    @Override
    public void export() {
        //EXPORT IMAGE!! snapshot?
        DataManager dataManager = (DataManager)app.getDataComponent();
        FileManager fileManager = (FileManager)app.getFileComponent();
        String filePath = "./export/The World/temp/" + dataManager.getMapName() + ".rvm";
        
        try {
            fileManager.exportData(dataManager, filePath);
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void reloadWorkspaceFromNew() {
        DataManager dataManager = (DataManager)app.getDataComponent();
        FileManager fileManager = (FileManager)app.getFileComponent();
        dataManager.fillPolygons(Paint.valueOf("#556B2F"));
        System.out.println("size from New: " + dataManager.getPolygonList().size());
        stackPane.getChildren().remove(mapGroup);
        mapGroup.getChildren().clear();
        imagePane.getChildren().clear();
        barPane.getChildren().clear();
        
        zoomSlider.setValue(100.0);
        dataManager.setZoom(100.0);
        
        for (Polygon poly: dataManager.getPolygonList()) {
          
          //use the right width
          poly.setStrokeWidth(dataManager.getSubregions().get(0).getSubregionBorderThickness());
          
          
          mapGroup.getChildren().addAll(poly);
          
          System.out.println(mapGroup.getChildren().size());
          
          
        }
        dataManager.setBackgroundColor(Color.CORAL);
        //centerMap();
        app.getGUI().getAppPane().setCenter(splitPane);
        
    }
    public TableView<SubRegion> getSubregionsTable() {
        return subregionsTable;
    }
    
    @Override
    public void reloadWorkspace() {
        
        DataManager dataManager = (DataManager)app.getDataComponent();
        FileManager fileManager = (FileManager)app.getFileComponent();
        
        //following few lines were added to allow for the editing of subregions to update the workspace / table
        //subregionsTable.getItems().clear();
        //System.out.println(dataManager.getSubregions().get(0).getCapitalName());
        
        
        //if (!subregionsTable.getItems().isEmpty()) 
        if (!dataManager.getSubregions().isEmpty()) {
            //subregionsTable.getItems().clear();
            System.out.println("if statement entered.");
            
            System.out.println("leader of sr 1: " + dataManager.getSubregions().get(0).getLeaderName()
                + "      ---- color of last one:" + dataManager.getSubregions().get(dataManager.getSubregions().size()-1).getSubregionColor().toString());
            
            System.out.println("number of sr's: " + dataManager.getSubregions().size());
            System.out.println("size of table: " + subregionsTable.getItems().size());
            
            ObservableList<SubRegion> tempList = dataManager.getSubregions();
            subregionsTable.getItems().clear();
            
            
            System.out.println("tempList size:" + tempList.size());
            System.out.println("clear worked.");
            
            System.out.println("number of sr's: " + dataManager.getSubregions().size());
            System.out.println("size of table: " + subregionsTable.getItems().size());
            
            subregionsTable.setItems(dataManager.getSubregions());
            System.out.println("set worked.");
        }
        
        System.out.println("initTable(): subregions size: " + dataManager.getSubregions());
        
        
        //clears the workspace
        //workspace.getChildren().clear();
        
        //set the background to be lightblue
        //workspace.setStyle("-fx-background-color: lightblue;");
           
        //fill polygons with green
        dataManager.fillPolygons(Paint.valueOf("#556B2F"));
        
        //clears the pane so you can load something else
        //hw5
        
        mapGroup.getChildren().clear();
        stackPane.getChildren().clear();
        //stackPane.getChildren().remove(mapGroup);
        imagePane.getChildren().clear();
        barPane.getChildren().clear();
        
        //update slider values
        zoomSlider.setValue(dataManager.getZoom());
        borderThickness.setValue(dataManager.getBorderThickness());
        System.out.println(dataManager.getZoom());
        //update colorpickers
        changeBackgroundColorPicker.setValue(dataManager.getBackgroundColor());
        changeBorderColorPicker.setValue(dataManager.getBorderColor());
        
        
        if (dataManager.getFirstLoadForSliders()) {
            zoomSlider.setValue(dataManager.getZoom());
            dataManager.setFirstLoadForSliders(false);
            //System.out.println(dataManager.getFirstLoadForSliders());
            //System.out.println(dataManager.getPolygonList());
            
            //System.out.println(dataManager.getSubregions());
            //System.out.println(dataManager.getSubregions().size());
            //if (dataManager.getSubregions().get(0).getPolygonList().isEmpty()) {
              //  System.out.println("asdf");
                //mapController.addPolygonsToSubregions();
            //}
        }
        
       //this was for testing i guess 
        if (!dataManager.getSubregions().isEmpty()) {
            System.out.println("first sr size- " + dataManager.getSubregions().get(0).getPolygonList().size());
            
        }
        else {
            System.out.println("subregions list is empty");
        }
        //this below if statement was added to make sure that the polygons have been added to subregions
        //and does it if they haven't been.
        //Or at least, that's what I want, but I'm getting an error, prob bc they're not initialized
        //for the first run.
        
        //if (!dataManager.getPolygonList().isEmpty() && !dataManager.getSubregions().isEmpty() && dataManager.getSubregions().get(0).getPolygonList().isEmpty())
            //mapController.addPolygonsToSubregions();
        
        dataManager.setZoom(zoomSlider.getValue());
        dataManager.setBorderThickness(borderThickness.getValue());
        for (SubRegion sub: dataManager.getSubregions()) {
            sub.setSubregionBorderThickness(borderThickness.getValue());
        }
        
        //find border color
        Color tempBorderColor = dataManager.getBorderColor();
        String hexBorderColorString = String.format( "#%02X%02X%02X",
            (int)( tempBorderColor.getRed() * 255 ),
            (int)( tempBorderColor.getGreen() * 255 ),
            (int)( tempBorderColor.getBlue() * 255 ) );
        
        //add polygons
        for (Polygon poly: dataManager.getPolygonList()) {
          //use the right color
          poly.setStroke(Paint.valueOf(hexBorderColorString));
          
          //change the border to red if there is a selected
          if (selected && tempIndex != -1) {
              
            if (dataManager.getSubregions().get(tempIndex).getPolygonList().contains(poly)) {
                poly.setStroke(Color.RED);
            }
            
          }
              
              
          //use the right width
          poly.setStrokeWidth(dataManager.getSubregions().get(0).getSubregionBorderThickness());
          
          mapGroup.getChildren().addAll(poly);
          
          //for every polygon, set it on mouseclicked to the setPolygonOnMouseClicked(polygon) method
          //This handles highlighting and is documented in the method. The e.getClcikCount() == 2 part
          //is for double clicking on a polygon, which opens the edit process for the currently selected
          //item in the table (which will be that polygon as of the first of the two clicks).
          poly.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                SubRegion it = subregionsTable.getSelectionModel().getSelectedItem();
                mapController.processEditSubregion(it);
            }
            setPolygonOnMouseClicked(poly);
          });
          
        }
        if (selected && tempIndex != -1) {
              
            for (Polygon polygon: dataManager.getPolygonList()) {
                if (dataManager.getSubregions().get(tempIndex).getPolygonList().contains(polygon)) {
                    //System.out.println("#reloadWorkspace: Current subregion selection has this polygon.");
                    polygon.toFront();
                }
            }
        }
        //hw5
        
        
        //subregionsTable.setItems(dataManager.getSubregions());
        
        //centerMap();
        
        //HW5 - Taking into account the data. CenterMap() does this using the zoom data field in the manager.
        Color tempColor = dataManager.getBackgroundColor();
        String hexColorString = String.format( "#%02X%02X%02X",
            (int)( tempColor.getRed() * 255 ),
            (int)( tempColor.getGreen() * 255 ),
            (int)( tempColor.getBlue() * 255 ) );
        mapGroup.setStyle("-fx-background-color: " + hexColorString);
        stackPane.setStyle("-fx-background-color: " + hexColorString);
        //Now change color of the polygons/subregions as well
        //System.out.println(dataManager.getPolygonList().size());
        //System.out.println(dataManager.getSubregions().size());
        
        changePolygonColors();
        //changeBorderColor();
        
        //now set up the two necessary images where they need to be
        String imagePath = dataManager.getCoatOfArmsImagePath();
        String imagePath2 = dataManager.getRegionFlagImagePath();
        if (!imagePath.equals("")) {
            
            Image image = new Image("file:" + imagePath);
            ImageView im = new ImageView(image);
            
            imagePane.getChildren().add(im);
            im.relocate(dataManager.getCoatOfArmsImagePos().getX(), dataManager.getCoatOfArmsImagePos().getY());
        }
        if (!imagePath2.equals("")) {
            Image image = new Image("file:" + imagePath2);
            ImageView im = new ImageView(image);
            
            imagePane.getChildren().add(im);
            im.relocate(dataManager.getFlagImagePos().getX(), dataManager.getFlagImagePos().getY());
        }
        //now take a snapshot
        
        
        //workspace.getChildren().addAll(renderPane);
        
        //clip it to avoid overflow
        Rectangle clip = new Rectangle();
        clip.setHeight(app.getGUI().getPrimaryScene().getHeight()-60);
        clip.setWidth(app.getGUI().getPrimaryScene().getWidth());
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        //workspace.setClip(clip);
        
        //hw4
        //workspace.getChildren().clear();
        //splitPane.getItems().clear();
        //editToolbar.getChildren().clear();
        //initHW4Layout();
        
        System.out.println("size:" + mapGroup.getChildren().size());
        mapGroup.setScaleX(dataManager.getZoom());
        mapGroup.setScaleY(dataManager.getZoom());
        
        stackPane.getChildren().add(mapGroup);
        //app.getGUI().getAppPane().setCenter(splitPane);
        
        
    }
    
    public void setPolygonOnMouseClicked(Polygon polyClicked) {
        DataManager dataManager = (DataManager)app.getDataComponent();
        int temp = 0;
        int counter = 0;
        
        for (SubRegion sub : dataManager.getSubregions()) {
            if (sub.getPolygonList().contains(polyClicked)) {
                temp = counter;
                System.out.println("index of SR with polyClicked:" + temp);
                
            }
            counter++;
        }
        //System.out.println("tempindex: " + tempIndex);
        
        
        if (selected) {
            
            selected = false;
            updateBorderColor();
            //if the subregion that you clicked on is not the same as the one you had selected,
            //select the one you clicked on. The one you clicked on can be got using your counter,
            //(or temp), and the one that you had selected previously is stored by tempIndex.
            //clearSelection if not?
            if (tempIndex != temp) {
                //clear previous selection that tempIndex stored
                subregionsTable.getSelectionModel().clearSelection();
                //select based on temp
                subregionsTable.getSelectionModel().select(dataManager.getSubregions().get(temp));
                
                lastSelectedIndex=subregionsTable.getSelectionModel().getSelectedIndex();
                selected = true;
                
                //set the tempIndex to be the currently selected index
                tempIndex = temp;
                
                //highlight the polygon red
                updateBorderColor();
            }
            else {
                //they are the same (the one you clicked on and the one that was previously highlighted
                //so deselect everything
                
                //actually I don't want to fix this. It's fine as it is.
                //Better to spend my time doing another use case then making this better
                //(it's already good according to the SRS).
                
                //System.out.println("deselect");
                //reset tempIndex
                //tempIndex = -1;
                //selected = false
                //subregionsTable.getSelectionModel().clearSelection();
                //System.out.println("deselected model");
                //updateBorderColor();
            }
            
            
        }
        
        if (!selected) {
            //select temp in the table
            subregionsTable.getSelectionModel().select(dataManager.getSubregions().get(temp));
            //select temp as a polygon
            
            //update tempIndex and other things
            lastSelectedIndex=subregionsTable.getSelectionModel().getSelectedIndex();
            selected = true;
            tempIndex = temp;
            //highlights it for us:
            updateBorderColor();
            
        }
        
    }
    
    public void newRenderPane() {
        renderPane = new Pane();
    }
    
    public void updateBorderColor() {
        DataManager dataManager = (DataManager)app.getDataComponent();
        dataManager.setBorderThickness(borderThickness.getValue());
        mapGroup.getChildren().clear();
        for (SubRegion sub: dataManager.getSubregions()) {
            sub.setSubregionBorderThickness(borderThickness.getValue());
        }
        //add the polygons
        Color tempBorderColor = dataManager.getBorderColor();
        String hexBorderColorString = String.format( "#%02X%02X%02X",
            (int)( tempBorderColor.getRed() * 255 ),
            (int)( tempBorderColor.getGreen() * 255 ),
            (int)( tempBorderColor.getBlue() * 255 ) );
        for (Polygon poly: dataManager.getPolygonList()) {
          //use the right color
          poly.setStroke(Paint.valueOf(hexBorderColorString));
          
          //change the border to red if there is a selected
          if (selected && tempIndex != -1) {
              
            if (dataManager.getSubregions().get(tempIndex).getPolygonList().contains(poly)) {
                //System.out.println("##Reload: For loop: Current subregion selection has this polygon.");
                poly.setStroke(Color.RED);
            } 
          }
              
              
          //use the right width
          poly.setStrokeWidth(dataManager.getSubregions().get(0).getSubregionBorderThickness());
          
          mapGroup.getChildren().addAll(poly);
        }
        
        if (selected && tempIndex != -1) {
              
            for (Polygon polygon: dataManager.getPolygonList()) {
                if (dataManager.getSubregions().get(tempIndex).getPolygonList().contains(polygon)) {
                    //System.out.println("#reloadWorkspace: Current subregion selection has this polygon.");
                    polygon.toFront();
                }
            }
        }
        
    }

    @Override
    public void initStyle() {
    }
    
    public void changePolygonColors() {
        DataManager dataManager = (DataManager)app.getDataComponent();
        int adder = 0;
        //above int marks how many extra polygons we are at in the numPolygonsList
        //System.out.println(dataManager.getSubregions().size());
        for (int k = 0; k < dataManager.getSubregions().size(); k++) {
            //given time i should put this into my filemanager (loading +colors, meaning load datamanager info before polygons)
            //if (dataManager.getSubregions().get(k) == null || dataManager.getPolygonList().get(k) == null)
                //break;
            Color tempColor2 = dataManager.getSubregions().get(k).getSubregionColor();
            String hexColorString2 = String.format( "#%02X%02X%02X",
                (int)( tempColor2.getRed() * 255 ),
                (int)( tempColor2.getGreen() * 255 ),
                (int)( tempColor2.getBlue() * 255 ) );
            dataManager.getPolygonList().get(k+adder).setFill(Paint.valueOf(hexColorString2));
            //System.out.println((k+adder) + ":" + hexColorString2);
            //System.out.println("---" + (dataManager.getNumPolygonsList().get(k)-1));
            for (int i = 0; i < dataManager.getNumPolygonsList().get(k)-1; i++) {
                //System.out.println(" this is i: " + i);
                dataManager.getPolygonList().get(k+adder+1).setFill(Paint.valueOf(hexColorString2));
                adder++;
                //System.out.println("****" + (k+adder) + ":" + hexColorString2);
            }
            
        }
        
    }
    
    public void changeBorderColor() {
        DataManager dataManager = (DataManager)app.getDataComponent();
        Color tempColor2 = dataManager.getBorderColor();
        String hexColorString2 = String.format( "#%02X%02X%02X",
            (int)( tempColor2.getRed() * 255 ),
            (int)( tempColor2.getGreen() * 255 ),
            (int)( tempColor2.getBlue() * 255 ) );
    }
    
    public void removeButtons() {
        FlowPane fp = (FlowPane)app.getGUI().getAppPane().getTop();
        fp.getChildren().remove(2);
        fp.getChildren().remove(0);
    }
    
    public Pane getRenderPane() {
        return renderPane;
    }
    
    public void centerMap() {
        //center the map and zoom based on the zoom variable
        DataManager dataManager = (DataManager)app.getDataComponent();
        FileManager fileManager = (FileManager)app.getFileComponent();
        ////double centerX = (fileManager.getMinX()+fileManager.getMaxX())/2.0;
        //scale coordinate as in datamanager method called in filemanager
        //double centerXNew = centerX/360*1280;
        /////double centerY = (fileManager.getMinY()+fileManager.getMaxY())/2.0;
        //scale
        //double centerYNew = dataManager.getPolygonList().get(1).getPoints().get(1);
        //System.out.println("an actual x: " + dataManager.getPolygonList().get(0).getPoints().get(0));
        //System.out.println("an actual y: " + dataManager.getPolygonList().get(0).getPoints().get(1));
        //System.out.println("my attempted actual x: " + centerXNew);
        //System.out.println("my attempted actual y: " + centerYNew);
        
        //IMPORTANT: the next bit cents centerYNew to be what it actually should be.
        //For some reason scaling it didn't work.
        double fooMinY = 0.0;
        double fooMaxY = 0.0;
        double fooMinX = 0.0;
        double fooMaxX = 0.0;
        for (Polygon poly : dataManager.getPolygonList()) {
            if (fooMaxY < poly.getPoints().get(1))
                fooMaxY = poly.getPoints().get(1);
            if (fooMinY == 0.0)
                fooMinY = poly.getPoints().get(1);
            if (fooMinY > poly.getPoints().get(1))
                fooMinY = poly.getPoints().get(1);
            
            if (fooMaxX < poly.getPoints().get(0))
                fooMaxX = poly.getPoints().get(0);
            if (fooMinX == 0.0)
                fooMinX = poly.getPoints().get(0);
            if (fooMinX > poly.getPoints().get(0))
                fooMinX = poly.getPoints().get(0);
        }
        double centerYNew = (fooMinY+fooMaxY)/2;
        double centerXNew = (fooMinX+fooMaxX)/2;
        
        
        Circle circle = new Circle(5.0, Paint.valueOf("#999999"));
        circle.setVisible(false);
        //circle.setCenterX(centerXNew+(1280/2)-5);
        circle.setCenterX(centerXNew);
        circle.setCenterY(centerYNew);
        mapGroup.getChildren().add(circle);
        
        mapGroup.setScaleX(dataManager.getZoom());
        mapGroup.setScaleY(dataManager.getZoom());
        
        //center on the circle
        double h = app.getGUI().getPrimaryScene().getHeight()/2+30;
        double w = app.getGUI().getPrimaryScene().getWidth()/4;
        
        //System.out.println("h and w shouldn't change");
        //System.out.println(h);
        //System.out.println(w);
        
        //System.out.println("yTranslate:" + mapGroup.getTranslateY());
        //System.out.println("xTrans" + mapGroup.getTranslateX());
        
        while (Math.abs((h - mapGroup.localToScene(circle.getCenterX(), circle.getCenterY()).getY())) > 5) {
            if (h < mapGroup.localToScene(circle.getCenterX(), circle.getCenterY()).getY())
            {mapGroup.setTranslateY(mapGroup.getTranslateY()-5.0); }
            if (h > mapGroup.localToScene(circle.getCenterX(), circle.getCenterY()).getY())
            {mapGroup.setTranslateY(mapGroup.getTranslateY()+5.0);}

        }
        while (Math.abs((w - mapGroup.localToScene(circle.getCenterX(), circle.getCenterY()).getX())) > 5) {
            if (w < mapGroup.localToScene(circle.getCenterX(), circle.getCenterY()).getX())
            {mapGroup.setTranslateX(mapGroup.getTranslateX()-5.0);}
            if (w > mapGroup.localToScene(circle.getCenterX(), circle.getCenterY()).getX()) {
                mapGroup.setTranslateX(mapGroup.getTranslateX()+5.0);
            }

        }
        
        
        //System.out.println("yTranslate post-centering:" + mapGroup.getTranslateY());
        //System.out.println("xTrans" + mapGroup.getTranslateX());
        //System.out.println("circle center:" + centerXNew + "," + centerYNew);
        //System.out.println("mapGroup toScene:" + mapGroup.localToScene(circle.getCenterX(), circle.getCenterY()));
        //System.out.println("stackPane toScene:" + stackPane.localToScene(circle.getCenterX(), circle.getCenterY()));
        
        if (firstZoomCheck) {
            //mapGroup.setTranslateX(mapGroup.getTranslateY()-5);
            mapGroup.setTranslateY(mapGroup.getTranslateY() + 150);
            mapGroup.setTranslateX(mapGroup.getTranslateX() + 675);
            System.out.println("firstZoomCheck if statement entered");
            //centerXNew -= (322.7168273925781 - 316.6902770996094);
            
            firstZoomCheck = false;
        }
        
        mapGroup.getChildren().remove(circle);
        //mapController.processSetMapCenter((fileManager.getMinX()+fileManager.getMaxX())/2.0,
            //(fileManager.getMinY()+fileManager.getMaxY())/2.0, mapPane);
        //System.out.println("centerX: " + centerX);
        //System.out.println("centerY: " + centerY);
        
        //a failed experiment. The center points are not reflective of the actual points. Should I
        //just adjust it for the difference?
        
    }
}
