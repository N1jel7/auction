package by.n1jel.auction.repository;

import by.n1jel.auction.entity.Lot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Long> {


    Optional<Lot> findLotById(Long id);

    Page<Lot> findLotsBySoldTrue(Pageable pageable);

    List<Lot> findLotsByType(String type);

    List<Lot> findLotsByNameContaining(String name);

    List<Lot> findLotsBySoldTrueAndPriceIsBetween(BigDecimal priceAfter, BigDecimal priceBefore);

    List<Lot> findLotsBySoldFalseAndPriceIsBetween(BigDecimal priceAfter, BigDecimal priceBefore);

    Page<Lot> findLotsBySoldFalse(Pageable pageable);

    @Query("""
            SELECT l FROM Lot l WHERE l.soldAt >= :from AND l.soldAt <= :to
            """)
    List<Lot> findLotsInPeriod(LocalDateTime from, LocalDateTime to);

}
