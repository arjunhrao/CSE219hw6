/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvme.data;

import javafx.scene.image.ImageView;

/**
 *
 * @author ravirao
 */
public class ImageObject {
    ImageView iv = null;
    double x,y;
    String imagePath;
    
    public ImageObject(ImageView imV, double initX, double initY, String initPath) {
        iv = imV;
        x=initX;
        y=initY;
        imagePath = initPath;
    }
}
