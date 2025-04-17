package org.bshg.demo.services.impl;
import org.bshg.demo.entity.core.User;
import org.bshg.demo.repository.UserDao;
import org.bshg.demo.services.facade.UserService;
import org.bshg.demo.entity.core.Order;
import org.bshg.demo.services.facade.OrderService;
import org.bshg.demo.entity.core.Bill;
import org.bshg.demo.services.facade.BillService;
import org.bshg.demo.zutils.service.ServiceHelper;
import org.bshg.demo.zutils.pagination.Pagination;
import org.bshg.demo.exceptions.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;
@Service
public class UserServiceImpl implements UserService {
//--------------- FIND -------------------------------------
public User findById(Long id) {
return repository.findById(id).orElse(null);
}
public List<User> findAll() {
return repository.findAll();
}
public List<User> findAllOptimized() {
return findAll();
}
@Override
public Pagination<User> findPaginated(int page, int size) {
var pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
var found = repository.findAll(pageable);
return new Pagination<>(found);
}
//--------------- CREATE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public User create(User item) {
if (item == null) return null;
return repository.save(item);
}
@Transactional(rollbackFor = Exception.class)
public List<User> create(List<User> items) {
List<User> result = new ArrayList<>();
if (items == null || items.isEmpty()) return result;
items.forEach(it -> result.add(create(it)));
return result;
}
//--------------- UPDATE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public User update(User item) {
if (item == null || item.getId() == null) return null;
var oldItem = findById(item.getId());
if (oldItem == null) throw new NotFoundException("Unknown User To Be Updated!");
return repository.save(item);
}
@Transactional(rollbackFor = Exception.class)
public List<User> update(List<User> items) {
List<User> result = new ArrayList<>();
if (items == null || items.isEmpty()) return result;
items.forEach(it -> result.add(update(it)));
return result;
}
//--------------- DELETE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public void deleteById(Long id) {
User item = findById(id);
if (item != null) delete(item);
}
@Transactional(rollbackFor = Exception.class)
public void delete(User item) {
deleteAssociated(item);
repository.delete(item);
}
@Transactional(rollbackFor = Exception.class)
public void delete(List<User> items) {
if (items == null || items.isEmpty()) return;
items.forEach(this::delete);
}
@Transactional(rollbackFor = Exception.class)
public void deleteByIdIn(List<Long> ids) {
ids.forEach(id -> {
User item = findById(id);
if (item != null) {
deleteAssociated(item);
}
});
repository.deleteByIdIn(ids);
}
//--------------- FIND AND DELETE BYs ----------------------
//----------------------------------------------------------
@Transactional(rollbackFor = Exception.class)
public void deleteAssociated(User item) {
deleteAssociatedObjects(item);
}
public void deleteAssociatedObjects(User item) {
ServiceHelper.nullifyInContainer(item.getId(), orderService::findByCreatorId, Order::setCreator, (Order value) -> orderService.update(value));
ServiceHelper.nullifyInContainer(item.getId(), billService::findByIssuerId, Bill::setIssuer, (Bill value) -> billService.update(value));
}
//----------------------------------------------------------
@Autowired private UserDao repository;
@Lazy @Autowired private OrderService orderService;
@Lazy @Autowired private BillService billService;
}