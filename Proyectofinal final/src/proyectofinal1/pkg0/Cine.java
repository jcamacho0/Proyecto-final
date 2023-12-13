package proyectofinal1.pkg0;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Cine {

    public void peliculas() {
        //ventana de cine
        JFrame frame = new JFrame("Cine");
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

        // Configuracion de nombres de peliculas
        nombresPeliculas[0] = "The Matrix";
        nombresPeliculas[1] = "Forrest Gump";
        nombresPeliculas[2] = "16 Whishes";

    }//fin del metodo peliculas

    private final int salas, emptySeat = 0, asientosSala;
    private final int[][] reservas;
    private final Empleado[] empleados;
    private String[] nombresPeliculas;  // Nombres de las películas

    public Cine(int numSalas, int numAsientosPorSala, Empleado[] empleados, String[] nombresPeliculas) {
        this.salas = numSalas;
        this.asientosSala = numAsientosPorSala;
        this.reservas = new int[salas][asientosSala];
        this.empleados = empleados;
        this.nombresPeliculas = new String[numSalas];
    }

    public void reservar() {
        try {
            int sala = getValidInput("Ingrese el número de sala (1-" + salas + "):", 1, salas, false) - 1;
            int seleccionAsiento = showAvailableSeats(sala);
            int idEmpleado = getValidEmployeeID();

            if (reservas[sala][seleccionAsiento] != 0) {
                JOptionPane.showMessageDialog(null, "El asiento ya está reservado. Inténtelo nuevamente.");
                return;
            }

            reservas[sala][seleccionAsiento] = idEmpleado;
            JOptionPane.showMessageDialog(null, "Reserva exitosa. Asiento " + getSeatLabel(seleccionAsiento)
            + " en la sala " + (sala + 1) + " reservado.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Inténtelo nuevamente.");
        }//end of catch, in case of user error
    }//end of method reservar

    public void mostrar() {
        StringBuilder mensaje = new StringBuilder("Reservas del Cine:\n");
    
        //leyenda de salas
        for (int sala = 0; sala < salas; sala++) {
            mensaje.append("Sala ").append(sala + 1).append(": ");
        }
        mensaje.append("\n");
    
        //informacion de asientos
        for (int asiento = 0; asiento < asientosSala; asiento++) {
            mensaje.append("Asiento ").append(getSeatLabel(asiento)).append(": ");
            
            for (int sala = 0; sala < salas; sala++) {
                if (reservas[sala][asiento] != emptySeat) {
                    mensaje.append("Reservado (ID Empleado: ").append(reservas[sala][asiento]).append("), ");
                } else {
                    mensaje.append("Disponible, ");
                }
            }
            mensaje.append("\n");
        }
    
        JOptionPane.showMessageDialog(null, mensaje.toString());
    }//end of method

    public void modificar() {
        try {
            int sala = getValidInput("Ingrese el número de sala (1-" + salas + "):", 1, salas, false) - 1;
            int seleccionAsiento = getValidInput("Seleccione un asiento reservado para la sala " + (sala + 1) + ":", 1, asientosSala, true);
            
            if (reservas[sala][seleccionAsiento] == emptySeat) {
                JOptionPane.showMessageDialog(null, "No hay reserva para este asiento. Inténtelo nuevamente.");
                return;
            }

            reservas[sala][seleccionAsiento] = emptySeat;
            JOptionPane.showMessageDialog(null, "Cancelación de reserva exitosa. Asiento " + (seleccionAsiento + 1)
                    + " en la sala " + (sala + 1) + " liberado.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Inténtelo nuevamente.");
        }//end of catch incase of user error
    }//end of method modificar


    private int getValidInput(String message, int min, int max, boolean isSeatSelection) {
        if (isSeatSelection) {
            return getValidSeatInput(message, min, max);
        } else {
            return getValidGenericInput(message, min, max);
        }
    }

    //metodo para seleccionar asientos
    private int getValidSeatInput(String message, int min, int max) {
        int filas = 5;
        int columnas = 5;
    
        do {
            String inputStr = JOptionPane.showInputDialog(message);
    
            if (inputStr == null) {
                //manejar cancelar o cerrar la ventana de entrada
                System.exit(0);  
            }
    
            if (inputStr.length() == 2) {
                char rowChar = Character.toUpperCase(inputStr.charAt(0));
                char columnChar = inputStr.charAt(1);
    
                if (rowChar >= 'A' && rowChar < 'A' + filas && Character.isDigit(columnChar)) {
                    int row = rowChar - 'A';
                    int column = Character.getNumericValue(columnChar);
    
                    if (row * columnas + column >= min && row * columnas + column < max) {
                        return row * columnas + column;
                    }
                }
            }
    
            JOptionPane.showMessageDialog(null, "Entrada inválida. Inténtelo nuevamente.");
        } while (true);
    }
    
    //metodo auxiliar para validar el rango de entrada generico
    private int getValidGenericInput(String message, int min, int max) {
        int input;
        do {
            try {
                input = Integer.parseInt(JOptionPane.showInputDialog(message));
            } catch (NumberFormatException e) {
                mostrarMensajeError("Entrada inválida. Inténtelo nuevamente.");
                input = min - 1; //asignar un valor que haga que se repita el bucle
            }
        } while (input < min || input > max);
        return input;
    }
        private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    private int getValidEmployeeID() {
        int idEmpleado;
        do {
            idEmpleado = getValidInput("Ingrese su ID de empleado para confirmar la reserva:", 1, Integer.MAX_VALUE, false);
        } while (!validarEmpleado(idEmpleado));
        return idEmpleado;
    }//end of method getValidEmployeeID, this is to extract the employee id

    private String getSeatLabel(int index) {
        int row = index / 5;
        int column = index % 5;
        char rowLabel = (char) ('A' + row);
        int columnLabel = column + 1;
        return String.valueOf(rowLabel) + columnLabel;
    }

    private int showAvailableSeats(int sala) {
        //construir la cuadrícula de asientos en formato de cadena
        StringBuilder opcionesAsientos = new StringBuilder("     Película: " + nombresPeliculas[sala] + "\n");
        opcionesAsientos.append("L: asiento libre\nO: asiento ocupado\n\n");
    
        //leyenda de filas (A, B, C...) a la izquierda
        char etiquetaFila = 'A';
        for (int i = 0; i < asientosSala; i++) {
            // Nueva fila al inicio de cada fila de asientos
            if (i % 5 == 0) {
                opcionesAsientos.append(etiquetaFila).append("  ");
                etiquetaFila++;
            }
    
            if (i >= 25) {
                break; //detener el bucle cuando se alcance el limite de asientos
            }
    
            if (reservas[sala][i] == emptySeat) {
                opcionesAsientos.append("L   ");  //asiento libre
            } else {
                opcionesAsientos.append("O   ");  //asiento ocupado
            }
    
            //nueva linea despues de cada fila de asientos
            if ((i + 1) % 5 == 0 && i < 24) {
                opcionesAsientos.append("\n");
            } else if (i == 24) {
                break; //detener el bucle si se alcanza el ultimo asiento
            }
        }
    
        //leyenda de columnas (1, 2, 3...) en la parte inferior
        opcionesAsientos.append("\n    ");
        for (int i = 1; i <= 5; i++) {
            opcionesAsientos.append(i).append("   ");
        }
    
        //mensaje adicional al final 
        opcionesAsientos.append("\n\nSelecciona tu asiento (ej: B2)");

        //mostrar la cuadricula y obtener la seleccion de asiento
        int selectedIndex = getValidSeatInput(opcionesAsientos.toString(), 1, asientosSala) - 1;
        return selectedIndex;
    }//end of method showAvailableSeats

    private boolean validarEmpleado(int idEmpleado) {
        for (Empleado empleado : empleados) {
            if (empleado.getId() == idEmpleado) {
                return true;
            }
        }
        return false;
    }

}//fin de la clase peliculas
