package ni.edu.uam.practicahilos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProcesadorController {

    @FXML private Label lblEstado;
    @FXML private Label lblPorcentaje;
    @FXML private Label lblTiempo;
    @FXML private ProgressBar barraProgreso;
    @FXML private Button btnIniciar;
    @FXML private Button btnDetener;
    @FXML private Button btnReiniciar;

    private Thread hiloProceso;

    private int progreso = 0;
    private long tiempoAcumulado = 0;

    @FXML
    private void iniciarProceso() {

        if (hiloProceso != null && hiloProceso.isAlive()) return;
        if (progreso >= 100) return; // ya terminó

        lblEstado.setText("Procesando...");
        btnIniciar.setDisable(true);
        btnDetener.setDisable(false);
        btnReiniciar.setDisable(true);

        Runnable tarea = () -> {
            long inicio = System.currentTimeMillis();
            try {
                for (int i = progreso + 1; i <= 100; i++) {

                    if (Thread.currentThread().isInterrupted())
                        throw new InterruptedException();

                    Thread.sleep(100);

                    final int avance = i;
                    Platform.runLater(() -> {
                        barraProgreso.setProgress(avance / 100.0);
                        lblPorcentaje.setText(avance + "%");
                    });

                    progreso = i;
                }

                long fin = System.currentTimeMillis();
                tiempoAcumulado += (fin - inicio);
                final long total = tiempoAcumulado;

                Platform.runLater(() -> {
                    lblEstado.setText("Proceso finalizado");
                    lblTiempo.setText("Tiempo: " + total + " ms");
                    // Ya no se puede reanudar, solo reiniciar
                    btnIniciar.setText("Iniciar");
                    btnIniciar.setDisable(true);
                    btnDetener.setDisable(true);
                    btnReiniciar.setDisable(false);
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                long fin = System.currentTimeMillis();
                tiempoAcumulado += (fin - inicio);
                final long total = tiempoAcumulado;

                Platform.runLater(() -> {
                    lblEstado.setText("Detenido en " + progreso + "%");
                    lblTiempo.setText("Tiempo acumulado: " + total + " ms");
                    // Se puede reanudar
                    btnIniciar.setText("Reanudar");
                    btnIniciar.setDisable(false);
                    btnDetener.setDisable(true);
                    btnReiniciar.setDisable(false);
                });
            }
        };

        hiloProceso = new Thread(tarea, "Hilo-Procesamiento");
        hiloProceso.setDaemon(true);
        hiloProceso.start();
    }

    @FXML
    private void detenerProceso() {
        if (hiloProceso != null && hiloProceso.isAlive()) {
            btnDetener.setDisable(true);
            lblEstado.setText("Solicitando detención...");
            hiloProceso.interrupt();
        }
    }

    @FXML
    private void reiniciarProceso() {
        if (hiloProceso != null && hiloProceso.isAlive()) return;

        progreso = 0;
        tiempoAcumulado = 0;

        barraProgreso.setProgress(0);
        lblPorcentaje.setText("0%");
        lblEstado.setText("Esperando inicio");
        lblTiempo.setText("Tiempo: 0 ms");

        btnIniciar.setText("Iniciar");
        btnIniciar.setDisable(false);
        btnDetener.setDisable(true);
        btnReiniciar.setDisable(true);
    }

    public void detenerAlCerrar() {
        if (hiloProceso != null && hiloProceso.isAlive()) {
            hiloProceso.interrupt();
        }
    }
}