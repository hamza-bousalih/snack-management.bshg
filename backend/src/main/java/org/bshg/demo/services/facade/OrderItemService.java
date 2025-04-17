package org.bshg.demo.services.facade;
import org.bshg.demo.entity.core.OrderItem;
import org.bshg.demo.entity.core.Order;
import org.bshg.demo.entity.core.MenuItem;
import org.bshg.demo.zutils.pagination.Pagination;
import java.util.List;
public interface OrderItemService {
OrderItem findById(Long id);
List<OrderItem> findAllOptimized();
List<OrderItem> findAll();
Pagination<OrderItem> findPaginated(int page, int size);
OrderItem create(OrderItem item);
List<OrderItem> create(List<OrderItem> item);
OrderItem update(OrderItem item);
List<OrderItem> update(List<OrderItem> item);
void deleteById(Long id);
void delete(OrderItem item);
void delete(List<OrderItem> items);
void deleteByIdIn(List<Long> ids);
int deleteByOrderId(Long id);
List<OrderItem> findByOrderId(Long id);
int deleteByMenuItemId(Long id);
List<OrderItem> findByMenuItemId(Long id);
}