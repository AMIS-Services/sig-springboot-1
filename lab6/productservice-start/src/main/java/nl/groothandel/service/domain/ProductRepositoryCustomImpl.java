package nl.groothandel.service.domain;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{

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
//    Product productDb = em.find(Product.class, product.getId(), LockModeType.PESSIMISTIC_WRITE);
        Query q = em.createNativeQuery("select pdt.id from product pdt where pdt.PRODUCT_ID = :productId for update", "IdValueMapping");
        q.setParameter("productId",product.getProductId());
        List<Long> ids = q.getResultList();
        if (!ids.isEmpty()){
            product.setId(ids.get(0).longValue());
            product = em.merge(product);
            return product;
        }
        else throw new EntityNotFoundException("Product with id "+product.getProductId()+" was not found");
    }
}