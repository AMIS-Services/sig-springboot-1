package nl.groothandel.service.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@SqlResultSetMapping(
        name = "IdValueMapping",
        classes = @ConstructorResult(
                targetClass = Long.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class)}))
public class Product {
    //private static final Long serialVersionID = 1L;
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "product_sequence")
  @SequenceGenerator(name = "product_sequence", sequenceName = "PRODUCT_SEQ")
  private Long   id;
  @Column(unique=true)
  private String productId;
  private String name;
  private String type;
  private String brand;
  private String bottleSize;
  private double abv;
  private double price;

    public Product()
    {
        super();
    }

  public Product(String productId, String name, String type, String brand, String bottleSize, double abv, double price) {
    this.productId = productId;
    this.name = name;
    this.type = type;
    this.brand = brand;
    this.bottleSize = bottleSize;
    this.abv = abv;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getBottleSize() {
    return bottleSize;
  }

  public void setBottleSize(String bottleSize) {
    this.bottleSize = bottleSize;
  }

  public double getAbv() {
    return abv;
  }

  public void setAbv(double abv) {
    this.abv = abv;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
