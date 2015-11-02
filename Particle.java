package particlesystem;

import javafx.scene.effect.BlendMode;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Blend;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;

import java.util.List;
import java.util.ArrayList;

public class Particle {

    /**
     * | Common @Property
     * |
     */
    private static final Double G = 9.81; // 32.2ft
    private static Color strokeColor;
    private static Color fill;
    private static int strokeWidth;
    private static int amount = ControlPanel.normal;
    private static Direction dir;
    private static int life;
    private static int x;
    private static int y;


    /**
     * | @Property for circle
     * |
     */
    private static int radius =ControlPanel.defSize;

    public Particle() {}

    // Effects
    public static Bloom glowing() {
        Bloom glowing = new Bloom();
        glowing.setThreshold(0.1);
        glowing.setInput(lightenIntersection());
        return glowing;
    }

    public static Blend lightenIntersection() {
        Blend lighten = new Blend();
        lighten.setMode(BlendMode.SRC_OVER);
        return lighten;
    }

    public static  Node drawCircle() {
        setX();
        setY();
        setFill(Color.rgb(0, 0, 0, 0));
        setStrokeColor(Color.rgb(252, 112, 48, 1));
        setStrokeWidth(2);

        Circle circle = new Circle();
        circle.setCenterX(getX());
        circle.setCenterY(getY());
        circle.setRadius(getRadii());
        circle.setFill(getFill());

        circle.setStrokeWidth(getStrokeWidth());
        circle.setStroke(getStrokeColor());

        circle.setEffect(glowing());

        return circle;
    }

    public static ObservableList<Node> particle() {


        /**
         * | !important @Needed List. because we need delete particle
         * | when times right || ex: life time reaches maximum ||
         */

        List<Node> list = new ArrayList<>();
        ObservableList<Node> particles = FXCollections.observableList(list);
        particles.addListener((ListChangeListener<Node>) c -> {
            System.out.println("particles: " + particles.size());
        });

        int i = 0;
        for (; i < getAmount(); ++i) {
            particles.add(drawCircle());
        }


        return particles;
    }

    public static void drawParticles() {
        /**
         * | Draw particles to the screen
         */

        for (Node c : particle()) {
            ParticleSystem.particleSpace.getChildren().add(c);
        }

    }

    /**
     * | redraw particles
     * | after parameter changed
     * |
     */
    public static void redrawParticles(int amount, int size) {
        /**|| clear before setting any value.
         * || This is very important
         */
        ParticleSystem.particleSpace.getChildren().clear();
        setAmount(amount);
        setRadii(size);
        drawParticles();
    }

    /**
     * | set & retrieve amount of particles
     * |
     */
    public static void setAmount(int _amount) {
        amount = _amount;
    }

    public static int getAmount() {
        return amount;
    }

    /**
     * | coordinates
     */
    public static void setX() {

        x = (int) Math.round(Math.random() * Viewport.WIDTH.getValue() - 50);
    }

    public static int getX() {
        return x;
    }

    public static void setY() {
        y = (int) Math.round(Math.random() * Viewport.HEIGHT.getValue() - 50);
    }

    public static int getY() {
        return y;
    }

    /**
     * Size of circles @Radius
     * | @param Custom radius
     */

    public static void setRadii(int r) {
        radius = r;
    }

    public static int getRadii() {
        return radius;
    }

    /**
     * Color of particles @Fill
     * | @param Default fill
     * | @param Custom fill
     */
    public static void setFill() {
        fill = Color.rgb(255, 255, 255, 1);
    }

    public static void setFill(Color color) {
        fill = color;
    }

    public static Color getFill() {
        return fill;
    }

    /**
     * Color of particles @Stroke color
     * | @param Default stroke color
     * | @param Custom stroke color
     */
    public static void setStrokeColor() {
        strokeColor = Color.rgb(255, 255, 255, 1);
    }

    public static void setStrokeColor(Color color) {
        strokeColor = color;
    }

    public static Color getStrokeColor() {
        return strokeColor;
    }

    /**
     * | stroke width
     */
    public static void setStrokeWidth(int s_width) {
        strokeWidth = s_width;
    }

    public static int getStrokeWidth() {
        return strokeWidth;
    }

}
