package proyectofinal1.pkg0;

public class Empleado {

    public String nombre;
    public int id;
    public String horaClase;

    public Empleado(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //metodo para obtener la hora de las clases
    public String getHoraClase() {
        return horaClase;
    }

    //metodo para establecer la hora de las clases
    public void setHoraClase(String horaClase) {
        this.horaClase = horaClase;
    }

}//fin de la clase
