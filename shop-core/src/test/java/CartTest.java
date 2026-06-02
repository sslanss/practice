
import org.example.Cart;
import org.example.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CartTest {
    private Cart cart;
    private Product apple;
    private Product bread;
    @BeforeEach
    void setUp() {
        cart  = new Cart();
        apple = new Product("1", "Яблоко", 50.0);
        bread = new Product("2", "Хлеб",   30.0);
    }
    @Test
    @DisplayName("Новая корзина пуста")
    void newCartIsEmpty() {
        assertTrue(cart.isEmpty());
        assertEquals(0.0, cart.getTotalPrice());
    }
    @Test
    @DisplayName("Добавление товара увеличивает список и сумму")
    void addItemIncreasesListAndTotal() {
        cart.addItem(apple);
        assertEquals(1, cart.getItems().size());
        assertEquals(50.0, cart.getTotalPrice());
    }
    @Test
    @DisplayName("Сумма нескольких товаров считается правильно")
    void totalPriceIsCorrect() {
        cart.addItem(apple);
        cart.addItem(bread);
        assertEquals(80.0, cart.getTotalPrice(), 0.001);
    }
    @Test
    @DisplayName("Удаление товара по ID работает")
    void removeItemById() {
        cart.addItem(apple);
        cart.addItem(bread);
        boolean removed = cart.removeItem("1");
        assertTrue(removed);
        assertEquals(1, cart.getItems().size());
        assertEquals(30.0, cart.getTotalPrice());
    }
    @Test
    @DisplayName("Удаление несуществующего ID возвращает false")
    void removeNonExistentItemReturnsFalse() {
        cart.addItem(apple);
        assertFalse(cart.removeItem("999"));
    }
    @Test
    @DisplayName("clear() очищает корзину")
    void clearEmptiesCart() {
        cart.addItem(apple);
        cart.clear();
        assertTrue(cart.isEmpty());
        assertEquals(0.0, cart.getTotalPrice());
    }
    @Test
    @DisplayName("addItem(null) бросает исключение")
    void addNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(null));
    }
    @Test
    @DisplayName("getItems() возвращает неизменяемый список")
    void getItemsReturnsUnmodifiableList() {
        cart.addItem(apple);
        assertThrows(UnsupportedOperationException.class,
                () -> cart.getItems().add(bread));
    }
}