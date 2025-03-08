package org.theleakycauldron.diagonalley.paymentservice.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.theleakycauldron.diagonalley.orderservice.entities.Order;
import org.theleakycauldron.diagonalley.paymentservice.exceptions.StripeSessionNotCreatedException;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class StripePaymentService implements DiagonAlleyPaymentService {
    


    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    @Override
    public String getPaymentLink(Order order) {
        try {
            Stripe.apiKey = stripeApiKey;

            // Create line items for the session
            List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
            
            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("inr")
                        .setUnitAmount(order.getBillAmount().longValue() * 100) // Convert to paise
                        .setProductData(
                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName("Order #" + order.getTrackingNumber())
                                .setDescription("Payment for order " + order.getTrackingNumber())
                                .build()
                        )
                        .build()
                )
                .setQuantity(1L)
                .build();
            
            lineItems.add(lineItem);

            // Create the checkout session
            SessionCreateParams params = SessionCreateParams.builder()
                    .setExpiresAt((System.currentTimeMillis() / 1000) + (30 * 60))
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl + "?orderId=" + order.getUuid() + "&amount=" + order.getBillAmount().longValue())
                .setCancelUrl(cancelUrl + "?orderId=" + order.getUuid())
                .addAllLineItem(lineItems)
                .setClientReferenceId(order.getTrackingNumber()) // To identify the order in webhooks
                .putMetadata("orderId", order.getTrackingNumber())
                .build();
            System.out.println(stripeApiKey);
            Session session = Session.create(params);
            return session.getUrl();
            
        } catch (Exception e) {
            throw new StripeSessionNotCreatedException("Error creating Stripe session", e);
        }
    }
    
}
