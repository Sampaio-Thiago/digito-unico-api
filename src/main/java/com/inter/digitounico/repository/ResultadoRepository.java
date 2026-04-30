package com.inter.digitounico.repository;

import com.inter.digitounico.entity.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    @Query("SELECT r FROM Resultado r WHERE r.usuario.id = :usuarioId ORDER BY r.dataCalculo DESC")
    List<Resultado> findByUsuarioIdOrderByDataCalculoDesc(@Param("usuarioId") Long usuarioId);
}
