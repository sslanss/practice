import org.example.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ProductTest {
    @Test
    @DisplayName("Корректный товар создаётся без ошибок")
    void validProduct_createsSuccessfully() {
        Product p = new Product("1", "Телефон", 15000.0);
        assertEquals("1", p.getId());
        assertEquals("Телефон", p.getName());
        assertEquals(15000.0, p.getPrice());
    }
    @Test
    @DisplayName("Отрицательная цена → IllegalArgumentException")
    void negativePrice_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Product("1", "Товар", -1.0));
    }
    @Test
    @DisplayName("Нулевая цена допустима (бесплатный товар)")
    void zeroPriceIsAllowed() {
        assertDoesNotThrow(() -> new Product("1", "Бонус", 0.0));
    }
}