package org.bshg.demo.services.impl;
import org.bshg.demo.entity.core.OrderItem;
import org.bshg.demo.repository.OrderItemDao;
import org.bshg.demo.services.facade.OrderItemService;
import org.bshg.demo.entity.core.Order;
import org.bshg.demo.services.facade.OrderService;
import org.bshg.demo.entity.core.MenuItem;
import org.bshg.demo.services.facade.MenuItemService;
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
public class OrderItemServiceImpl implements OrderItemService {
//--------------- FIND -------------------------------------
public OrderItem findById(Long id) {
return repository.findById(id).orElse(null);
}
public List<OrderItem> findAll() {
return repository.findAll();
}
public List<OrderItem> findAllOptimized() {
return findAll();
}
@Override
public Pagination<OrderItem> findPaginated(int page, int size) {
var pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
var found = repository.findAll(pageable);
return new Pagination<>(found);
}
//--------------- CREATE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public OrderItem create(OrderItem item) {
if (item == null) return null;
// check if order exists
var order = item.getOrder();
if (order != null) {
if(order.getId() == null) item.setOrder(null);
else {
var found = orderService.findById(order.getId());
if (found == null) throw new NotFoundException("Unknown Given Order");
item.setOrder(found);
}
}
// check if menuItem exists
var menuItem = item.getMenuItem();
if (menuItem != null) {
if(menuItem.getId() == null) item.setMenuItem(null);
else {
var found = menuItemService.findById(menuItem.getId());
if (found == null) throw new NotFoundException("Unknown Given MenuItem");
item.setMenuItem(found);
}
}
return repository.save(item);
}
@Transactional(rollbackFor = Exception.class)
public List<OrderItem> create(List<OrderItem> items) {
List<OrderItem> result = new ArrayList<>();
if (items == null || items.isEmpty()) return result;
items.forEach(it -> result.add(create(it)));
return result;
}
//--------------- UPDATE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public OrderItem update(OrderItem item) {
if (item == null || item.getId() == null) return null;
var oldItem = findById(item.getId());
if (oldItem == null) throw new NotFoundException("Unknown OrderItem To Be Updated!");
return repository.save(item);
}
@Transactional(rollbackFor = Exception.class)
public List<OrderItem> update(List<OrderItem> items) {
List<OrderItem> result = new ArrayList<>();
if (items == null || items.isEmpty()) return result;
items.forEach(it -> result.add(update(it)));
return result;
}
//--------------- DELETE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public void deleteById(Long id) {
OrderItem item = findById(id);
if (item != null) delete(item);
}
@Transactional(rollbackFor = Exception.class)
public void delete(OrderItem item) {
repository.delete(item);
}
@Transactional(rollbackFor = Exception.class)
public void delete(List<OrderItem> items) {
if (items == null || items.isEmpty()) return;
items.forEach(this::delete);
}
@Transactional(rollbackFor = Exception.class)
public void deleteByIdIn(List<Long> ids) {
repository.deleteByIdIn(ids);
}
//--------------- FIND AND DELETE BYs ----------------------
@Override
@Transactional(rollbackFor = Exception.class)
public int deleteByOrderId(Long id){
if (id == null) return 0;
return repository.deleteByOrderId(id);
}
@Override
public List<OrderItem> findByOrderId(Long id){
return repository.findByOrderId(id);
}
@Override
@Transactional(rollbackFor = Exception.class)
public int deleteByMenuItemId(Long id){
if (id == null) return 0;
return repository.deleteByMenuItemId(id);
}
@Override
public List<OrderItem> findByMenuItemId(Long id){
return repository.findByMenuItemId(id);
}
//----------------------------------------------------------
//----------------------------------------------------------
@Autowired private OrderItemDao repository;
@Lazy @Autowired private OrderService orderService;
@Lazy @Autowired private MenuItemService menuItemService;
}