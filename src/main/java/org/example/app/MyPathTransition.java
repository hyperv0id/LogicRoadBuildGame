package org.example.app;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import org.example.GameType;

public class MyPathTransition {
    boolean haveStar = false;
    GameType type = GameType.NONE;
    Duration totalTime;
    Duration starFadeTime;
    PathTransition pt;
    public MyPathTransition(){

    }

    public void play(){
        pt.play();
    }
    public final void setOnFinished(EventHandler<ActionEvent> eve) {

    }
}
