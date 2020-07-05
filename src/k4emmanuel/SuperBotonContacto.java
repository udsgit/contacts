/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k4emmanuel;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author GAMING PC
 */
public class SuperBotonContacto extends Button {

    private ImageView portaImgContacto;
    private Label labContacto;
    private HBox fondoBoton;
    private int index;

    SuperBotonContacto(String nombre, String apellido, String imgUrl, int index) {
        this.index = index;
        portaImgContacto = new ImageView(new Image(imgUrl));
        portaImgContacto.setFitHeight(50);
        portaImgContacto.setFitWidth(50);
        portaImgContacto.setSmooth(true);
        portaImgContacto.setClip(new Circle(25, 25, 20));
        labContacto = new Label(nombre + " " + apellido);
        labContacto.setTextAlignment(TextAlignment.LEFT);
        labContacto.setFont(new Font("Arial", 12));
        fondoBoton = new HBox();
        fondoBoton.setAlignment(Pos.CENTER_LEFT);
        fondoBoton.setSpacing(20);
        setGraphic(fondoBoton);
        fondoBoton.getChildren().addAll(portaImgContacto, labContacto);
        setMinSize(300, 50);
        setMaxSize(300, 50);
        
        setOnAction((event) -> {
            AddContactos.editarContacto(this.index);
        });
    }

    public ImageView getPortaImgContacto() {
        return portaImgContacto;
    }

    public void setPortaImgContacto(ImageView portaImgContacto) {
        this.portaImgContacto = portaImgContacto;
    }

    public Label getLabContacto() {
        return labContacto;
    }

    public void setLabContacto(Label labContacto) {
        this.labContacto = labContacto;
    }

    public HBox getFondoBoton() {
        return fondoBoton;
    }

    public void setFondoBoton(HBox fondoBoton) {
        this.fondoBoton = fondoBoton;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    

}
