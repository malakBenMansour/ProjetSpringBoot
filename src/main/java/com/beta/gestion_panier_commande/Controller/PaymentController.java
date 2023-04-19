package com.beta.gestion_panier_commande.Controller;

import com.beta.gestion_panier_commande.Model.Commande;
import com.beta.gestion_panier_commande.Model.Order;
import com.beta.gestion_panier_commande.Service.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.stripe.*;

import java.util.HashMap;
import java.util.Map;

@RestController



public class PaymentController {

@Autowired
    private PayPalService service;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @GetMapping("/")
    public String home() {
        return "home";
    }

/*
    @PostMapping("/pay")
    public String payment(@ModelAttribute("order") Order order) {
        try {
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),order.getIntent(), order.getDescription(), "http://localhost:8080/" + CANCEL_URL,"http://localhost:8080/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

    */

/*
JSON :


{
  "order": {
    "price": 10.99,
    "currency": "USD",
    "description": "Example Order",
    "stripeToken": "tok_visa"
  }
}


 */
    @PostMapping("/pay2")
    public String payment(@ModelAttribute("order") Order order, Model model) {
        Stripe.apiKey = "sk_test_YourTestSecretKey";

        Map<String, Object> params = new HashMap<>();
        params.put("amount", order.getPrice() * 100);
        params.put("currency", order.getCurrency());
        params.put("description", order.getDescription());
        params.put("source", order.getStripeToken());

        try {
            Charge charge = Charge.create(params);
            model.addAttribute("chargeId", charge.getId());
            return "success";
        } catch (StripeException e) {
            model.addAttribute("error", e.getMessage());
            return "failure";
        }
    }


}
