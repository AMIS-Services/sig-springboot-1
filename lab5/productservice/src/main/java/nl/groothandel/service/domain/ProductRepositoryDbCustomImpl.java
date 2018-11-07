package nl.groothandel.service.domain;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Repository
@Transactional
public class ProductRepositoryDbCustomImpl implements ProductRepositoryDbCustom{

  @PersistenceContext
  EntityManager em;


  @Override
  @Transactional
  public Product insert(Product product) {
    try {
      em.persist(product);
    }
    catch (Exception e){
      throw e;
    }
    return product;
  }

  @Override
  @Transactional
  public Product update(Product product) {
    Product productDb = em.find(Product.class, product.getId(), LockModeType.PESSIMISTIC_WRITE);
    if (productDb != null){
      product = em.merge(product);
      return product;
    }
    else throw new EntityNotFoundException("Product with id "+product.getId()+" was not found");
  }
}