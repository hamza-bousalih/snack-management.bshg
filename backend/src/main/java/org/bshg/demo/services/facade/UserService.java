package org.bshg.demo.services.facade;
import org.bshg.demo.entity.core.User;
import org.bshg.demo.zutils.pagination.Pagination;
import java.util.List;
public interface UserService {
User findById(Long id);
List<User> findAllOptimized();
List<User> findAll();
Pagination<User> findPaginated(int page, int size);
User create(User item);
List<User> create(List<User> item);
User update(User item);
List<User> update(List<User> item);
void deleteById(Long id);
void delete(User item);
void delete(List<User> items);
void deleteByIdIn(List<Long> ids);
}