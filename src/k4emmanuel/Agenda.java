package k4emmanuel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Agenda")
public class Agenda implements Serializable {

    //creo atributos
    @XmlElement(name = "Contacto")
    private final List<Contacto> contactos;

    //métodos de la clase
    public int getContactosLength() {
        return contactos.size();
    }

    public void nuevoContacto(String nombre, String apellido, String telefono, String imgUrl) {
        contactos.add(new Contacto(nombre.toLowerCase(), apellido.toLowerCase(), telefono, imgUrl));
        vaciarOrdenarCargarSuperBotonesContacto();
    }

    public void buscarContacto(String cadena) {
        MenuPrincipal.botonContactoPane.getChildren().clear();
        for (Contacto c : MenuPrincipal.agenda.contactos) {
                if((c.getNombre() + " " + c.getApellido()).contains(cadena)){
                MenuPrincipal.botonContactoPane.getChildren().add(new SuperBotonContacto(c.getNombre(), c.getApellido(), c.getImgUrl(), contactos.indexOf(c)));
            }
        }

    }

    public void guardarContactosEnFichero() throws IOException {
        MenuPrincipal.fc.setTitle("Exportar agenda");
        MenuPrincipal.fc.setInitialDirectory(new File(System.getProperty("user.home")));
        MenuPrincipal.fc.getExtensionFilters().clear();
        MenuPrincipal.fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        MenuPrincipal.archivoSeleccionado = MenuPrincipal.fc.showSaveDialog(K4Emmanuel.priStage);
        if (MenuPrincipal.archivoSeleccionado != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(MenuPrincipal.archivoSeleccionado.getPath()))) {
                for (Contacto i : contactos) {
                    bw.write(i.getNombre() + "/" + i.getApellido() + "/" + i.getTelefono() + "/" + i.getImgUrl());
                    bw.newLine();
                }
            }
        }
        MenuPrincipal.archivoSeleccionado = null;
    }

    public void cargarContactosDeFichero() throws FileNotFoundException, IOException {
        MenuPrincipal.fc.setTitle("Importar agenda");
        MenuPrincipal.fc.setInitialDirectory(new File(System.getProperty("user.home")));
        MenuPrincipal.fc.getExtensionFilters().clear();
        MenuPrincipal.fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        MenuPrincipal.archivoSeleccionado = MenuPrincipal.fc.showOpenDialog(K4Emmanuel.priStage);
        if (MenuPrincipal.archivoSeleccionado != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(MenuPrincipal.archivoSeleccionado.getPath()))) {
                String cadena = br.readLine();
                MenuPrincipal.agenda.contactos.clear();
                MenuPrincipal.botonContactoPane.getChildren().clear();
                while (cadena != null) {
                    String[] atributos = cadena.split("/");
                    contactos.add(new Contacto(atributos[0], atributos[1], atributos[2], atributos[3]));
                    cadena = br.readLine();
                }
                contactos.sort(null);
                SuperBotonContacto nuevo;
                for (Contacto c : contactos) {
                    MenuPrincipal.botonContactoPane.getChildren().add(nuevo = new SuperBotonContacto(c.getNombre(), c.getApellido(), c.getImgUrl(), contactos.indexOf(c)));
                    if (nuevo.getPortaImgContacto().getImage().isError()) {
                        contactos.get(contactos.indexOf(c)).setImgUrl(AddContactos.imgUrlPorDefecto);
                        nuevo.getPortaImgContacto().setImage(new Image(AddContactos.imgUrlPorDefecto));
                    }
                }
            }
        }
        MenuPrincipal.archivoSeleccionado = null;
    }

    public void guardarAgendaCompletaEnFicheroBinario() throws FileNotFoundException, IOException {
        MenuPrincipal.fc.setTitle("Exportar agenda");
        MenuPrincipal.fc.setInitialDirectory(new File(System.getProperty("user.home")));
        MenuPrincipal.fc.getExtensionFilters().clear();
        MenuPrincipal.fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data Files", "*.dat"));
        MenuPrincipal.archivoSeleccionado = MenuPrincipal.fc.showSaveDialog(K4Emmanuel.priStage);
        if (MenuPrincipal.archivoSeleccionado != null) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MenuPrincipal.archivoSeleccionado.getPath()))) {
                out.writeObject(this);
            }
        }
        MenuPrincipal.archivoSeleccionado = null;
    }

    public void cargarAgendaCompletaDeFicheroBinario() throws FileNotFoundException, IOException, ClassNotFoundException {
        MenuPrincipal.fc.setTitle("Importar agenda");
        MenuPrincipal.fc.setInitialDirectory(new File(System.getProperty("user.home")));
        MenuPrincipal.fc.getExtensionFilters().clear();
        MenuPrincipal.fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data Files", "*.dat"));
        MenuPrincipal.archivoSeleccionado = MenuPrincipal.fc.showOpenDialog(K4Emmanuel.priStage);
        if (MenuPrincipal.archivoSeleccionado != null) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(MenuPrincipal.archivoSeleccionado.getPath()));
            MenuPrincipal.agenda.contactos.clear();
            MenuPrincipal.agenda = (Agenda) in.readObject();
            MenuPrincipal.botonContactoPane.getChildren().clear();
            for (Contacto c : MenuPrincipal.agenda.getContactos()) {
                MenuPrincipal.botonContactoPane.getChildren().add(new SuperBotonContacto(c.getNombre(), c.getApellido(), c.getImgUrl(), MenuPrincipal.agenda.getContactos().indexOf(c)));
            }
        }
        MenuPrincipal.archivoSeleccionado = null;
    }

    public void guardarAgendaCompletaEnXML() throws JAXBException {
        MenuPrincipal.fc.setTitle("Exportar agenda");
        MenuPrincipal.fc.setInitialDirectory(new File(System.getProperty("user.home")));
        MenuPrincipal.fc.getExtensionFilters().clear();
        MenuPrincipal.fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Xml Files", "*.xml"));
        MenuPrincipal.archivoSeleccionado = MenuPrincipal.fc.showSaveDialog(K4Emmanuel.priStage);
        if (MenuPrincipal.archivoSeleccionado != null) {
            JAXBContext contexto = JAXBContext.newInstance(Agenda.class);
            Marshaller marshall = contexto.createMarshaller();
            marshall.setProperty(JAXB_FORMATTED_OUTPUT, true);
            marshall.marshal(this, new File(MenuPrincipal.archivoSeleccionado.getPath()));
        }
        MenuPrincipal.archivoSeleccionado = null;
    }

    public void cargarAgendaCompletaEnXML() throws JAXBException {
        MenuPrincipal.fc.setTitle("Importar agenda");
        MenuPrincipal.fc.setInitialDirectory(new File(System.getProperty("user.home")));
        MenuPrincipal.fc.getExtensionFilters().clear();
        MenuPrincipal.fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Xml Files", "*.xml"));
        MenuPrincipal.archivoSeleccionado = MenuPrincipal.fc.showOpenDialog(K4Emmanuel.priStage);
        if (MenuPrincipal.archivoSeleccionado != null) {
            JAXBContext contexto = JAXBContext.newInstance(Agenda.class);
            Unmarshaller unMarshall = contexto.createUnmarshaller();
            MenuPrincipal.agenda.contactos.clear();
            MenuPrincipal.agenda = (Agenda) unMarshall.unmarshal(new File(MenuPrincipal.archivoSeleccionado.getPath()));
            MenuPrincipal.botonContactoPane.getChildren().clear();
            for (Contacto c : MenuPrincipal.agenda.getContactos()) {
                MenuPrincipal.botonContactoPane.getChildren().add(new SuperBotonContacto(c.getNombre(), c.getApellido(), c.getImgUrl(), MenuPrincipal.agenda.getContactos().indexOf(c)));
            }
        }
        MenuPrincipal.archivoSeleccionado = null;
    }

    public boolean borrarContactos(String nombre) {
        Iterator<Contacto> i = contactos.iterator();
        boolean encontrado = false;
        boolean borrado = false;
        while (i.hasNext() && !encontrado) {
            Contacto c = i.next();
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                contactos.remove(c);
                borrado = true;
                encontrado = true;
            }
        }
        return borrado;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void vaciarOrdenarCargarSuperBotonesContacto() {
        MenuPrincipal.botonContactoPane.getChildren().clear();
        contactos.sort(null);
        for (Contacto c : contactos) {
            MenuPrincipal.botonContactoPane.getChildren().add(new SuperBotonContacto(c.getNombre(), c.getApellido(), c.getImgUrl(), contactos.indexOf(c)));
        }
    }

//creo constructor
    public Agenda() {
        contactos = new ArrayList<>();
    }
}
