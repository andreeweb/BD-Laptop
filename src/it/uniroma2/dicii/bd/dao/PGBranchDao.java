package it.uniroma2.dicii.bd.dao;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.BranchDao;
import it.uniroma2.dicii.bd.interfaces.GPointDao;
import it.uniroma2.dicii.bd.model.Branch;
import it.uniroma2.dicii.bd.model.GPoint;
import it.uniroma2.dicii.bd.model.GPointBranch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PGBranchDao implements BranchDao{

    @Override
    public void insertBranch(Branch branch) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            this._insertBranch(branch, conn);
            this._insertFilamentBranchRelation(branch, conn);

            conn.close();

        }  catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {
                if (conn != null)
                    conn.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }

    }

    private void _insertBranch(Branch branch, Connection connection) throws DaoException {

        final String sql = "INSERT INTO branch(idbranch, filament, type) values (?,?,?) ON conflict (idbranch, filament) do nothing";
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, branch.getIdBranch());
            preparedStatement.setInt(2, branch.getFilament().getIdfil());
            preparedStatement.setString(3, branch.getType().toString());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        }  catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {
                if (preparedStatement != null)
                    preparedStatement.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }
    }

    private void _insertFilamentBranchRelation(Branch branch, Connection connection) throws DaoException {

        final String sql = "INSERT INTO filament_branch(branch, filament, galactic_longitude, galactic_latitude, sequence, flux) values (?,?,?,?,?,?) ON conflict (branch, filament, galactic_longitude, galactic_latitude) do nothing";
        PreparedStatement preparedStatement = null;

        try {

            GPointDao dao = DaoFactory.getSingletonInstance().getGPointDAO();

            // insert points
            for (GPoint point : branch.getgPointBranch()){
                dao.insertGPoint(point);
            }

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, branch.getIdBranch());
            preparedStatement.setInt(2, branch.getFilament().getIdfil());

            for (GPointBranch pointBranch : branch.getgPointBranch()){
                preparedStatement.setDouble(3, pointBranch.getGlongitude());
                preparedStatement.setDouble(4, pointBranch.getGlatitude());
                preparedStatement.setInt(5, pointBranch.getSequenceNumber());
                preparedStatement.setBigDecimal(6, pointBranch.getFlux());
                preparedStatement.executeUpdate();
            }

            preparedStatement.close();

        }catch (SQLException e){

            throw new DaoException(e.getMessage(), e.getCause());

        }finally {

            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException(e.getMessage(), e.getCause());
            }
        }
    }

}
