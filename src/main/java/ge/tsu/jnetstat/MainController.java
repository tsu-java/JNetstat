package ge.tsu.jnetstat;

import ge.tsu.jnetstat.parser.NetstatParser;
import ge.tsu.jnetstat.parser.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.*;

/**
 * See:
 * https://superuser.com/questions/1025252/how-to-list-open-ports-and-application-using-them-in-windows#1025253
 * https://www.baeldung.com/run-shell-command-in-java
 */
public class MainController implements Initializable {

    private Stage primaryStage;
    private final boolean isWindows;
    private final ObservableList<Result> parsedResults;

    @FXML
    private TableView<Result> tableView;

    public MainController() {
        isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        parsedResults = FXCollections.observableArrayList();
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setItems(parsedResults);
        try {
            refreshTable(true);
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshTable() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        refreshTable(false);
    }

    private void refreshTable(boolean initialize)
            throws IOException, InterruptedException, ExecutionException, TimeoutException {

        ProcessBuilder builder = new ProcessBuilder();
        if (isWindows) {
            builder.command("netstat", "-b", "-a", "-n", "-p", "tcp", "-o");
        } else {
            throw new UnsupportedOperationException("Not supported yet");
        }
        Process process = builder.start();

        NetstatParser parser = new NetstatParser(process);

        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
            Future<List<Result>> future = executorService.submit(parser);

            int exitCode = process.waitFor();

            parsedResults.clear();
            parsedResults.addAll(future.get(5, TimeUnit.SECONDS));

            if (initialize) {
                initializeTableColumns();
            }

            if (exitCode != 0) {
                throw new IllegalStateException("Error getting nice cmd result! Something happened :(");
            }
        }
    }

    private void initializeTableColumns() {
        // Delete any pre-existing columns!
        tableView.getColumns().clear();

        // Name
        TableColumn<Result, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(parsedResults.get(0).nameProperty().getName()));

        // Protocol
        TableColumn<Result, String> protocolCol = new TableColumn<>("Protocol");
        protocolCol.setCellValueFactory(new PropertyValueFactory<>(parsedResults.get(0).protocolProperty().getName()));

        // Local Address
        TableColumn<Result, String> localAddressCol = new TableColumn<>("Local Address");
        localAddressCol.setCellValueFactory(new PropertyValueFactory<>(
                parsedResults.get(0).localAddressProperty().getName())
        );

        // Local Address Port
        TableColumn<Result, String> localAddressPortCol = new TableColumn<>("Local Address Port");
        localAddressPortCol.setCellValueFactory(new PropertyValueFactory<>(
                parsedResults.get(0).localAddressPortProperty().getName())
        );

        // Foreign Address
        TableColumn<Result, String> foreignAddressCol = new TableColumn<>("Foreign Address");
        foreignAddressCol.setCellValueFactory(new PropertyValueFactory<>(
                parsedResults.get(0).foreignAddressProperty().getName())
        );

        // Foreign Address Port
        TableColumn<Result, String> foreignAddressPortCol = new TableColumn<>("Foreign Address Port");
        foreignAddressPortCol.setCellValueFactory(new PropertyValueFactory<>(
                parsedResults.get(0).foreignAddressPortProperty().getName())
        );

        // State
        TableColumn<Result, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<>(parsedResults.get(0).stateProperty().getName()));

        // PID
        TableColumn<Result, String> pidCol = new TableColumn<>("PID");
        pidCol.setCellValueFactory(new PropertyValueFactory<>(parsedResults.get(0).pidProperty().getName()));

        tableView.getColumns().setAll(nameCol, pidCol, stateCol, protocolCol, localAddressCol, localAddressPortCol,
                foreignAddressCol, foreignAddressPortCol);
    }

    public void onRefresh(ActionEvent event) {
        try {
            refreshTable();
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
