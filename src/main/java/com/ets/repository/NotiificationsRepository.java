package com.ets.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ets.model.Notiifications;

@Repository
public interface NotiificationsRepository extends JpaRepository<Notiifications, Long> {

    List<Notiifications> findByIsRead(boolean isRead);
}
