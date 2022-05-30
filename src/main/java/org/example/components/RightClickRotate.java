package org.example.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import javafx.scene.input.MouseButton;
import org.example.app.GameLevelCtrl;
import org.example.info.BroadData;

public class RightClickRotate extends Component {
    double angle;
    public RightClickRotate(double ang){
        this.angle = ang;
    }

    /**
     * 右键点击 组件时才会 顺时针旋转ang度
     */
    @Override
    public void onAdded() {
        // 旋转45°
        ViewComponent vc = entity.getViewComponent();
        vc.addOnClickHandler(event ->{
            if(event.getButton() == MouseButton.SECONDARY){
                entity.setRotation(entity.getRotation()+45);
                int row=entity.getInt("row"),col=entity.getInt("col");
                // 删除非法输入
                if (row<0 || col<0)return;
                GameLevelCtrl.broadDatas[row][col].setRotation(entity.getRotation());
            }
        });
    }
}