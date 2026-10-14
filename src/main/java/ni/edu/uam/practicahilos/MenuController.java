package ni.edu.uam.practicahilos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private void abrirPractica() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("procesador-view.fxml")
        );
        Stage stage = new Stage();
        stage.setTitle("Práctica - Procesador de registros");
        stage.setScene(new Scene(loader.load()));

        ProcesadorController controller = loader.getController();
        stage.setOnCloseRequest(event -> controller.detenerAlCerrar());
        stage.show();
    }

    @FXML
    private void abrirReto() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("monitor-view.fxml")
        );
        Stage stage = new Stage();
        stage.setTitle("Reto - Monitor concurrente");
        stage.setScene(new Scene(loader.load()));

        MonitorController controller = loader.getController();
        stage.setOnCloseRequest(event -> controller.detenerAlCerrar());
        stage.show();
    }

    @FXML
    private void salir() {
        Platform.exit();
    }
}
