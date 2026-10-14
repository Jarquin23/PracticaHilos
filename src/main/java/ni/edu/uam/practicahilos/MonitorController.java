package ni.edu.uam.practicahilos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class MonitorController {

    @FXML private ProgressBar barraClientes;
    @FXML private Label lblEstadoClientes;
    @FXML private Label lblTiempoClientes;
    @FXML private Button btnIniciarClientes;
    @FXML private Button btnDetenerClientes;
    @FXML private Button btnReiniciarClientes;

    @FXML private ProgressBar barraProductos;
    @FXML private Label lblEstadoProductos;
    @FXML private Label lblTiempoProductos;
    @FXML private Button btnIniciarProductos;
    @FXML private Button btnDetenerProductos;
    @FXML private Button btnReiniciarProductos;

    private Thread hiloClientes;
    private Thread hiloProductos;

    private int progresoClientes = 0;
    private long tiempoClientes = 0;

    private int progresoProductos = 0;
    private long tiempoProductos = 0;

    @FXML
    private void iniciarClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) return;
        if (progresoClientes >= 100) return;

        lblEstadoClientes.setText("Procesando clientes...");
        btnIniciarClientes.setDisable(true);
        btnDetenerClientes.setDisable(false);
        btnReiniciarClientes.setDisable(true);

        Runnable tarea = () -> {
            long inicio = System.currentTimeMillis();
            try {
                for (int i = progresoClientes + 1; i <= 100; i++) {

                    if (Thread.currentThread().isInterrupted())
                        throw new InterruptedException();

                    Thread.sleep(80);

                    final int avance = i;
                    Platform.runLater(() ->
                            barraClientes.setProgress(avance / 100.0));

                    progresoClientes = i;
                }

                long fin = System.currentTimeMillis();
                tiempoClientes += (fin - inicio);
                final long total = tiempoClientes;

                Platform.runLater(() -> {
                    lblEstadoClientes.setText("Clientes finalizado");
                    lblTiempoClientes.setText("Tiempo: " + total + " ms");
                    btnIniciarClientes.setText("Iniciar clientes");
                    btnIniciarClientes.setDisable(true);
                    btnDetenerClientes.setDisable(true);
                    btnReiniciarClientes.setDisable(false);
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                long fin = System.currentTimeMillis();
                tiempoClientes += (fin - inicio);
                final long total = tiempoClientes;

                Platform.runLater(() -> {
                    lblEstadoClientes.setText(
                            "Detenido en " + progresoClientes + "%"
                    );
                    lblTiempoClientes.setText("Tiempo acumulado: " + total + " ms");
                    btnIniciarClientes.setText("Reanudar clientes");
                    btnIniciarClientes.setDisable(false);
                    btnDetenerClientes.setDisable(true);
                    btnReiniciarClientes.setDisable(false);
                });
            }
        };

        hiloClientes = new Thread(tarea, "Hilo-Clientes");
        hiloClientes.setDaemon(true);
        hiloClientes.start();
    }

    @FXML
    private void detenerClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) {
            btnDetenerClientes.setDisable(true);
            lblEstadoClientes.setText("Deteniendo clientes...");
            hiloClientes.interrupt();
        }
    }

    @FXML
    private void reiniciarClientes() {
        if (hiloClientes != null && hiloClientes.isAlive()) return;

        progresoClientes = 0;
        tiempoClientes = 0;

        barraClientes.setProgress(0);
        lblEstadoClientes.setText("Esperando inicio");
        lblTiempoClientes.setText("Tiempo: 0 ms");

        btnIniciarClientes.setText("Iniciar clientes");
        btnIniciarClientes.setDisable(false);
        btnDetenerClientes.setDisable(true);
        btnReiniciarClientes.setDisable(true);
    }

    @FXML
    private void iniciarProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) return;
        if (progresoProductos >= 50) return;

        lblEstadoProductos.setText("Procesando productos...");
        btnIniciarProductos.setDisable(true);
        btnDetenerProductos.setDisable(false);
        btnReiniciarProductos.setDisable(true);

        Runnable tarea = () -> {
            long inicio = System.currentTimeMillis();
            try {
                for (int i = progresoProductos + 1; i <= 50; i++) {

                    if (Thread.currentThread().isInterrupted())
                        throw new InterruptedException();

                    Thread.sleep(150);

                    final int avance = i;
                    Platform.runLater(() ->
                            barraProductos.setProgress(avance / 50.0));

                    progresoProductos = i;
                }

                long fin = System.currentTimeMillis();
                tiempoProductos += (fin - inicio);
                final long total = tiempoProductos;

                Platform.runLater(() -> {
                    lblEstadoProductos.setText("Productos finalizado");
                    lblTiempoProductos.setText("Tiempo: " + total + " ms");
                    btnIniciarProductos.setText("Iniciar productos");
                    btnIniciarProductos.setDisable(true);
                    btnDetenerProductos.setDisable(true);
                    btnReiniciarProductos.setDisable(false);
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                long fin = System.currentTimeMillis();
                tiempoProductos += (fin - inicio);
                final long total = tiempoProductos;

                Platform.runLater(() -> {
                    lblEstadoProductos.setText(
                            "Detenido en " + progresoProductos + "/50"
                    );
                    lblTiempoProductos.setText("Tiempo acumulado: " + total + " ms");
                    btnIniciarProductos.setText("Reanudar productos");
                    btnIniciarProductos.setDisable(false);
                    btnDetenerProductos.setDisable(true);
                    btnReiniciarProductos.setDisable(false);
                });
            }
        };

        hiloProductos = new Thread(tarea, "Hilo-Productos");
        hiloProductos.setDaemon(true);
        hiloProductos.start();
    }

    @FXML
    private void detenerProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) {
            btnDetenerProductos.setDisable(true);
            lblEstadoProductos.setText("Deteniendo productos...");
            hiloProductos.interrupt();
        }
    }

    @FXML
    private void reiniciarProductos() {
        if (hiloProductos != null && hiloProductos.isAlive()) return;

        progresoProductos = 0;
        tiempoProductos = 0;

        barraProductos.setProgress(0);
        lblEstadoProductos.setText("Esperando inicio");
        lblTiempoProductos.setText("Tiempo: 0 ms");

        btnIniciarProductos.setText("Iniciar productos");
        btnIniciarProductos.setDisable(false);
        btnDetenerProductos.setDisable(true);
        btnReiniciarProductos.setDisable(true);
    }

    public void detenerAlCerrar() {
        if (hiloClientes != null && hiloClientes.isAlive())
            hiloClientes.interrupt();
        if (hiloProductos != null && hiloProductos.isAlive())
            hiloProductos.interrupt();
    }
}