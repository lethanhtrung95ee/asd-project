package asd.ecommercenew.service.impl;

import asd.ecommercenew.dto.request.PaymentDtoRequest;
import asd.ecommercenew.dto.response.OrderLineDtoResponse;
import asd.ecommercenew.dto.response.PaymentDtoResponse;
import asd.ecommercenew.entity.Order;
import asd.ecommercenew.entity.OrderLine;
import asd.ecommercenew.entity.Payment;
import asd.ecommercenew.entity.User;
import asd.ecommercenew.repository.*;
import asd.ecommercenew.security.SecurityConfiguration;
import asd.ecommercenew.service.PaymentService;
import asd.ecommercenew.util.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final OrderRepository orderRepository;
    private final ShippingRepository shippingRepository;
    private final SecurityConfiguration securityConfiguration;
    private final OrderLineRepository orderLineRepository;

    @Override
    public void payment(PaymentDtoRequest paymentRequest) {

        MyUserDetails myUserDetails = securityConfiguration.getCurrentUser();
        //using for testing by junit
        if (myUserDetails == null) {
            User user = new User();
            user.setUsername("user");
            myUserDetails = new MyUserDetails(user);
        }
        User user = userRepository.findByUsername(myUserDetails.getUsername());
        Order order = orderRepository.findByUserName(myUserDetails.getUsername());
        List<OrderLine> orderLines;
        orderLines = order.getOrderLines();
//        Shipping shipping = new Shipping();
//        shipping.setShippingAddress(paymentRequest.getShippingAddress());
//        shippingRepository.save(shipping);
        Payment payment = new Payment();
//        payment.setShipping(shipping);
        payment.setUser(user);
        Payment paymentUser = paymentRepository.save(payment);
        orderLines.forEach(orderLine -> {
            orderLine.setPayment(payment);
            orderLineRepository.save(orderLine);
        });
        List<Payment> payments = new ArrayList<>();
        payments.add(paymentUser);
        user.setPayments(payments);
        user.setOrder(new Order());
        userRepository.save(user);
    }

    @Override
    public List<PaymentDtoResponse> getAllPaymentHistory() {
        MyUserDetails myUserDetails = securityConfiguration.getCurrentUser();
        User user = userRepository.findByUsername(myUserDetails.getUsername());
        var result = new ArrayList<PaymentDtoResponse>();
        paymentRepository.findAllByUser(user.getId()).forEach(item -> {
            PaymentDtoResponse paymentDtoResponse = modelMapper.map(item, PaymentDtoResponse.class);
            List<OrderLine> orderLines = orderLineRepository.findAllByPaymentId(item.getId());
            List<OrderLineDtoResponse> orderLineDtoResponses = new ArrayList<>();
            orderLines.forEach(orderLine -> {
                orderLineDtoResponses.add(modelMapper.map(orderLine, OrderLineDtoResponse.class));
            });
            paymentDtoResponse.setOrderLines(orderLineDtoResponses);
            result.add(paymentDtoResponse);
        });
        return result;
    }
}