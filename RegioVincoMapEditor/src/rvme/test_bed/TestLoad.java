/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvme.test_bed;

import java.io.IOException;
import rvme.MapEditorApp;
import rvme.data.DataManager;
import rvme.data.SubRegion;
import rvme.file.FileManager;

/**
 *
 * @author arjun rao
 */
public class TestLoad {
    
    public static DataManager dataManager;
    
    public DataManager getDataManager() {return dataManager;}
    
    public static void main(String[] args) throws IOException {
        MapEditorApp app = new MapEditorApp();
        dataManager = new DataManager(app);
        FileManager fileManager = new FileManager();
        fileManager.loadData(dataManager, "./work/Andorra.json");
        //You should print out all relevant data values like editing settings (background color, border thickness, etc.), subregion data, etc.
        System.out.println(dataManager.getMapName());
        System.out.println(dataManager.getParentDirectory());
        System.out.println(dataManager.getBackgroundColor().getRed()*255);
        System.out.println(dataManager.getBackgroundColor().getGreen()*255);
        System.out.println(dataManager.getBackgroundColor().getBlue()*255);
        System.out.println(dataManager.getBorderColor().getRed());
        System.out.println(dataManager.getBorderColor().getGreen());
        System.out.println(dataManager.getBorderColor().getBlue());
        System.out.println(dataManager.getRawMapPath());
        System.out.println(dataManager.getRegionFlagImagePath());
        System.out.println(dataManager.getCoatOfArmsImagePath());
        System.out.println(dataManager.getMapPositionX());
        System.out.println(dataManager.getMapPositionY());
        
        for (SubRegion subregion : dataManager.getSubregions()) {
            String temp = subregion.getSubregionName() + ", " + subregion.getCapitalName() + ", " +
                    subregion.getLeaderName() + "," + subregion.getSubregionBorderThickness();
            temp += "\n" + (int)(subregion.getSubregionColor().getRed()*255) + ","
                    + (int)(subregion.getSubregionColor().getGreen()*255) +","+ (int)(subregion.getSubregionColor().getBlue()*255);
            System.out.println(temp);
        }
    }
}
