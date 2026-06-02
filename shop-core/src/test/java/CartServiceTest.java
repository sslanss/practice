import org.example.Cart;
import org.example.CartService;
import org.example.Product;
import org.example.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private ProductRepository repository;
    @InjectMocks
    private CartService service;
    private Cart cart;
    private Product laptop;
    @BeforeEach
    void setUp() {
        cart   = new Cart();
        laptop = new Product("1", "Ноутбук", 75000.0);
    }
    @Test
    @DisplayName("addToCart: товар найден → добавляется в корзину")
    void addToCart_productFound_addsToCart() {
        when(repository.findById("1")).thenReturn(Optional.of(laptop));
        service.addToCart("1", cart);
        assertEquals(1, cart.getItems().size());
        assertEquals("Ноутбук", cart.getItems().get(0).getName());
        verify(repository, times(1)).findById("1");
    }
    @Test
    @DisplayName("addToCart: товар не найден → IllegalArgumentException")
    void addToCart_productNotFound_throwsException() {
        when(repository.findById("99")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,
                () -> service.addToCart("99", cart));
        assertTrue(cart.isEmpty());
        verify(repository).findById("99");
    }
    @Test
    @DisplayName("addToCart: репозиторий вызывается ровно один раз")
    void addToCart_repositoryCalledOnce() {
        when(repository.findById("1")).thenReturn(Optional.of(laptop));
        service.addToCart("1", cart);
        verify(repository, times(1)).findById("1");
        verifyNoMoreInteractions(repository);
    }
    @Test
    @DisplayName("applyDiscount: 10% скидка считается правильно")
    void applyDiscount_tenPercent_correctResult() {
        cart.addItem(laptop);
        double result = service.applyDiscount(cart, 10);
        assertEquals(67500.0, result, 0.001);
    }
    @Test
    @DisplayName("applyDiscount: 0% ничего не меняет")
    void applyDiscount_zeroPercent_unchangedTotal() {
        cart.addItem(laptop);
        assertEquals(75000.0, service.applyDiscount(cart, 0), 0.001);
    }
    @Test
    @DisplayName("applyDiscount: 100% даёт ноль")
    void applyDiscount_hundredPercent_zero() {
        cart.addItem(laptop);
        assertEquals(0.0, service.applyDiscount(cart, 100), 0.001);
    }
    @Test
    @DisplayName("applyDiscount: отрицательная скидка → IllegalArgumentException")
    void applyDiscount_negativePercent_throwsException() {
        cart.addItem(laptop);
        assertThrows(IllegalArgumentException.class,
                () -> service.applyDiscount(cart, -5));
    }
    @Test
    @DisplayName("applyDiscount: скидка > 100 → IllegalArgumentException")
    void applyDiscount_over100_throwsException() {
        cart.addItem(laptop);
        assertThrows(IllegalArgumentException.class,
                () -> service.applyDiscount(cart, 101));
    }
    @Test
    @DisplayName("checkout: возвращает сумму и очищает корзину")
    void checkout_returnsTotalAndClearsCart() {
        cart.addItem(laptop);
        double total = service.checkout(cart);
        assertEquals(75000.0, total, 0.001);
        assertTrue(cart.isEmpty());
    }
    @Test
    @DisplayName("checkout: пустая корзина → IllegalStateException")
    void checkout_emptyCart_throwsException() {
        assertThrows(IllegalStateException.class,
                () -> service.checkout(cart));
    }
    @Test
    @DisplayName("applyDiscount и checkout не обращаются к репозиторию")
    void discountAndCheckout_doNotUseRepository() {
        cart.addItem(laptop);
        service.applyDiscount(cart, 20);
        service.checkout(cart);
        verifyNoInteractions(repository);
    }
}