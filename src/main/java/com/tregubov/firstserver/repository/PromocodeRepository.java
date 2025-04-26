package com.tregubov.firstserver.repository;

import com.tregubov.firstserver.entities.order.Promocode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PromocodeRepository extends JpaRepository<Promocode, Integer> {

    @Query(value = "SELECT COUNT(*) > 0 FROM used_promocode WHERE account_id = :accountId AND promocode_id = :promocodeId",
            nativeQuery = true)
    boolean isPromocodeUsedByAccount(@Param("accountId") UUID accountId,
                                     @Param("promocodeId") Integer promocodeId);

}
