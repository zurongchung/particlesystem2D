package particlesystem;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;

import java.util.List;
import java.util.ArrayList;

import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.DoubleStringConverter;

public class ControlPanel {

    // sync Particle.amount with it
    final static int normal = 10;
    private static int amount_value = normal;      // must have a default value. else change size will cause particle amount set to none;
    public static Double velocity_value = 1.0;
    private static int life_time;
    // sync Particle.radius with it
    final static int defSize = 1;
    private static int size_value = defSize;

    private static int tBoxWidth = 48;
    private static int tBoxHeight = 16;
    private static Double gap = 10.0;
    private static Insets padding = new Insets(10, 0, 10, 0);
    private static Double fontSize = 12.0;
    private static Pos align = Pos.CENTER_LEFT;

    static IntegerStringConverter typeConverter = new IntegerStringConverter();
    static DoubleStringConverter dtsConverter = new DoubleStringConverter();

    public ControlPanel() {}


    public static TitledPane particleControl() {

        List<Node> panels = new ArrayList<>();

        ObservableList<Node> layoutPanels = FXCollections.observableList(panels);

        layoutPanels.addListener((ListChangeListener<Node>) c -> {

            System.out.println("panel amount: " + layoutPanels.size());
        });

        layoutPanels.addAll(type(),
                            amount(),
                            size(),
                            velocity(),
                            life());

        Double panelWidth = 140.0;
        GridPane panelSet = new GridPane();
        panelSet.setVgap(gap);
        panelSet.setMinWidth(panelWidth);

        TitledPane panel = new TitledPane();
        panel.setText("Particles System");
        panel.setContent(panelSet);

        int row = 0;
        for (Node p : layoutPanels) {
            Separator sp = new Separator();
            sp.setPrefWidth(panelWidth);

            VBox tmp = new VBox();
            tmp.getChildren().add(sp);
            tmp.getStyleClass().add("dividerContainer");

            panelSet.add(p, 0, row);
            panelSet.add(tmp, 0, row + 1);
            row++;
        }

        return panel;
    }

    /**
     * | setting panel of particles types
     * |
     */

    public static VBox type() {
        Tooltip tip = new Tooltip();
        tip.setText("Change the type of particles");
        String title = "Type";

        Label type = new Label();
        type.setText(title + ": ");

        ObservableList<String> typeName = FXCollections.observableArrayList();
        typeName.addAll("Line",
                        "Rectangle",
                        "Square",
                        "Circle");
        ComboBox t = new ComboBox(typeName);
        t.setValue(typeName.get(3));
        t.setTooltip(tip);

        HBox h = new HBox();
        h.setSpacing(gap);
        h.setAlignment(align);
        h.getChildren().addAll(type, t);

        VBox v = new VBox();
        v.setSpacing(gap);
        v.setPadding(padding);
        v.getStyleClass().add("panel");
        v.getChildren().addAll(h);
        return v;
    }
    /**
     * | setting panel of particles amount
     * |
     */
    public static VBox amount() {

        int max = 100;
        int min = 1;

        String title = "amount";

        Tooltip tip = new Tooltip();
        tip.setText("How much particles need to generate");

        Slider slide = new Slider();
        slide.setMax(max);
        slide.setMin(min);
        slide.setValue(normal);
        slide.setSnapToTicks(true);
        slide.setBlockIncrement(1);
        slide.setTooltip(tip);

        Label amount = new Label();
        amount.setText(title + ": ");

        /**
         * Only accept numerical value
         */

        TextField input = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }
            @Override
            public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }

        };
        input.setText(Integer.toString((int) slide.getValue()));
        input.setMaxWidth(tBoxWidth);
        input.setMaxHeight(tBoxHeight);
        input.setFont(Font.font(fontSize));
        input.setTooltip(tip);

        /**
         * increase or decrease amount by press a key
         *
         */
        EventHandler<KeyEvent> changeAmountByPressKey = e -> {
            switch (e.getCode()) {
                case UP:
                    amount_value = typeConverter.fromString(input.getText());
                    amount_value += 1;
                    input.setText(typeConverter.toString(amount_value));
                    slide.setValue(amount_value);

                    Particle.setAmount(amount_value);
                    /** sync Particle.amount
                     * | first clear scene
                     *  and then redraw particles
                     */
                    Particle.redrawParticles();

                    break;
                case DOWN:
                    amount_value = typeConverter.fromString(input.getText());
                    if (amount_value <= 1) {
                        break;
                    }else {
                        amount_value -= 1;
                        input.setText(typeConverter.toString(amount_value));
                        slide.setValue(amount_value);

                        Particle.setAmount(amount_value);

                        Particle.redrawParticles();

                    }
                    break;
                case ENTER:
                    amount_value = typeConverter.fromString(input.getText());
                    slide.setValue(amount_value);

                    Particle.setAmount(amount_value);

                    Particle.redrawParticles();

            }
        };

        input.addEventHandler(KeyEvent.KEY_PRESSED, changeAmountByPressKey);

        /**
         * increase or decrease amount by drag slide
         *
         */
        EventHandler<MouseEvent> changeAmountByDragSlide = e -> {
            amount_value = (int) slide.getValue();
            input.setText(typeConverter.toString(amount_value));

            Particle.setAmount(amount_value);
            /** sync Particle.amount
             * | first clear scene
             *  and then redraw particles
             */
            Particle.redrawParticles();
        };
        slide.addEventHandler(MouseEvent.MOUSE_CLICKED, changeAmountByDragSlide);

        /*||------ Even value reached maximum, Drag event still exist ------||*/
        slide.addEventHandler(MouseEvent.MOUSE_DRAGGED, changeAmountByDragSlide);

        HBox inputBox = new HBox();
        inputBox.setSpacing(gap);

        inputBox.setAlignment(align);
        inputBox.getChildren().addAll(amount, input);

        VBox v = new VBox();
        v.setSpacing(gap);
        v.setPadding(padding);
        v.getStyleClass().add("panel");
        v.getChildren().addAll(inputBox, slide);
        return v;
    }

    /**
     * | setting panel of particles size
     * |
     */
    public static VBox size() {

        int max = 100;
        int min = 1;

        String title = "size";

        Tooltip tip = new Tooltip();
        tip.setText("How big each particle is");

        Slider slide = new Slider();
        slide.setMax(max);
        slide.setMin(min);
        slide.setValue(defSize);
        slide.setSnapToTicks(true);
        slide.setBlockIncrement(1);
        slide.setTooltip(tip);

        Label size = new Label();
        size.setText(title + ": ");

        /**
         * Only accept numerical value
         */

        TextField input = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }
            @Override
            public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }

        };
        input.setText(Integer.toString((int) slide.getValue()));
        input.setMaxWidth(tBoxWidth);
        input.setMaxHeight(tBoxHeight);
        input.setFont(Font.font(fontSize));
        input.setTooltip(tip);

        /**
         * increase or decrease amount by press a key
         *
         */
        EventHandler<KeyEvent> changeSizeByPressKey = e -> {
            switch (e.getCode()) {
                case UP:
                    size_value = typeConverter.fromString(input.getText());
                    size_value += 1;
                    input.setText(typeConverter.toString(size_value));
                    slide.setValue(size_value);

                    Particle.setRadii(size_value);
                    /** sync Particle.amount
                     * | first clear scene
                     *  and then redraw particles
                     */
                    Particle.redrawParticles();

                    break;
                case DOWN:
                    size_value = typeConverter.fromString(input.getText());
                    if (size_value <= 1) {
                        break;
                    }else {
                        size_value -= 1;
                        input.setText(typeConverter.toString(size_value));
                        slide.setValue(size_value);

                        Particle.setRadii(size_value);

                        Particle.redrawParticles();

                    }
                    break;
                case ENTER:
                    size_value = typeConverter.fromString(input.getText());
                    slide.setValue(size_value);

                    Particle.setRadii(size_value);

                    Particle.redrawParticles();

            }
        };

        input.addEventHandler(KeyEvent.KEY_PRESSED, changeSizeByPressKey);

        /**
         * increase or decrease amount by drag slide
         *
         */
        EventHandler<MouseEvent> changeSizeByDragSlide = e -> {
            size_value = (int) slide.getValue();

            input.setText(typeConverter.toString(size_value));

            Particle.setRadii(size_value);
            /** sync Particle.amount
             * | first clear scene
             *  and then redraw particles
             */
            Particle.redrawParticles();
        };
        slide.addEventHandler(MouseEvent.MOUSE_CLICKED, changeSizeByDragSlide);

        /*||------ Even value reached maximum, Drag event still exist ------||*/
        slide.addEventHandler(MouseEvent.MOUSE_DRAGGED, changeSizeByDragSlide);

        HBox inputBox = new HBox();
        inputBox.setSpacing(gap);
        inputBox.setAlignment(align);
        inputBox.getChildren().addAll(size, input);

        VBox v = new VBox();
        v.setSpacing(gap);
        v.setPadding(padding);
        v.getChildren().addAll(inputBox, slide);
        return v;
    }

    /**
     * | setting panel of particles velocity
     * |
     */

    public static VBox velocity() {
        Double max = 120.0;
        Double min = 0.0;
        String title = "velocity";

        Tooltip tip = new Tooltip();
        tip.setText("How fast the particle moves");

        Slider slide = new Slider();
        slide.setMin(min);
        slide.setMax(max);
        slide.setValue(min);
        slide.setBlockIncrement(1.0);
        slide.setSnapToTicks(true);
        slide.setTooltip(tip);

        Label label = new Label();
        label.setText(title + ": ");

        /**
         * Only accept numerical input
         *
         */

        TextField input = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }
            @Override
            public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }

        };
        input.setText(Integer.toString((int) slide.getValue()));
        input.setMaxWidth(tBoxWidth);
        input.setMaxHeight(tBoxHeight);
        input.setFont(Font.font(fontSize));
        input.setTooltip(tip);

        /**
         * increase or decrease velocity by press a key
         *
         */

        EventHandler<KeyEvent> changeVelocityByPressKey = e -> {

            switch (e.getCode()) {
                case UP:
                    velocity_value = dtsConverter.fromString(input.getText());
                    velocity_value += 1;
                    input.setText(dtsConverter.toString(velocity_value));
                    // sync slide value with current input value
                    slide.setValue(velocity_value);
                    Particle.setVelocity(velocity_value);
                    break;
                case DOWN:
                    velocity_value = dtsConverter.fromString(input.getText());
                    velocity_value -= 1;
                    input.setText(dtsConverter.toString(velocity_value));
                    // sync slide value with current input value
                    slide.setValue(velocity_value);
                    Particle.setVelocity(velocity_value);
                    break;
            }
        };

        /**
         * increase or decrease amount by Enter numbers
         *
         */
        EventHandler<KeyEvent> changeVelocityByEnterNumber = e -> {

            velocity_value = dtsConverter.fromString(input.getText());
            slide.setValue(velocity_value);
            Particle.setVelocity(velocity_value);
        };

        input.addEventHandler(KeyEvent.KEY_RELEASED, changeVelocityByEnterNumber);
        input.addEventHandler(KeyEvent.KEY_PRESSED, changeVelocityByPressKey);

        /**
         * increase or decrease velocity by drag slide
         *
         */
        EventHandler<MouseEvent> changeVelocityByDragSlider = e -> {
            velocity_value = slide.getValue();
            input.setText(dtsConverter.toString(velocity_value));
            Particle.setVelocity(velocity_value);
        };
        slide.addEventHandler(MouseEvent.MOUSE_DRAGGED, changeVelocityByDragSlider);
        slide.addEventHandler(MouseEvent.MOUSE_CLICKED, changeVelocityByDragSlider);

        HBox inputBox = new HBox();
        inputBox.setSpacing(gap);
        inputBox.setAlignment(align);
        inputBox.getChildren().addAll(label, input);

        VBox v = new VBox();
        v.setSpacing(gap);
        v.setPadding(padding);
        v.getChildren().addAll(inputBox, slide);
        return v;
    }

    /**
     * | setting panel of particles life
     * |
     */
    public static VBox life() {
        int max = Viewport.WIDTH.getValue();
        int min = 1;
        String title = "life";

        Tooltip tip = new Tooltip();
        tip.setText("How far they can goes before they die");

        Slider slide = new Slider();
        slide.setMin(min);
        slide.setMax(max);
        slide.setValue(min);
        slide.setBlockIncrement(1);
        slide.setSnapToTicks(true);
        slide.setTooltip(tip);

        Label label = new Label();
        label.setText(title + ": ");

        /**
         * Only accept numerical input
         *
         */
        TextField input = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }
            @Override
            public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }

        };
        input.setText(Integer.toString((int) slide.getValue()));
        input.setMaxWidth(tBoxWidth);
        input.setMaxHeight(tBoxHeight);
        input.setFont(Font.font(fontSize));
        input.setTooltip(tip);

        /**
         * increase or decrease velocity by press a key
         *
         */

        EventHandler<KeyEvent> changeVelocityByPressKey = e -> {

            switch (e.getCode()) {
                case UP:
                    life_time = typeConverter.fromString(input.getText());
                    life_time += 1;
                    input.setText(typeConverter.toString(life_time));
                    // sync slide value with current input value
                    slide.setValue(life_time);
                    break;
                case DOWN:
                    life_time = typeConverter.fromString(input.getText());
                    life_time -= 1;
                    input.setText(typeConverter.toString(life_time));
                    // sync slide value with current input value
                    slide.setValue(life_time);
                    break;
            }
        };

        /**
         * increase or decrease amount by Enter numbers
         *
         */
        EventHandler<KeyEvent> changeVelocityByEnterNumber = e -> {

            life_time = typeConverter.fromString(input.getText());
            slide.setValue(life_time);
        };

        input.addEventHandler(KeyEvent.KEY_RELEASED, changeVelocityByEnterNumber);
        input.addEventHandler(KeyEvent.KEY_PRESSED, changeVelocityByPressKey);

        /**
         * increase or decrease velocity by drag slide
         *
         */
        EventHandler<MouseEvent> changeVelocityByDragSlider = e -> input.setText(typeConverter.toString((int)slide.getValue()));
        slide.addEventHandler(MouseEvent.MOUSE_DRAGGED, changeVelocityByDragSlider);
        slide.addEventHandler(MouseEvent.MOUSE_CLICKED, changeVelocityByDragSlider);

        HBox inputBox = new HBox();
        inputBox.setSpacing(gap);
        inputBox.setAlignment(align);
        inputBox.getChildren().addAll(label, input);

        VBox v = new VBox();
        v.setSpacing(gap);
        v.setPadding(padding);
        v.getChildren().addAll(inputBox, slide);
        return v;
    }



}
