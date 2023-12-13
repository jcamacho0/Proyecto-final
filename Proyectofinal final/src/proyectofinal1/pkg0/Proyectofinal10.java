package proyectofinal1.pkg0;

import javax.swing.*;

public class Proyectofinal10 {

    public static void main(String[] args) {

        //crear empleados
        Empleado empleado1 = new Empleado("Sofia Vergara", 1234);
        Empleado empleado2 = new Empleado("Jimena Camacho", 2345);
        Empleado empleado3 = new Empleado("Manrique Torres", 3456);
        Empleado empleado4 = new Empleado("Moises Fonseca", 4567);

        //array de emleados
        Empleado[] empleados = {empleado1, empleado2, empleado3, empleado4};
        //array nombre de pelis
        String[] nombresPeliculas = new String[3];
        
        //creando las nuevas instancias de la clase secretaria
        Secretaria secretaria = new Secretaria();
        Yoga yoga = new Yoga();
        Cine cine = new Cine(3, 30, empleados, nombresPeliculas);
        Entrenador entrenador = new Entrenador(empleados);
        Baile baile = new Baile();

        //metodo para ingresar al programa
        //secretaria.ingresar();

        //crear la ventana principal
        JFrame frame = new JFrame("Programa de Resrvas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 80);

        //crear botones para cada opcion
        JButton cinesButton = new JButton("Salas de cines");
        JButton entrenadorButton = new JButton("Entrenador de Gimnasio");
        JButton yogaButton = new JButton("Clases de Yoga");
        JButton baileButton = new JButton("Clases de Baile");

        //configurar acciones
        cinesButton.addActionListener(e -> cine.peliculas());
        entrenadorButton.addActionListener(e -> entrenador.gym());
        yogaButton.addActionListener(e -> yoga.yoga(empleados));
        baileButton.addActionListener(e -> baile.baile(empleados));

        //crear panel para organizar botones
        JPanel panel = new JPanel();
        panel.add(cinesButton);
        panel.add(entrenadorButton);
        panel.add(yogaButton);
        panel.add(baileButton);

        //agregar al jframe
        frame.getContentPane().add(panel);

        //hacerlo visible
        frame.setVisible(true);

    }//final del main

}//final de la clase
