package org.bshg.demo.services.facade;
import org.bshg.demo.entity.core.Order;
import org.bshg.demo.entity.core.User;
import org.bshg.demo.entity.core.Table;
import org.bshg.demo.entity.core.OrderItem;
import org.bshg.demo.zutils.pagination.Pagination;
import java.util.List;
public interface OrderService {
Order findById(Long id);
List<Order> findAllOptimized();
List<Order> findAll();
Pagination<Order> findPaginated(int page, int size);
Order create(Order item);
List<Order> create(List<Order> item);
Order update(Order item);
List<Order> update(List<Order> item);
void deleteById(Long id);
void delete(Order item);
void delete(List<Order> items);
void deleteByIdIn(List<Long> ids);
int deleteByCreatorId(Long id);
Order findByCreatorId(Long id);
int deleteByTableId(Long id);
List<Order> findByTableId(Long id);
}