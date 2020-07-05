package k4emmanuel;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javax.xml.bind.JAXBException;

public class MenuPrincipal {

    static FileChooser fc = new FileChooser();
    static File archivoSeleccionado;
    static Agenda agenda = new Agenda();
    static BorderPane priPane = new BorderPane();
    static TextField textFieldBuscar = new TextField();
    static ScrollPane deslizarPane = new ScrollPane();
    static VBox botonContactoPane = new VBox(), menuPrincipalYBuscarPane = new VBox();
    static HBox buscarContactoPane = new HBox();
    static Scene priScene = new Scene(priPane, 292, 342);
    static MenuBar menuPrincipal = new MenuBar();
    static Menu menuOpciones = new Menu("Opciones");
    static Menu menuImportar = new Menu("Importar");
    static Menu menuExportar = new Menu("Exportar");
    static MenuItem anaCon = new MenuItem("Nuevo contacto"),
            expTxt = new MenuItem("Exportar a fichero de texto"),
            expBin = new MenuItem("Exportar a fichero binario"),
            expXml = new MenuItem("Exportar a fichero Xml"),
            impTxt = new MenuItem("Importar de fichero de texto"),
            impBin = new MenuItem("Importar de fichero binario"),
            impXml = new MenuItem("Importar de fichero XML");

    static void crearMenuPrincipal() {
        menuOpciones.getItems().addAll(anaCon);
        menuImportar.getItems().addAll(impTxt, impBin, impXml);
        menuExportar.getItems().addAll(expTxt, expBin, expXml);
        menuPrincipal.getMenus().addAll(menuOpciones, menuImportar, menuExportar);
        textFieldBuscar.setFocusTraversable(false);
        textFieldBuscar.setPromptText("Buscar un contacto");
        textFieldBuscar.setPrefWidth(198);
        menuPrincipalYBuscarPane.getChildren().addAll(menuPrincipal, textFieldBuscar);
        priPane.setTop(menuPrincipalYBuscarPane);
        deslizarPane.setContent(botonContactoPane);
        deslizarPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        priPane.setCenter(deslizarPane);
        K4Emmanuel.priStage.setResizable(false);
        K4Emmanuel.priStage.setTitle("Agenda FX 1.0");
        K4Emmanuel.priStage.setScene(priScene);
        K4Emmanuel.priStage.show();

        textFieldBuscar.textProperty().addListener(((observable, oldValue, newValue) -> {
            agenda.buscarContacto(textFieldBuscar.getText());
        }));

        anaCon.setOnAction(
                (event) -> {
                    AddContactos.addContacto();
                }
        );

        expTxt.setOnAction(
                (event) -> {
                    try {
                        agenda.guardarContactosEnFichero();
                    } catch (IOException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );

        expBin.setOnAction(
                (event) -> {
                    try {
                        agenda.guardarAgendaCompletaEnFicheroBinario();
                    } catch (IOException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );

        expXml.setOnAction(
                (event) -> {
                    try {
                        agenda.guardarAgendaCompletaEnXML();
                    } catch (JAXBException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );

        impTxt.setOnAction(
                (event) -> {
                    try {
                        agenda.cargarContactosDeFichero();
                    } catch (IOException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );

        impBin.setOnAction(
                (event) -> {
                    try {
                        agenda.cargarAgendaCompletaDeFicheroBinario();
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );

        impXml.setOnAction(
                (event) -> {
                    try {
                        agenda.cargarAgendaCompletaEnXML();
                    } catch (JAXBException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );

    }

}
