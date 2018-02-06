package co.com.ceiba.parqueadero.model;

public class MotoModel extends VehiculoModel{
	
	private static String TIPOVEHICULO="Moto";
	private int cilindraje;

	public MotoModel(String placa, boolean parqueado, int cilindraje) {
		super(placa, parqueado);
		this.cilindraje = cilindraje;
	}

	public MotoModel() {
		super();
	}

	@Override
	public String getTipoVehiculo() {
		return TIPOVEHICULO;
	}

	public int getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}	
}