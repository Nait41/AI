package apps;

import controllers.AStarController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tree.Node;
import tree.search.heuristics.HeuristicI;

import java.io.IOException;

public class AStarApp extends javafx.application.Application {
    private double xOffset;
    private double yOffset;
    public static Node initNode;
    public static Node goalNode;
    public static HeuristicI Heuristic;

    public AStarApp(Node initNode, Node goalNode, HeuristicI Heuristic) {
        this.initNode = initNode;
        this.goalNode = goalNode;
        this.Heuristic = Heuristic;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AStarApp.class.getResource("../panes/aStarSearch.fxml"));
        Parent root = fxmlLoader.load();
        //fxmlLoader.<AStarController>getController().preset(initNode, goalNode, Heuristic);
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