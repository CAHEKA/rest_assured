package PojoClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(force = true)
public class Product {

    public Product(String name, String category, Double price, Double discount) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = discount;
    }
    
//    @JsonIgnore
    private Integer id;
    private String name;
    private String category;
    private Double price;
    private Double discount;
}
