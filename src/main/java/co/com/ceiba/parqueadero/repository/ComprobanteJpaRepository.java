package co.com.ceiba.parqueadero.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.entity.VehiculoEntity;

@Repository("comprobanteJpaRepository")
public interface ComprobanteJpaRepository extends JpaRepository<ComprobantePagoEntity, Serializable>{
	
	
	@Query("SELECT t FROM ComprobantePagoEntity t WHERE t.placaFk = :placaFk")
	public abstract ComprobantePagoEntity findByPlaca(@Param("placaFk") String placaFk);
	
	@Query("SELECT t FROM ComprobantePagoEntity t WHERE t.idComprobantePago = :idComprobantePago")
	public abstract ComprobantePagoEntity findById(@Param("idComprobantePago") int idComprobantePago);
	
}
