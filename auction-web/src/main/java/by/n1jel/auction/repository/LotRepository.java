package by.n1jel.auction.repository;

import by.n1jel.auction.entity.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LotRepository extends JpaRepository<Lot, Long> {
    Optional<Lot> findLotById(Long id);
}
