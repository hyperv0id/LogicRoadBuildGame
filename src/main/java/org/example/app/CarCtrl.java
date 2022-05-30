package org.example.app;

import com.almasb.fxgl.dsl.FXGL;
import com.sun.javafx.scene.traversal.Direction;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import org.example.GameType;
import org.example.info.BroadData;
import org.example.info.LevelInfo;

public class CarCtrl {
    static LevelInfo info;
    static BroadData[][] datas;
    private static Path path;

    public static void startRunCar() {
        info = GameLevelCtrl.info;
        datas = GameLevelCtrl.broadDatas;

        path = new Path();
        path.setStrokeWidth(2);
        path.setStroke(Color.GREEN);


        // 初始化出发点
        BroadData startData = datas[info.startX()][info.startY()];
        Point2D startPos = startData.getPlace();
        path.getElements().add(new MoveTo(startPos.getX(),startPos.getY()));

        int outPoint = (int) (startData.getRotation()/45);
        int[] nextBroadPlace = getNextBroad(info.startX(),info.startY(),outPoint);
        BroadData data = datas[nextBroadPlace[0]][nextBroadPlace[1]];

        while (data.getEntityType()!=GameType.NONE && data.getEntityType()!=GameType.EndingPoint){

            LineTo line = new LineTo(data.getPlace().getX(),data.getPlace().getY());
            path.getElements().add(line);
            outPoint = getOutPoint(data, (outPoint+4)%8);
            nextBroadPlace = getNextBroad(nextBroadPlace[0],nextBroadPlace[1],outPoint);
            if (nextBroadPlace!=null){

                data = datas[nextBroadPlace[0]][nextBroadPlace[1]];
            }else {
                data = new BroadData();
            }

        };
        if (data.getEntityType()== GameType.EndingPoint){
            // TODO: 2022/5/24
            Point2D endPoint = datas[info.endX()][info.endY()].getPlace();
            path.getElements().add(new LineTo(endPoint.getX(),endPoint.getY()));
        }

        Image image = new Image("assets/textures/accessory/car.png");
        ImageView iv = new ImageView(image);
        iv.setX(2);
        iv.setY(1000);
        FXGL.addUINode(iv,500,500);

//        AnimatedPath
        PathTransition pl = new PathTransition();
        pl.setNode(iv);
        pl.setDuration(Duration.seconds(2));
        pl.setPath(path);
        pl.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pl.setAutoReverse(false);
        pl.play();
        pl.setOnFinished(e-> {
            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.seconds(1));
            ft.setNode(iv);
            ft.setToValue(0);
            ft.play();
            ft.setOnFinished(ee->{FXGL.removeUINode(iv);});
        });

    }

    /**
     * 接收一个入射点，返回一个出射点
     * @param broadData 这块地的信息
     * @param inPoint 入射角
     * @return outPoint 出射角
     */
    public static int getOutPoint(BroadData broadData, int inPoint){
        int outPoint = -1;
        int[][] startEnd = new int[0][];
        // 范围 0-8
        int dAng = ((int)broadData.getRotation())%360 / 45;
        switch (broadData.getEntityType()){
            case Cross -> {
                startEnd = new int[][]{{1,5},{5,1},{3,7},{7,3}};
            }
            case Hyperbola -> {
                startEnd = new int[][]{{1, 3},{3, 1},{5,7},{7,5}};
            }
            case Arc -> {
                startEnd = new int[][]{{0,5},{5,0}};
            }
        }

        for (int[] pair : startEnd) {
            pair[1] = (pair[1] + dAng) % 8;
            pair[0] = (pair[0] + dAng) % 8;
        }

        for (int[] pair : startEnd) {
            if(inPoint == pair[0]){
                outPoint = pair[1];
                break;
            }
        }
        return outPoint;
    }

    static int[] getNextBroad(int row, int col, int outPoint){
        double ang = ((double) outPoint*45)/180;
        ang *= Math.PI;
        double dx = Math.sin(ang);
        double dy = Math.cos(ang);
        if(Math.abs(dx-0)>1e-5){
            dx /= Math.abs(dx);
        }if (Math.abs(dy-0)>1e-5){
            dy /= -Math.abs(dy);
        }

        int nx = row + (int)dy, ny = col + (int)dx;
        if (nx<0||nx>= info.bottomRows() || ny<0||ny>=info.bottomCols()){
            return null;
        };
        return new int[]{nx, ny};
    }

}
