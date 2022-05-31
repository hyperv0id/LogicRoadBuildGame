package org.example.app;


import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

public class EntityCtrl {
    public static void backToCardSlot(Entity entity){

        // TODO: 2022/5/22 添加运动动画，不然动作太单一
        entity.setPosition((Point2D) entity.getProperties().getObject("originPlace"));
        entity.setRotation(0);

        String lastPos = entity.getString("lastPos");
        if(lastPos.equals("in")){
            VarManager.inc(entity.getType(),1);
        }
        entity.setProperty("lastPos","out");
        entity.setProperty("row", -1);
        entity.setProperty("col", -1);
    }
}
