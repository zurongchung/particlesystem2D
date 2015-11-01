package particlesystem;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;

import java.util.List;
import java.util.ArrayList;

import javafx.util.converter.IntegerStringConverter;

public class ControlPanel {

    private static int value;
    private static Double gap = 10.0;
    private static Double fontSize = 12.0;
    private static Pos align = Pos.CENTER_LEFT;

    static IntegerStringConverter typeConverter = new IntegerStringConverter();

    public ControlPanel() {}

    /**
     * | setting panel of velocity
     * |
     */
    public static TitledPane particleControl() {

        List<Node> panels = new ArrayList<>();

        ObservableList<Node> layoutPanels = FXCollections.observableList(panels);

        layoutPanels.addListener((ListChangeListener<Node>) c -> {

            System.out.println("panel amount: " + layoutPanels.size());
        });

        layoutPanels.addAll(amount(),
                            velocity(),
                            life());

        GridPane panelSet = new GridPane();
        panelSet.setVgap(gap);

        int row = 0;
        for (Node p : layoutPanels) {

            panelSet.add(p, 0, row);
            row++;
        }

        TitledPane panel = new TitledPane();
        panel.setText("Particles System");
        panel.setContent(panelSet);
        return panel;
    }

    /**
     * | setting panel of particles amount
     * |
     */
    public static VBox amount() {

        Double max = 100.0;
        Double min = 1.0;
        Double normal = 10.0;
        String title = "amount";

        Tooltip tip = new Tooltip();
        tip.setText("How much particles need to generate");

        Slider slide = new Slider();
        slide.setMax(max);
        slide.setMin(min);
        slide.setValue(normal);
        slide.setSnapToTicks(true);
        slide.setBlockIncrement(1.0);
        slide.setTooltip(tip);

        Label amount = new Label();
        amount.setText(title + ": ");

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
        input.setMaxWidth(48);
        input.setMaxHeight(16);
        input.setFont(Font.font(fontSize));
        input.setTooltip(tip);

        /**
         * increase or decrease amount by press a key
         *
         */
        EventHandler<KeyEvent> changeAmountByPressKey = e -> {
            switch (e.getCode()) {
                case UP:
                    value = typeConverter.fromString(input.getText());
                    value += 1;
                    input.setText(typeConverter.toString(value));
                    slide.setValue(value);
                    break;
                case DOWN:
                    value = typeConverter.fromString(input.getText());
                    if (value <= 1) {
                        break;
                    }else {
                        value -= 1;
                        input.setText(typeConverter.toString(value));
                        slide.setValue(value);
                    }
                    break;
            }
        };

        /**
         * increase or decrease amount by Enter numbers
         *
         */
        EventHandler<KeyEvent> changeAmountByEnterNumber = e -> {

            value = typeConverter.fromString(input.getText());
            slide.setValue(value);
        };

        input.addEventHandler(KeyEvent.KEY_PRESSED, changeAmountByPressKey);
        input.addEventHandler(KeyEvent.KEY_RELEASED, changeAmountByEnterNumber);

        /**
         * increase or decrease amount by drag slide
         *
         */
        EventHandler<MouseEvent> changeAmountByDragSlide = e -> input.setText(typeConverter.toString((int)slide.getValue()));
        slide.addEventHandler(MouseEvent.MOUSE_CLICKED, changeAmountByDragSlide);
        slide.addEventHandler(MouseEvent.MOUSE_DRAGGED, changeAmountByDragSlide);




        HBox inputBox = new HBox();
        inputBox.setSpacing(gap);
        inputBox.setAlignment(align);
        inputBox.getChildren().addAll(amount, input);

        VBox v = new VBox();
        v.setSpacing(gap);
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
        input.setMaxWidth(48);
        input.setMaxHeight(16);
        input.setFont(Font.font(fontSize));
        input.setTooltip(tip);

        /**
         * increase or decrease velocity by press a key
         *
         */

        EventHandler<KeyEvent> changeVelocityByPressKey = e -> {

            switch (e.getCode()) {
                case UP:
                    value = typeConverter.fromString(input.getText());
                    value += 1;
                    input.setText(typeConverter.toString(value));
                    // sync slide value with current input value
                    slide.setValue(value);
                    break;
                case DOWN:
                    value = typeConverter.fromString(input.getText());
                    value -= 1;
                    input.setText(typeConverter.toString(value));
                    // sync slide value with current input value
                    slide.setValue(value);
                    break;
            }
        };

        /**
         * increase or decrease amount by Enter numbers
         *
         */
        EventHandler<KeyEvent> changeVelocityByEnterNumber = e -> {

            value = typeConverter.fromString(input.getText());
            slide.setValue(value);
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
        input.setMaxWidth(48);
        input.setMaxHeight(16);
        input.setFont(Font.font(fontSize));
        input.setTooltip(tip);

        /**
         * increase or decrease velocity by press a key
         *
         */

        EventHandler<KeyEvent> changeVelocityByPressKey = e -> {

            switch (e.getCode()) {
                case UP:
                    value = typeConverter.fromString(input.getText());
                    value += 1;
                    input.setText(typeConverter.toString(value));
                    // sync slide value with current input value
                    slide.setValue(value);
                    break;
                case DOWN:
                    value = typeConverter.fromString(input.getText());
                    value -= 1;
                    input.setText(typeConverter.toString(value));
                    // sync slide value with current input value
                    slide.setValue(value);
                    break;
            }
        };

        /**
         * increase or decrease amount by Enter numbers
         *
         */
        EventHandler<KeyEvent> changeVelocityByEnterNumber = e -> {

            value = typeConverter.fromString(input.getText());
            slide.setValue(value);
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
        v.getChildren().addAll(inputBox, slide);
        return v;
    }


}
