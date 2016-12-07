package com.ugurhicyilmam.repository;

import com.ugurhicyilmam.domain.Todo;
import com.ugurhicyilmam.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<Todo, Long>{
    Page<Todo> findByUserOrderByCreatedAtDesc(Pageable pageable, User user);
}
