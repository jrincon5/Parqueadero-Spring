package co.com.ceiba.parqueadero.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.entity.VehiculoEntity;

@Repository("comprobanteRepository")
public interface ComprobanteRepository extends JpaRepository<ComprobantePagoEntity, Serializable>{
		
	@Query(value = "SELECT * FROM parqueadero.comprobante_pago WHERE placa_fk = ?1 ORDER BY id_comprobante_pago DESC LIMIT 1", nativeQuery = true)
	public abstract ComprobantePagoEntity findByPlaca(VehiculoEntity placaFk);	
}
