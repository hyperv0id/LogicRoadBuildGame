package org.example.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MyDraggable extends Component {
    boolean isDragging = false;
    Point2D offset = new Point2D(0,0);

    EventHandler<MouseEvent> onRelease = e-> {
        isDragging = false;
    };
    /**
     * 判断是否被点击，如果是左键点击，则获得鼠标位置
     */
    EventHandler<MouseEvent> onPress = e->{
        if(e.getButton() == MouseButton.SECONDARY)return;
        isDragging = true;
        offset = FXGL.getInput().getMousePositionWorld().subtract(entity.getPosition());
    };
    /**
     * 将两个事件添加到事件代理中
     */
    @Override
    public void onAdded() {
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_PRESSED, onPress);
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_RELEASED, onRelease);
    }

    /**
     * @return 不知道为什么，作者写的 ’boolean=false‘
     */
    @Override
    public boolean isComponentInjectionRequired() {
        return true;
    }

    /**
     * @param tpf 帧数
     * 如果没被拖动，则不更新，反之跟随鼠标移动
     */
    @Override
    public void onUpdate(double tpf) {
        if(!isDragging)return;
        Point2D mousePlace = FXGL.getInput().getMousePositionWorld();
        entity.setPosition( mousePlace.subtract(offset));
    }
}
