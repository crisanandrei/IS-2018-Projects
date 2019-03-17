package com.is.AutoShop.repository;

import com.is.AutoShop.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;


@Service
public interface ClientRepository extends MongoRepository<Client,String> {
         Client findByUserName(String userName);
         Client findByClientId(String clientId);
         void deleteByClientId(String clientId);

}
