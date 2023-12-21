package lk.ijse.carHire.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.carHire.dao.custom.CarDao;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.entity.CarEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    @Override
    public boolean save(CarEntity entity) throws Exception {

        Connection  con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO car VALUES(?,?,?,?,?,?,?,?)";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1, entity.getCarId());
        pstm.setString(2, entity.getLicensePlate());
        pstm.setString(3,entity.getMake());
        pstm.setString(4,entity.getModel());
        pstm.setString(5, String.valueOf(entity.getYear()));
        pstm.setString(6,entity.getCategoryId());
        pstm.setString(7, String.valueOf(entity.getDailyRentalRate()));
        pstm.setString(8,entity.getAvailable());

        return pstm.executeUpdate()>0;

    }

    @Override
    public boolean update(CarEntity entity) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "UPDATE  car SET CarNumber=?, Make=?,Model=?, Year=?, CategoryID=?, DailyRentalRate=?, IsAvailable=? WHERE CarID=?";

        PreparedStatement pstm = con.prepareStatement(sql);


        pstm.setString(1, entity.getLicensePlate());
        pstm.setString(2,entity.getMake());
        pstm.setString(3,entity.getModel());
        pstm.setString(4, String.valueOf(entity.getYear()));
        pstm.setString(5,entity.getCategoryId());
        pstm.setString(6, String.valueOf(entity.getDailyRentalRate()));
        pstm.setString(7,entity.getAvailable());
        pstm.setString(8, entity.getCarId());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public CarEntity search(String s) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();

        String sql ="SELECT * FROM car WHERE CarID=?";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1,s);

        ResultSet rst = pstm.executeQuery();

        if(rst.next()){
            CarEntity ce = new CarEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5),
                    rst.getString(6),
                    rst.getDouble(7),
                    rst.getString(8)
            );

            return ce;
        }else {
            new Alert(Alert.AlertType.WARNING,"Car Not Found").show();

        }
        return null;
    }

    @Override
    public boolean delete(String s) throws Exception {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM car WHERE CarID=?";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1,s);

        return pstm.executeUpdate() >0;
    }

    @Override
    public List<CarEntity> getAll() throws Exception {
        List<CarEntity> carEntityList = new ArrayList<>();

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM car";

        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet rst = pstm.executeQuery();

        while (rst.next()){
            carEntityList.add(new CarEntity(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5),
                    rst.getString(6),
                    rst.getDouble(7),
                    rst.getString(8)

            ));
        }
        return carEntityList;
    }
}
