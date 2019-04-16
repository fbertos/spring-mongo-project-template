package org.fbertos.security.acls.dao;

import org.fbertos.security.acls.domain.MongoAcl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface AclRepository extends MongoRepository<MongoAcl, Serializable> {
	Optional<MongoAcl> findById(Serializable id);
	List<MongoAcl> findByInstanceIdAndClassName(Serializable instanceId, String className);
	List<MongoAcl> findByParentId(Serializable parentId);
	Long deleteByInstanceId(Serializable instanceId);
	
	@Query("{ 'className' : ?0 }")
	List<MongoAcl> findByClassName(String className);	
	
}