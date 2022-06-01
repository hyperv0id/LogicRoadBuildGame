package org.example.app;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import org.example.GameType;
import org.example.info.LevelInfo;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class BottomCtrl {
    protected static Entity bottomAccessory;
    protected static Point2D[] outPlace = new Point2D[8];
    protected static int rows_,cols_;
    protected static Point2D[][] placePoints_ = new Point2D[0][];
    protected static GameType[][] types = new GameType[0][];
    protected static double[][] angles = new double[0][];
    protected static boolean[][] haveStar;


    /** 初始化
     * @param rows 有几行
     * @param cols 有几列
     */
    public static void init(LevelInfo info){
        rows_ = info.bottomRows();
        cols_ = info.bottomCols();
        placePoints_ = new Point2D[rows_][cols_];
        types = new GameType[rows_][cols_];
        angles = new double[rows_][cols_];
        haveStar = new boolean[rows_][cols_];
        // 创建实体
        initBottom();
        // 放置点
        generatePlacePoints();
        // 出口相对中心坐标
        generateCornerOffset();
        // 初始化个位置实体类型为NONE
        initGameType();
        // 初始化角度
        initAngle();
    }

    // ===================================

    /**
     * 初始化底板实体
     */
    private static void initBottom() {
        // 创建底板
        bottomAccessory = getGameWorld().create("BasicBottom",new SpawnData());
        // 设置底板位置，margin的左上下大小相同
        double bottomMargin = (FXGL.getAppHeight()-bottomAccessory.getHeight())/2;
        bottomAccessory.setPosition(bottomMargin,bottomMargin);
        FXGL.getGameWorld().addEntity(bottomAccessory);
    }

    /**
     * 初始化角度的信息
     */
    private static void initAngle() {
        for (int i = 0; i < rows_; i++) {
            for (int j = 0; j < cols_; j++) {
                angles[i][j] = 0;
            }
        }
    }

    /**
     * 初始化实体类型的信息
     */
    private static void initGameType() {
        for (int i = 0; i < rows_; i++) {
            for (int j = 0; j < cols_; j++) {
                types[i][j] = GameType.NONE;
            }
        }
    }

    /**
     * 初始化不同角度下的坐标偏移
     */
    private static void generateCornerOffset() {
        double scale = bottomAccessory.getWidth()/9.5;
        for (int i = 0; i < outPlace.length; i++) {
            double ang = ((double) i*45)/180;
            ang *= Math.PI;
            outPlace[i] = new Point2D(Math.sin(ang), -Math.cos(ang)).multiply(scale);
        }
    }


    /**
     * 得到小车运动轨迹
     */
    public static Path getPath() {
        Path path = new Path();
        // 处理起点
        int[] rowCol = getRowColByType(GameType.Starting_Point);
        Point2D startP = getPlacePoint(rowCol[0], rowCol[1]);
        path.getElements().add(new MoveTo(startP.getX(), startP.getY()));
        int outPoint = (int)getAngle(rowCol[0], rowCol[1])/45;
        int[] np = getNextBroad(rowCol[0],rowCol[1], outPoint);
        GameType type = getType(np[0],np[1]);

        while (type!=GameType.NONE && type!=GameType.EndingPoint){
            int inPoint = (outPoint+4)%8;// 入射角
            Point2D inPlace = getOffset(inPoint).add(getPlacePoint(np[0],np[1]));// 入射点
            path.getElements().add(new LineTo(inPlace.getX(), inPlace.getY()));// 直线连接两个板块

            // 先得到出射点
            outPoint = getOutPoint(np[0],np[1],inPoint);
            if (outPoint!=-1){
                // 根据出入情况绘制曲线
                path.getElements().add(innerPath(np[0],np[1], outPoint));
                // 准备下一次循环
                np = getNextBroad(np[0],np[1],outPoint);
            }else{
                np = null;
            }
            if(np!=null){
                type = getType(np[0],np[1]);
            }else {
                type = GameType.NONE;
            }
        }
        if (type==GameType.EndingPoint){
            Point2D endPlace = getPlacePoint(np[0],np[1]);
            path.getElements().add(new LineTo(endPlace.getX(),endPlace.getY()));
        }
        return path;
    }


    /**
     * 得到内部曲线
     * @param x 第几行
     * @param y 第几列
     * @param out 出口在哪个方向
     */
    public static PathElement innerPath(int x, int y, int out){
        PathElement e = null;
        GameType type = getType(x,y);
        switch (type){
            case Cross -> {
                Point2D to = getPlacePoint(x,y).add(getOffset(out));
                e = new LineTo(to.getX(), to.getY());
            }case Hyperbola, Arc -> {
                Point2D cent = getPlacePoint(x,y);
                Point2D to = cent.add(getOffset(out));
                QuadCurveTo qct = new QuadCurveTo();
                qct.setX(to.getX());
                qct.setY(to.getY());
                qct.setControlX(cent.getX());
                qct.setControlY(cent.getY());
                e = qct;
            }
            default -> {}
        }
        return e;
    }

    /**
     * 将组件放置到棋盘上的对应位置
     * @param entity 组件对象
     * @param x 放到第几行
     * @param y 放到第几列
     */
    public static void setPosition(Entity entity, int x,int y){
        Point2D p2d = getPlacePoint(x, y);
        Point2D entitySize = new Point2D(entity.getWidth()/2,entity.getHeight()/2);
        entity.setPosition(p2d.subtract(entitySize));
    }
    /**
     * 将组件放置到棋盘上的对应位置
     * @param entity 组件对象
     * @param x 放到第几行
     * @param y 放到第几列
     */
    public static void setPosition(Entity entity, Point2D p2d){
        Point2D entitySize = new Point2D(entity.getWidth()/2,entity.getHeight()/2);
        entity.setPosition(p2d.subtract(entitySize));
    }
    /**
     * 生成放置点
     */
    public static void generatePlacePoints(){
        int rows = rows_,
                cols = cols_;
        // 得到底板中心点
        Point2D center = new Point2D(bottomAccessory.getWidth()/2, bottomAccessory.getHeight()/2)
                .add(bottomAccessory.getPosition()).add(-2,-2);
        FXGL.entityBuilder().view(new Rectangle(5,5, Color.YELLOW)).at(center).buildAndAttach();
        double width = bottomAccessory.getWidth()/rows,
                height = bottomAccessory.getHeight()/cols;
        width*=0.95;
        height*=0.95;
        // 以中心点为基准，设置物块放置点
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                placePoints_[i][j] = center.add(
                        new Point2D((j-2)*height+height/2,(i-2)*width+width/2));
            }
        }
    }

    public static int[] getNextBroad(int row_n, int col_n, int outPoint){
        double ang = ((double) outPoint*45)/180;
        ang *= Math.PI;
        double dx = Math.sin(ang);
        double dy = Math.cos(ang);
        if(Math.abs(dx-0)>1e-5){
            dx /= Math.abs(dx);
        }
        if (Math.abs(dy-0)>1e-5){
            dy /= -Math.abs(dy);
        }

        int nx = row_n + (int)dy, ny = col_n + (int)dx;
        if (nx<0||nx>= rows_ || ny<0||ny>=cols_){
            return null;
        }
        return new int[]{nx, ny};
    }

    /**
     * 接收一个入射点，返回一个出射点
     * @param row_n 第几行
     * @param col_n 第几列
     * @param inPoint 入射角
     * @return outPoint 出射角
     */
    public static int getOutPoint(int row_n,int col_n, int inPoint){
        int outPoint = -1;
        int[][] startEnd = new int[0][];
        // 范围 0-8
        int dAng = ((int) angles[row_n][col_n])%360 / 45;
        switch (types[row_n][col_n]){
            case Cross -> startEnd = new int[][]{{1,5},{5,1},{3,7},{7,3}};
            case Hyperbola -> startEnd = new int[][]{{1, 3},{3, 1},{5,7},{7,5}};
            case Arc -> startEnd = new int[][]{{0,5},{5,0}};
            default -> {}
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

    /**
     * @param i 八向方位
     * @return 相对中心点对应坐标
     */
    public static Point2D getOffset(int i) {
        assert i>=0 && i<=8;
        return outPlace[i];
    }

    /**
     * 得到x行y列的放置点坐标
     * @param x 行
     * @param y 列
     * @return 坐标
     */
    public static Point2D getPlacePoint(int x, int y) {
        assert x>=0&&y>=0&&x<rows_&&y<cols_;
        return placePoints_[x][y];
    }

    public static GameType getType(int x, int y) {
        assert x>=0&&y>=0&&x<rows_&&y<cols_;
        return types[x][y];
    }

    public static double getAngle(int x, int y) {
        assert x>=0&&y>=0&&x<rows_&&y<cols_;
        return angles[x][y];
    }

    public static int getRows() {
        return rows_;
    }

    public static int getCols() {
        return cols_;
    }

    public static Entity getEntity() {
        return bottomAccessory;
    }

    public static void setType(int x, int y, GameType type){
        assert x>=0&&y>=0&&x<rows_&&y<cols_;
        types[x][y] = type;
    }
    public static void setRotation(int x, int y, double ang){
        assert x>=0&&y>=0&&x<rows_&&y<cols_;
        angles[x][y] = ang;
    }

    /**
     * 设置星星
     * @param x 行号
     * @param y 列号
     */
    public static void setStar(int x, int y, boolean isStar) {
        BottomCtrl.haveStar[x][y] = isStar;
    }

    public static int[] getClosestRowCol(Point2D point){
        double dist = 1e9;
        int[] ret = new int[]{-1,-1};
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                double nd = point.distance(getPlacePoint(i,j));
                if(dist>=nd){
                    dist = nd;
                    ret = new int[]{i,j};
                }
            }
        }
        return ret;
    }

    
    public static int[] getClosestRowCol(Entity entity){
        Point2D point = entity.getPosition().add(
            new Point2D(entity.getWidth()/2, entity.getHeight()/2));
        double dist = 1e9;
        int[] ret = new int[]{-1,-1};
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                double nd = point.distance(getPlacePoint(i,j));
                if(dist>=nd){
                    dist = nd;
                    ret = new int[]{i,j};
                }
            }
        }
        return ret;
    }
    public static Point2D getClosestPlacePoint(Point2D point){
        double dist = 1e9;
        Point2D ret = null;
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                double nd = point.distance(getPlacePoint(i,j));
                if(dist>=nd){
                    dist = nd;
                    ret = getPlacePoint(i,j);
                }
            }
        }
        return ret;
    }

    
    public static int[] getRowColByType(GameType type) {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                if(getType(i, j) == type){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }


    public static boolean haveStar(int x,int y){
        return haveStar[x][y];
    }


    // ===================================
    // 打印
    // ===================================
    public static void printType(){
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                System.out.print(getType(i, j)+"\t");
            }System.out.println();
        }
    }
    public static void printAngle(){
        
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                System.out.print(getAngle(i, j)+"\t");
            }System.out.println();
        }
    }
}
