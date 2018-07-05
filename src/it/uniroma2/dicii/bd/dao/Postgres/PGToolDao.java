package it.uniroma2.dicii.bd.dao.Postgres;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.ToolDao;
import it.uniroma2.dicii.bd.model.Satellite;
import it.uniroma2.dicii.bd.model.Tool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PGToolDao implements ToolDao{

    @Override
    public List<Tool> getTools(Satellite satellite) throws DaoException {

        List<Tool> list = new ArrayList<Tool>();

        Statement stmt = null;
        Connection conn = null;

        try {

            PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
            conn = manager.getConnectionFromConnectionPool();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT tool.name as tool, satellite.name as satellite, band " +
                    "FROM tool " +
                    "JOIN satellite ON tool.satellite = satellite.name " +
                    "WHERE satellite='" + satellite.getName() + "'";

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {

                Tool tool = new Tool(rs.getString("tool"));
                tool.setBand(rs.getString("band"));

                tool.setSatellite(satellite);
                list.add(tool);
            }

            // Clean-up
            rs.close();
            stmt.close();
            conn.close();

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

        return list;
    }

    @Override
    public List<Tool> getAllTools() throws DaoException {

        List<Tool> list = new ArrayList<Tool>();

        Statement stmt = null;
        Connection conn = null;

        try {

            PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
            conn = manager.getConnectionFromConnectionPool();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT tool.name as tool, satellite.name as satellite, band " +
                    "FROM tool " +
                    "JOIN satellite ON tool.satellite = satellite.name";

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {

                Tool tool = new Tool(rs.getString("tool"));
                tool.setBand(rs.getString("band"));

                Satellite satellite = new Satellite(rs.getString("satellite"));

                tool.setSatellite(satellite);

                list.add(tool);
            }

            // Clean-up
            rs.close();
            stmt.close();
            conn.close();

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

        return list;
    }

    @Override
    public void insertTool(Tool tool) throws DaoException {

        PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
        PreparedStatement preparedStatement = null;
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            final String sql = "INSERT INTO tool(name, band, satellite) values (?,?,?)";

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, tool.getName());
            preparedStatement.setString(2, tool.getBandString());
            preparedStatement.setString(3, tool.getSatellite().getName());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

        }  catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {
                if (conn != null)
                    conn.close();

                if (preparedStatement != null)
                    preparedStatement.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }
    }

    @Override
    public void deleteTool(Tool tool) throws DaoException {

        PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
        PreparedStatement preparedStatement = null;
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            final String sql = "DELETE FROM tool WHERE name='" + tool.getName() + "'";

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            conn.close();

        }  catch (SQLException e) {

            throw new DaoException(e.getMessage(), e.getCause());

        } finally {

            try {
                if (conn != null)
                    conn.close();

                if (preparedStatement != null)
                    preparedStatement.close();

            } catch (SQLException e) {

                throw new DaoException(e.getMessage(), e.getCause());
            }
        }
    }
}
