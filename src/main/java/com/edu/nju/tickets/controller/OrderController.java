package com.edu.nju.tickets.controller;

import com.edu.nju.tickets.exception.TicketException.TicketAlreadyCheckedException;
import com.edu.nju.tickets.service.OrderService;
import com.edu.nju.tickets.vo.Order.OrderInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/reserve", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> reserve(@RequestBody OrderInfoVO vo) {
        try{
            String orderToken = orderService.reserve(vo);
            return new ResponseEntity<>(orderToken,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/payment", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> payment(@RequestParam String orderToken,
                                          @RequestParam String couponToken) {
        try {
            orderService.payment(orderToken,couponToken);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/cancel", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> cancel(@RequestParam String orderToken) {
        try {
            orderService.cancelOrder(orderToken);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/debook", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> debook(@RequestParam String orderToken) {
        try {
            orderService.debook(orderToken);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/ticket/check", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> checkTicket(@RequestParam String ticketToken) {
        try {
            orderService.checkTicket(ticketToken);
            return new ResponseEntity<>("success",HttpStatus.OK);
        } catch (TicketAlreadyCheckedException e) {
            return new ResponseEntity<>("fail",HttpStatus.OK);
        }
    }

    @GetMapping(value = "",  produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<OrderInfoVO> findOrderByToken(@RequestParam String orderToken) {
        try {
            OrderInfoVO vo = orderService.findOrderByToken(orderToken);
            return new ResponseEntity<>(vo,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<List<OrderInfoVO>> findOrdersByUser(@RequestParam String username) {
        try {
            List<OrderInfoVO> list = orderService.findOrdersByUser(username);
            return new ResponseEntity<>(list,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/booked", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<List<OrderInfoVO>> findBookedOrdersByUser(@RequestParam String username) {
        try {
            List<OrderInfoVO> list = orderService.findBookedOrdersByUser(username);
            return new ResponseEntity<>(list,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/cancelled", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<List<OrderInfoVO>> findCancelledOrdersByUser(@RequestParam String username) {
        try {
            List<OrderInfoVO> list = orderService.findCancelledOrdersByUser(username);
            return new ResponseEntity<>(list,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/completed", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<List<OrderInfoVO>> findCompletedOrdersByUser(@RequestParam String username) {
        try {
            List<OrderInfoVO> list = orderService.findCompletedOrdersByUser(username);
            return new ResponseEntity<>(list,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/debook/amount", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Double> getDebookAmount(@RequestParam String orderToken){
        try{
            double amount = orderService.getDebookAmount(orderToken);
            return new ResponseEntity<>(amount,HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/locale")
    @ResponseBody
    public ResponseEntity<String> localeBook(@RequestParam String orderToken) {
        orderService.localeBook(orderToken);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }
}
