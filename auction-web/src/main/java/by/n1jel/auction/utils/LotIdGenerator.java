package by.n1jel.auction.utils;


import org.springframework.stereotype.Component;

@Component
public class LotIdGenerator {
    private Long id = 0L;

    public Long getId() {
        id++;
        return id;
    }
}
