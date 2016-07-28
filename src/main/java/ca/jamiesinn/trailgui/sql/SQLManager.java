package ca.jamiesinn.trailgui.sql;

import ca.jamiesinn.trailgui.TrailGUI;
import ca.jamiesinn.trailgui.trails.Trail;

import java.sql.*;
import java.util.*;

public class SQLManager
{
    private Connection connection;

    private TrailGUI pl;

    /**
     *
     * @param plugin plugin
     * @param host host
     * @param port port
     * @param database database
     * @param user user
     * @param pass pass
     * @throws SQLException
     */
    public SQLManager(TrailGUI plugin, String host, int port, String database, String user, String pass) throws SQLException
    {
        connectToDatabase(host, port, database, user, pass);
        if (connection != null)
        {
            createTables(connection);
        }
        this.pl = plugin;
    }

    public Connection getConnection()
    {
        return connection;
    }

    private boolean connectToDatabase(String host, int port, String database, String user, String pass)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        try
        {
            pl.getLogger().info("MySQL JDBC Driver Registered");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, pass);
        }
        catch (SQLException e)
        {
            pl.getLogger().warning("Connection Failed. Check config.");
            e.printStackTrace();
            return false;
        }
        pl.getLogger().info("Connected to MySQL Database Successfully.");
        return connection != null;
    }

    private void createTables(Connection connection) throws SQLException
    {
        PreparedStatement stmt;
        String query =
                "CREATE TABLE IF NOT EXISTS userdata"
                        + "(uuid VARCHAR(64) NOT NULL UNIQUE,"
                        + "trails VARCHAR(255));";
        stmt = connection.prepareStatement(query);
        stmt.executeUpdate();
        stmt.close();

    }

    public void insertUser(UUID player, List<Trail> active) throws SQLException
    {
        PreparedStatement statement;
        String insertRowSQL =
                "INSERT INTO userdata"+
                        "(uuid, trails) VALUES" +
                        "(?,?)" +
                        "ON DUPLICATE KEY UPDATE trails=?";
        statement = connection.prepareStatement(insertRowSQL);
        statement.setString(1, player.toString());
        statement.setString(2, active.toString());
        statement.setString(3, active.toString());
        statement.executeUpdate();
        statement.close();
    }

    private boolean exists(Connection connection, UUID player) throws SQLException
    {
        PreparedStatement statement;
        String existsSQL = "SELECT 1 FROM userdata WHERE uuid = ?";

        statement = connection.prepareStatement(existsSQL);
        statement.setString(1, player.toString());
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    public HashMap<UUID, List<Trail>> getTrails() throws SQLException
    {
        HashMap<UUID, List<Trail>> result = new HashMap<>();
        PreparedStatement uuidSt;
        PreparedStatement trailSt;
        String uuidSQL = "SELECT uuid FROM userdata WHERE trails IS NOT NULL";
        String trailsSQL = "SELECT trails FROM userdata WHERE trails IS NOT NULL";
        trailSt = connection.prepareStatement(trailsSQL);
        uuidSt = connection.prepareStatement(uuidSQL);


        ResultSet uuidRS = uuidSt.executeQuery();
        ResultSet trailRS = trailSt.executeQuery();

        List<UUID> players = new ArrayList<>();
        List<List<Trail>> trails = new ArrayList<>();
        List<List<String>> rawTrails = new ArrayList<>();
        while(uuidRS.next() && trailRS.next())
        {
            players.add(UUID.fromString(uuidRS.getString("uuid")));
            rawTrails.add(Arrays.asList(trailRS.getString("trails").split(",")));

        }
        for(List<String> l: rawTrails)
        {
            List<Trail> tr = new ArrayList<>();
            for(String s : l)
            {
                Trail t = TrailGUI.trailTypes.get(s);
                tr.add(t);
            }
            trails.add(tr);
        }
        for(UUID p : players)
        {
            int index = players.indexOf(p);
            result.put(p, trails.get(index));
        }
        return result;
    }

    public boolean disconnect()
    {
        try
        {
            if(connection != null && !connection.isClosed())
                connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return true;
    }
}
