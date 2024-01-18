package ge.tsu.jnetstat.parser;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Result {

    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final StringProperty protocol = new SimpleStringProperty(this, "protocol");
    private final StringProperty localAddress = new SimpleStringProperty(this, "localAddress");
    private final IntegerProperty localAddressPort = new SimpleIntegerProperty(this, "localAddressPort");
    private final StringProperty foreignAddress = new SimpleStringProperty(this, "foreignAddress");
    private final IntegerProperty foreignAddressPort = new SimpleIntegerProperty(this, "foreignAddressPort");
    private final StringProperty state = new SimpleStringProperty(this, "state");
    private final IntegerProperty pid = new SimpleIntegerProperty(this, "pid");

    public Result(String name, String protocol, String localAddress, int localAddressPort,
                  String foreignAddress, int foreignAddressPort, String state, int pid) {
        setName(name);
        setProtocol(protocol);
        setLocalAddress(localAddress);
        setLocalAddressPort(localAddressPort);
        setForeignAddress(foreignAddress);
        setForeignAddressPort(foreignAddressPort);
        setState(state);
        setPid(pid);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getProtocol() {
        return protocol.get();
    }

    public StringProperty protocolProperty() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol.set(protocol);
    }

    public String getLocalAddress() {
        return localAddress.get();
    }

    public StringProperty localAddressProperty() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress.set(localAddress);
    }

    public int getLocalAddressPort() {
        return localAddressPort.get();
    }

    public IntegerProperty localAddressPortProperty() {
        return localAddressPort;
    }

    public void setLocalAddressPort(int localAddressPort) {
        this.localAddressPort.set(localAddressPort);
    }

    public String getForeignAddress() {
        return foreignAddress.get();
    }

    public StringProperty foreignAddressProperty() {
        return foreignAddress;
    }

    public void setForeignAddress(String foreignAddress) {
        this.foreignAddress.set(foreignAddress);
    }

    public int getForeignAddressPort() {
        return foreignAddressPort.get();
    }

    public IntegerProperty foreignAddressPortProperty() {
        return foreignAddressPort;
    }

    public void setForeignAddressPort(int foreignAddressPort) {
        this.foreignAddressPort.set(foreignAddressPort);
    }

    public String getState() {
        return state.get();
    }

    public StringProperty stateProperty() {
        return state;
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public int getPid() {
        return pid.get();
    }

    public IntegerProperty pidProperty() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid.set(pid);
    }
}
