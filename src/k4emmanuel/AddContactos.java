package k4emmanuel;

import java.io.File;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class AddContactos {

    static String fcImgUrl = "resources/people/vacio.png";
    static final String imgUrlPorDefecto = "resources/people/vacio.png";
    static Image imgContacto = new Image(imgUrlPorDefecto);
    static Image imgAtras = new Image("resources/icons/atras.png");
    static Image imgGuardar = new Image("resources/icons/guardar.png");
    static Image imgBorrar = new Image("resources/icons/borrar.png");
    static final ImageView portaImgContacto = new ImageView(imgContacto);
    static ImageView portaImgAtras = new ImageView(imgAtras);
    static ImageView portaImgGuardar = new ImageView(imgGuardar);
    static ImageView portaImgBorrar = new ImageView(imgBorrar);
    static TextField nombre = new TextField(), apellido = new TextField(), telefono = new TextField();
    static Label alerta = new Label();
    static Pane contactosPane = new Pane();
    static Scene addContactosScene = new Scene(contactosPane, 200, 400);

    static void crearSceneAddContactosAndEditContactos() {
        portaImgContacto.relocate(50, 28);
        portaImgContacto.setFitHeight(100);
        portaImgContacto.setFitWidth(100);
        portaImgContacto.setClip(new Circle(50, 50, 45));
        nombre.relocate(26, 144);
        nombre.setPromptText("Nombre");
        apellido.relocate(26, 175);
        apellido.setPromptText("Apellido");
        telefono.relocate(26, 207);
        telefono.setPromptText("Telefono");
        alerta.relocate(50, 239);
        portaImgAtras.setFitHeight(50);
        portaImgAtras.setFitWidth(50);
        portaImgAtras.relocate(15, 335);
        portaImgBorrar.setFitHeight(50);
        portaImgBorrar.setFitWidth(50);
        portaImgBorrar.relocate(75, 335);
        portaImgGuardar.setFitHeight(50);
        portaImgGuardar.setFitWidth(50);
        portaImgGuardar.relocate(135, 335);

        portaImgAtras.setOnMouseClicked((event) -> {
            K4Emmanuel.priStage.setScene(MenuPrincipal.priScene);
            limpiar();
        });
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar foto del contacto");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        portaImgContacto.setOnMouseClicked((event) -> {

            File archivoSeleccionado = fc.showOpenDialog(K4Emmanuel.priStage);
            if (archivoSeleccionado != null) {
                fcImgUrl = "file:" + archivoSeleccionado.getPath();
                portaImgContacto.setImage(new Image(fcImgUrl));
            }
        }
        );
    }

    static void editarContacto(int index) {
        contactosPane.getChildren().clear();
        contactosPane.getChildren().addAll(portaImgContacto, nombre, apellido, telefono, alerta, portaImgAtras, portaImgBorrar, portaImgGuardar);
        nombre.setText(MenuPrincipal.agenda.getContactos().get(index).getNombre());
        apellido.setText(MenuPrincipal.agenda.getContactos().get(index).getApellido());
        telefono.setText(MenuPrincipal.agenda.getContactos().get(index).getTelefono());
        portaImgContacto.setImage(new Image(MenuPrincipal.agenda.getContactos().get(index).getImgUrl()));
        fcImgUrl = MenuPrincipal.agenda.getContactos().get(index).getImgUrl();
        
        K4Emmanuel.priStage.setScene(addContactosScene);

        portaImgBorrar.setOnMouseClicked((event) -> {
            alerta.setText("Contacto borrado");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(0.5));
            delay.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    limpiar();
                    MenuPrincipal.agenda.getContactos().remove(index);
                    MenuPrincipal.botonContactoPane.getChildren().clear();
                    for (Contacto c : MenuPrincipal.agenda.getContactos()) {
                        MenuPrincipal.botonContactoPane.getChildren().add(new SuperBotonContacto(c.getNombre(), c.getApellido(), c.getImgUrl(), MenuPrincipal.agenda.getContactos().indexOf(c)));
                    }
                    K4Emmanuel.priStage.setScene(MenuPrincipal.priScene);
                }
            });
            delay.play();
        });

        portaImgGuardar.setOnMouseClicked((event) -> {
            MenuPrincipal.agenda.getContactos().get(index).setNombre(nombre.getText());
            MenuPrincipal.agenda.getContactos().get(index).setApellido(apellido.getText());
            MenuPrincipal.agenda.getContactos().get(index).setTelefono(telefono.getText());
            MenuPrincipal.agenda.getContactos().get(index).setImgUrl(fcImgUrl);
            alerta.setText("Contacto editado");
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(0.5));
            delay.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    limpiar();
                    MenuPrincipal.botonContactoPane.getChildren().clear();
                    MenuPrincipal.agenda.getContactos().sort(null);
                    for (Contacto c : MenuPrincipal.agenda.getContactos()) {
                        MenuPrincipal.botonContactoPane.getChildren().add(new SuperBotonContacto(c.getNombre(), c.getApellido(), c.getImgUrl(), MenuPrincipal.agenda.getContactos().indexOf(c)));
                    }
                    K4Emmanuel.priStage.setScene(MenuPrincipal.priScene);
                }
            });
            delay.play();
        });
    }

    static void addContacto() {
        fcImgUrl = imgUrlPorDefecto;
        contactosPane.getChildren().clear();
        contactosPane.getChildren().addAll(portaImgContacto, nombre, apellido, telefono, alerta, portaImgAtras, portaImgGuardar);
        K4Emmanuel.priStage.setScene(addContactosScene);
        portaImgGuardar.setOnMouseClicked((event) -> {
            MenuPrincipal.agenda.nuevoContacto(nombre.getText(), apellido.getText(), telefono.getText(), fcImgUrl);
            alerta.setText("Contacto guardado");
            ocultarAlerta(0.5);
        });
    }

    private static void ocultarAlerta(Double tiempo) {
        PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(tiempo));
        delay.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                limpiar();
            }
        });
        delay.play();
    }

    private static void limpiar() {
        alerta.setText("");
        nombre.setText("");
        apellido.setText("");
        telefono.setText("");
        portaImgContacto.setImage(imgContacto);
    }

}
