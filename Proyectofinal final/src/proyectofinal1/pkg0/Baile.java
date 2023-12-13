package proyectofinal1.pkg0;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JOptionPane;

public class Baile {

    //ventana de clases de baile
    public void baile(Empleado[] empleados) {
        this.empleados = empleados; //set the empleados
        JFrame frame = new JFrame("Clases de Baile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 170);

        // Crear botones para las acciones
        JButton reservarButton = new JButton("Reservar clase de Baile");
        JButton mostHorarioButton = new JButton("Mostrar horario de Baile");
        JButton modButton = new JButton("Modificar Reservas");

        //configurar las acciones de cada boton
        reservarButton.addActionListener(e -> reservar());
        mostHorarioButton.addActionListener(e -> mostrar());
        modButton.addActionListener(e -> modificar());

        // Crear panel para organizar los botones
        JPanel panel = new JPanel();
        panel.add(reservarButton);
        panel.add(mostHorarioButton);
        panel.add(modButton);

        // Agregar al JFrame
        frame.getContentPane().add(panel);

        // Hacerlo visible
        frame.setVisible(true);
    }//fin de metodo 

    private static final int numClases = 1, espacios = 30;
    private static final String[] classHours = {"7 pm", "8 pm"}; //horarios de la clase
    private String[][] reservas; //matriz para almacenar
    private Empleado[] empleados; //para almacenar los empleados

    //constructor de la clase Baile
    public Baile() {
        reservas = new String[numClases][espacios]; //[Clase][Espacio]
    }

    //metodo para realizar una reserva
    private void reservar() {
        int clase = 0; //solo hay una clase de baile

        //espacios disponibles
        String selectEsp = "Escoja un espacio para la clase de baile:\n";
        for (int i = 0; i < espacios; i++) {
            if (reservas[clase][i] == null) {
                selectEsp += (i + 1) + ". Espacio " + (i + 1) + "\n";
            }//fin de if
        }//fin de for

        int chooseSpace = Integer.parseInt(JOptionPane.showInputDialog(selectEsp)) - 1;

        if (chooseSpace < 0 || chooseSpace >= espacios || reservas[clase][chooseSpace] != null) {
            JOptionPane.showMessageDialog(null, "Selección de espacio no válida o no disponible. Inténtelo nuevamente.");
            return;
        }//fin del if

        //obtener el empleado
        Empleado empleado = obtenerEmpleado();

        if (empleado == null) {
            JOptionPane.showMessageDialog(null, "Empleado no válido. Inténtelo nuevamente.");
            return;
        }//fin del if

        //mostrar las opciones de horas disponibles
        String opcionesHoras = "Seleccione la hora de reserva para la Clase " + (clase + 1)
                + " y el Espacio " + (chooseSpace + 1) + ":\n";
        for (int i = 0; i < classHours.length; i++) {
            opcionesHoras += (i + 1) + ". " + classHours[i] + "\n";
        }//fin de for

        int seleccionHora = Integer.parseInt(JOptionPane.showInputDialog(opcionesHoras)) - 1;

        if (seleccionHora < 0 || seleccionHora >= classHours.length) {
            JOptionPane.showMessageDialog(null, "Selección de hora no válida. Inténtelo nuevamente.");
            return;
        }//fin de if

        //reservar espacio
        reservas[clase][chooseSpace] = "Reservado para " + empleado.getNombre() + " a las " + classHours[seleccionHora];
        JOptionPane.showMessageDialog(null, "Reserva exitosa. Clase " + (clase + 1)
                + " en el Espacio " + (chooseSpace + 1) + " reservada para " + empleado.getNombre()
                + " a las " + classHours[seleccionHora] + ".");
    }//fin del metodo

    //metodo para mostrar las reservas de clases de yoga
    private void mostrar() {
        StringBuilder mensaje = new StringBuilder("Reservas de Baile:\n");

        for (int clase = 0; clase < numClases; clase++) {
            mensaje.append("Clase ").append(clase + 1).append(":\n");

            for (int espacio = 0; espacio < espacios; espacio++) {
                if (reservas[clase][espacio] != null) {
                    mensaje.append("Espacio ").append(espacio + 1)
                            .append(": ").append(reservas[clase][espacio]).append("\n");
                } else {
                    mensaje.append("Espacio ").append(espacio + 1).append(": Disponible\n");
                }//fin del else
            }//fin del for

            mensaje.append("\n");
        }//fin del for

        JOptionPane.showMessageDialog(null, mensaje.toString());
    }//fin del metodo

    private void modificar() {
        int clase = 0; //solo hay una clase

        //mostrar las reservas actuales 
        StringBuilder reservasActuales = new StringBuilder("Reservas actuales para la clase de baile" + ":\n");

        for (int espacio = 0; espacio < espacios; espacio++) {
            if (reservas[clase][espacio] != null) {
                reservasActuales.append("Espacio ").append(espacio + 1)
                        .append(": ").append(reservas[clase][espacio]).append("\n");
            }//fin del if
        }//fin del for

        reservasActuales.append("\n");
        JOptionPane.showMessageDialog(null, reservasActuales.toString());

        //preguntar al usuario si desea modificar o eliminar la reserva
        String[] opciones = {"Modificar", "Eliminar", "Cancelar"};
        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una acción:", "Gestión de Reservas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == 0) { //modificar reserva, llamar al metodo
            modificarHoraReserva(clase);
        } else if (seleccion == 1) { //eliminar reserva, llamar al metodo
            eliminarReserva(clase);
        }//fin del else if
    }//fin del if

    //metodo para modificar una reserva
    private void modificarHoraReserva(int clase) {
        int espacio = Integer.parseInt(JOptionPane.showInputDialog("Seleccione el espacio a modificar (1-" + espacios + "):")) - 1;

        if (espacio < 0 || espacio >= espacios || reservas[clase][espacio] == null) {
            JOptionPane.showMessageDialog(null, "Selección de espacio no válida o no reservado. Inténtelo nuevamente.");
            return;
        }//fin de if

        //obtener la nueva hora de reserva
        int seleccionHora = obtenerNuevaHoraReserva();

        //actualizar la hora de reserva
        reservas[clase][espacio] = "Reservado para " + reservas[clase][espacio].substring(reservas[clase][espacio].lastIndexOf(" ") + 1);
        JOptionPane.showMessageDialog(null, "Hora de reserva modificada con éxito a las " + classHours[seleccionHora] + ".");
    }//fin del metodo

    //metodo para obtener la nueva hora de reserva 
    private int obtenerNuevaHoraReserva() {
        //mostrar las opciones de horas disponibles
        StringBuilder opcionesHoras = new StringBuilder("Seleccione la nueva hora de reserva:\n");
        for (int i = 0; i < classHours.length; i++) {
            opcionesHoras.append(i + 1).append(". ").append(classHours[i]).append("\n");
        }//fin del for

        int seleccionHora = Integer.parseInt(JOptionPane.showInputDialog(opcionesHoras.toString())) - 1;

        while (seleccionHora < 0 || seleccionHora >= classHours.length) {
            JOptionPane.showMessageDialog(null, "Selección de hora no válida. Inténtelo nuevamente.");
            seleccionHora = Integer.parseInt(JOptionPane.showInputDialog(opcionesHoras.toString())) - 1;
        }//fin del while

        return seleccionHora;
    }//fin del metodo

    //metodo para eliminar una reserva
    private void eliminarReserva(int clase) {
        int espacio = Integer.parseInt(JOptionPane.showInputDialog("Seleccione el espacio a liberar (1-" + espacios + "):")) - 1;

        if (espacio < 0 || espacio >= espacios || reservas[clase][espacio] == null) {
            JOptionPane.showMessageDialog(null, "Selección de espacio no válida o no reservado. Inténtelo nuevamente.");
            return;
        }//fin del if

        // Liberar el espacio
        reservas[clase][espacio] = null;
        JOptionPane.showMessageDialog(null, "Reserva eliminada con éxito.");
    }//fin del metodo

    //metodo para obtener el empleado asociado a la reserva mediante su ID
    private Empleado obtenerEmpleado() {
        int idEmpleado = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su ID de empleado:"));

        for (Empleado empleado : empleados) {
            if (empleado.getId() == idEmpleado) {
                return empleado;
            }//fin del if
        }//fin del for

        return null;
    }//fin del metodo

}//fin de clase
