package particlesystem;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;

import java.util.List;
import java.util.ArrayList;

import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.DoubleStringConverter;

public class ControlPanel {

    // sync Particle.amount with it
    final static int defParticleNum = 2;
    final static int defMaxLife = 300;
    public static double defVelocity = 0.1;
    // sync Particle.radius with it
    final static int defSize = 1;

    private static int tBoxWidth = 48;
    private static int tBoxHeight = 16;
    private static double gap = 10.0;
    private static Insets padding = new Insets(10, 0, 10, 0);
    private static double fontSize = 12.0;
    private static Pos align = Pos.CENTER_LEFT;

    static IntegerStringConverter typeConverter = new IntegerStringConverter();
    static DoubleStringConverter dtsConverter = new DoubleStringConverter();

    public ControlPanel() {}


    public static TitledPane particleControl() {

        List<Node> panels = new ArrayList<>();

        ObservableList<Node> layoutPanels = FXCollections.observableList(panels);

        layoutPanels.addListener((ListChangeListener<Node>) c -> System.out.println("panel amount: " + layoutPanels.size()));

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

        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Line",
                        "Rectangle",
                        "Square",
                        "Circle");
        ComboBox typeList = new ComboBox(items);
        typeList.setValue(items.get(3));
        typeList.setTooltip(tip);

        HBox h = new HBox();
        h.setSpacing(gap);
        h.setAlignment(align);
        h.getChildren().addAll(type, typeList);

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

        int max = 30;
        int min = 1;

        String identity = "amount";

        Tooltip tip = new Tooltip();
        tip.setText("How much particles need to generate");

        Slider slide = new Slider();
        slide.setMax(max);
        slide.setMin(min);
        slide.setValue(defParticleNum);
        slide.setSnapToTicks(true);
        slide.setBlockIncrement(1);
        slide.setTooltip(tip);

        Label amount = new Label();
        amount.setText(identity + ": ");

        /**
         * Only accept numerical value
         */

        TextField amount_field = new TextField() {
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
        amount_field.setText(typeConverter.toString((int) slide.getValue()));
        amount_field.setMaxWidth(tBoxWidth);
        amount_field.setMaxHeight(tBoxHeight);
        amount_field.setFont(Font.font(fontSize));
        amount_field.setTooltip(tip);

        /**
         * increase or decrease amount by press a key
         *
         */
        EventHandler<KeyEvent> changeAmountByPressKey = e -> {
            switch (e.getCode()) {
                case UP:
                    actionCtrl(e, amount_field, slide, identity);
                    break;
                case DOWN:
                    actionCtrl(e, amount_field, slide, identity);
                    break;
                case ENTER:
                    actionCtrl(e, amount_field, slide, identity);
            }
        };

        amount_field.addEventHandler(KeyEvent.KEY_PRESSED, changeAmountByPressKey);

        /**
         * increase or decrease amount by drag slide
         *
         */
        EventHandler<MouseEvent> changeAmountByDragSlide = e -> slideCtrl(amount_field, slide, identity);

        slide.addEventHandler(MouseEvent.MOUSE_CLICKED, changeAmountByDragSlide);

        /*||------ Even value reached maximum, Drag event still exist ------||*/
        slide.addEventHandler(MouseEvent.MOUSE_DRAGGED, changeAmountByDragSlide);

        HBox inputBox = new HBox();
        inputBox.setSpacing(gap);

        inputBox.setAlignment(align);
        inputBox.getChildren().addAll(amount, amount_field);

        VBox v = new VBox();
        v.setSpacing(gap);
        v.setPadding(padding);
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

        String identity = "size";

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
        size.setText(identity + ": ");

        /**
         * Only accept numerical value
         */

        TextField size_field = new TextField() {
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
        size_field.setText(typeConverter.toString((int) slide.getValue()));
        size_field.setMaxWidth(tBoxWidth);
        size_field.setMaxHeight(tBoxHeight);
        size_field.setFont(Font.font(fontSize));
        size_field.setTooltip(tip);

        /**
         * increase or decrease amount by press a key
         *
         */
        EventHandler<KeyEvent> changeSizeByPressKey = e -> {
            switch (e.getCode()) {
                case UP:
                    actionCtrl(e, size_field, slide, identity);
                    break;
                case DOWN:
                    actionCtrl(e, size_field, slide, identity);
                    break;
                case ENTER:
                    actionCtrl(e, size_field, slide, identity);
                    break;

            }
        };

        size_field.addEventHandler(KeyEvent.KEY_PRESSED, changeSizeByPressKey);

        /**
         * increase or decrease amount by drag slide
         *
         */
        EventHandler<MouseEvent> changeSizeByDragSlide = e -> slideCtrl(size_field, slide, identity);

        slide.addEventHandler(MouseEvent.MOUSE_CLICKED, changeSizeByDragSlide);

        /*||------ Even value reached maximum, Drag event still exist ------||*/
        slide.addEventHandler(MouseEvent.MOUSE_DRAGGED, changeSizeByDragSlide);

        HBox inputBox = new HBox();
        inputBox.setSpacing(gap);
        inputBox.setAlignment(align);
        inputBox.getChildren().addAll(size, size_field);

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
        double max = 40.0;
        double min = -40.0;
        String title = "velocity";
        String vxId = "vx";
        String vyId = "vy";

        Tooltip vxTip = new Tooltip();
        vxTip.setText("How fast the particle moves along x axis");
        Tooltip vyTip = new Tooltip();
        vyTip.setText("How fast the particle moves along y axis");

        Slider slide = new Slider();
        slide.setMin(min);
        slide.setMax(max);
        slide.setValue(defVelocity);
        slide.setBlockIncrement(1.0);
        slide.setSnapToTicks(true);
        //slide.setTooltip(tip);

        Label label = new Label();
        label.setText(title);
        Label vxLabel = new Label();
        vxLabel.setText(vxId + ": ");
        Label vyLabel = new Label();
        vyLabel.setText(vyId + ": ");
        Label slideBelong = new Label("to " + vxId);
        slideBelong.setLabelFor(slide);

        /**
         * Only accept numerical input
         *
         */

        TextField vxField = new TextField() {
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
        vxField.setText(dtsConverter.toString(slide.getValue()));
        vxField.setMaxWidth(tBoxWidth);
        vxField.setMaxHeight(tBoxHeight);
        vxField.setFont(Font.font(fontSize));
        vxField.setTooltip(vxTip);

        TextField vyField = new TextField() {
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
        vyField.setText(dtsConverter.toString(slide.getValue()));
        vyField.setMaxWidth(tBoxWidth);
        vyField.setMaxHeight(tBoxHeight);
        vyField.setFont(Font.font(fontSize));
        vyField.setTooltip(vyTip);


        /**
         * increase or decrease velocity by press a key
         *
         */
        EventHandler<KeyEvent> changeVelocityByPressKey = e -> {

            switch (e.getCode()) {
                case UP:
                    v_ActCtrl(e, vxField, vyField, slide);
                    break;
                case DOWN:
                    v_ActCtrl(e, vxField, vyField, slide);
                    break;
                case ENTER:
                    v_ActCtrl(e, vxField, vyField, slide);
                    break;
            }

        };

        /**
         * | auto detect which one ( vx or vy) to change
         */
        EventHandler<MouseEvent> changeSlideBelong = e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (e.getTarget().equals(vxField)) {
                    System.out.println("vx " + e.getTarget().equals(vxField));
                    slideBelong.setText("to " + vxId);
                }else if (e.getTarget().equals(vyField)) {
                    System.out.println("vy " + e.getTarget().equals(vyField));
                    slideBelong.setText("to " + vyId);
                }
            }
        };

        /**
         * increase or decrease velocity by drag slide
         *
         */
        EventHandler<MouseEvent> changeVelocityByDragSlider = e -> v_slideCtrl(slideBelong, vxField, vyField, slide);

        // vx
        vxField.addEventHandler(KeyEvent.KEY_PRESSED, changeVelocityByPressKey);
        vxField.addEventHandler(MouseEvent.MOUSE_CLICKED, changeSlideBelong);
        // vy
        vyField.addEventHandler(KeyEvent.KEY_PRESSED, changeVelocityByPressKey);
        vyField.addEventHandler(MouseEvent.MOUSE_CLICKED, changeSlideBelong);

        slide.addEventHandler(MouseEvent.MOUSE_DRAGGED, changeVelocityByDragSlider);
        slide.addEventHandler(MouseEvent.MOUSE_CLICKED, changeVelocityByDragSlider);

        HBox vxWrap = new HBox();
        vxWrap.setSpacing(gap);
        vxWrap.setAlignment(align);
        vxWrap.getChildren().addAll(vxLabel, vxField);
        HBox vyWrap = new HBox();
        vyWrap.setSpacing(gap);
        vyWrap.setAlignment(align);
        vyWrap.getChildren().addAll(vyLabel, vyField);

        VBox v = new VBox();
        v.setSpacing(gap);
        v.setPadding(padding);
        v.getChildren().addAll(vxWrap, vyWrap, slideBelong,slide);
        return v;
    }

    /**
     * | setting panel of particles life
     * |
     */
    public static VBox life() {
        int max = Viewport.WIDTH.getValue();
        int min = 1;
        String identity = "life";

        Tooltip tip = new Tooltip();
        tip.setText("How far they can goes before they die");

        Slider slide = new Slider();
        slide.setMin(min);
        slide.setMax(max);
        slide.setValue(defMaxLife);
        slide.setBlockIncrement(1);
        slide.setSnapToTicks(true);
        slide.setTooltip(tip);

        Label label = new Label();
        label.setText(identity + ": ");

        /**
         * Only accept numerical input
         *
         */
        TextField life_field = new TextField() {
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
        life_field.setText(Integer.toString((int) slide.getValue()));
        life_field.setMaxWidth(tBoxWidth);
        life_field.setMaxHeight(tBoxHeight);
        life_field.setFont(Font.font(fontSize));
        life_field.setTooltip(tip);

        /**
         * increase or decrease velocity by press a key
         *
         */

        EventHandler<KeyEvent> changeVelocityByPressKey = e -> {

            switch (e.getCode()) {
                case UP:
                    actionCtrl(e, life_field, slide, identity);
                    break;
                case DOWN:
                    actionCtrl(e, life_field, slide, identity);
                    break;
                case ENTER:
                    actionCtrl(e, life_field, slide, identity);
                    break;
            }
        };

        life_field.addEventHandler(KeyEvent.KEY_PRESSED, changeVelocityByPressKey);

        /**
         * increase or decrease velocity by drag slide
         *
         */
        EventHandler<MouseEvent> changeVelocityByDragSlider = e -> slideCtrl(life_field, slide, identity);
        slide.addEventHandler(MouseEvent.MOUSE_DRAGGED, changeVelocityByDragSlider);
        slide.addEventHandler(MouseEvent.MOUSE_CLICKED, changeVelocityByDragSlider);

        HBox inputBox = new HBox();
        inputBox.setSpacing(gap);
        inputBox.setAlignment(align);
        inputBox.getChildren().addAll(label, life_field);

        VBox v = new VBox();
        v.setSpacing(gap);
        v.setPadding(padding);
        v.getChildren().addAll(inputBox, slide);
        return v;
    }

    public static void actionCtrl(KeyEvent e, TextField field, Slider slide, String identity) {
        int value;
        value = typeConverter.fromString(field.getText());

        if (e.getCode() == KeyCode.UP) {
            value += 1;
            field.setText(typeConverter.toString(value));
        } else if (e.getCode() == KeyCode.DOWN) {
            if (value > 1) value -= 1;
            field.setText(typeConverter.toString(value));
        }
        slide.setValue(value);
        identify(identity, value);

        new Particle().drawParticles();
    }
    public static void v_ActCtrl(KeyEvent e, TextField vx, TextField vy, Slider slide) {
        double value;
        TextField field = vx;

        if (!e.getTarget().equals(vx)) field = vy;

        value = dtsConverter.fromString(field.getText());
        if (e.getCode() == KeyCode.UP) {
            value += 1;
            field.setText(dtsConverter.toString(value));
        }else if (e.getCode() == KeyCode.DOWN) {
            value -= 1;
            field.setText(dtsConverter.toString(value));
        }
        // sync slide value with current input value
        slide.setValue(value);

        if (e.getTarget().equals(vx)) {
            new Particle().setVx(value);
        }else {
            new Particle().setVy(value);
        }

        new Particle().drawParticles();
    }
    public static void v_slideCtrl(Label belongTo, TextField vx, TextField vy, Slider slide){
        double value;
        TextField field = vx;
        if (!belongTo.getText().equals("to vx")) field = vy;

        value = slide.getValue();
        field.setText(dtsConverter.toString(value));

       if (belongTo.getText().equals("to vx")) {
           new Particle().setVx(value);
        }else {
            new Particle().setVy(value);
        }
        new Particle().drawParticles();
    }

    // common uses
    public static void slideCtrl(TextField field, Slider slide, String identity){
        int value;
        value = (int)slide.getValue();
        field.setText(typeConverter.toString(value));
        identify(identity, value);

        new Particle().drawParticles();
    }
    public static void identify(String id, int _value) {
        switch (id) {
            case "amount":
                Particle.setAmount(_value);
                break;
            case "size":
                Particle.setRadii(_value);
                break;
            case "life":
                Particle.setMaxLifeLife(_value);
                break;
        }
    }
}
