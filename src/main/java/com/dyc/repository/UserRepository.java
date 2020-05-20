package com.dyc.repository;

import com.dyc.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String>, CrudRepository<User, String>, JpaSpecificationExecutor {
    //使用native SQL 例子
    @Query(value = "select * from user where name = :name", nativeQuery = true)
    List<User> findByName(@Param("name") String supplierCode);

    //简单分页
    Page<User> findAll(Pageable pageable);

    //多条件查询使用JPQL
    @Query("Select e from  User e where e.name = :name ")
    Page<User> findBySomethingElseId(@Param("name") String id, Pageable pageable);

    //使用Specification 分页查询
    Page<User> findAll(Specification specification, Pageable pageable);
}
