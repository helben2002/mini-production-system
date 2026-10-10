package inventory_service;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InventoryControllerTest {

    private final InventoryController controller =
            new InventoryController();

    @Test
    void shouldReturnAvailableProduct() {

        Map<String, Object> result =
                controller.getInventory(101L);

        assertEquals(101L, result.get("productId"));
        assertEquals(25, result.get("quantity"));
        assertEquals(true, result.get("available"));
    }

    @Test
    void shouldReturnUnavailableProduct() {

        Map<String, Object> result =
                controller.getInventory(103L);

        assertEquals(0, result.get("quantity"));
        assertEquals(false, result.get("available"));
    }
}