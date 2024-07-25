package com.github.lacoming.eventsapi.repository;

import com.github.lacoming.eventsapi.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
