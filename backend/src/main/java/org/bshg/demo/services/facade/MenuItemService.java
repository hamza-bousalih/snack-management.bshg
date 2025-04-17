package org.bshg.demo.services.facade;
import org.bshg.demo.entity.core.MenuItem;
import org.bshg.demo.entity.core.OrderItem;
import org.bshg.demo.zutils.pagination.Pagination;
import java.util.List;
public interface MenuItemService {
MenuItem findById(Long id);
List<MenuItem> findAllOptimized();
List<MenuItem> findAll();
Pagination<MenuItem> findPaginated(int page, int size);
MenuItem create(MenuItem item);
List<MenuItem> create(List<MenuItem> item);
MenuItem update(MenuItem item);
List<MenuItem> update(List<MenuItem> item);
void deleteById(Long id);
void delete(MenuItem item);
void delete(List<MenuItem> items);
void deleteByIdIn(List<Long> ids);
}