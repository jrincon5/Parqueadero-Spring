package co.com.ceiba.parqueadero.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;
import co.com.ceiba.parqueadero.entity.VehiculoEntity;

@Repository("comprobanteRepository")
public interface ComprobanteRepository extends JpaRepository<ComprobantePagoEntity, Serializable>{
		
	@Query("SELECT TOP 1 t FROM ComprobantePagoEntity t DESC WHERE t.placaFk = :placaFk")
	public abstract ComprobantePagoEntity findByPlaca(@Param("placaFk") VehiculoEntity placaFk);	
}
