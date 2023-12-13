package proyectofinal1.pkg0;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.*;

public class Yoga {

    //ventana de clases de yoga
    public void yoga(Empleado[] empleados) {
        this.empleados = empleados; //set the empleados
        JFrame frame = new JFrame("Clases de Yoga");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 170);

        //crear botones para las acciones
        JButton reservarButton = new JButton("Reservar clase de Yoga");
        JButton mostHorarioButton = new JButton("Mostrar horario de Yoga");
        JButton modButton = new JButton("Gestionar Reservas");

        //configurar las acciones de cada boton
        reservarButton.addActionListener(e -> reservar());
        mostHorarioButton.addActionListener(e -> mostrar());
        modButton.addActionListener(e -> modificar());

        //crear panel para organizar los botones
        JPanel panel = new JPanel();
        panel.add(reservarButton);
        panel.add(mostHorarioButton);
        panel.add(modButton);

        //agregar al JFrame
        frame.getContentPane().add(panel);

        //hacerlo visible
        frame.setVisible(true);
    }//fin del metodo

    private static final int numClases = 1, espacios = 30;
    private static final String[] classHours = {"7 pm", "8 pm"}; //horarios de las clases de Yoga
    private String[][] reservas; //matriz para almacenar las reservas de clases [Clase][Espacio]
    private Empleado[] empleados; //added to store the empleados

    public Yoga() {
        reservas = new String[numClases][espacios]; //[Clase][Espacio]
    }

    private void reservar() {
        int clase = 0; // solo hay una clase

        // mostrar espacios disponibles
        String opcionesEspacios = "Escoja un espacio para la clase de yoga:\n";
        for (int i = 0; i < espacios; i++) {
            if (reservas[clase][i] == null) {
                opcionesEspacios += (i + 1) + ". Espacio " + (i + 1) + "\n";
            }//fin de if
        }//fin de for

        int seleccionEspacio = Integer.parseInt(JOptionPane.showInputDialog(opcionesEspacios)) - 1;

        if (seleccionEspacio < 0 || seleccionEspacio >= espacios || reservas[clase][seleccionEspacio] != null) {
            JOptionPane.showMessageDialog(null, "Error");
            return;
        }//fin de if

        //obtener el empleado
        Empleado empleado = obtenerEmpleado();

        if (empleado == null) {
            JOptionPane.showMessageDialog(null, "Error.");
            return;
        }//fin de if

        //mostrar las opciones de horas disponibles
        String opcionesHoras = "Escoja la hora de reserva"
                + " y el Espacio " + (seleccionEspacio + 1) + ":\n";
        for (int i = 0; i < classHours.length; i++) {
            opcionesHoras += (i + 1) + ". " + classHours[i] + "\n";
        }//fin del for

        int seleccionHora = Integer.parseInt(JOptionPane.showInputDialog(opcionesHoras)) - 1;

        if (seleccionHora < 0 || seleccionHora >= classHours.length) {
            JOptionPane.showMessageDialog(null, "Error.");
            return;
        }//fin del if

        //reservar espacio
        reservas[clase][seleccionEspacio] = "Reservado para " + empleado.getNombre() + " a las " + classHours[seleccionHora];
        JOptionPane.showMessageDialog(null, "Reservada. Clase " + (clase + 1)
                + " en el Espacio " + (seleccionEspacio + 1) + " reservada para " + empleado.getNombre()
                + " a las " + classHours[seleccionHora] + ".");
    }//fin del metodo

    //metodo para mostrar las reservas de clases de yoga
    private void mostrar() {
        StringBuilder mensaje = new StringBuilder("Reservas de Yoga:\n");

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

        //mostrar las reservas actuales para la clase seleccionada
        StringBuilder reservasActuales = new StringBuilder("Reservas para la Clase " + (clase + 1) + ":\n");

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
        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una acción:", "Reservas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == 0) { //modificar reserva, llamar el metodo
            modificarHoraReserva(clase);
        } else if (seleccion == 1) { //eliminar reserva, llamar el metodo
            eliminarReserva(clase);
        }//fin de else if
    }//fin del metodo

    //metodo para modificar la reserva
    private void modificarHoraReserva(int clase) {
        int espacio = Integer.parseInt(JOptionPane.showInputDialog("Escoja el espacio (1-" + espacios + "):")) - 1;

        if (espacio < 0 || espacio >= espacios || reservas[clase][espacio] == null) {
            JOptionPane.showMessageDialog(null, "Error.");
            return;
        }//fin de if

        //obtener la nueva hora de reserva
        int seleccionHora = obtenerNuevaHoraReserva();

        //actualizar la hora de reserva
        reservas[clase][espacio] = "Reservado para " + reservas[clase][espacio].substring(reservas[clase][espacio].lastIndexOf(" ") + 1);
        JOptionPane.showMessageDialog(null, "Hora de reserva modificada! a las " + classHours[seleccionHora] + ".");
    }//fin del metodo

    //metodo para obtener la nueva hora de reserva 
    private int obtenerNuevaHoraReserva() {
        //mostrar las opciones de horas disponibles
        StringBuilder opcionesHoras = new StringBuilder("Escoja la nueva hora para reservar:\n");
        for (int i = 0; i < classHours.length; i++) {
            opcionesHoras.append(i + 1).append(". ").append(classHours[i]).append("\n");
        }//fin del for

        int seleccionHora = Integer.parseInt(JOptionPane.showInputDialog(opcionesHoras.toString())) - 1;

        while (seleccionHora < 0 || seleccionHora >= classHours.length) {
            JOptionPane.showMessageDialog(null, "Error.");
            seleccionHora = Integer.parseInt(JOptionPane.showInputDialog(opcionesHoras.toString())) - 1;
        }//fin de while

        return seleccionHora;
    }//fin de metodo

    //metodo para eliminar una reserva
    private void eliminarReserva(int clase) {
        int espacio = Integer.parseInt(JOptionPane.showInputDialog("Escoja el espacio a borrar (1-" + espacios + "):")) - 1;

        if (espacio < 0 || espacio >= espacios || reservas[clase][espacio] == null) {
            JOptionPane.showMessageDialog(null, "Error.");
            return;
        }//fin de if

        //liberar el espacio
        reservas[clase][espacio] = null;
        JOptionPane.showMessageDialog(null, "Reserva eliminada.");
    }//fin de metodo

    //metodo para obtener el empleado mediante su ID
    private Empleado obtenerEmpleado() {
        int idEmpleado = Integer.parseInt(JOptionPane.showInputDialog("Digite su ID de empleado:"));

        for (Empleado empleado : empleados) {
            if (empleado.getId() == idEmpleado) {
                return empleado;
            }//fin del if
        }//fin del for

        return null;
    }//fin del metodo

}//end of class
