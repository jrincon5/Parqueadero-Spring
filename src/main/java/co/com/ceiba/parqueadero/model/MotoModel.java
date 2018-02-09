package co.com.ceiba.parqueadero.model;

public class MotoModel extends VehiculoModel{
	
	private static final String TIPOVEHICULO="Moto";
	private int cilindraje;

	public MotoModel(String placa, boolean parqueado, int cilindraje) {
		super(placa, parqueado);
		this.cilindraje = cilindraje;
	}

	@Override
	public String getTipoVehiculo() {
		return TIPOVEHICULO;
	}

	public int getCilindraje() {
		return cilindraje;
	}

	public MotoModel() {
		super();
	}

	public MotoModel(String placa, boolean parqueado) {
		super(placa, parqueado);
	}
}