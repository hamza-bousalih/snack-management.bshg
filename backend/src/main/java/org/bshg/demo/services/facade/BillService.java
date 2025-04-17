package org.bshg.demo.services.facade;
import org.bshg.demo.entity.core.Bill;
import org.bshg.demo.entity.core.Order;
import org.bshg.demo.entity.core.User;
import org.bshg.demo.zutils.pagination.Pagination;
import java.util.List;
public interface BillService {
Bill findById(Long id);
List<Bill> findAllOptimized();
List<Bill> findAll();
Pagination<Bill> findPaginated(int page, int size);
Bill create(Bill item);
List<Bill> create(List<Bill> item);
Bill update(Bill item);
List<Bill> update(List<Bill> item);
void deleteById(Long id);
void delete(Bill item);
void delete(List<Bill> items);
void deleteByIdIn(List<Long> ids);
int deleteByOrderId(Long id);
Bill findByOrderId(Long id);
int deleteByIssuerId(Long id);
Bill findByIssuerId(Long id);
}