package edu.icet.dto;

import edu.icet.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Integer id;
    private String name;
    private List<Product> productList;
}
