package com.javatechie.repository;

import com.javatechie.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository  extends JpaRepository<AuditLog,Long> {
}
