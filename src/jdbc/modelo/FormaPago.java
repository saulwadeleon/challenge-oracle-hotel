package jdbc.modelo;

public class FormaPago {
    private int idFormaPago;
    private String descripcionFormaPago;

    // Constructor
    public FormaPago(int idFormaPago, String descripcionFormaPago) {
        this.idFormaPago = idFormaPago;
        this.descripcionFormaPago = descripcionFormaPago;
    }

    // Getters y Setters
    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public String getDescripcionFormaPago() {
        return descripcionFormaPago;
    }

    public void setDescripcionFormaPago(String descripcionFormaPago) {
        this.descripcionFormaPago = descripcionFormaPago;
    }
}
