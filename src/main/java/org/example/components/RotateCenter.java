package org.example.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class RotateCenter extends Component {
    public final int  LEFT_TOP=0;
    public static final int CENTER=1;
    public final int LEFT_BOTTOM =2;
    public final int RIGHT_TOP = 3;
    public final int RIGHT_BOTTOM = 4;
    private int option;
    public RotateCenter(int centerOption){
        this.option = centerOption;
    }

    @Override
    public void onAdded() {
        switch (option){
            case CENTER -> {
                entity.setRotationOrigin(new Point2D(entity.getWidth()/2, entity.getHeight()/2));}
            case LEFT_BOTTOM -> {
                entity.setRotationOrigin(new Point2D(0, entity.getHeight()));}
            case RIGHT_TOP -> {
                entity.setRotationOrigin(new Point2D(entity.getWidth(),0));
            }
            case RIGHT_BOTTOM -> {
                entity.setRotationOrigin(new Point2D(entity.getWidth(), entity.getHeight()));
            }
            default -> {}
        }
    }
}
