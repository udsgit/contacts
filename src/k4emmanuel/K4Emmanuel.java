
package k4emmanuel;

import javafx.application.Application;

import javafx.stage.Stage;



public class K4Emmanuel extends Application {
    
    
  
    
    static Stage priStage;
    
    @Override
    public void start(Stage priStage) {
        this.priStage = priStage;
        MenuPrincipal.crearMenuPrincipal();
        AddContactos.crearSceneAddContactosAndEditContactos();
        
 
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
