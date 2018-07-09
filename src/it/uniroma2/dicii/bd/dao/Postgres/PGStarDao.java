package it.uniroma2.dicii.bd.dao.Postgres;

import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.enumeration.StarType;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.interfaces.GPointDao;
import it.uniroma2.dicii.bd.interfaces.StarDao;
import it.uniroma2.dicii.bd.model.Filament;
import it.uniroma2.dicii.bd.model.GPoint;
import it.uniroma2.dicii.bd.model.Star;
import it.uniroma2.dicii.bd.model.Tool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PGStarDao implements StarDao{

    @Override
    public void insertStar(Star star) throws DaoException {

        PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
        Connection conn = null;

        try {

            conn = manager.getConnectionFromConnectionPool();

            GPointDao dao = DaoFactory.getSingletonInstance().getGPointDAO();
            dao.insertGPoint(star.getPosition());

            this._insertStar(star, conn);

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
    public List<Star> getStarsInsideRectRegion(GPoint center, Float side1, Float side2) throws DaoException {

        PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
        Connection conn = null;
        List<Star> starList = new ArrayList<>();

        try {

            conn = manager.getConnectionFromConnectionPool();

            starList = _getStarsInsideRectRegion(center, side1, side2, starList, conn);

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

        return starList;
    }

    @Override
    public List<Star> getStarsInsideFilamentByID(Integer filamentID) throws DaoException {

        PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
        Connection conn = null;

        List<Star> starList = new ArrayList<>();

        try {

            conn = manager.getConnectionFromConnectionPool();

            Filament filament = new Filament(filamentID);

            starList = this._getStarsInsideFilament(filament, starList, null, conn);

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

        return starList;
    }

    @Override
    public List<Star> getStarsInsideFilamentOrdered(Integer filamentID, String orderby) throws DaoException {

        PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
        Connection conn = null;

        List<Star> starList = new ArrayList<>();

        try {

            conn = manager.getConnectionFromConnectionPool();

            Filament filament = new Filament(filamentID);

            starList = this._getStarsInsideFilament(filament, starList, orderby, conn);

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

        return starList;
    }

    @Override
    public List<Star> areInsideFilament(List<Star> starList) throws DaoException {

        PGConnectionManager manager = PGConnectionManager.getSingletonInstance();
        Connection conn = null;
        List<Star> stars;

        try {

            conn = manager.getConnectionFromConnectionPool();

            stars = this._checkIfStarsAreInsideFilament(starList, conn);

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

        return stars;
    }

    private List<Star> _checkIfStarsAreInsideFilament(List<Star> starList, Connection conn) throws DaoException {

        Statement stmt = null;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT * FROM filament";

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                Filament filament = new Filament(rs.getInt("idfil"));

                FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();
                List<GPoint> filamentBoundary = dao.getFilamentBoundary(filament);

                for (Star star : starList){

                    // not check again if set
                    if (star.getFilament() != null)
                        continue;

                    Double atanResult = 0.0;

                    for(int i=0; i < filamentBoundary.size()-1; i++) {

                        GPoint actualBoundaryPoint = filamentBoundary.get(i);
                        GPoint nextBoundaryPoint = filamentBoundary.get(i+1);

                        Double num = ((actualBoundaryPoint.getGlongitude() - star.getPosition().getGlongitude())*(nextBoundaryPoint.getGlatitude() - star.getPosition().getGlatitude())) -
                                ((actualBoundaryPoint.getGlatitude()-star.getPosition().getGlatitude())*(nextBoundaryPoint.getGlongitude()-star.getPosition().getGlongitude()));

                        Double den = ((actualBoundaryPoint.getGlongitude() - star.getPosition().getGlongitude())*(nextBoundaryPoint.getGlongitude() - star.getPosition().getGlongitude())) +
                                ((actualBoundaryPoint.getGlatitude()-star.getPosition().getGlatitude())*(nextBoundaryPoint.getGlatitude()-star.getPosition().getGlatitude()));

                        atanResult += Math.atan(num/den);

                    }

                    // yes it is!
                    if (Math.abs(atanResult) >= 0.01)
                        star.setFilament(filament);
                    else
                        star.setFilament(null);

                }

                // exit-condition
                Boolean completed = true;
                for (Star star : starList){
                    if (star.getFilament() == null)
                        completed = false;
                }
                if (completed)
                    break;

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

        return starList;

    }

    private List<Star> _getStarsInsideFilament(Filament filament, List<Star> starList, String orderby, Connection conn) throws DaoException {

        Statement stmt = null;
        String sql;

        try {

            FilamentDao dao = DaoFactory.getSingletonInstance().getFilamentDAO();
            List<GPoint> filamentBoundary = dao.getFilamentBoundary(filament);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            if (orderby != null){
                sql = "SELECT * FROM star ORDER BY " + orderby + " ASC";
            }else{
                sql = "SELECT * FROM star";
            }

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                Star star = new Star(rs.getInt("idstar"));
                star.setName(rs.getString("name_star"));
                star.setFlux(rs.getBigDecimal("flux"));
                star.setType(StarType.valueOf(rs.getString("type")));
                star.setPosition(new GPoint(rs.getDouble("galactic_longitude"), rs.getDouble("galactic_latitude")));
                star.setTool(new Tool(rs.getString("nametool")));

                Double atanResult = 0.0;

                for(int i=0; i < filamentBoundary.size()-1; i++) {

                    GPoint actualBoundaryPoint = filamentBoundary.get(i);
                    GPoint nextBoundaryPoint = filamentBoundary.get(i+1);

                    Double num = ((actualBoundaryPoint.getGlongitude() - star.getPosition().getGlongitude())*(nextBoundaryPoint.getGlatitude() - star.getPosition().getGlatitude())) -
                            ((actualBoundaryPoint.getGlatitude()-star.getPosition().getGlatitude())*(nextBoundaryPoint.getGlongitude()-star.getPosition().getGlongitude()));

                    Double den = ((actualBoundaryPoint.getGlongitude() - star.getPosition().getGlongitude())*(nextBoundaryPoint.getGlongitude() - star.getPosition().getGlongitude())) +
                            ((actualBoundaryPoint.getGlatitude()-star.getPosition().getGlatitude())*(nextBoundaryPoint.getGlatitude()-star.getPosition().getGlatitude()));

                    atanResult += Math.atan(num/den);

                }

                if (Math.abs(atanResult) >= 0.01)
                    starList.add(star);
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

        return starList;

    }

    private List<Star> _getStarsInsideRectRegion(GPoint center, Float side1, Float side2, List<Star> starList, Connection conn) throws DaoException {

        Statement stmt = null;

        Double qx = center.getGlongitude() - side1/2;
        Double qy = center.getGlatitude() + side2/2;
        Double rx = center.getGlongitude() + side1/2;
        Double py = center.getGlatitude() - side2/2;

        try {

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT * " +
                    "FROM star " +
                    "WHERE  galactic_longitude < " + rx + " " +
                    "AND galactic_longitude > " + qx + " " +
                    "AND galactic_latitude < " + qy + " " +
                    "AND galactic_latitude > " + py;

            // execute
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){

                Star star = new Star(rs.getInt("idstar"));
                star.setName(rs.getString("name_star"));
                star.setFlux(rs.getBigDecimal("flux"));
                star.setType(StarType.valueOf(rs.getString("type")));
                star.setPosition(new GPoint(rs.getDouble("galactic_longitude"), rs.getDouble("galactic_latitude")));
                star.setTool(new Tool(rs.getString("nametool")));
                starList.add(star);
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

        return starList;
    }

    private void _insertStar(Star star, Connection connection) throws DaoException {

        final String sql = "INSERT INTO star(idstar, name_star, type, flux, galactic_longitude, galactic_latitude, nameTool) " +
                "values (?,?,?,?,?,?,?) ON conflict (idstar) " +
                "DO UPDATE SET name_star=excluded.name_star, type=excluded.type, " +
                "           flux=excluded.flux, galactic_longitude=excluded.galactic_longitude, " +
                "           galactic_latitude=excluded.galactic_latitude, nameTool=excluded.nameTool";

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, star.getIdStar());
            preparedStatement.setString(2, star.getName());
            preparedStatement.setString(3, String.valueOf(star.getType()));
            preparedStatement.setBigDecimal(4, star.getFlux());
            preparedStatement.setDouble(5, star.getPosition().getGlongitude());
            preparedStatement.setDouble(6, star.getPosition().getGlatitude());
            preparedStatement.setString(7, star.getTool().getName());

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
}
