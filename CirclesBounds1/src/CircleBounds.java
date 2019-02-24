import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Created by Pounders82 on 11/21/15.
 */
public class CircleBounds
        extends Application {
    private static Color circleFill = Color.SKYBLUE;
    private static Color circleBorder = Color.NAVY;
    private static double circleRadius = 10.0;
    private Pane pane;
    private Rectangle boundingBox;
    private static Color boundingBoxBorder = Color.GREEN;
    private static Color boundingBoxFill = Color.TRANSPARENT;
    private static double boundingBoxStrokeWidth = 5.0;
    private EventHandler<MouseEvent> createCircle;
    private EventHandler<MouseEvent> destroyCircle;

    public CircleBounds() {
        this.createCircle = new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    Circle c = new Circle(event.getX(), event.getY(), circleRadius, (Paint)circleFill);
                    c.setStroke((Paint)circleBorder);
                    CircleBounds.this.pane.getChildren().add(c);
                    c.setOnMouseClicked(CircleBounds.this.destroyCircle);
                    CircleBounds.this.resetBoundBox();
                }
            }
        };
        this.destroyCircle = new EventHandler<MouseEvent>(){

            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    Circle c = (Circle)event.getSource();
                    CircleBounds.this.pane.getChildren().remove(event.getSource());
                    CircleBounds.this.resetBoundBox();
                }
            }
        };
    }

    public void start(Stage primaryStage) throws Exception {
        this.pane = new Pane();
        this.pane.setOnMouseClicked(this.createCircle);
        this.boundingBox = new Rectangle();
        this.boundingBox.setStroke((Paint)boundingBoxBorder);
        this.boundingBox.setFill((Paint)boundingBoxFill);
        this.boundingBox.setStrokeWidth(boundingBoxStrokeWidth);
        this.pane.getChildren().add(this.boundingBox);
        Scene sc = new Scene((Parent)this.pane, 700.0, 700.0);
        primaryStage.setScene(sc);
        primaryStage.setTitle("Circle Clicker With Bounding Box");
        primaryStage.show();
    }

    private void resetBoundBox() {
        if (this.pane.getChildren().isEmpty()) {
            this.boundingBox.setWidth(0.0);
            this.boundingBox.setHeight(0.0);
        } else {
            double minX = Double.POSITIVE_INFINITY;
            double minY = Double.POSITIVE_INFINITY;
            double maxX = Double.NEGATIVE_INFINITY;
            double maxY = Double.NEGATIVE_INFINITY;
            for (Node n : this.pane.getChildren()) {
                if (n == this.boundingBox) continue;
                Circle c = (Circle)n;
                minX = Math.min(minX, c.getCenterX());
                minY = Math.min(minY, c.getCenterY());
                maxX = Math.max(maxX, c.getCenterX());
                maxY = Math.max(maxY, c.getCenterY());
            }
            this.boundingBox.setX(minX - circleRadius - boundingBoxStrokeWidth / 2.0);
            this.boundingBox.setY(minY - circleRadius - boundingBoxStrokeWidth / 2.0);
            this.boundingBox.setWidth(maxX - minX + 2.0 * circleRadius + boundingBoxStrokeWidth);
            this.boundingBox.setHeight(maxY - minY + 2.0 * circleRadius + boundingBoxStrokeWidth);
        }
    }

    public static void main(String[] args) {
        CircleBounds.launch((String[]) args);
    }

}
