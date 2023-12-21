package lk.ijse.carHire.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.carHire.dao.custom.RentDao;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.entity.RentEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RentDaoImpl implements RentDao {
    @Override
    public boolean save(RentEntity entity) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO rentals (RentalID, CarID,CustID, StartDate, EndDate) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, entity.getId());
        pstm.setString(2,entity.getCarId());
        pstm.setString(3,entity.getCustId());
        pstm.setString(4,entity.getPickupDate());
        pstm.setString(5,entity.getReturnDate());

        return pstm.executeUpdate() >0;
    }

    @Override
    public boolean update(RentEntity entity) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "UPDATE rentals SET  CustID=?, CarID=?, StartDate=?, EndDate=?, IsReturned=? WHERE RentalID=? ";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1,entity.getCustId());
        pstm.setString(2,entity.getCarId());
        pstm.setString(3,entity.getPickupDate());
        pstm.setString(4,entity.getReturnDate());
        pstm.setString(5,entity.getIsReturned());
        pstm.setString(6,entity.getId());


        return pstm.executeUpdate() >0;
    }

    @Override
    public RentEntity search(String s) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM rentals WHERE RentalID=?";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1,s);

        ResultSet rst = pstm.executeQuery();

        if(rst.next()){
            RentEntity entity = new RentEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );

            return  entity;
        }else {
            new Alert(Alert.AlertType.WARNING,"Rental Not Found").show();
        }
        return null;
    }

    @Override
    public boolean delete(String s) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM rentals WHERE RentalID=?";

        PreparedStatement pstm  = connection.prepareStatement(sql);

        pstm.setString(1,s);

        return pstm.executeUpdate() >0;
    }

    @Override
    public List<RentEntity> getAll() throws Exception {
        List<RentEntity> rentEntities = new ArrayList<>();

        Connection con =DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM rentals WHERE isReturned = 'No'";

        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet rst = pstm.executeQuery();

        while (rst.next()){
            rentEntities.add(new RentEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return rentEntities;
    }
}
