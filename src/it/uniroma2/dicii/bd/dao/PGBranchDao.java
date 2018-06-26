package it.uniroma2.dicii.bd.dao;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.BranchDao;
import it.uniroma2.dicii.bd.interfaces.GPointDao;
import it.uniroma2.dicii.bd.model.Branch;
import it.uniroma2.dicii.bd.model.GPoint;
import it.uniroma2.dicii.bd.model.GPointBranch;

import java.sql.*;

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

    @Override
    public GPoint getBranchMaxVertex(Branch branch) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        GPoint maxVertex = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            maxVertex = this._getBranchVertex(branch, Integer.MAX_VALUE, conn);

            conn.close();

        } catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {
                if (conn != null)
                    conn.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }

        return maxVertex;
    }

    @Override
    public GPoint getBranchMinVertex(Branch branch) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        GPoint maxVertex = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            maxVertex = this._getBranchVertex(branch, Integer.MIN_VALUE, conn);

            conn.close();

        } catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {
                if (conn != null)
                    conn.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }

        return maxVertex;
    }

    private GPoint _getBranchVertex(Branch branch, Integer value, Connection conn) throws DaoException {

        Statement stmt = null;
        GPoint vertex = null;
        String sql = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            if (value == Integer.MAX_VALUE){

                sql = "SELECT sequence, idbranch, galactic_longitude, galactic_latitude " +
                        "FROM filament " +
                        "JOIN filament_branch ON filament_branch.filament=filament.idfil " +
                        "JOIN branch ON filament_branch.branch = branch.idbranch " +
                        "WHERE idbranch=" + branch.getIdBranch() + " AND sequence IN " +
                        "(  SELECT MAX(sequence) " +
                        "   FROM filament " +
                        "   JOIN filament_branch ON filament_branch.filament=filament.idfil " +
                        "   WHERE filament_branch.branch='" + branch.getIdBranch() + "');";

            }else if(value == Integer.MIN_VALUE){

                sql = "SELECT sequence, idbranch, galactic_longitude, galactic_latitude " +
                        "FROM filament " +
                        "JOIN filament_branch ON filament_branch.filament=filament.idfil " +
                        "JOIN branch ON filament_branch.branch = branch.idbranch " +
                        "WHERE idbranch=" + branch.getIdBranch() + " AND sequence IN " +
                        "(  SELECT MIN(sequence) " +
                        "   FROM filament " +
                        "   JOIN filament_branch ON filament_branch.filament=filament.idfil " +
                        "   WHERE filament_branch.branch='" + branch.getIdBranch() + "');";

            }else{

                throw new DaoException("Wrong constant");
            }

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first())
                throw new DaoException("Branch not found");

            rs.first();

            vertex = new GPoint(rs.getDouble("galactic_longitude"), rs.getDouble("galactic_latitude"));

            // Clean-up
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());

        } finally {

            try {

                if (stmt != null)
                    stmt.close();

                if (conn != null)
                    conn.close();

            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }
        }

        return vertex;
    }

    private void _insertBranch(Branch branch, Connection connection) throws DaoException {

        final String sql = "INSERT INTO branch(idbranch, filament, type) values (?,?,?) ON conflict (idbranch, filament) DO UPDATE SET type=excluded.type";

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

        final String sql = "INSERT INTO filament_branch(branch, filament, galactic_longitude, galactic_latitude, sequence, flux) values (?,?,?,?,?,?) ON conflict (branch, filament, galactic_longitude, galactic_latitude)" +
                "DO UPDATE SET sequence=excluded.sequence, flux=excluded.flux";

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
