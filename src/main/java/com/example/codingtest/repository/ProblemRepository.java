package com.example.codingtest.repository;

import com.example.codingtest.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>
{
}
