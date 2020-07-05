package k4emmanuel;

import java.io.Serializable;

public class Contacto implements Serializable, Comparable<Contacto> {

    private String nombre;
    private String apellido;
    private String telefono;
    private String imgUrl;

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1);;
    }

    public void setApellido(String apellido) {
        this.apellido = Character.toUpperCase(apellido.charAt(0)) + apellido.substring(1);;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setImgUrl(String ruta) {
        imgUrl = ruta;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public int compareTo(Contacto otro) {

        int devolver = 0;

        if (nombre == null) {
            devolver = 0;
        } else if (nombre.compareTo(otro.nombre) < 0) {
            devolver = -1;
        } else if (nombre.compareTo(otro.nombre) > 0) {
            devolver = 1;
        }
        return devolver;
    }

    //creo constructor
    public Contacto() {
    }

    public Contacto(String nombre, String apellido, String numero, String imgUrl) {
        this.nombre = Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1);
        this.apellido = Character.toUpperCase(apellido.charAt(0)) + apellido.substring(1);
        this.telefono = numero;
        this.imgUrl = imgUrl;
    }

}
