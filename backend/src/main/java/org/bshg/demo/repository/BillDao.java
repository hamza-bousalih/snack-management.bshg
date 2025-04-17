package org.bshg.demo.repository;
import org.bshg.demo.entity.core.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface BillDao extends JpaRepository<Bill, Long> {
int deleteByIdIn(List<Long> ids);
int deleteByOrderId(Long id);
Bill findByOrderId(Long id);
int deleteByIssuerId(Long id);
Bill findByIssuerId(Long id);
}