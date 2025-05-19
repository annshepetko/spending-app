package com.ann.spending.authorization.repository;

import com.ann.spending.authorization.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   @Query("SELECT SUM(s.amount)" +
           "FROM Spending s " +
           "WHERE MONTH(s.date) = :month " +
           "AND YEAR(s.date) = :year group by s.user"
   )
   BigDecimal findTotalSpendingByUserAndMonth(User user, int year, int month);

   Optional<User> findByEmail(String email);
}
