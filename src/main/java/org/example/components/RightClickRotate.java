package org.example.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;

import org.example.app.EntityCtrl;

import javafx.scene.input.MouseButton;


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
                EntityCtrl.updateRotate(entity);
                FXGL.play("rotate.wav");
            }
        });
    }
}