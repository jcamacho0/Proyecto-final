
package proyectofinal1.pkg0;

import javax.swing.JOptionPane;

public class Secretaria {
    public int contrasenia;
    
    public void ingresar(){
        contrasenia= Integer.parseInt(JOptionPane.showInputDialog("Binvenid@ secretari@! \nDigite su contrasenia:"));
        if (contrasenia == 1234) {
            
        } else {
            System.err.println("Vuelve a intentar");
            System.exit(1);
        }
    }

    
    
    
}
