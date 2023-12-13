package proyectofinal1.pkg0;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Entrenador {

    public void gym() {
        //ventana de cine
        JFrame frame = new JFrame("Gymnasio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 170);

        //crear botones para las acciones
        JButton reservarButton = new JButton("Reservar Espacio");
        JButton mostHorarioButton = new JButton("Ver reservas");
        JButton modButton = new JButton("Modificar reservas");

        //configurar las acciones de cada boton
        reservarButton.addActionListener(e -> reservar());
        mostHorarioButton.addActionListener(e -> mostrar());
        modButton.addActionListener(e -> modificar());

        //crear panel para organizar los botones
        JPanel panel = new JPanel();
        panel.add(reservarButton);
        panel.add(mostHorarioButton);
        panel.add(modButton);

        //agregar al jframe
        frame.getContentPane().add(panel);

        //hacerlo visible
        frame.setVisible(true);

    }

    private static final int entrenadores = 2, horarios = 6, personas = 5;//numero maximo de personas para las que se pueden hacer reservas en un horario con un entrenador
    private String[][][] reservas; //matriz para almacenar las reservas de los entrenadores
    private Empleado[] empleados;

    //constructor
    public Entrenador(Empleado[] empleados) {
        reservas = new String[entrenadores][horarios][personas]; //[Entrenador][Horario][ID de Empleado]
        this.empleados = empleados;
    }//fin de constructor

    //metodo para reservar espacio con los entrenadores
    private void reservar() {
        int entrenador = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de entrenador (1-" + Entrenador.entrenadores + "):")) - 1;

        if (entrenador < 0 || entrenador >= Entrenador.entrenadores) {
            JOptionPane.showMessageDialog(null, "Número de entrenador no válido. Inténtelo nuevamente.");
            return;
        }//fin de if

        //mostrar horarios disponibles
        String opcionesHorarios = "Seleccione un horario para el entrenador " + (entrenador + 1) + ":\n";
        for (int i = 0; i < horarios; i++) {
            if (reservas[entrenador][i][0] == null) {
                opcionesHorarios += (i + 1) + ". Horario " + (i + 14) + ":00 - " + (i + 15) + ":00\n";
            }//fin de if
        }//fin de for

        int seleccionHorario = Integer.parseInt(JOptionPane.showInputDialog(opcionesHorarios)) - 1;

        if (seleccionHorario < 0 || seleccionHorario >= horarios || reservas[entrenador][seleccionHorario][0] != null) {
            JOptionPane.showMessageDialog(null, "Selección de horario no válida o no disponible. Inténtelo nuevamente.");
            return;
        }//if

        //solicitar ID del empleado
        int idEmpleado = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su ID de empleado para confirmar la reserva:"));

        if (!validarEmpleado(idEmpleado)) {
            JOptionPane.showMessageDialog(null, "ID de empleado no válido. Inténtelo nuevamente.");
            return;
        }//if

        // Reservar horario
        reservas[entrenador][seleccionHorario][0] = "Reservado";
        reservas[entrenador][seleccionHorario][1] = String.valueOf(idEmpleado);
        JOptionPane.showMessageDialog(null, "Reserva exitosa. Entrenador " + (entrenador + 1)
                + " a las " + "Horario " + (seleccionHorario + 14) + ":00 - " + (seleccionHorario + 15) + ":00 para la persona con ID " + idEmpleado + ".");
    }//fin de método

    private int mostrarOpciones() {
        String[] opcionesEdicion = {"Cancelar Reserva", "Cambiar a Otro Horario", "Cambiar con Otro Entrenador"};
        int select = JOptionPane.showOptionDialog(
                null,
                "Seleccione una opción de edición para su reserva:",
                "Editar Reserva",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcionesEdicion,
                opcionesEdicion[0]
        );
        return select;
    }

    private void realizarAccion(int seleccion, int entrenador, int horario) {
        if (seleccion == 0) {
            // cancelar Reserva
            cancelar(entrenador, horario);
        } else if (seleccion == 1) {
            // cambiar a otro Horario
            cambiarHorario(entrenador, horario);
        } else if (seleccion == 2) {
            // cambiar a otro Entrenador
            cambiarEntrenador(entrenador, horario);
        } else {
            JOptionPane.showMessageDialog(null, "Opción no válida. No se realizó ningún cambio en la reserva.");
        }
    }

    private void modificar() {
        // solicitar ID del empleado
        int idEmpleado = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su ID de empleado para editar la reserva:"));

        if (!validarEmpleado(idEmpleado)) {
            JOptionPane.showMessageDialog(null, "ID de empleado no válido. Inténtelo nuevamente.");
            return;
        }//fin de if

        // buscar la reserva del empleado
        int entrenador = -1;
        int horario = -1;

        for (int i = 0; i < Entrenador.entrenadores; i++) {
            for (int j = 0; j < horarios; j++) {
                if (reservas[i][j][0] != null && reservas[i][j][1].equals(String.valueOf(idEmpleado))) {
                    entrenador = i;
                    horario = j;
                    break;
                }//if
            }//for
            if (entrenador != -1) {
                break;
            }
        }//for

        if (entrenador == -1 || horario == -1) {
            JOptionPane.showMessageDialog(null, "No se encontró ninguna reserva para el ID de empleado proporcionado.");
            return;
        }//if

        //mostrar opciones de edicion
        int select = mostrarOpciones();

        realizarAccion(select, entrenador, horario);
    }//fin de metodo

    private void mostrar() {
        StringBuilder mensaje = new StringBuilder("Reservas del Gym:\n");

        for (int entrenador = 0; entrenador < Entrenador.entrenadores; entrenador++) {
            mensaje.append("Entrenador ").append(entrenador + 1).append(":\n");

            for (int horario = 0; horario < horarios; horario++) {
                if (reservas[entrenador][horario][0] != null) {
                    int idEmpleado = Integer.parseInt(reservas[entrenador][horario][1]);
                    String nombreEmpleado = obtenerNombreEmpleado(idEmpleado);
                    mensaje.append("Horario ").append(horario + 14).append(":00 - ").append(horario + 15).append(":00: Reservado (ID Empleado: ").append(nombreEmpleado).append(")\n");
                } else {
                    mensaje.append("Horario ").append(horario + 14).append(":00 - ").append(horario + 15).append(":00: Disponible\n");
                }//else
            }//for

            mensaje.append("\n");
        }//for

        JOptionPane.showMessageDialog(null, mensaje.toString());
    }//fin de metodo
    
    private void cancelar(int entrenador, int horario) {
        //verificar si hay una reserva para cancelar
        if (reservas[entrenador][horario] != null) {
            //cancelar reserva
            reservas[entrenador][horario] = null;
            JOptionPane.showMessageDialog(null, "Reserva cancelada con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "No hay reserva para cancelar en este horario.");
        }//else
    }//fin de metodo

    private void cambiarHorario(int entrenador, int horario) {
        //mostrar horarios disponibles
        String opcionesHorarios = "Seleccione un nuevo horario para su reserva:\n";

        //construir la lista de horarios disponibles
        for (int i = 0; i < horarios; i++) {
            if (i != horario && reservas[entrenador][i][0] == null) {
                opcionesHorarios += (i + 1) + ". Horario " + (i + 14) + ":00 - " + (i + 15) + ":00\n";
            }
        }

        //obtener la seleccion del usuario
        int nuevoHorario = Integer.parseInt(JOptionPane.showInputDialog(opcionesHorarios)) - 1;

        //validar la seleccion del horario
        if (nuevoHorario < 0 || nuevoHorario >= horarios || reservas[entrenador][nuevoHorario][0] != null) {
            JOptionPane.showMessageDialog(null, "Selección de nuevo horario no válida o no disponible. No se realizó ningún cambio en la reserva.");
            return;
        }

        //intercambiar directamente los horarios
        reservas[entrenador][nuevoHorario][0] = reservas[entrenador][horario][0];
        reservas[entrenador][nuevoHorario][1] = reservas[entrenador][horario][1];

        //liberar el horario anterior
        reservas[entrenador][horario][0] = null;
        reservas[entrenador][horario][1] = null;

        //mostrar mensaje de exito
        JOptionPane.showMessageDialog(null, "Reserva editada con éxito. Entrenador " + (entrenador + 1)
                + " a las " + "Horario " + (nuevoHorario + 14) + ":00 - " + (nuevoHorario + 15) + ":00 para la persona con ID " + reservas[entrenador][nuevoHorario][1] + ".");
    }//fin de metodo

    private void cambiarEntrenador(int entrenador, int horario) {
        //mostrar entrenadores disponibles
        String opcionesEntrenadores = "Seleccione otro entrenador para su reserva:\n";

        //construir la lista de entrenadores disponibles
        for (int i = 0; i < Entrenador.entrenadores; i++) {
            if (i != entrenador && reservas[i][horario][0] == null) {
                opcionesEntrenadores += (i + 1) + ". Entrenador " + (i + 1) + "\n";
            }
        }

        //obtener la selección del usuario
        int nuevoEntrenador = Integer.parseInt(JOptionPane.showInputDialog(opcionesEntrenadores)) - 1;

        //validar la selección del entrenador
        if (nuevoEntrenador < 0 || nuevoEntrenador >= Entrenador.entrenadores || reservas[nuevoEntrenador][horario][0] != null) {
            JOptionPane.showMessageDialog(null, "Selección de nuevo entrenador no válida o no disponible. No se realizó ningún cambio en la reserva.");
            return;
        }

        //intercambiar directamente los entrenadores
        reservas[nuevoEntrenador][horario][0] = reservas[entrenador][horario][0];
        reservas[nuevoEntrenador][horario][1] = reservas[entrenador][horario][1];

        //liberar la reserva anterior
        reservas[entrenador][horario][0] = null;
        reservas[entrenador][horario][1] = null;

        //mostrar mensaje de éxito
        JOptionPane.showMessageDialog(null, "Reserva editada con éxito. Entrenador " + (nuevoEntrenador + 1)
                + " a las " + "Horario " + (horario + 14) + ":00 - " + (horario + 15) + ":00 para la persona con ID " + reservas[nuevoEntrenador][horario][1] + ".");
    }//fin del metodo

   
    private boolean validarEmpleado(int idEmpleado) {
        for (Empleado empleado : empleados) {
            if (empleado.getId() == idEmpleado) {
                return true;
            }//if
        }//for
        return false;
    }//fin de metodo

    private String obtenerNombreEmpleado(int idEmpleado) {
        for (Empleado empleado : empleados) {
            if (empleado.getId() == idEmpleado) {
                return empleado.getNombre();
            }//if
        }//for
        return "Desconocido";
    }//fin de metodo

}//fin de la clase
