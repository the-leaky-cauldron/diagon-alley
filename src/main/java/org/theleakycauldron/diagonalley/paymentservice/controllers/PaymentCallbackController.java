package org.theleakycauldron.diagonalley.paymentservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.theleakycauldron.diagonalley.orderservice.entities.OrderStatus;
import org.theleakycauldron.diagonalley.orderservice.services.DiagonAlleyOrderService;
import org.theleakycauldron.diagonalley.paymentservice.dtos.PaymentFailureResponseDTO;
import org.theleakycauldron.diagonalley.paymentservice.dtos.PaymentSuccessResponseDTO;

@RestController
@RequestMapping("/payment")
public class PaymentCallbackController {

    private final DiagonAlleyOrderService orderService;

    public PaymentCallbackController(
            DiagonAlleyOrderService orderService
    ) {
        this.orderService = orderService;
    }

    @GetMapping("/success")
    public ResponseEntity<PaymentSuccessResponseDTO> paymentSuccess(@RequestParam("orderId") String orderId, @RequestParam("amount") long amount) {

        String message = "🎉 Payment Successful! Your order #" + orderId + " has been confirmed. Amount Paid: ₹" + amount +".";

        orderService.updateOrderStatus(orderId, OrderStatus.PAYMENT_COMPLETED);

        PaymentSuccessResponseDTO  response = PaymentSuccessResponseDTO.builder()
                .message(message)
                .build();
        return ResponseEntity.ok(response);

    }

    @GetMapping("/failure")
    public ResponseEntity<PaymentFailureResponseDTO> paymentFailure(@RequestParam("orderId") String orderId) {

        String message = "❌ Payment for Order #" + orderId + " was unsuccessful. Please check your payment details and try again";

        orderService.updateOrderStatus(orderId, OrderStatus.PAYMENT_PENDING);

        PaymentFailureResponseDTO  response = PaymentFailureResponseDTO.builder()
                .message(message)
                .build();
        return ResponseEntity.ok(response);

    }

    
}
