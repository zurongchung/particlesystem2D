package particlesystem;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.paint.LinearGradient;
import javafx.animation.Animation;


public class ParticleSystem extends Application {
    static Group root = new Group();
    static Group particleSpace = new Group();
    @Override
    public void start(Stage stage) throws Exception{

        root.getChildren().addAll(panelContainer(), particleSpace);
        /**
         * | Draw particles to the screen
         */
        root.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            switch (e.getCode()) {
                case UP:
                    Particle.VMove -= Particle.getVelocity();
                    System.out.println("up");
                    break;
                case DOWN:
                    Particle.VMove += Particle.getVelocity();
                    System.out.println("down");
                    break;
                case LEFT:
                    Particle.HMove -= Particle.getVelocity();
                    System.out.println("left");
                    break;
                case RIGHT:
                    Particle.HMove += Particle.getVelocity();
                    System.out.println("right");
                    break;
                default:
                    System.out.println("Hit the Wrong key.");
            }

            Particle.animation(Particle.HMove, Particle.VMove);
        });

        Particle.animation(Particle.HMove, Particle.VMove);

        Stop[] stop = new Stop[]{
                new Stop(0, Color.web("#6A260D")),
                new Stop(1, Color.web("#B25928"))
        };
        LinearGradient bg = new LinearGradient(0, 0, 0, Viewport.HEIGHT.getValue(), false, CycleMethod.NO_CYCLE, stop);

        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        scene.setFill(Color.BLACK);
        stage.setWidth(Viewport.WIDTH.getValue());
        stage.setHeight(Viewport.HEIGHT.getValue());
        stage.setScene(scene);
        stage.setTitle("2D Particle System");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public BorderPane panelContainer() {

        BorderPane sideMenu = new BorderPane();
        sideMenu.setId("side-menu");
        sideMenu.setLeft(ControlPanel.particleControl());


        return sideMenu;
    }
}
