package com.chatapp.chatapps.repository;

import com.chatapp.chatapps.domain.Chat;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
    
    @Tailable // 데이터가 계속 흘러가게 하는 것
    @Query("{sender:?0, receiver:?1}") // mongodb에서 sender가 sender일 때 receiver가 receiver를 찾는 것
    Flux<Chat> mfindBySender(String sender, String receiver); // Flux(데이터의 흐름)
}
