package PojoClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class Cart {
    private Integer id;
    private String name;
    private Double price;
    private Integer quantity;
    private String category;
    private Double discount;
}
