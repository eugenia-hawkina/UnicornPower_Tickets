package telran.ashkelon2018.ticket.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.ticket.domain.Manager;

public interface ManagerAccountRepository extends MongoRepository<Manager, String> {

}
