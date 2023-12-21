package lk.ijse.carHire.dao.custom.impl;

import lk.ijse.carHire.dao.custom.ReturnDao;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.entity.ReturnEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReturnDaoImpl implements ReturnDao {
    @Override
    public boolean save(ReturnEntity entity) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO returns(ReturnID,RentalID, CarID, LateFees) VALUES (?, ?,?,?)";



        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,entity.getReturnId());
        pstm.setString(2,entity.getRentalId());
        pstm.setString(3,entity.getCarId());
        pstm.setDouble(4, entity.getFee());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(ReturnEntity entity) throws Exception {
        return false;
    }

    @Override
    public ReturnEntity search(String s) throws Exception {
        return null;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public List<ReturnEntity> getAll() throws Exception {
        List<ReturnEntity> returnEntities = new ArrayList<>();

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM returns";

        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet rst = pstm.executeQuery();

        while (rst.next()){
            returnEntities.add(new ReturnEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            ));
        }

        return returnEntities;
    }
}
