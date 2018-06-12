package it.uniroma2.dicii.bd.dao;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.interfaces.GPointDao;
import it.uniroma2.dicii.bd.model.Filament;
import it.uniroma2.dicii.bd.model.GPoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PGFilamentDao implements FilamentDao{

    @Override
    public void insertFilament(Filament filament) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            this._insertFilament(filament, conn);

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
    public void insertAllBoundaryPointPerFilament(Filament filament) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;

        try {

            GPointDao dao = DaoFactory.getSingletonInstance().getGPointDAO();

            conn = manager.getConnectionFromConnectionPool();

            for (GPoint point : filament.getBoundary()) {
                dao.insertGPoint(point);
                this._insertFilamentBoundaryPointRelation(filament, point, conn);
            }

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
    }

    @Override
    public GPoint getFilamentCentroidByID(Integer filamentID) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        GPoint centoid = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            Filament filament = new Filament(filamentID);

            centoid = this._getFilamentCentroid(filament, conn);

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

        return centoid;
    }

    @Override
    public GPoint getFilamentCentroidByName(String filamentName) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        GPoint centoid = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            Filament filament = new Filament(null);
            filament.setName(filamentName);

            centoid = this._getFilamentCentroid(filament, conn);

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

        return centoid;
    }

    @Override
    public GPoint getFilamentExtensionByID(Integer filamentID) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        GPoint centoid = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            Filament filament = new Filament(filamentID);

            centoid = this._getFilamentExtension(filament, conn);

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

        return centoid;
    }

    @Override
    public GPoint getFilamentExtensionByName(String filamentName) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        GPoint centoid = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            Filament filament = new Filament(null);
            filament.setName(filamentName);

            centoid = this._getFilamentExtension(filament, conn);

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

        return centoid;
    }

    @Override
    public Integer getCountFilamentSegmentByName(String filamentName) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        Integer numberOfsegment = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            Filament filament = new Filament(null);
            filament.setName(filamentName);

            numberOfsegment = this._getCountFilamentSegment(filament, conn);

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

        return numberOfsegment;
    }

    @Override
    public Integer getCountFilamentSegmentByID(Integer filamentID) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        Integer numberOfsegment = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            Filament filament = new Filament(filamentID);

            numberOfsegment = this._getCountFilamentSegment(filament, conn);

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

        return numberOfsegment;
    }

    @Override
    public List<Filament> getFilamentsByLuminanceAndEllipticity(Double percentageLuminance, Float ellipticity_min, Float ellipticity_max, Integer limit, Integer offset) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        List<Filament> filamentList = new ArrayList<>();

        try {

            conn = manager.getConnectionFromConnectionPool();

            filamentList = _getFilamentsByLuminanceAndEllipticity(percentageLuminance, ellipticity_min, ellipticity_max, limit, offset, filamentList, conn);

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

        return filamentList;
    }

    @Override
    public List<Filament> getFilamentsByNumberOfSegments(Integer from, Integer to, Integer limit, Integer offset) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        List<Filament> filamentList = new ArrayList<>();

        try {

            conn = manager.getConnectionFromConnectionPool();

            filamentList = _getFilamentsByNumberOfSegments(from, to, filamentList, limit, offset, conn);

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

        return filamentList;
    }

    @Override
    public List<Filament> getFilamentInsideSquareRegion(GPoint center, Float side, Integer limit, Integer offset) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        List<Filament> filamentList = new ArrayList<>();

        try {

            conn = manager.getConnectionFromConnectionPool();

            filamentList = _getFilamentInsideSquareRegion(center, side, filamentList, conn, limit, offset);

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

        return filamentList;
    }

    @Override
    public List<Filament> getFilamentInsideCircleRegion(GPoint center, Float radius, Integer limit, Integer offset) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        List<Filament> filamentList = new ArrayList<>();

        try {

            conn = manager.getConnectionFromConnectionPool();

            filamentList = _getFilamentInsideCircleRegion(center, radius, filamentList, conn, limit, offset);

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

        return filamentList;
    }

    @Override
    public List<GPoint> getFilamentBoundary(Filament filament) throws DaoException {

        ConnectionManager manager = ConnectionManager.getSingletonInstance();
        Connection conn = null;
        List<GPoint> boundary = new ArrayList<>();

        try {

            conn = manager.getConnectionFromConnectionPool();

            boundary = this._getFilamentBoundary(filament, boundary, conn);

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

        return boundary;
    }

    // PRIVATE

    private List<Filament> _getFilamentInsideSquareRegion(GPoint center, Float side, List<Filament> filamentList, Connection conn, Integer limit, Integer offset) throws DaoException {

        Statement stmt = null;

        Double diagonal = side*Math.sqrt(2);

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT DISTINCT on (filament) filament, idfil, name, total_flux, mean_density, mean_temperature, ellipticity, contrast " +
                    "FROM filament_boundary " +
                    "JOIN filament ON filament.idfil=filament_boundary.filament " +
                    "WHERE sqrt(power(galactic_longitude - " + center.getGlongitude() + ",2) + power(galactic_latitude - " + center.getGlatitude() + ",2)) <= " + diagonal/2 +
                    " AND " + (center.getGlongitude() - side/2) + " <= galactic_longitude" +
                    " AND galactic_longitude <= " + (center.getGlongitude() + side/2) +
                    " AND " + (center.getGlatitude() - side/2) + " <= galactic_latitude" +
                    " AND galactic_latitude <= " + (center.getGlatitude() + side/2) +
                    " AND filament NOT IN " +
                    "   (SELECT DISTINCT filament " +
                    "    FROM filament_boundary " +
                        "WHERE sqrt(power(galactic_longitude - " + center.getGlongitude() + ",2) + power(galactic_latitude - " + center.getGlatitude() + ",2)) > " + diagonal/2 +
                        " AND " + (center.getGlongitude() - side/2) + " > galactic_longitude" +
                        " AND galactic_longitude > " + (center.getGlongitude() + side/2) +
                        " AND " + (center.getGlatitude() - side/2) + " > galactic_latitude" +
                        " AND galactic_latitude > " + (center.getGlatitude() + side/2) +
                    ") ORDER BY filament LIMIT " + limit +
                    " OFFSET " + offset;

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                Filament filament = new Filament(rs.getInt("idfil"));
                filament.setName(rs.getString("name"));
                filament.setTotalFlux(rs.getBigDecimal("total_flux"));
                filament.setMeanDensity(rs.getBigDecimal("mean_density"));
                filament.setMeanTemperature(rs.getFloat("mean_temperature"));
                filament.setEllipticity(rs.getFloat("ellipticity"));
                filament.setContrast(rs.getFloat("contrast"));

                filamentList.add(filament);
            }

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

        return filamentList;

    }

    private List<Filament> _getFilamentInsideCircleRegion(GPoint center, Float radius, List<Filament> filamentList, Connection conn, Integer limit, Integer offset) throws DaoException {

        Statement stmt = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT DISTINCT on (filament) filament, idfil, name, total_flux, mean_density, mean_temperature, ellipticity, contrast " +
                    "FROM filament_boundary " +
                    "JOIN filament ON filament.idfil=filament_boundary.filament " +
                    "WHERE sqrt(power(galactic_longitude - " + center.getGlongitude() + ",2)+power(galactic_latitude - " + center.getGlatitude() + ",2)) <= " + radius +
                    " AND filament NOT IN " +
                    "   (SELECT DISTINCT filament " +
                    "    FROM filament_boundary " +
                    "    WHERE sqrt(power(galactic_longitude- " + center.getGlongitude() + " ,2)+power(galactic_latitude - " + center.getGlatitude() + " ,2)) > " + radius + ") " +
                    "LIMIT " + limit +
                    " OFFSET " + offset;

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                Filament filament = new Filament(rs.getInt("idfil"));
                filament.setName(rs.getString("name"));
                filament.setTotalFlux(rs.getBigDecimal("total_flux"));
                filament.setMeanDensity(rs.getBigDecimal("mean_density"));
                filament.setMeanTemperature(rs.getFloat("mean_temperature"));
                filament.setEllipticity(rs.getFloat("ellipticity"));
                filament.setContrast(rs.getFloat("contrast"));

                filamentList.add(filament);
            }

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

        return filamentList;
    }

    private List<Filament> _getFilamentsByNumberOfSegments(Integer from, Integer to, List<Filament> filamentList, Integer limit, Integer offset, Connection conn) throws DaoException {

        Statement stmt = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT idfil, name, total_flux, mean_density, mean_temperature, ellipticity, contrast " +
                    "FROM filament " +
                    "JOIN filament_branch ON filament_branch.filament=filament.idfil " +
                    "JOIN branch ON filament_branch.branch = branch.idbranch " +
                    "GROUP BY filament.idfil " +
                    "HAVING COUNT(DISTINCT idbranch) >= " + from + " AND COUNT(DISTINCT idbranch) <= " + to + " ORDER BY idfil " +
                    "LIMIT " + limit +
                    "OFFSET " + offset;

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                Filament filament = new Filament(rs.getInt("idfil"));
                filament.setName(rs.getString("name"));
                filament.setTotalFlux(rs.getBigDecimal("total_flux"));
                filament.setMeanDensity(rs.getBigDecimal("mean_density"));
                filament.setMeanTemperature(rs.getFloat("mean_temperature"));
                filament.setEllipticity(rs.getFloat("ellipticity"));
                filament.setContrast(rs.getFloat("contrast"));

                filamentList.add(filament);
            }

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

        return filamentList;
    }

    private List<Filament> _getFilamentsByLuminanceAndEllipticity(Double percentageLuminance, Float ellipticity_min, Float ellipticity_max, Integer limit, Integer offset, List<Filament> filamentList, Connection conn) throws DaoException {

        Statement stmt = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            Double contrast = (1 + (percentageLuminance/100));

            String sql = "SELECT * " +
                    "FROM filament " +
                    "WHERE contrast >= " + contrast + " and ellipticity >= " +  ellipticity_min + " and ellipticity <= " +  ellipticity_max +
                    " ORDER BY idfil" +
                    " LIMIT " + limit +
                    " OFFSET " + offset;

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                Filament filament = new Filament(rs.getInt("idfil"));
                filament.setName(rs.getString("name"));
                filament.setTotalFlux(rs.getBigDecimal("total_flux"));
                filament.setMeanDensity(rs.getBigDecimal("mean_density"));
                filament.setMeanTemperature(rs.getFloat("mean_temperature"));
                filament.setEllipticity(rs.getFloat("ellipticity"));
                filament.setContrast(rs.getFloat("contrast"));

                filamentList.add(filament);
            }

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

        return filamentList;
    }

    private GPoint _getFilamentCentroid(Filament filament, Connection conn) throws DaoException {

        Statement stmt = null;
        GPoint centroid = null;
        String sql = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            if (filament.getIdfil() != null){

                sql = "SELECT AVG(galactic_longitude) AS longitude, AVG(galactic_latitude) AS latitude " +
                        "FROM filament " +
                        "JOIN filament_boundary ON filament.idfil=filament_boundary.filament " +
                        "WHERE idfil='" + filament.getIdfil() +"'";

            }else if(filament.getName() != null){

                sql = "SELECT AVG(galactic_longitude) AS longitude, AVG(galactic_latitude) AS latitude " +
                        "FROM filament " +
                        "JOIN filament_boundary ON filament.idfil=filament_boundary.filament " +
                        "WHERE name='" + filament.getName() +"'";

            }else{

                throw new DaoException("No Name, ID provided");
            }

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first())
                throw new DaoException("Filament not found");

            rs.first();

            centroid = new GPoint(rs.getDouble("longitude"), rs.getDouble("latitude"));


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

        return centroid;

    }

    private GPoint _getFilamentExtension(Filament filament, Connection conn) throws DaoException {

        Statement stmt = null;
        GPoint centroid = null;
        String sql = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            if (filament.getIdfil() != null){

                sql = "SELECT MAX(galactic_longitude)-MIN(galactic_longitude) AS longitude, MAX(galactic_latitude)-MIN(galactic_latitude) AS latitude " +
                        "FROM filament " +
                        "JOIN filament_boundary ON filament.idfil=filament_boundary.filament " +
                        "WHERE idfil='" + filament.getIdfil() +"'";

            }else if(filament.getName() != null){

                sql = "SELECT MAX(galactic_longitude)-MIN(galactic_longitude) AS longitude, MAX(galactic_latitude)-MIN(galactic_latitude) AS latitude " +
                        "FROM filament " +
                        "JOIN filament_boundary ON filament.idfil=filament_boundary.filament " +
                        "WHERE name='" + filament.getName() +"'";

            }else{

                throw new DaoException("No Name, ID provided");
            }

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first())
                throw new DaoException("Filament not found");

            rs.first();

            centroid = new GPoint(rs.getDouble("longitude"), rs.getDouble("latitude"));

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

        return centroid;

    }

    private Integer _getCountFilamentSegment(Filament filament, Connection conn) throws DaoException {

        Statement stmt = null;
        Integer numberOfSegment = 0;
        String sql = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            if (filament.getIdfil() != null){

                sql = "SELECT COUNT(DISTINCT branch) " +
                        "FROM filament_branch " +
                        "JOIN branch ON filament_branch.branch = branch.idbranch " +
                        "WHERE filament_branch.filament='" + filament.getIdfil() +"'";

            }else if(filament.getName() != null){

                sql = "SELECT COUNT(DISTINCT branch) " +
                        "FROM filament_branch " +
                        "JOIN filament ON filament_branch.filament=filament.idfil " +
                        "JOIN branch ON filament_branch.branch = branch.idbranch " +
                        "WHERE filament.name='" + filament.getName() +"'";

            }else{

                throw new DaoException("No Name, ID provided");
            }

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.first())
                throw new DaoException("Filament not found");

            rs.first();

            numberOfSegment = rs.getInt("count");

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

        return numberOfSegment;

    }

    private void _insertFilament(Filament filament, Connection connection) throws DaoException {

        final String sql = "INSERT INTO filament(idfil, name, total_flux, mean_density, mean_temperature, ellipticity, contrast, nameTool) " +
                "values (?,?,?,?,?,?,?,?) ON conflict (idfil) " +
                "DO UPDATE SET name=excluded.name, total_flux=excluded.total_flux, " +
                "                   mean_density=excluded.mean_density, mean_temperature=excluded.mean_temperature, " +
                "                   ellipticity=excluded.ellipticity, contrast=excluded.contrast, nameTool=excluded.nameTool";

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, filament.getIdfil());
            preparedStatement.setString(2, filament.getName());
            preparedStatement.setBigDecimal(3, filament.getTotalFlux());
            preparedStatement.setBigDecimal(4, filament.getMeanDensity());
            preparedStatement.setFloat(5, filament.getMeanTemperature());
            preparedStatement.setFloat(6, filament.getEllipticity());
            preparedStatement.setFloat(7, filament.getContrast());
            preparedStatement.setString(8, filament.getTool().getName());
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

    private void _insertFilamentBoundaryPointRelation(Filament filament, GPoint point, Connection connection) throws DaoException {

        final String sqlFilamentPoint = "INSERT INTO filament_boundary(filament, galactic_longitude, galactic_latitude) " +
                "values (?,?,?) ON conflict DO NOTHING";

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sqlFilamentPoint);

            preparedStatement.setInt(1, filament.getIdfil());
            preparedStatement.setDouble(2, point.getGlongitude());
            preparedStatement.setDouble(3, point.getGlatitude());

            preparedStatement.executeUpdate();

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

    private List<GPoint> _getFilamentBoundary(Filament filament, List<GPoint> boundary ,Connection conn) throws DaoException {

        Statement stmt = null;

        try {

            ConnectionManager manager = ConnectionManager.getSingletonInstance();
            conn = manager.getConnectionFromConnectionPool();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT * " +
                    "FROM filament_boundary " +
                    "WHERE filament='" + filament.getIdfil() +"'";

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                GPoint point = new GPoint(rs.getDouble("galactic_longitude"), rs.getDouble("galactic_latitude"));
                boundary.add(point);
            }

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

        return boundary;
    }
}
