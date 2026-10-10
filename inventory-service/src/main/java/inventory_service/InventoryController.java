package inventory_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    private final Map<Long, Integer> stock = Map.of(
            101L, 25,
            102L, 10,
            103L, 0);

    @GetMapping("/{productId}")
    public Map<String, Object> getInventory(@PathVariable Long productId) {

        log.info("Received inventory request for productId={}", productId);

        int quantity = stock.getOrDefault(productId, 0);

        if (quantity == 0) {
            log.warn("Product {} is out of stock or unknown", productId);
        } else {
            log.info("Product {} has quantity={}", productId, quantity);
        }

        return Map.of(
                "productId", productId,
                "quantity", quantity,
                "available", quantity > 0);
    }
}