package org.bshg.demo.repository;
import org.bshg.demo.entity.core.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface OrderDao extends JpaRepository<Order, Long> {
int deleteByIdIn(List<Long> ids);
int deleteByCreatorId(Long id);
Order findByCreatorId(Long id);
int deleteByTableId(Long id);
List<Order> findByTableId(Long id);
}