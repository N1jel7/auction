package by.n1jel.auction.repository;

import by.n1jel.auction.entity.Trader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TraderRepository extends JpaRepository<Trader, Long> {

    Optional<Trader> findTraderById(Long id);

}
