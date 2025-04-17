package org.bshg.demo.services.facade;
import org.bshg.demo.entity.core.Table;
import org.bshg.demo.zutils.pagination.Pagination;
import java.util.List;
public interface TableService {
Table findById(Long id);
List<Table> findAllOptimized();
List<Table> findAll();
Pagination<Table> findPaginated(int page, int size);
Table create(Table item);
List<Table> create(List<Table> item);
Table update(Table item);
List<Table> update(List<Table> item);
void deleteById(Long id);
void delete(Table item);
void delete(List<Table> items);
void deleteByIdIn(List<Long> ids);
}