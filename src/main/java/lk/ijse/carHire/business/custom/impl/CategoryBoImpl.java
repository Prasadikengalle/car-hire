package lk.ijse.carHire.business.custom.impl;

import lk.ijse.carHire.business.custom.CategoryBo;
import lk.ijse.carHire.dao.DaoFacoty;
import lk.ijse.carHire.dao.DaoType;
import lk.ijse.carHire.dao.custom.CategoryDao;
import lk.ijse.carHire.dto.CategoryDto;
import lk.ijse.carHire.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class CategoryBoImpl implements CategoryBo {

    CategoryDao dao = DaoFacoty.getDao(DaoType.CATEGORY);
    @Override
    public boolean saveCategory(CategoryDto dto) throws Exception {

        var entity = new CategoryEntity(
                dto.getId(),
                dto.getCategoryName()
        );

        return dao.save(entity);
    }

    @Override
    public boolean updateCategory(CategoryDto dto) throws Exception {

        var entity = new CategoryEntity(
                dto.getId(),
                dto.getCategoryName()

        );

        return dao.update(entity);
    }

    @Override
    public CategoryDto searchCategory(String id) throws Exception {

        CategoryEntity entity = dao.search(id);

        return  new CategoryDto(
                entity.getCategoryID(),
                entity.getCategoryName()
        );
    }

    @Override
    public boolean deleteCategory(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public List<CategoryDto> getAllCategories() throws Exception {

        List<CategoryEntity> categoryEntities = dao.getAll();

        List<CategoryDto> dtoList = new ArrayList<>();

        for(CategoryEntity entity : categoryEntities){
            dtoList.add(new CategoryDto(entity.getCategoryID(),entity.getCategoryName()));

        }
        return  dtoList;
    }
}
