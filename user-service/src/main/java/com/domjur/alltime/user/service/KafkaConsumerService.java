package com.domjur.alltime.user.service;

import com.domjur.alltime.user.entity.UserResponse;
import com.domjur.alltime.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @Autowired
    UserRepository userRepository;

    @KafkaListener(topics = "${message.topic}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void addNewUser(UserResponse userResponse) {
        System.out.println("Received Message: " + userResponse);
        userRepository.save(userResponse);
    }
}
