package com.itda.memberservice.team.repository;

import com.itda.memberservice.team.entity.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

}