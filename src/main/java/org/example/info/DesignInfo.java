package org.example.info;

public record DesignInfo
(
    // 地图有几行
	int bottomCols,
    // 地图有几列
	int bottomRows,

    // 开始横坐标
	int startX,
    // 开始纵坐标
	int startY,
    // 开始角度
	double startAng,

    // 终点横坐标
	int endX,
    // 终点纵坐标
	int endY,

    // 香蕉个数
	int curveOrbitNum,
    // 直道个数
	int crossNum,
    // 弯道个数
	int curveSquareNum,

    // 星星个数
    int starNum,
	int[][] starPlace,
    // 障碍物个数
	int obstacleNum,
    // 障碍物位置
    int[][] obstaclePlace
)
{
    public String toString(){
        return null;
    }
}
