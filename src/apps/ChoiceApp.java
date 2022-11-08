package apps;

import controllers.ChoiceController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import tree.Node;

import java.io.IOException;
import java.util.ArrayList;

public class ChoiceApp extends javafx.application.Application {
    private double xOffset;
    private double yOffset;
    private final Node initNode;
    private final Node goalNode;

    public ChoiceApp() {
        ArrayList<ArrayList<Integer>> initState = new ArrayList<>() {{
            add(new ArrayList<>(){{add(6); add(0); add(8);}});
            add(new ArrayList<>(){{add(5); add(2); add(1);}});
            add(new ArrayList<>(){{add(4); add(3); add(7);}});
        }};
        ArrayList<ArrayList<Integer>> goalState = new ArrayList<>() {{
            add(new ArrayList<>(){{add(1); add(2); add(3);}});
            add(new ArrayList<>(){{add(8); add(0); add(4);}});
            add(new ArrayList<>(){{add(7); add(6); add(5);}});
        }};
        initNode = new Node(initState, new Pair<>(0,1));
        goalNode = new Node(goalState, new Pair<>(1,1));
    }

    public ChoiceApp(Node initNode, Node goalNode) {
        this.initNode = initNode;
        this.goalNode = goalNode;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChoiceApp.class.getResource("../panes/choice.fxml"));
        Parent root = fxmlLoader.load();
        fxmlLoader.<ChoiceController>getController().preset(initNode, goalNode);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}