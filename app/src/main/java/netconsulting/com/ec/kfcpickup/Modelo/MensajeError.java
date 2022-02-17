package netconsulting.com.ec.kfcpickup.Modelo;

import java.util.List;

public class MensajeError {
    private String code;
    private String status;
    private String message;
    private List<Warnig> warning;

    public void setWarning(List<Warnig> warning) {
        this.warning = warning;
    }

    public List<Warnig> getWarning() {
        return warning;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
