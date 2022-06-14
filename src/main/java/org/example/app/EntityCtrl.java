package org.example.app;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import org.example.GameType;

import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class EntityCtrl {


    /**
     * change the type to corresponding row and col
     * @param entity the entity you want to change
     *
     */
    public static void lossPPFocus(Entity entity){
        if(entity.getString("lastPos").equals("out"))return;
        int[] sp = BottomCtrl.getClosestRowCol(entity);
        int x=sp[0],y=sp[1];
        

        BottomCtrl.setType(x, y, GameType.NONE);
        BottomCtrl.setRotation(x, y, 0);
    }


    /**
     * update the row and col, because it is close to the destination, and in more detail, different type have different appearance
     * @param entity the entity you want to change 
     * @param x row number
     * @param y col number
     */
    public static void update(Entity entity,int x,int y){
        entity.setProperty("row", x);
        entity.setProperty("col", y);
        GameType type = (GameType) entity.getType();
        switch (type) {
            case Arc,Cross, Hyperbola->
                    updateBasic(entity, x, y);
            case StartingPoint,EndingPoint,Obstacle->
                    updateStartEnd(entity, x, y);
            default ->{}
        }
    }


    private static void updateStartEnd(Entity entity, int x, int y) {

        BottomCtrl.setRotation(x, y, entity.getRotation());
        BottomCtrl.setType(x, y, GameType.StartingPoint);

        entity.setProperty("row", x);
        entity.setProperty("col", y);
        int row=entity.getInt("row"),col=entity.getInt("col");
        BottomCtrl.setRotation(row, col, entity.getRotation());
        BottomCtrl.setType(row, col, (GameType) entity.getType());
    }


    public static void updateBasic(Entity entity, int x,int y){

        if (entity.getString("lastPos").equals("out")){
            VarManager.inc(entity.getType(), -1);
            entity.setProperty("lastPos","in");
        }
        // 在鼠标按下时原来的已经置空，不要操作
        // 设置这次的位置，并设置对应位置的类型
        entity.setProperty("row", x);
        entity.setProperty("col", y);
        int row=entity.getInt("row"),col=entity.getInt("col");
        BottomCtrl.setRotation(row, col, entity.getRotation());
        BottomCtrl.setType(row, col, (GameType) entity.getType());
        // BottomCtrl.setRotation(row, col, entity.getRotation());
    }


    public static void updateRotate(Entity entity) {
        int[] pos = BottomCtrl.getClosestRowCol(entity);
        int row = pos[0],col=pos[1];

        // detect illegal input
        if (row<0 || col<0)return;
        BottomCtrl.setRotation(row,col,entity.getRotation());
    }


    public static void backToLastPlace(Entity entity, GameType type) {

        Point2D np = entity.getPosition();
        int lastRow = entity.getProperties().getInt("row");
        int lastCol = entity.getProperties().getInt("col");
        if(lastRow<0 || lastCol<0 || lastRow>=4 || lastCol>=4){
            // 表示组件可以跳出到地图外面，所以要回到卡牌槽
            backToCardSlot(entity);
            return;
        }

        Point2D halfSize = new Point2D(entity.getWidth(), entity.getHeight()).multiply(0.5);
        Point2D dest = BottomCtrl.getPlacePoint(lastRow, lastCol).subtract(halfSize);
        Path path = new Path();
        path.getElements().add(new MoveTo(np.getX(),np.getY()));
        path.getElements().add(new LineTo(dest.getX(),dest.getY()));

        var anim = FXGL.animationBuilder()
                .duration(Duration.seconds(0.3))
                .translate(entity)
                .alongPath(path);
        anim.setOnFinished(new Runnable(){
            @Override
            public void run() {
                // 得复原上次的Type信息
                BottomCtrl.setType(lastRow, lastCol, type);
                BottomCtrl.setPosition(entity, lastRow, lastCol);
            }

        });
        anim.buildAndPlay();

    }

    public static void backToCardSlot(Entity entity){
        int row = entity.getInt("row"), col = entity.getInt("col");
        if(row>=0&&col>=0&&row<BottomCtrl.getRows()&&col<BottomCtrl.getCols()){
            BottomCtrl.setType(row, col, GameType.NONE);
        }
        Point2D np = entity.getPosition();
        Point2D op = (Point2D) entity.getProperties().getObject("originPlace");

        Path path = new Path();
        path.getElements().add(new MoveTo(np.getX(),np.getY()));
        path.getElements().add(new LineTo(op.getX(),op.getY()));

        var anim = FXGL.animationBuilder()
                .duration(Duration.seconds(0.3))
                .translate(entity)
                .alongPath(path);
        anim.setOnFinished(new Runnable(){
            @Override
            public void run() {
                entity.setRotation(0);
                entity.setPosition(op);
                String lastPos = entity.getString("lastPos");
                if(lastPos.equals("in")){
                    VarManager.inc(entity.getType(),1);
                }
                entity.setProperty("lastPos","out");
                entity.setProperty("row", -1);
                entity.setProperty("col", -1);
            }

        });
        anim.buildAndPlay();
    }
}
