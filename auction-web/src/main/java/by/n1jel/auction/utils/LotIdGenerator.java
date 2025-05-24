package by.n1jel.auction.utils;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
public class LotIdGenerator {
    private Long id = 0L;

    public Long getId() {
        id++;
        return id;
    }
}
