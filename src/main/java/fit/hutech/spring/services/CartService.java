package fit.hutech.spring.services;
import fit.hutech.spring.daos.Cart;
import fit.hutech.spring.daos.Item;
import fit.hutech.spring.entities.Invoice;
import fit.hutech.spring.entities.ItemInvoice;
import fit.hutech.spring.repositories.IBookRepository;
import fit.hutech.spring.repositories.IInvoiceRepository;
import fit.hutech.spring.repositories.IItemInvoiceRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.SERIALIZABLE,
        rollbackFor = {Exception.class, Throwable.class})
public class CartService {
    private static final String CART_SESSION_KEY = "cart";
    private final IInvoiceRepository invoiceRepository;
    private final IItemInvoiceRepository itemInvoiceRepository;
    private final IBookRepository bookRepository;
    
    public Cart getCart(@NotNull HttpSession session) {
        return Optional.ofNullable((Cart) session.getAttribute(CART_SESSION_KEY))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    session.setAttribute(CART_SESSION_KEY, cart);
                    log.debug("New cart created for session: {}", session.getId());
                    return cart;
                });
    }
    
    public void updateCart(@NotNull HttpSession session, Cart cart) {
        session.setAttribute(CART_SESSION_KEY, cart);
        log.debug("Cart updated for session: {}", session.getId());
    }
    
    public void removeCart(@NotNull HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
        log.debug("Cart removed for session: {}", session.getId());
    }
    
    public int getSumQuantity(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToInt(Item::getQuantity)
                .sum();
    }
    
    public double getSumPrice(@NotNull HttpSession session) {
        return getCart(session).getCartItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
    
    public void saveCart(@NotNull HttpSession session) {
        var cart = getCart(session);
        if (cart.getCartItems().isEmpty()) {
            log.warn("Attempted to save empty cart for session: {}", session.getId());
            return;
        }
        
        var invoice = new Invoice();
        invoice.setInvoiceDate(new Date());
        invoice.setPrice(getSumPrice(session));
        invoiceRepository.save(invoice);
        log.info("Invoice created with id: {}, totalPrice: {}", invoice.getId(), invoice.getPrice());
        
        cart.getCartItems().forEach(item -> {
            bookRepository.findById(item.getBookId())
                    .ifPresentOrElse(
                            book -> {
                                var itemInvoice = new ItemInvoice();
                                itemInvoice.setInvoice(invoice);
                                itemInvoice.setQuantity(item.getQuantity());
                                itemInvoice.setBook(book);
                                itemInvoiceRepository.save(itemInvoice);
                            },
                            () -> log.warn("Book not found for item: id={}", item.getBookId())
                    );
        });
        
        removeCart(session);
        log.info("Cart saved and cleared for session: {}", session.getId());
    }
}