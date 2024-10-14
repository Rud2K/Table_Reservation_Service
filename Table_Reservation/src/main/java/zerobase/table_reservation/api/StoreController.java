package zerobase.table_reservation.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.model.Store;
import zerobase.table_reservation.service.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store")
public class StoreController {

  private final StoreService storeService;

  /**
   * 매장 정보를 조회하는 메소드 GET /store/{storeId}
   * 
   * @param storeId 매장 ID
   * @return 조회한 매장 정보를 포함한 ResponseEntity
   * 
   * 주어진 매장 ID로 매장 정보를 조회하여 반환합니다.
   */
  @GetMapping("/{storeId}")
  public ResponseEntity<Store> getStore(@PathVariable("storeId") Long storeId) {
    Store store = this.storeService.getStoreById(storeId);
    return ResponseEntity.ok(store);
  }

  /**
   * 매장 이름으로 매장을 검색하는 메소드 GET /store/search
   * 
   * @param name 검색할 매장 이름
   * @return 검색 조건에 맞는 매장 리스트를 포함한 ResponseEntity
   * 
   * 주어진 이름으로 매장을 검색하여 리스트로 반환합니다.
   */
  @GetMapping("/search")
  public ResponseEntity<List<Store>> getStores(@RequestParam(name = "name") String name) {
    List<Store> stores = this.storeService.getStoresByName(name);
    return ResponseEntity.ok(stores);
  }

  /**
   * 매장 생성 요청을 처리하는 메소드 POST /store
   * 
   * @param store 생성할 매장 정보
   * @return 생성된 매장 정보를 포함한 ResponseEntity
   * 
   * 매장 정보를 받아 새로운 매장을 생성하고, 생성된 정보를 반환합니다.
   */
  @PostMapping
  @PreAuthorize("hasRole('PARTNER')")
  public ResponseEntity<Store> createStore(@RequestBody Store store) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Store newStore = this.storeService.createStore(store, authentication.getName());
    return ResponseEntity.status(HttpStatus.CREATED).body(newStore);
  }

  /**
   * 매장 정보를 수정하는 메소드 PUT /store/{storeId}
   * 
   * @param storeId 수정할 매장 ID
   * @param store 수정할 매장 정보
   * @return 수정된 매장 정보를 포함한 ResponseEntity
   * 
   * 주어진 매장 ID와 수정할 매장 정보를 받아 해당 매장 정보를 수정하고, 수정된 정보를 반환합니다.
   */
  @PutMapping("/{storeId}")
  @PreAuthorize("hasRole('PARTNER')")
  public ResponseEntity<Store> updateStore(
      @PathVariable("storeId") Long storeId,
      @RequestBody Store store) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Store updatedStore = this.storeService.updateStore(storeId, store, authentication.getName());
    return ResponseEntity.ok(updatedStore);
  }

  /**
   * 매장 삭제 요청을 처리하는 메소드 DELETE /store/{storeId}
   * 
   * @param storeId 삭제할 매장 ID
   * @return ResponseEntity<Void>
   * 
   * 주어진 매장 ID로 매장을 삭제하고, 결과로 No Content 응답을 반환합니다.
   */
  @DeleteMapping("/{storeId}")
  @PreAuthorize("hasRole('PARTNER')")
  public ResponseEntity<Void> deleteStore(@PathVariable("storeId") Long storeId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    this.storeService.deleteStore(storeId, authentication.getName());
    return ResponseEntity.noContent().build();
  }

}
