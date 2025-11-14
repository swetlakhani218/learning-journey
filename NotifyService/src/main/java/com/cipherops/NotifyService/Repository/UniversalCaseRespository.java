package com.cipherops.NotifyService.Repository;

import com.cipherops.NotifyService.Model.universalCase.UniversalCase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UniversalCaseRespository extends MongoRepository <UniversalCase,String>{
    Optional<UniversalCase> findByCaseId(String caseId);
}
