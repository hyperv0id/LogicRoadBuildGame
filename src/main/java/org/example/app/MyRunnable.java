package org.example.app;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;

import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class MyRunnable implements java.lang.Runnable{

    protected Entity entity;
    protected Texture texture;
    public MyRunnable(Entity entity, Texture texture) {
        this.entity = entity;
        this.texture = texture;
    }
    public void run() {
        System.out.println("爷跑完了");
        
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.seconds(1));
        ft.setNode(texture);
        ft.setToValue(0);
        ft.play();
        ft.setOnFinished(e->{
            entity.removeFromWorld();
        });
    }

    static {} {
    }
}
