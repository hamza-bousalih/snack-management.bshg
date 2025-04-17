package org.bshg.demo.webservice.controller;
import org.bshg.demo.entity.core.Bill;
import org.bshg.demo.services.facade.BillService;
import org.bshg.demo.webservice.converter.BillConverter;
import org.bshg.demo.webservice.dto.BillDto;
import org.bshg.demo.zutils.pagination.Pagination;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.List;
@RestController
@RequestMapping("bill")
public class BillApi {
@Autowired private BillService service;
@Autowired private BillConverter converter;
@GetMapping("/id/{id}")
public ResponseEntity<BillDto> findById(@PathVariable Long id) {
var result = service.findById(id);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@GetMapping
public ResponseEntity<List<BillDto>> findAll() {
var result = service.findAll();
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@GetMapping("/optimized")
public ResponseEntity<List<BillDto>> findAllOptimized() {
var result = service.findAllOptimized();
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@GetMapping("/paginated")
public ResponseEntity<Pagination<BillDto>> findPaginated(
@RequestParam(name = "page", defaultValue = "0", required = false) int page,
@RequestParam(name = "size", defaultValue = "10", required = false) int size
) {
var result = service.findPaginated(page, size);
return ResponseEntity.ok(result.convert(converter::toDto));
}
@PostMapping
public ResponseEntity<BillDto> save(@RequestBody BillDto dto) {
if (dto == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dto);
var result = service.create(item);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@PostMapping("/all")
public ResponseEntity<List<BillDto>> save(@RequestBody List<BillDto> dtos) {
if (dtos == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dtos);
var result = service.create(item);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@PutMapping()
public ResponseEntity<BillDto> update(@RequestBody BillDto dto) {
if (dto == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dto);
var result = service.update(item);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@PutMapping("/all")
public ResponseEntity<List<BillDto>> update(@RequestBody List<BillDto> dtos) {
if (dtos == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dtos);
var result = service.update(item);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@DeleteMapping
public ResponseEntity<BillDto> delete(@RequestBody BillDto dto) {
if (dto == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dto);
service.delete(item);
return ResponseEntity.ok(dto);
}
@DeleteMapping("/all")
public ResponseEntity<List<BillDto>> delete(@RequestBody List<BillDto> dtos) {
if (dtos == null) return ResponseEntity.status(HttpStatus.CONFLICT).build();
var item = converter.toItem(dtos);
service.delete(item);
return ResponseEntity.ok(dtos);
}
@DeleteMapping("/id/{id}")
public ResponseEntity<Long> deleteById(@PathVariable Long id) {
service.deleteById(id);
return ResponseEntity.ok(id);
}
@DeleteMapping("/ids")
public ResponseEntity<List<Long>> deleteByIdIn(@RequestParam("id") List<Long> ids) {
service.deleteByIdIn(ids);
return ResponseEntity.ok(ids);
}
@DeleteMapping("/order/id/{id}")
public ResponseEntity<Long> deleteByOrderId(@PathVariable Long id){
service.deleteByOrderId(id);
return ResponseEntity.ok(id);
}
@GetMapping("/order/id/{id}")
public ResponseEntity<BillDto> findByOrderId(@PathVariable Long id){
var result = service.findByOrderId(id);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
@DeleteMapping("/issuer/id/{id}")
public ResponseEntity<Long> deleteByIssuerId(@PathVariable Long id){
service.deleteByIssuerId(id);
return ResponseEntity.ok(id);
}
@GetMapping("/issuer/id/{id}")
public ResponseEntity<BillDto> findByIssuerId(@PathVariable Long id){
var result = service.findByIssuerId(id);
var resultDto = converter.toDto(result);
return ResponseEntity.ok(resultDto);
}
}