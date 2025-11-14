package com.cipherops.OutboundRelayService.Repository;

import com.cipherops.OutboundRelayService.Model.universalCase.UniversalCaseNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversalCaseNoteRepository extends MongoRepository<UniversalCaseNote, String> {
    List<UniversalCaseNote> findTop100ByOutboxStatusOrderByOutboxCreatedAtAsc(String status);
}

