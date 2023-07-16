package com.renata.atelierehartesbackend.controller;

import com.renata.atelierehartesbackend.common.ApiResponse;
import com.renata.atelierehartesbackend.dto.checkout.StripeResponse;
import com.renata.atelierehartesbackend.dto.checkout.CheckoutItemDto;
import com.renata.atelierehartesbackend.exceptions.AuthenticationFailException;
import com.renata.atelierehartesbackend.exceptions.OrderNotFoundException;
import com.renata.atelierehartesbackend.model.Order;
import com.renata.atelierehartesbackend.model.User;
import com.renata.atelierehartesbackend.service.TokenService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.renata.atelierehartesbackend.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());

        return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
    }

    // place order after checkout
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("token") String token, @RequestParam("sessionId") String sessionId)
            throws AuthenticationFailException {
        // validate token
        tokenService.authenticate(token);
        // retrieve user
        User user = tokenService.getUser(token);
        // place the order
        orderService.placeOrder(user, sessionId);
        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }

    // get all orders
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("token") String token) throws AuthenticationFailException {
        // validate token
        tokenService.authenticate(token);
        // retrieve user
        User user = tokenService.getUser(token);
        // get orders
        List<Order> orderDtoList = orderService.listOrders(user);

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }


    // get orderitems for an order
    @GetMapping("/{id}")
    public ResponseEntity getOrderById(@PathVariable("id") Integer id, @RequestParam("token") String token) throws AuthenticationFailException {
        // validate token
        tokenService.authenticate(token);
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

//    @DeleteMapping("/remove/{id}")
//    public ResponseEntity removeOrder(@PathVariable("id") Integer id, @RequestParam("token") String token) throws AuthenticationFailException {
//        // validate token
//        tokenService.authenticate(token);
//        try {
//            Order order = orderService.getOrder(id);
//            return new ResponseEntity<>(order, HttpStatus.OK);
//        } catch (OrderNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
}