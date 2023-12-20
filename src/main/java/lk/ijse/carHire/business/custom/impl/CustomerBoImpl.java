package lk.ijse.carHire.business.custom.impl;

import lk.ijse.carHire.business.custom.CustomerBo;
import lk.ijse.carHire.dao.DaoFacoty;
import lk.ijse.carHire.dao.DaoType;
import lk.ijse.carHire.dao.custom.CustomerDao;
import lk.ijse.carHire.dto.CustomerDto;
import lk.ijse.carHire.entity.CustomerEntity;

import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {

    CustomerDao  dao = DaoFacoty.getDao(DaoType.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDto dto) throws Exception {

        var customerEntity = new CustomerEntity(
                dto.getId(),
                dto.getTitle(),
                dto.getName(),
                dto.getDob(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getCity(),
                dto.getProvince(),
                dto.getZip()
        );

        return dao.save(customerEntity);
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws Exception {
        CustomerEntity customerEntity = new CustomerEntity(
                dto.getId(),
                dto.getTitle(),
                dto.getName(),
                dto.getDob(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getCity(),
                dto.getProvince(),
                dto.getZip()
        );

        return dao.update(customerEntity);
    }

    @Override
    public CustomerDto searchCustomer(String id) throws Exception {

        CustomerEntity entity = dao.search(id);
        return  new CustomerDto(
                entity.getId(),
                entity.getTitle(),
                entity.getName(),
                entity.getDob(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getCity(),
                entity.getProvince(),
                entity.getZip()
        );
    }

    @Override
    public boolean deleteCustomer(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public List<CustomerDto> getAllCustomers() throws Exception {

        List<CustomerEntity> customerEntities =dao.getAll();

        List<CustomerDto> customerDtos = new ArrayList<>();

        for(CustomerEntity customerEntity : customerEntities){
            customerDtos.add(new CustomerDto(
                    customerEntity.getId(),
                    customerEntity.getTitle(),
                    customerEntity.getName(),
                    customerEntity.getDob(),
                    customerEntity.getPhone(),
                    customerEntity.getAddress(),
                    customerEntity.getCity(),
                    customerEntity.getProvince(),
                    customerEntity.getZip()));

        }
        return  customerDtos;
    }
}
