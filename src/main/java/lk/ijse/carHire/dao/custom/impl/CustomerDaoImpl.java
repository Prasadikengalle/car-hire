package lk.ijse.carHire.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.carHire.dao.custom.CustomerDao;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.entity.CustomerEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity entity) throws Exception {

        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE customers SET CustTitle=?, CustName=?, DOB=?, Phone=?, Address=?, City=?,  Province=?, PostalCode=? WHERE CustID=?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,entity.getTitle());
        pstm.setString(2,entity.getName());
        pstm.setString(3,entity.getDob());
        pstm.setString(4,entity.getPhone());
        pstm.setString(5,entity.getAddress());
        pstm.setString(6,entity.getCity());
        pstm.setString(7,entity.getProvince());
        pstm.setString(8,entity.getZip());
        pstm.setString(9,entity.getId());

        return pstm.executeUpdate() >0;
    }

    @Override
    public boolean update(CustomerEntity entity) throws Exception {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "UPDATE customers SET CustTitle=?, CustName=?, DOB=?, Phone=?, Address=?, City=?,  Province=?, PostalCode=? WHERE CustID=?";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1,entity.getTitle());
        pstm.setString(2,entity.getName());
        pstm.setString(3,entity.getDob());
        pstm.setString(4,entity.getPhone());
        pstm.setString(5,entity.getAddress());
        pstm.setString(6,entity.getCity());
        pstm.setString(7,entity.getProvince());
        pstm.setString(8,entity.getZip());
        pstm.setString(9,entity.getId());

        return pstm.executeUpdate() >0;
    }

    @Override
    public CustomerEntity search(String s) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customers WHERE CustID=?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1,s);

        ResultSet rst = pstm.executeQuery();

        if(rst.next()){
            CustomerEntity en = new CustomerEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)

            );

            return en;

        }else {
            new Alert(Alert.AlertType.WARNING,"Customer Not Found").show();
        }
        return null;

    }

    @Override
    public boolean delete(String s) throws Exception {

        Connection con = DBConnection.getInstance().getConnection();

        String sql ="DELETE FROM customers WHERE CustID=?";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1,s);

        return pstm.executeUpdate() >0 ;
    }

    @Override
    public List<CustomerEntity> getAll() throws Exception {
        List<CustomerEntity> customerEntities = new ArrayList<>();

        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM customers";

        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet rst = pstm.executeQuery();

        while (rst.next()){
            customerEntities.add(new CustomerEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            ));
        }


        return customerEntities;
    }

}
