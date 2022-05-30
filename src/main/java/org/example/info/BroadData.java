package com.example.info;

import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import org.example.GameType;


/**
 * 记录版面数据
 */
public class BroadData
{
    Point2D place;
    double rotation;
    GameType entityType;

    public BroadData(){
        place=new Point2D(0,0);
        rotation= 0;
        entityType=GameType.NONE;
    }

    public Point2D getPlace() {
        return place;
    }

    public void setPlace(Point2D place) {
        this.place = place;
    }
    public void setPlace(double x,double y) {
        this.place = new javafx.geometry.Point2D(x,y);
    }

    public GameType getEntityType() {
        return entityType;
    }

    public void setEntityType(GameType entityType) {
        this.entityType = entityType;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return "BroadData{" +
                "place=" + place +
                ", rotation=" + rotation +
                ", entityType=" + entityType +
                '}';
    }
}
