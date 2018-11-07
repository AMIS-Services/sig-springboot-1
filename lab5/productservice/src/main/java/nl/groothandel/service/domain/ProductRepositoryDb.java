package nl.groothandel.service.domain;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositoryDb extends JpaRepository<Product, Long>, ProductRepositoryDbCustom{
  Optional<Product> findByProductId(String productId);
}