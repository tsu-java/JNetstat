module ge.tsu.jnetstat {
    requires javafx.controls;
    requires javafx.fxml;

    opens ge.tsu.jnetstat to javafx.fxml;
    exports ge.tsu.jnetstat;
    exports ge.tsu.jnetstat.parser;
    opens ge.tsu.jnetstat.parser to javafx.fxml;
}