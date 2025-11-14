package com.cipherops.NotifyService.Controller;

import com.cipherops.NotifyService.Model.Notification;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/sse")
public class NotificationSseController {

    // userId → SseEmitter
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping("/stream/{userId}")
    public SseEmitter connect(@PathVariable String userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));

        try {
            emitter.send(SseEmitter.event()
                    .name("INIT")
                    .data("Connected as " + userId + " at " + Instant.now()));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    public void sendToUser(String userId, Notification notification) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(notification));
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(userId);
            }
        }
    }
}

