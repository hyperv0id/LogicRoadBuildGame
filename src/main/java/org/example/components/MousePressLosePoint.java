package org.example.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.example.GameType;
import org.example.app.BottomCtrl;

public class MousePressLosePoint extends Component {
    @Override
    public void onAdded() {
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_PRESSED, e->{
            if (e.getButton() == MouseButton.SECONDARY)
                return;
            if ("in".equals(entity.getString("lastPos"))){
                int lastRow=entity.getInt("row"),lastCol=entity.getInt("col");
                // 将上一次的方块置空
                BottomCtrl.setType(lastRow, lastCol, GameType.NONE);
            }
        });
    }
}
