package org.example.app;

import com.almasb.fxgl.dsl.FXGL;
import org.example.GameType;

import java.io.Serializable;

public class VarManager {
    public static void inc(Serializable type, int n){
        if (GameType.Arc.equals(type)) {
            FXGL.inc("arcNum", n);
        } else if (GameType.Hyperbola.equals(type)) {
            FXGL.inc("hyperbolaNum",n);
        }else if (GameType.Cross.equals(type)){
            FXGL.inc("crossNum",n);
        }
    }
}
