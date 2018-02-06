package co.com.ceiba.parqueadero.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.parqueadero.entity.ComprobantePagoEntity;

@Repository("comprobanteJpaRepository")
public interface ComprobanteJpaRepository extends JpaRepository<ComprobantePagoEntity, Serializable>{

}
