package PojoClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Product {
    private Integer id;
    private String name;
    private String category;
    private Double price;
    private Double discount;
}
