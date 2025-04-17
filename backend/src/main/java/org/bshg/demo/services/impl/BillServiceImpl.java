package org.bshg.demo.services.impl;
import org.bshg.demo.entity.core.Bill;
import org.bshg.demo.repository.BillDao;
import org.bshg.demo.services.facade.BillService;
import org.bshg.demo.entity.core.Order;
import org.bshg.demo.services.facade.OrderService;
import org.bshg.demo.entity.core.User;
import org.bshg.demo.services.facade.UserService;
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
public class BillServiceImpl implements BillService {
//--------------- FIND -------------------------------------
public Bill findById(Long id) {
return repository.findById(id).orElse(null);
}
public List<Bill> findAll() {
return repository.findAll();
}
public List<Bill> findAllOptimized() {
return findAll();
}
@Override
public Pagination<Bill> findPaginated(int page, int size) {
var pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
var found = repository.findAll(pageable);
return new Pagination<>(found);
}
//--------------- CREATE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public Bill create(Bill item) {
if (item == null) return null;
createAssociatedObject(item);
return repository.save(item);
}
@Transactional(rollbackFor = Exception.class)
public List<Bill> create(List<Bill> items) {
List<Bill> result = new ArrayList<>();
if (items == null || items.isEmpty()) return result;
items.forEach(it -> result.add(create(it)));
return result;
}
//--------------- UPDATE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public Bill update(Bill item) {
if (item == null || item.getId() == null) return null;
var oldItem = findById(item.getId());
if (oldItem == null) throw new NotFoundException("Unknown Bill To Be Updated!");
// update order
var order = item.getOrder();
var oldOrder = oldItem.getOrder();
if (oldOrder == null) {
if (order != null) orderService.create(order);
} else {
// if (order == null) orderService.delete(oldOrder);
if (order != null) {
order.setId(oldOrder.getId());
orderService.update(order);
}
}
// update issuer
var issuer = item.getIssuer();
var oldIssuer = oldItem.getIssuer();
if (oldIssuer == null) {
if (issuer != null) userService.create(issuer);
} else {
// if (issuer == null) userService.delete(oldIssuer);
if (issuer != null) {
issuer.setId(oldIssuer.getId());
userService.update(issuer);
}
}
return repository.save(item);
}
@Transactional(rollbackFor = Exception.class)
public List<Bill> update(List<Bill> items) {
List<Bill> result = new ArrayList<>();
if (items == null || items.isEmpty()) return result;
items.forEach(it -> result.add(update(it)));
return result;
}
//--------------- DELETE -----------------------------------
@Transactional(rollbackFor = Exception.class)
public void deleteById(Long id) {
Bill item = findById(id);
if (item != null) delete(item);
}
@Transactional(rollbackFor = Exception.class)
public void delete(Bill item) {
deleteAssociated(item);
repository.delete(item);
}
@Transactional(rollbackFor = Exception.class)
public void delete(List<Bill> items) {
if (items == null || items.isEmpty()) return;
items.forEach(this::delete);
}
@Transactional(rollbackFor = Exception.class)
public void deleteByIdIn(List<Long> ids) {
ids.forEach(id -> {
Bill item = findById(id);
if (item != null) {
deleteAssociated(item);
}
});
repository.deleteByIdIn(ids);
}
//--------------- FIND AND DELETE BYs ----------------------
@Override
@Transactional(rollbackFor = Exception.class)
public int deleteByOrderId(Long id){
Bill found = findByOrderId(id);
if (found == null) return 0;
this.deleteAssociated(found);
return repository.deleteByOrderId(id);
}
@Override
public Bill findByOrderId(Long id){
return repository.findByOrderId(id);
}
@Override
@Transactional(rollbackFor = Exception.class)
public int deleteByIssuerId(Long id){
Bill found = findByIssuerId(id);
if (found == null) return 0;
this.deleteAssociated(found);
return repository.deleteByIssuerId(id);
}
@Override
public Bill findByIssuerId(Long id){
return repository.findByIssuerId(id);
}
//----------------------------------------------------------
public void createAssociatedObject(Bill item) {
if (item == null) return;
ServiceHelper.createObject(item, Bill::getOrder, orderService::create);
ServiceHelper.createObject(item, Bill::getIssuer, userService::create);
}
@Transactional(rollbackFor = Exception.class)
public void deleteAssociated(Bill item) {
deleteAssociatedObjects(item);
}
public void deleteAssociatedObjects(Bill item) {
ServiceHelper.deleteById(item.getOrder(), Order::getId, this.orderService::deleteById);
ServiceHelper.deleteById(item.getIssuer(), User::getId, this.userService::deleteById);
}
//----------------------------------------------------------
@Autowired private BillDao repository;
@Lazy @Autowired private OrderService orderService;
@Lazy @Autowired private UserService userService;
}