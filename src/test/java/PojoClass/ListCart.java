package PojoClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class ListCart {
        ArrayList< Cart > cart;
        private float total_discount;
        private float total_price;
}
