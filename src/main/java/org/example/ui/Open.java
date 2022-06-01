package org.example.ui;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.animation.PathTransition;
import javafx.scene.shape.*;
import javafx.util.Duration;
//The open page controller
public class Open {
    @FXML
    private ImageView tra;
    @FXML
    private Button start;
    public static boolean flag=true;
    @FXML                                               //在这个方法里怎么加一个火车汽笛的音效，时间和动画长度（1800millis）同（然后如果这个音效能加，那其实那四个mode选择按钮也可以加...我确实没整明白咋加，那个游戏界面的音乐是用fxgl的setting我这儿也没法copy）
    void op(ActionEvent eve) throws Exception {          //the event that would happen when start button is clicked
        javafx.scene.shape.Path path=new javafx.scene.shape.Path();  //new a path variable to realize animation
        path.getElements().add(new MoveTo(40, 50));  //start from
        path.getElements().add(new LineTo(520, 50));  //go to destination through a line
        PathTransition pt=new PathTransition();
        pt.setDuration(Duration.millis(1800));  //set time of animation
        pt.setPath(path);//set path
        pt.setNode(tra);//set object
        pt.setCycleCount(1);
        pt.play();  //play animation
        pt.setOnFinished(new EventHandler<ActionEvent>(){ //after animation, add the event about what to do next
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) start.getScene().getWindow();    //get the current page
                stage.close();    //close it
                Stage stage2=new Stage();  //build a new stage and a new Sta variable to new a stage(for the next page)
                Sta st2=new Sta();
                try {
                    st2.secon(stage2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } );
    }

}
