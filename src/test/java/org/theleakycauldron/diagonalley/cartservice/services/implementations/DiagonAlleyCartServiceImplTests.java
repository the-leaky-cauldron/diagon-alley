package org.theleakycauldron.diagonalley.cartservice.services.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyAddItemToCartRequestDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyAddItemToCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyDeleteCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyGetCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyPaymentResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyRemoveItemFromCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.dtos.DiagonAlleyUpdateCartResponseDTO;
import org.theleakycauldron.diagonalley.cartservice.entities.Cart;
import org.theleakycauldron.diagonalley.cartservice.entities.CartItem;
import org.theleakycauldron.diagonalley.cartservice.exceptions.CartAlreadyEmptyException;
import org.theleakycauldron.diagonalley.cartservice.exceptions.InvalidCartToAddCartItemsException;
import org.theleakycauldron.diagonalley.cartservice.exceptions.NoCartFoundException;
import org.theleakycauldron.diagonalley.cartservice.exceptions.NoItemFoundInCartException;
import org.theleakycauldron.diagonalley.cartservice.repositories.DiagonAlleyRDBCartItemRepository;
import org.theleakycauldron.diagonalley.cartservice.repositories.DiagonAlleyRDBCartRepository;
import org.theleakycauldron.diagonalley.orderservice.entities.Order;
import org.theleakycauldron.diagonalley.orderservice.entities.OrderStatus;
import org.theleakycauldron.diagonalley.orderservice.services.DiagonAlleyOrderService;
import org.theleakycauldron.diagonalley.paymentservice.services.DiagonAlleyPaymentService;

class DiagonAlleyCartServiceImplTests {

    private DiagonAlleyRDBCartRepository cartRepository;

    private DiagonAlleyOrderService orderService;

    private DiagonAlleyPaymentService paymentService;

    private DiagonAlleyRDBCartItemRepository cartItemRepository;

    private DiagonAlleyCartServiceImpl service;

    @BeforeEach
    void setUp() {
        cartRepository = Mockito.mock(DiagonAlleyRDBCartRepository.class);
        orderService = Mockito.mock(DiagonAlleyOrderService.class);
        paymentService = Mockito.mock(DiagonAlleyPaymentService.class);
        cartItemRepository = Mockito.mock(DiagonAlleyRDBCartItemRepository.class);
        service = new DiagonAlleyCartServiceImpl(cartRepository, orderService, paymentService, cartItemRepository);
    }

    @Test
    void addItemToCart_appendsItemAndUpdatesTotal() {
        Cart cart = Cart.builder()
                .uuid(UUID.fromString("11111111-2222-3333-4444-555555555555"))
                .userId("user-1")
                .items(new ArrayList<>())
                .totalPrice(0.0)
                .build();
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        DiagonAlleyAddItemToCartRequestDTO request = new DiagonAlleyAddItemToCartRequestDTO("product-1", 2, 12.5);

        DiagonAlleyAddItemToCartResponseDTO response = service.addItemToCart("user-1", request);

        assertEquals("user-1", response.getUserId());
        assertEquals("product-1", response.getProductId());
        assertEquals(2, response.getQuantity());
        assertEquals(25.0, response.getTotalPrice());
        assertEquals(1, cart.getItems().size());
        assertEquals(25.0, cart.getTotalPrice());
        assertEquals(cart, cart.getItems().get(0).getCart());
        verify(cartRepository).save(cart);
    }

    @Test
    void addItemToCart_throwsWhenCartMissing() {
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.empty());

        assertThrows(InvalidCartToAddCartItemsException.class,
                () -> service.addItemToCart("user-1", new DiagonAlleyAddItemToCartRequestDTO("product-1", 1, 10.0)));
        verify(cartRepository, never()).save(any());
    }

    @Test
    void removeItemFromCart_removesItemAndReducesTotal() {
        CartItem cartItem = CartItem.builder().productId("product-1").quantity(2).unitPrice(15.0).build();
        Cart cart = Cart.builder()
                .uuid(UUID.fromString("22222222-3333-4444-5555-666666666666"))
                .userId("user-1")
                .items(new ArrayList<>(List.of(cartItem)))
                .totalPrice(30.0)
                .build();
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        DiagonAlleyRemoveItemFromCartResponseDTO response = service.removeItemFromCart("user-1", "product-1");

        assertEquals("product-1", response.getProductId());
        assertEquals(0, cart.getItems().size());
        assertEquals(0.0, cart.getTotalPrice());
        verify(cartRepository).save(cart);
    }

    @Test
    void removeItemFromCart_throwsWhenCartMissing() {
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.empty());

        assertThrows(NoCartFoundException.class, () -> service.removeItemFromCart("user-1", "product-1"));
    }

    @Test
    void updateItemQuantity_withZeroRemovesItem() {
        CartItem cartItem = CartItem.builder().productId("product-1").quantity(2).unitPrice(12.0).build();
        Cart cart = Cart.builder()
                .uuid(UUID.fromString("33333333-4444-5555-6666-777777777777"))
                .userId("user-1")
                .items(new ArrayList<>(List.of(cartItem)))
                .totalPrice(24.0)
                .build();
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        DiagonAlleyUpdateCartResponseDTO response = service.updateItemQuantity("user-1", "product-1", 0);

        assertEquals("product-1", response.getProductId());
        assertEquals(0, cart.getItems().size());
        assertEquals(0.0, cart.getTotalPrice());
        verify(cartItemRepository).delete(cartItem);
        verify(cartRepository).save(cart);
    }

    @Test
    void updateItemQuantity_withPositiveQuantityUpdatesItem() {
        CartItem cartItem = CartItem.builder().productId("product-1").quantity(2).unitPrice(12.0).build();
        Cart cart = Cart.builder()
                .uuid(UUID.fromString("44444444-5555-6666-7777-888888888888"))
                .userId("user-1")
                .items(new ArrayList<>(List.of(cartItem)))
                .totalPrice(24.0)
                .build();
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        DiagonAlleyUpdateCartResponseDTO response = service.updateItemQuantity("user-1", "product-1", 3);

        assertEquals("product-1", response.getProductId());
        assertEquals(3, cartItem.getQuantity());
        assertEquals(36.0, cart.getTotalPrice());
        verify(cartItemRepository, never()).delete(any());
        verify(cartRepository).save(cart);
    }

    @Test
    void updateItemQuantity_throwsWhenCartMissing() {
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.empty());

        assertThrows(NoCartFoundException.class, () -> service.updateItemQuantity("user-1", "product-1", 1));
    }

    @Test
    void updateItemQuantity_throwsWhenItemMissing() {
        Cart cart = Cart.builder()
                .uuid(UUID.fromString("55555555-6666-7777-8888-999999999999"))
                .userId("user-1")
                .items(new ArrayList<>())
                .totalPrice(0.0)
                .build();
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.of(cart));

        assertThrows(NoItemFoundInCartException.class, () -> service.updateItemQuantity("user-1", "product-1", 1));
    }

    @Test
    void getCart_createsCartWhenMissing() {
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DiagonAlleyGetCartResponseDTO response = service.getCart("user-1");

        assertEquals("user-1", response.getUserId());
        assertEquals(0.0, response.getTotalPrice());
        assertEquals(0, response.getProducts().size());
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void clearCart_deletesCartWhenPresent() {
        Cart cart = Cart.builder()
                .uuid(UUID.fromString("66666666-7777-8888-9999-aaaaaaaaaaaa"))
                .userId("user-1")
                .items(new ArrayList<>())
                .build();
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.of(cart));

        DiagonAlleyDeleteCartResponseDTO response = service.clearCart("user-1");

        assertEquals("user-1", response.getUserId());
        assertEquals("66666666-7777-8888-9999-aaaaaaaaaaaa", response.getCartId());
        verify(cartRepository).delete(cart);
    }

    @Test
    void clearCart_throwsWhenCartMissing() {
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.empty());

        assertThrows(CartAlreadyEmptyException.class, () -> service.clearCart("user-1"));
    }

    @Test
    void checkout_createsOrderDeletesCartAndReturnsPaymentLink() {
        Cart cart = Cart.builder()
                .uuid(UUID.fromString("77777777-8888-9999-aaaa-bbbbbbbbbbbb"))
                .userId("user-1")
                .items(new ArrayList<>(List.of(
                        CartItem.builder().productId("product-1").quantity(2).unitPrice(15.0).build(),
                        CartItem.builder().productId("product-2").quantity(1).unitPrice(20.0).build()
                )))
                .totalPrice(50.0)
                .build();
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.of(cart));
        when(paymentService.getPaymentLink(any(Order.class))).thenReturn("https://stripe.test/session");

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);

        DiagonAlleyPaymentResponseDTO response = service.checkout("user-1");

        verify(orderService).createOrder(orderCaptor.capture());
        verify(cartRepository).delete(cart);
        assertEquals("https://stripe.test/session", response.getPaymentLink());

        Order order = orderCaptor.getValue();
        assertEquals("user-1", order.getUserId());
        assertEquals(OrderStatus.CREATED, order.getOrderStatus());
        assertEquals(50.0, order.getBillAmount());
        assertEquals(2, order.getOrderItems().size());
        assertEquals("product-1", order.getOrderItems().get(0).getProductId());
        assertNotNull(order.getTrackingNumber());
        assertFalse(order.isDeleted());
    }

    @Test
    void checkout_throwsWhenCartMissing() {
        when(cartRepository.findCartByUserId("user-1")).thenReturn(Optional.empty());

        assertThrows(NoCartFoundException.class, () -> service.checkout("user-1"));
    }
}
