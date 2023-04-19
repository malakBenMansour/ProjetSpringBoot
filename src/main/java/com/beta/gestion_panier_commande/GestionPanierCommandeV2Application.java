package com.beta.gestion_panier_commande;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import com.stripe.param.TokenCreateParams;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionPanierCommandeV2Application {

    public static void main(String[] args) {
        SpringApplication.run(GestionPanierCommandeV2Application.class, args);
        Stripe.apiKey = "sk_test_51MyFeLDEQW6yrUeCMVp7tRHTRLmXT8qlTbcqjhWsdIvd7QRlKxYPkP9vHW4v3unhXYEaipeZV8tCkUXgFuEyw0tJ00jbuEWC1U";
        TokenCreateParams params = TokenCreateParams.builder()
                .setCard(TokenCreateParams.Card.builder()
                        .setNumber("4242424242424242")
                        .setExpMonth(String.valueOf(12))
                        .setExpYear(String.valueOf(2024)) // This is a valid expiration year
                        .setCvc("123")
                        .build())
                .build();

        try {
            Token token = Token.create(params);
            System.out.println("Token ID: " + token.getId());
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}
