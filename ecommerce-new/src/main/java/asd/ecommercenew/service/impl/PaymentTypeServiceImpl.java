package asd.ecommercenew.service.impl;

import asd.ecommercenew.dto.request.PaymentTypeDtoRequest;
import asd.ecommercenew.entity.PaymentType;
import asd.ecommercenew.entity.User;
import asd.ecommercenew.repository.PaymentTypeRepository;
import asd.ecommercenew.repository.UserRepository;
import asd.ecommercenew.security.SecurityConfiguration;
import asd.ecommercenew.service.PaymentTypeService;
import asd.ecommercenew.util.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final SecurityConfiguration securityConfiguration;

    @Override
    public void save(PaymentTypeDtoRequest paymentTypeDtoRequest) {
        MyUserDetails myUserDetails = securityConfiguration.getCurrentUser();
        User user = userRepository.findByUsername(myUserDetails.getUsername());
        PaymentType paymentType = modelMapper.map(paymentTypeDtoRequest, PaymentType.class);
        paymentType.setUser(user);
        paymentTypeRepository.save(paymentType);
    }
}