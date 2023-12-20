package lk.ijse.carHire.business.custom;

import lk.ijse.carHire.dto.CategoryDto;

import java.util.List;

public interface CategoryBo {
    boolean saveCategory(CategoryDto dto) throws Exception;

    boolean updateCategory(CategoryDto categoryDto) throws  Exception;

    CategoryDto searchCategory(String id) throws  Exception;

    boolean deleteCategory(String id) throws Exception;

    List<CategoryDto> getAllCategories() throws Exception;
}
