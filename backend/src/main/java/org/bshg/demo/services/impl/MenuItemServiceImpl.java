package org.bshg.demo.services.impl;
import org.bshg.demo.entity.core.MenuItem;
import org.bshg.demo.repository.MenuItemDao;
import org.bshg.demo.services.facade.MenuItemService;
import org.bshg.demo.entity.core.OrderItem;
import org.bshg.demo.services.facade.OrderItemService;
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
public class MenuItemServiceImpl implements MenuItemService {
//--------------- FIND -------------------------------------
public MenuItem findById(Long id) {
return repository.findById(id).orElse(null);
}
public List<MenuItem> findAll() {
return repository.findAll();
}
public List<MenuItem> findAllOptimized() {
return repository.findAllOptimized();
}
@Override
public Pagination<MenuItem> findPaginated(int page, int size) {
var pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
var found = repository.findAll(pageable);
return new Pagination<>(found);
}
//--------------- CREATE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public MenuItem create(MenuItem item) {
if (item == null) return null;
MenuItem saved = repository.save(item);
createAssociatedList(saved);
return saved;
}
@Transactional(rollbackFor = Exception.class)
public List<MenuItem> create(List<MenuItem> items) {
List<MenuItem> result = new ArrayList<>();
if (items == null || items.isEmpty()) return result;
items.forEach(it -> result.add(create(it)));
return result;
}
//--------------- UPDATE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public MenuItem update(MenuItem item) {
if (item == null || item.getId() == null) return null;
var oldItem = findById(item.getId());
if (oldItem == null) throw new NotFoundException("Unknown MenuItem To Be Updated!");
MenuItem saved = repository.save(item);
updateAssociatedList(saved);
return saved;
}
@Transactional(rollbackFor = Exception.class)
public List<MenuItem> update(List<MenuItem> items) {
List<MenuItem> result = new ArrayList<>();
if (items == null || items.isEmpty()) return result;
items.forEach(it -> result.add(update(it)));
return result;
}
//--------------- DELETE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public void deleteById(Long id) {
MenuItem item = findById(id);
if (item != null) delete(item);
}
@Transactional(rollbackFor = Exception.class)
public void delete(MenuItem item) {
deleteAssociated(item);
repository.delete(item);
}
@Transactional(rollbackFor = Exception.class)
public void delete(List<MenuItem> items) {
if (items == null || items.isEmpty()) return;
items.forEach(this::delete);
}
@Transactional(rollbackFor = Exception.class)
public void deleteByIdIn(List<Long> ids) {
ids.forEach(id -> {
MenuItem item = findById(id);
if (item != null) {
deleteAssociated(item);
}
});
repository.deleteByIdIn(ids);
}
//--------------- FIND AND DELETE BYs ----------------------
//----------------------------------------------------------
public void createAssociatedList(MenuItem item) {
if (item == null || item.getId() == null) return;
ServiceHelper.createList(item, MenuItem::getOrderItem, OrderItem::setMenuItem, orderItemService::create);
}
public void updateAssociatedList(MenuItem item) {
if (item == null || item.getId() == null) return;
ServiceHelper.updateList(
item, orderItemService.findByMenuItemId(item.getId()),
item.getOrderItem(), OrderItem::setMenuItem,
orderItemService::update,
orderItemService::delete
);
}
@Transactional(rollbackFor = Exception.class)
public void deleteAssociated(MenuItem item) {
deleteAssociatedList(item);
}
public void deleteAssociatedList(MenuItem item) {
orderItemService.deleteByMenuItemId(item.getId());
}
//----------------------------------------------------------
@Autowired private MenuItemDao repository;
@Lazy @Autowired private OrderItemService orderItemService;
}