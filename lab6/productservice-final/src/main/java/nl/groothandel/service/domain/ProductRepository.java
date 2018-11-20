package nl.groothandel.service.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom{
    Optional<Product> findByProductId(String productId);
    List<Product> findByBrandIgnoreCaseAndPriceBetween(String productId, Double priceFloor, Double priceCeiling);

    @Query("Select p from Product p where (p.price >= ?1 or ?1 = null) and (p.price <= ?2 or ?2 = null)")
    List<Product>  customQuery(Double priceFloor, Double priceCeiling);

    @Query(value = "select * from product where (price >= ?1 or ?1 is null) and (price <= ?2 or ?2 is null)", nativeQuery = true)
    List<Product>  nativeQuery(Double priceFloor, Double priceCeiling);
}
