package netconsulting.com.ec.kfcpickup.Modelo;

public class ResponseData {
    public String estado;
    public String codigo;
    public String msg;
    public Data data;
    public java.util.List<Object> error;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public java.util.List<Object> getError() {
        return error;
    }

    public void setError(java.util.List<Object> error) {
        this.error = error;
    }
}
