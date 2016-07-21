/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvme.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Scale;
import static javafx.scene.transform.Transform.scale;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
import static rvme.PropertyType.*;
import saf.AppTemplate;
import rvme.data.DataManager;
import rvme.data.SubRegion;
import rvme.file.FileManager;
import rvme.gui.Workspace;
import static saf.settings.AppPropertyType.*;
import static saf.settings.AppStartupConstants.FILE_PROTOCOL;
import static saf.settings.AppStartupConstants.PATH_IMAGES;

/**
 *
 * @author ravirao
 */
public class MapController {
    AppTemplate app;
    DataManager myManager;
    double counter = 1.0;
    double temp1 = 0.0;
    double temp2 = 0.0;
    double temp3 = 0.0;
    double temp4 = 0.0;
    double x2 = 0.0;
    double y2 = 0.0;
    int foo = 0;
    Circle circle = new Circle(5.0, Paint.valueOf("#000000"));
    //hw5
    Circle centerCircle = new Circle(5.0, Paint.valueOf("#000000"));
    double ccX = 0.0;
    double ccY = 0.0;
    Button pdButton;
    Button dfButton;
    Button prevButton;
    Button nextButton;
    TextField mapName;
    TextField dataFile;
    Label capitalLabel;
    Label leaderLabel;
    Label nameLabel;
    TextField parentDirectory;
    File selectedParentDir;
    File selectedFile;
    boolean testing = true;
    int currentIndex = 0;
    TextField subregionName;
    TextField leader;
    TextField capital;
    
    public MapController(AppTemplate initApp) {
	app = initApp;
        //myManager=(DataManager)app.getDataComponent();
        
    }
    
    public Circle getCircle() {return circle;}
    public Circle getCenterCircle() {return centerCircle;}
    
    public DataManager getDataManager() { return myManager;}
    public void setDataManager(DataManager x) { myManager = x;}
    
    public void processInitialize() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        myManager=(DataManager)app.getDataComponent();
        workspace.reloadWorkspace();
    }
    
    public void processSetMapCenter(double x, double y, Pane renderPane) {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        myManager=(DataManager)app.getDataComponent();
        
        
        //can center this at the point we clicked at for clarity
        circle = new Circle(5.0, Paint.valueOf("#999999"));
        circle.setVisible(false);
        circle.setCenterX(x);
        circle.setCenterY(y);
        renderPane.getChildren().add(circle);
        
        //System.out.println(x);
        //System.out.println(y-62);
        double newX = renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX();
        double newY = renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY();
        //System.out.println(newX);
        //x - newX = some constant, and we want the same constant for x - newX after the zoom.
        double temp1 = newX;
        double temp2 = newY;
        
        //System.out.println(renderPane.getLayoutX());
        renderPane.setScaleX(myManager.getZoom());
        renderPane.setScaleY(myManager.getZoom());
        
        
        newX = renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX();
        newY = renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY();
        //System.out.println(newX);
        //while (Math.abs(x - newX - temp1) >= 2) {
        //System.out.println(renderPane.getLayoutX());
        //the following code will never be reached, but it was a way that worked for me for first time clicking.
        /**if (counter == 0.0) {
            System.out.println(renderPane.getLayoutX());
            renderPane.setLayoutX(renderPane.getLayoutX() - (newX - temp1)/(counter));
            System.out.println(renderPane.getLayoutX());

            renderPane.setLayoutY(renderPane.getLayoutY() - (newY - temp2)/(counter));
        }*/
        
        double h = app.getGUI().getPrimaryScene().getHeight()/2;
        double w = app.getGUI().getPrimaryScene().getWidth()/2;
        if (true) {
            while (Math.abs((h - renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY())) > 5) {
                if (h < renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY())
                {renderPane.setTranslateY(renderPane.getTranslateY()-5.0);}
                if (h > renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY())
                {renderPane.setTranslateY(renderPane.getTranslateY()+5.0);}
                
            }
            while (Math.abs((w - renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX())) > 5) {
                if (w < renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX())
                {renderPane.setTranslateX(renderPane.getTranslateX()-5.0);}
                if (w > renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX()) {
                    renderPane.setTranslateX(renderPane.getTranslateX()+5.0);
                }

            }
        }
        
        renderPane.getChildren().remove(circle);
        workspace.reloadWorkspace();
    }
    
    public void processZoomIn(double x, double y, Pane renderPane, double xOrigin, double yOrigin) {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        myManager=(DataManager)app.getDataComponent();
        
        //double newOriginX = x;
        //double newOriginY = y-10;
        
        //renderPane.setLayoutX(x);
        //renderPane.setLayoutY(y);
        //renderPane.setTranslateX(xOrigin-x);
        //renderPane.setTranslateY(yOrigin-(y-10));
        
        //root.getLayoutX add root.getWidth/2 - x
        if (counter == 1) {
            double layoutX = renderPane.getLayoutX();
            double layoutY = renderPane.getLayoutY();
            temp3 = renderPane.getWidth()/2;
            temp4 = renderPane.getHeight()/2;
        }
        if (counter != 1) {
            x2 = x2+(x-app.getGUI().getPrimaryScene().getWidth()/2)/counter;
            y2 = y2+(y-app.getGUI().getPrimaryScene().getHeight()/2)/counter;
        }
        if (counter == 1) {
        x2 = x;
        y2 = y-62;
        }
        
        
        renderPane.setLayoutX(renderPane.getLayoutX() + (temp3) - x);
        renderPane.setLayoutY(renderPane.getLayoutY() + (temp4) - (y-62));
        //same for y
        
       
        
        
        //System.out.println(renderPane.getScaleX());
        //ZOOM
        //renderPane.setScaleX(1.2*renderPane.getScaleX());
        //renderPane.setScaleY(1.2*renderPane.getScaleY());
        
        //renderPane.setLayoutX(layoutX+ (.2*paneWidth) - x);
        //renderPane.setLayoutY(layoutY + (.2*paneHeight) - y);
        
        //can center this at the point we clicked at for clarity
        circle = new Circle(5.0, Paint.valueOf("#999999"));
        circle.setVisible(false);
        circle.setCenterX(x2);
        circle.setCenterY(y2);
        renderPane.getChildren().add(circle);
        System.out.println(x);
        System.out.println(y-62);
        
        //System.out.println(x);
        //System.out.println(y-62);
        double newX = renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX();
        double newY = renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY();
        //System.out.println(newX);
        //x - newX = some constant, and we want the same constant for x - newX after the zoom.
        double temp1 = newX;
        double temp2 = newY;
        
        //System.out.println(renderPane.getLayoutX());
        
        renderPane.setScaleX(renderPane.getScaleX()*2.0);
        renderPane.setScaleY(renderPane.getScaleY()*2.0);
        

        newX = renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX();
        newY = renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY();
        //System.out.println(newX);
        //while (Math.abs(x - newX - temp1) >= 2) {
        //System.out.println(renderPane.getLayoutX());
        //the following code will never be reached, but it was a way that worked for me for first time clicking.
        if (counter == 0.0) {
            System.out.println(renderPane.getLayoutX());
            renderPane.setLayoutX(renderPane.getLayoutX() - (newX - temp1)/(counter));
            System.out.println(renderPane.getLayoutX());

            renderPane.setLayoutY(renderPane.getLayoutY() - (newY - temp2)/(counter));
        }
        
        double h = app.getGUI().getPrimaryScene().getHeight()/2;
        double w = app.getGUI().getPrimaryScene().getWidth()/2;
        if (counter != 0.0) {
            while (Math.abs((h - renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY())) > 5) {
                if (h < renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY())
                {renderPane.setTranslateY(renderPane.getTranslateY()-5.0);}
                if (h > renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY())
                {renderPane.setTranslateY(renderPane.getTranslateY()+5.0);}
                
            }
            while (Math.abs((w - renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX())) > 5) {
                if (w < renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX())
                {renderPane.setTranslateX(renderPane.getTranslateX()-5.0);}
                if (w > renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX()) {
                    renderPane.setTranslateX(renderPane.getTranslateX()+5.0);
                }

            }
        }
        System.out.println("*****");
        counter = counter*2.0;
        
        renderPane.getChildren().remove(circle);
        workspace.reloadWorkspace();
        
    }
    
    public void processZoomOut(double x, double y, Pane renderPane, double xOrigin, double yOrigin) {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        myManager=(DataManager)app.getDataComponent();
        
        if (x2 == 0 && y2 == 0) {
            x2 = app.getGUI().getPrimaryScene().getWidth()/2;
            y2 = app.getGUI().getPrimaryScene().getHeight()/2;
        }
        
        circle = new Circle(5.0, Paint.valueOf("#999999"));
        circle.setVisible(false);
        circle.setCenterX(x2);
        circle.setCenterY(y2);
        renderPane.getChildren().add(circle);
        
        renderPane.setScaleX(renderPane.getScaleX()*0.5);
        renderPane.setScaleY(renderPane.getScaleY()*0.5);
        
        
        
        double h = app.getGUI().getPrimaryScene().getHeight()/2;
        double w = app.getGUI().getPrimaryScene().getWidth()/2;
        if (counter != 0.0) {
            while (Math.abs((h - renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY())) > 5) {
                if (h < renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY())
                {renderPane.setTranslateY(renderPane.getTranslateY()-5.0);}
                if (h > renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getY())
                {renderPane.setTranslateY(renderPane.getTranslateY()+5.0);}
                
            }
            while (Math.abs((w - renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX())) > 5) {
                if (w < renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX())
                {renderPane.setTranslateX(renderPane.getTranslateX()-5.0);}
                if (w > renderPane.localToScene(circle.getCenterX(), circle.getCenterY()).getX()) {
                    renderPane.setTranslateX(renderPane.getTranslateX()+5.0);
                }

            }
        }
        
        
        counter = counter/2.0;
        
        workspace.reloadWorkspace();
        

        
    }
    
    public void processKeyLeft(Pane renderPane) {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        myManager=(DataManager)app.getDataComponent();
        renderPane.setLayoutX(renderPane.getLayoutX() + 50/counter);
        x2 = x2 - 50/counter;
        //x2 = x2+((x2-50)-app.getGUI().getPrimaryScene().getWidth()/2)/counter;
    }
    public void processKeyDown(Pane renderPane) {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        myManager=(DataManager)app.getDataComponent();
        renderPane.setLayoutY(renderPane.getLayoutY() - 50/counter);
        x2 += 50/counter;
        //y2 = y2+((y2+50)-app.getGUI().getPrimaryScene().getHeight()/2)/counter;

    }
    public void processKeyUp(Pane renderPane) {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        myManager=(DataManager)app.getDataComponent();
        renderPane.setLayoutY(renderPane.getLayoutY() + 50/counter);
        x2 -= 50/counter;
        //y2 = y2+((y2-50)-app.getGUI().getPrimaryScene().getHeight()/2)/counter;

    }
    public void processKeyRight(Pane renderPane) {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        myManager=(DataManager)app.getDataComponent();
        renderPane.setLayoutX(renderPane.getLayoutX() - 50/counter);
        x2 = x2 + 50/counter;
        //x2 = x2+((x2-50)-app.getGUI().getPrimaryScene().getWidth()/2)/counter;

    }

    //hw4
    public void processMapDimensions() {
        // ENABLE/DISABLE THE PROPER BUTTONS
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace();
        myManager=(DataManager)app.getDataComponent();
        
        //need a popup dialogue box with multiple input fields for all of the necessary data
        //the following line is included so that we can use things from the xml files
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //creates the dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(props.getProperty(DIMENSIONS_HEADING));
        
        //adds ok and cancel buttons
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        
        //start creating the boxes/panes for the gui
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        
        TextField width = new TextField();
        //Note: the next two commented lines of code set the prompty text, which is nice but unnecessary
        //category.setPromptText(props.getProperty(CATEGORY_PROMPT));
        TextField height = new TextField();
        //description.setPromptText("Description");

        Label widthLabel = new Label(props.getProperty(WIDTH_PROMPT_LABEL));
        Label heightLabel = new Label(props.getProperty(HEIGHT_PROMPT_LABEL));
        addSheetsToLabel(widthLabel, "width_prompt_label");
        addSheetsToLabel(heightLabel, "height_prompt_label");
        
        SubRegion mySubRegion = new SubRegion();
        
        gridPane.add(widthLabel, 0, 0);
        gridPane.add(width, 1, 0);
        gridPane.add(heightLabel, 0, 1);
        gridPane.add(height, 1, 1);
        
        dialog.getDialogPane().setContent(gridPane);
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.isPresent() && result.get() == okButtonType) {
            //set and save the data to myItem and add it to the arraylist in the datamanager obj myManager
            //use mysubregion? check the other controller
            
            //enable the save button
            //HW4: app.getGUI().getSaveButton().setDisable(false);
            
            changesMade();
            //update the workspace / table
            workspace.reloadWorkspace();
            //useless line of code: app.getWorkspaceComponent().getWorkspace().getChildren().clear();
        }
    }
        
    public void changesMade() {
        //changes the value of saved in the AppFileController to show that the list
        //has been edited and has not been saved so that exiting will make sure to ask before just exiting.
        app.getGUI().getFileController().setSaved(false);
    }
    
    public void addSheetsToLabel(Label o, String cssHeading) {
        o.getStylesheets().addAll(app.getGUI().getPrimaryScene().getStylesheets());
        o.getStyleClass().add(cssHeading);
    }

    public void processEditSubregion(SubRegion it) {
        System.out.println("\n-----------Edit subregion started:" + it.getLeaderName());
        
        // ENABLE/DISABLE THE PROPER BUTTONS
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	//workspace.reloadWorkspace();
        myManager=(DataManager)app.getDataComponent();
        
        //need a popup dialogue box with multiple input fields for all of the necessary data
        //the following line is included so that we can use things from the xml files
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //creates the dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(props.getProperty(EDIT_SUBREGION_HEADING));
        
        //adds ok and cancel buttons
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
        //above, there previously was an add Cancel button type (you could see exactly if you scroll to dimensions dialog,
        //but having that isn't necessary anymore.
        
        //start creating the boxes/panes for the gui
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        
        subregionName = new TextField();
        leader = new TextField();
        capital = new TextField();

        nameLabel = new Label(props.getProperty(SUBREGION_NAME_PROMPT_LABEL));
        leaderLabel = new Label(props.getProperty(LEADER_PROMPT_LABEL));
        capitalLabel = new Label(props.getProperty(CAPITAL_PROMPT_LABEL));
        addSheetsToLabel(nameLabel, "subregion_name_prompt_label");
        addSheetsToLabel(leaderLabel, "leader_prompt_label");
        addSheetsToLabel(capitalLabel, "capital_prompt_label");
        
        //set the data's fields to the text fields
        subregionName.setText(it.getSubregionName());
        leader.setText(it.getLeaderName());
        capital.setText(it.getCapitalName());
        //testing
        System.out.println(leader.getText());
        
        
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(subregionName, 1, 0);
        gridPane.add(leaderLabel, 0, 1);
        gridPane.add(leader, 1, 1);
        gridPane.add(capitalLabel, 0, 2);
        gridPane.add(capital, 1, 2);
        
        //note: I got lazy here and stopped doing xml bc I may as well finish first... Although css?
        VBox flagImageBox = new VBox();
        Label flagImageLabel = new Label("Flag Image:");
        //create the flag image
        String imagePath = FILE_PROTOCOL + it.getFlagImagePath();
        File flagImageFileName = new File(it.getFlagImagePath());
            
        Image flagImage = new Image(imagePath);
        ImageView flagIm = new ImageView(flagImage);
        flagIm.setFitWidth(200.0);
        flagIm.setFitHeight(200.0);
        //mapIm.relocate(15,100);
        
        flagImageBox.getChildren().addAll(flagImageLabel, flagIm);
        gridPane.add(flagImageBox, 0, 3);
        
        //now for the leaderbox
        VBox leaderImageBox = new VBox();
        Label leaderImageLabel = new Label("Leader Image:");
        //create the leader image
        //String imagePath2 = FILE_PROTOCOL + PATH_IMAGES + "LeaderImage.png";
        String imagePath2 = FILE_PROTOCOL + it.getLeaderImagePath();
        File leaderImageFileName = new File(it.getLeaderImagePath());
        
        Image leaderImage = new Image(imagePath2);
        ImageView leaderIm = new ImageView(leaderImage);
        leaderIm.setFitWidth(200.0);
        leaderIm.setFitHeight(200.0);
        //mapIm.relocate(15,100);
        
        
        leaderImageBox.getChildren().addAll(leaderImageLabel, leaderIm);
        gridPane.add(leaderImageBox, 1, 3);
        
        if (!leaderImageFileName.exists()) {
            leaderImageBox.getChildren().add(new Label(leaderImageFileName + "\ndoesn't exist."));
            
        }
        if (!flagImageFileName.exists()) {
            flagImageBox.getChildren().add(new Label(flagImageFileName + "\ndoesn't exist."));
            //flagImageLabel.setText("flag Image: " + flagImageFileName + " doesn't exist.");
        }
        
        //make and add prev and next buttons - NOTE: these buttons should be made outside later,
        //need to be able to access them. Also, maybe make a separate class for the subregion dialog.
        FlowPane nextPrevPane = new FlowPane();
        prevButton = app.getGUI().initChildButton(nextPrevPane, PREV_BUTTON.toString(), PREV_BUTTON_TT.toString(), false);
        nextButton = app.getGUI().initChildButton(nextPrevPane, NEXT_BUTTON.toString(), NEXT_BUTTON_TT.toString(), false);
        
        //disable/enable the right next or previous buttons
        if (myManager.getSubregions().indexOf(it) == 0)
            prevButton.setDisable(true);
        if (myManager.getSubregions().indexOf(it) == myManager.getSubregions().size()-1)
            nextButton.setDisable(true);
        
        //nextPrevPane.getChildren().add(new Label("asdf"));
        gridPane.add(nextPrevPane, 0, 4);
        
        handlePrevNextButtons(prevButton, nextButton, dialog, it);
        
        currentIndex = myManager.getSubregions().indexOf(it);
        System.out.println("\t\t\t------current:" + currentIndex);
        
        dialog.getDialogPane().setContent(gridPane);
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.isPresent() && result.get() == okButtonType) {
            //save the data to the dataManager
            int srIndex = myManager.getSubregions().indexOf(it);
            SubRegion sr = myManager.getSubregions().get(srIndex);
            sr.setCapitalName(capital.getText());
            sr.setLeaderName(leader.getText());
            sr.setSubregionName(subregionName.getText());
            
            
            //enable the save button
            //HW4: app.getGUI().getSaveButton().setDisable(false);
            
            changesMade();
            //update the workspace / table
            
            
            //workspace.getSubregionsTable().setItems(myManager.getSubregions());
            
            //update the selection
            System.out.println(myManager.getSubregions().get(0).getSubregionName());
            //workspace.setLastSelectedIndex(srIndex);
            //workspace.setTempIndex(srIndex);
            //workspace.setSelected(true);
            //workspace.getSubregionsTable().getSelectionModel().clearSelection();
            //workspace.getSubregionsTable().getSelectionModel().select(srIndex);
            
            //This code is really messy but it's ok. It actually doesn't really do anything,
            //except the last line after reloadWorkspace that forces a deselect, making double-clicking
            //on polygons retain selection and double clicking on table items not. This is okay because
            //I can say I wanted to implement both. It's better than leaving it with the double clicking
            //on the table --> OK leading to pseudo-highlighting the first subregion.
            //Actually, I fixed it by setting selected. Hooray!
            System.out.println("##srIndex to be selected after closing dialog: " +srIndex);
            //workspace.getSubregionsTable().getSelectionModel().clearSelection();
            //workspace.reloadWorkspace();
            workspace.getSubregionsTable().getSelectionModel().clearSelection();
            
            System.out.println(workspace.getSelected());
            workspace.setSelected(false);
            workspace.setTempIndex(srIndex);
            workspace.setLastSelectedIndex(srIndex);
            
            workspace.reloadMapFromTable(srIndex, true);
            //System.out.println(workspace.getSelected());
            workspace.getSubregionsTable().getSelectionModel().select(srIndex);
            //System.out.println(workspace.getSelected());
            //useless line of code: app.getWorkspaceComponent().getWorkspace().getChildren().clear();
        }
        
    }
    
    public void handlePrevNextButtons(Button prev, Button next, Dialog<ButtonType> dialog, SubRegion it) {
        prev.setOnAction(e -> {
            
            Workspace workspace = (Workspace)app.getWorkspaceComponent();
            myManager=(DataManager)app.getDataComponent();
            
            if (workspace.getTempIndex() != 0) {
                //dialog.close();
                
                int srIndex2 = myManager.getSubregions().indexOf(it);
                SubRegion sr = myManager.getSubregions().get(srIndex2);
                sr.setCapitalName(capital.getText());
                sr.setLeaderName(leader.getText());
                sr.setSubregionName(subregionName.getText());
                System.out.println("prev test: " + sr.getLeaderName());
                //copy pasted code from my edit subregion to make the right things occur when your
                //dialog closes
                System.out.println("* * * * * selected index in controller:" + workspace.getTempIndex());
                
                //workspace.getSubregionsTable().getSelectionModel().clearSelection();
                //workspace.reloadWorkspace();
                workspace.getSubregionsTable().getSelectionModel().clearSelection();

                System.out.println(workspace.getSelected());
                workspace.setSelected(false);
                workspace.setTempIndex(srIndex2);
                workspace.reloadMapFromTable(srIndex2, true);
                //System.out.println(workspace.getSelected());
                workspace.getSubregionsTable().getSelectionModel().select(srIndex2);
                System.out.println(" * *#* * temp index - 1: " + (workspace.getTempIndex()-1) + ",srIndex2:"+srIndex2);
                SubRegion newIt = myManager.getSubregions().get(workspace.getTempIndex()-1);
                System.out.println(newIt.getLeaderName());
                //SubRegion newIt = myManager.getSubregions().get(srIndex - 1);
                //workspace.setTempIndex(workspace.getTempIndex()-1);
                //SubRegion it = workspace.getSubregionsTable().getSelectionModel().getSelectedItem();
                processEditSubregion(newIt);
                dialog.close();
                
            }
            
        });
        next.setOnAction(e -> {
            Workspace workspace = (Workspace)app.getWorkspaceComponent();
            myManager=(DataManager)app.getDataComponent();
            
            if (workspace.getTempIndex() != myManager.getSubregions().size()-1) {
                //dialog.close();
                
                int srIndex2 = myManager.getSubregions().indexOf(it);
                SubRegion sr = myManager.getSubregions().get(srIndex2);
                sr.setCapitalName(capital.getText());
                sr.setLeaderName(leader.getText());
                sr.setSubregionName(subregionName.getText());
                System.out.println("prev test: " + sr.getLeaderName());
                //copy pasted code from my edit subregion to make the right things occur when your
                //dialog closes
                System.out.println("* * * * * selected index in controller:" + workspace.getTempIndex());
                
                //workspace.getSubregionsTable().getSelectionModel().clearSelection();
                //workspace.reloadWorkspace();
                workspace.getSubregionsTable().getSelectionModel().clearSelection();

                System.out.println(workspace.getSelected());
                workspace.setSelected(false);
                workspace.setTempIndex(srIndex2);
                workspace.reloadMapFromTable(srIndex2, true);
                //System.out.println(workspace.getSelected());
                workspace.getSubregionsTable().getSelectionModel().select(srIndex2);
                System.out.println(" * *#* * temp index + 1: " + (workspace.getTempIndex()+1) + ",srIndex2:"+srIndex2);
                SubRegion newIt = myManager.getSubregions().get(workspace.getTempIndex()+1);
                System.out.println(newIt.getLeaderName());
                //SubRegion newIt = myManager.getSubregions().get(srIndex - 1);
                //workspace.setTempIndex(workspace.getTempIndex()-1);
                //SubRegion it = workspace.getSubregionsTable().getSelectionModel().getSelectedItem();
                processEditSubregion(newIt);
                dialog.close();
                
            }

        });
    }

    public boolean processNewMapDialog() throws IOException {
        // ENABLE/DISABLE THE PROPER BUTTONS
	Workspace workspace = (Workspace)app.getWorkspaceComponent();
	//workspace.reloadWorkspace();
        myManager=(DataManager)app.getDataComponent();
        
        //need a popup dialogue box with multiple input fields for all of the necessary data
        //the following line is included so that we can use things from the xml files
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //creates the dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(props.getProperty(NEW_MAP_HEADING));
        
        //adds ok and cancel buttons
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        //above, there previously was an add Cancel button type (you could see exactly if you scroll to dimensions dialog,
        //but having that isn't necessary anymore.
        
        //start creating the boxes/panes for the gui
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        
        mapName = new TextField();
        parentDirectory = new TextField();
        dataFile = new TextField();

        //creating labels and buttons and formatting them
        Label mapNameLabel = new Label(props.getProperty(MAP_NAME_PROMPT_LABEL));
        Label parentDirectoryLabel = new Label(props.getProperty(PARENT_DIRECTORY_LABEL));
        Label dataFileLabel = new Label(props.getProperty(DATA_FILE_LABEL));
        addSheetsToLabel(mapNameLabel, "subregion_name_prompt_label");
        addSheetsToLabel(parentDirectoryLabel, "leader_prompt_label");
        addSheetsToLabel(dataFileLabel, "capital_prompt_label");
        FlowPane pd = new FlowPane();
        FlowPane df = new FlowPane();
        pd.getChildren().addAll(parentDirectoryLabel, parentDirectory);
        df.getChildren().addAll(dataFileLabel, dataFile);
        pdButton = app.getGUI().initChildButton(pd, PARENT_DIRECTORY_BUTTON.toString(), PARENT_DIRECTORY_BUTTON_TT.toString(), false);
        dfButton = app.getGUI().initChildButton(df, DATA_FILE_BUTTON.toString(), DATA_FILE_BUTTON_TT.toString(), false);
        
        setFileButtons();
        
        //myManager.setRawMapPath(selectedFile.getAbsolutePath());
        //System.out.println("new map:" + selectedFile.getAbsolutePath());
        //FileChooser fc = new FileChooser();
        
        FlowPane mapNameStuff = new FlowPane();
        mapNameStuff.getChildren().addAll(mapNameLabel, mapName);
        gridPane.add(mapNameStuff, 0, 0);
        gridPane.add(pd, 0, 1);
        gridPane.add(df, 0, 2);
        
        dialog.getDialogPane().setContent(gridPane);
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.isPresent() && result.get() == okButtonType) {
            //you pressed ok. Your files and textfields should have changed.
            //Now, you need to create the map using the file.
            
            createMap();
            
            System.out.println("createMap() complete");
            
            //System.out.println(myManager.getPolygonList().size());
            
            
            //enable the save button
            app.getGUI().getSaveButton().setDisable(false);
            
            changesMade();
            //update the workspace / table
            workspace.reloadWorkspaceFromNew();
            //useless line of code: app.getWorkspaceComponent().getWorkspace().getChildren().clear();
            workspace.reloadWorkspace();
            return true;
        } else {
            return false;
        }
        
    }
    
    public void createMap() throws IOException {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
	workspace.reloadWorkspace();
        myManager=(DataManager)app.getDataComponent();
        FileManager fileManager = (FileManager)app.getFileComponent();
        System.out.println("createMap(): " + dataFile.getText());
        
        fileManager.loadPolygonData(myManager, dataFile.getText());
        
        System.out.println("loadPolygonData complete");
        System.out.println("polygonlist size: " + myManager.getPolygonList().size());
        System.out.println("sr list size: " + myManager.getSubregions().size());
        System.out.println("numbers for polys size: " + myManager.getNumPolygonsList().size());
        
        //create blank subregions
        for (int j = 0; j < myManager.getNumPolygonsList().size(); j++) {
            SubRegion sr = new SubRegion("Subregion" + (j+1), "", "");
            myManager.getSubregions().add(sr);
        }
        
        System.out.println("first subregion size:" + myManager.getSubregions().get(0).getPolygonList().size());
        //add the appropriate polygons
        addPolygonsToSubregions();
        System.out.println("first sr size:" + myManager.getSubregions().get(0).getPolygonList().size());
        System.out.println("last sr size:" + myManager.getSubregions().get(myManager.getSubregions().size()-1).getPolygonList().size());
        //workspace.reloadWorkspace();
        
        System.out.println("reload complete");
    }
    
    public void addPolygonsToSubregions() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        myManager=(DataManager)app.getDataComponent();
        int adder = 0;
        for (int k = 0; k < myManager.getSubregions().size(); k++) {
            //System.out.println("k:" + k + "\n adder:" + adder);
            myManager.getSubregions().get(k).getPolygonList().add(myManager.getPolygonList().get(k+adder));
            
            for (int i = 0; i < myManager.getNumPolygonsList().get(k)-1; i++) {
                myManager.getSubregions().get(k).getPolygonList().add(myManager.getPolygonList().get(k+adder+1));
                adder++;
                //System.out.println("****" + (k+adder));
            }
        }
        
    }

    public void processRandomizeMapColors() {
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        myManager=(DataManager)app.getDataComponent();
        //polygons or subregions?
        //Generate a list of colors that can be used (254 / numberOfSubregions) and assign (randomly) each color to a subregion.
        ArrayList<Integer> list = new ArrayList<>();
        int counter = 0;
        //add random number from 1 - #subregions
        for (SubRegion sub : myManager.getSubregions()) {
            list.add(counter);
            counter++;
        }
        int spacing = 254 / myManager.getSubregions().size();
        for (int j = 0; j < myManager.getSubregions().size(); j++) {
            int temp = 1 + j*spacing;
            Color c = Color.rgb(temp, temp, temp);
            int whichSubregion = (int)(Math.random()*list.size());
            myManager.getSubregions().get(list.get(whichSubregion)).setSubregionColor(c);
            list.remove(whichSubregion);
        }
        
        workspace.reloadWorkspace();
        
    }
    
    public void setFileButtons() {
        pdButton.setOnAction(e -> {
            //let them choose the parent directory
            //FileChooser fc = new FileChooser();
            //fc.setInitialDirectory(new File("./raw_map_data"));
            //fc.setTitle("Select Your Map Coordinates File");
            //File file = fc.showOpenDialog(null);
            //selectedParentDir = file;
            DirectoryChooser dc = new DirectoryChooser();
            dc.setInitialDirectory(new File("./raw_map_data"));
            dc.setTitle("Select Your Parent DirectoryFile");
            File file = dc.showDialog(null);
            selectedParentDir = file;
            
            
            parentDirectory.setText(selectedParentDir.getAbsolutePath());
            //if (selectedFile != null)
                //okbtn.setDisable(false);
            
        });
        
        dfButton.setOnAction(e -> {
            //let them choose the parent directory
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("./raw_map_data"));
            fc.setTitle("Select Your Map Coordinates File");
            File file = fc.showOpenDialog(null);
            selectedFile = file;
            
            dataFile.setText(selectedFile.getAbsolutePath());
            
            //hw6 morning
            myManager.setRawMapPath(selectedFile.getAbsolutePath());
        });
    }
}
