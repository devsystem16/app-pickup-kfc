package netconsulting.com.ec.kfcpickup.Modelo;

import java.util.Date;

public class Orden {
    public String _id;
    public String state;
    public String stateMessage;
    public String stateTechnicalMessage;
    public String codigo_app;
    public Request request;
    public Date updated_at;
    public Date created_at;
    public ResponseData responseData;
    public String clase_css_estado;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateMessage() {
        return stateMessage;
    }

    public void setStateMessage(String stateMessage) {
        this.stateMessage = stateMessage;
    }

    public String getStateTechnicalMessage() {
        return stateTechnicalMessage;
    }

    public void setStateTechnicalMessage(String stateTechnicalMessage) {
        this.stateTechnicalMessage = stateTechnicalMessage;
    }

    public String getCodigo_app() {
        return codigo_app;
    }

    public void setCodigo_app(String codigo_app) {
        this.codigo_app = codigo_app;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public String getClase_css_estado() {
        return clase_css_estado;
    }

    public void setClase_css_estado(String clase_css_estado) {
        this.clase_css_estado = clase_css_estado;
    }
}
