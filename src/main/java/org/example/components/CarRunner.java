package org.example.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.Path;

import java.util.ArrayList;

public class CarRunner extends Component {
    public static boolean runCar(){
        FXGL.getDialogService().showMessageBox("Starting find path");
        ArrayList<Entity> entities =  FXGL.getGameWorld().getEntities();



        return false;
    }
    private Path drawSemiRing(double centerX, double centerY, double radius, Color bgColor, Color strokeColor) {
        Path path = new Path();
        path.setFill(bgColor);
        path.setStroke(strokeColor);


        HLineTo hLineToRightLeg = new HLineTo();
        hLineToRightLeg.setX(centerX + radius);

        ArcTo arcTo = new ArcTo();
        arcTo.setX(centerX - radius);
        arcTo.setY(centerY);
        arcTo.setRadiusX(radius);
        arcTo.setRadiusY(radius);

        HLineTo hLineToLeftLeg = new HLineTo();

        path.getElements().add(hLineToRightLeg);
        path.getElements().add(arcTo);
        path.getElements().add(hLineToLeftLeg);

        return path;
    }

    @Override
    public void onAdded() {
        // TODO: 2022/5/19
    }

    @Override
    public void onUpdate(double tpf) {
        // TODO: 2022/5/19  
    }
}
