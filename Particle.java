package particlesystem;

import javafx.animation.AnimationTimer;
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
    private static Color strokeColor = Color.rgb(255, 255, 255, 1);
    private static Color fill = Color.rgb(255, 255, 255, 1);
    private static int strokeWidth;
    private static int amount = ControlPanel.normal;
    private static Direction dir;
    private static int life = 0;
    private static int maxLife = ControlPanel.defMaxLife;
    private double x = Viewport.WIDTH.getValue() / 2.0;
    private double y = Viewport.HEIGHT.getValue() / 2.0;
    private double vx = Math.random() * 10 - 5;
    private double vy = Math.random() * 10 - 5;


    /**
     * | @Property for circle
     * |
     */
    private static int radius = ControlPanel.defSize;

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

    public Node drawCircle() {

        x += this.vx;
        y += this.vy;
        vy *= 1.9;
        vx *= 1.6;

        setFill(Color.rgb(0, 0, 0, 0));
        setStrokeColor(Color.rgb(252, 112, 48, 1));
        setStrokeWidth(2);

        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);
        circle.setFill(fill);

        circle.setStrokeWidth(strokeWidth);
        circle.setStroke(strokeColor);

        circle.setEffect(glowing());

        return circle;
    }

    public ObservableList<Node> particle() {


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
            particles.add(this.drawCircle());
        }


        return particles;
    }

    public void drawParticles() {
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
     * | there have another way to set value like this
     * | see velocity() in ControlPanel ==>   ( that way Maybe better )     Particle.velocity = velocity_value;!!!!!!!!!!!!!!!!!!!!!!!
     */
    public static void redrawParticles() {
        /**|| clear before drawing
         * || This is very important
         */
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                ParticleSystem.particleSpace.getChildren().clear();

                int i = 0;
                for (; i < Particle.getAmount(); ++i) {
                    new Particle().drawParticles();
                }
            }
        }.start();


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
    public void setX(double _x) {
        this.x = _x;
    }

    public double getX() {
        return this.x;
    }

    public void setY(double _y) {
        this.y = _y;
    }

    public double getY() {
        return this.y;
    }

    /**
     * Velocity of circles @velocity
     * | @param Custom velocity
     */
    public void setVx(double _vx) {
        this.vx = _vx;
    }
    public double getVx() {
        return this.vx;
    }
    public void setVy(double _vy) {
        this.vy = _vy;
    }
    public double getVy() {
        return this.vy;
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
     * |
     * | @param Custom fill
     */
    public static void setFill(Color color) {
        fill = color;
    }

    public static Color getFill() {
        return fill;
    }

    /**
     * Color of particles @Stroke color
     * |
     * | @param Custom stroke color
     */
    public static void setStrokeColor(Color color) {
        strokeColor = color;
    }

    public static Color getStrokeColor() {
        return strokeColor;
    }

    /**
     * | Width of particle @Stroke width
     * |
     */
    public static void setStrokeWidth(int s_width) {
        strokeWidth = s_width;
    }

    public static int getStrokeWidth() {
        return strokeWidth;
    }

    /**
     * | Life of particle @life
     * |
     */
    public static void setMaxLifeLife(int _maxLife) {
        maxLife = _maxLife;
    }
    public static int getMaxLifeLife() {
        return maxLife;
    }
}
