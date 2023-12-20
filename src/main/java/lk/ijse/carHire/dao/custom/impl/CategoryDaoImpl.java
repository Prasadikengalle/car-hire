package lk.ijse.carHire.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.carHire.dao.custom.CategoryDao;
import lk.ijse.carHire.db.DBConnection;
import lk.ijse.carHire.entity.CategoryEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public boolean save(CategoryEntity entity) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO carcategories VALUES(?,?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, entity.getCategoryID());
        pstm.setString(2, entity.getCategoryName());



        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean update(CategoryEntity entity) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "UPDATE carcategories SET CategoryName=? WHERE CategoryID=?";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1, entity.getCategoryName());
        pstm.setString(2, entity.getCategoryID());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public CategoryEntity search(String s) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM carcategories WHERE CategoryID=?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1,s);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
           CategoryEntity ce = new CategoryEntity(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );

            return ce;
        }else {
            new Alert(Alert.AlertType.WARNING,"Category Not Found").show();
        }

        return null;
    }

    @Override
    public boolean delete(String s) throws Exception {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM carcategories WHERE CategoryID=?";
        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1,s);

        return pstm.executeUpdate() >0;
    }

    @Override
    public List<CategoryEntity> getAll() throws Exception {

        List<CategoryEntity> entities = new ArrayList<>();

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM carcategories";

        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            entities.add(new CategoryEntity(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }

        return entities;
    }
}
