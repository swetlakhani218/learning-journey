package com.cipherops.NotifyService.Repository;

import com.cipherops.NotifyService.Model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByAssigneeIdAndStatus(String assigneeId, String status);

    List<Notification> findByAssigneeIdOrderByCreatedAtDesc(String assigneeId);
}








//
//import com.cipherops.NotifyService.Model.Notification;
//import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
//import reactor.core.publisher.Flux;
//
//public interface NotificationRepository extends ReactiveMongoRepository<Notification, String> {
//    Flux<Notification> findByUserIdOrderByCreatedAtDesc(String userId);
//}