package org.bshg.demo.repository;
import org.bshg.demo.entity.core.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface OrderItemDao extends JpaRepository<OrderItem, Long> {
int deleteByIdIn(List<Long> ids);
int deleteByOrderId(Long id);
List<OrderItem> findByOrderId(Long id);
int deleteByMenuItemId(Long id);
List<OrderItem> findByMenuItemId(Long id);
}