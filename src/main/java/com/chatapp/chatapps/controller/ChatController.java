package com.chatapp.chatapps.controller;

import java.time.LocalDateTime;

import com.chatapp.chatapps.domain.Chat;
import com.chatapp.chatapps.repository.ChatRepository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@RequiredArgsConstructor
@RestController //데이터 리턴
public class ChatController {
    
    private final ChatRepository chatRepository;


    // 귓속말할 때 사용
    @CrossOrigin
    @GetMapping(value = "/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE) //SSE 프로토콜, response 연결이 안끊어지고 계속 이어짐
    public Flux<Chat> getMsg(@PathVariable String sender, @PathVariable String receiver) {
        return chatRepository.mFindBySender(sender, receiver).subscribeOn(Schedulers.boundedElastic());
    }


    // 일반 채팅할 때 사용
    @CrossOrigin
    @GetMapping(value = "/chat/roomNum/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> findByRoomNum(@PathVariable Integer roomNum) {
        return chatRepository.mFindByRoomNum(roomNum)
                .subscribeOn(Schedulers.boundedElastic());
    }


    @CrossOrigin
    @PostMapping("/chat")
    public Mono<Chat> setMsg(@RequestBody Chat chat) {

        chat.setCreateAt(LocalDateTime.now());
        return chatRepository.save(chat); // Object를 반환하면 자동으로 json으로 반환해줌(MessageConverter)
        
    }


}
