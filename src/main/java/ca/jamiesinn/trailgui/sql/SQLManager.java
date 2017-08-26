package ca.jamiesinn.trailgui.sql;

import ca.jamiesinn.trailgui.TrailGUI;
import ca.jamiesinn.trailgui.trails.Trail;

import java.sql.*;
import java.util.*;

public class SQLManager
{
    private Connection connection;
    private String host;
    private int port;
    private String database;
    private String user;
    private String pass;


    private TrailGUI pl = TrailGUI.getPlugin();

    /**
     * @param host     host
     * @param port     port
     * @param database database
     * @param user     user
     * @param pass     pass
     * @throws SQLException exception
     */
    public SQLManager(String host, int port, String database, String user, String pass) throws SQLException
    {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.pass = pass;

        boolean res = connectToDatabase(host, port, database, user, pass);
        if (connection != null || !res)
        {
            createTables(connection);
        }
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

    public void insertUser(UUID player, List<String> active) throws SQLException
    {
        if (!isConnAlive()) return;
        PreparedStatement statement;
        String insertRowSQL =
                "INSERT INTO userdata" +
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

    public HashMap<UUID, List<Trail>> getTrails() throws SQLException
    {

        HashMap<UUID, List<Trail>> result = new HashMap<>();
        if (!isConnAlive()) return result;
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
        while (uuidRS.next() && trailRS.next())
        {
            List<String> parsed = Arrays.asList(trailRS.getString("trails").replaceAll("\\[|\\]", "").split(", "));
            if (parsed.isEmpty())
                continue;
            players.add(UUID.fromString(uuidRS.getString("uuid")));
            rawTrails.add(parsed);
        }


        for (List<String> l : rawTrails)
        {
            List<Trail> tr = new ArrayList<>();
            for (String s : l)
            {
                Trail t = TrailGUI.trailTypes.get(s);
                tr.add(t);
            }
            trails.add(tr);
        }
        for (UUID p : players)
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
            if (connection != null && !connection.isClosed())
                connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return true;
    }

    private boolean isConnAlive() throws SQLException
    {
        if (this.connection.isValid(500))
            return true;
        else
        {
            connectToDatabase(this.host, this.port, this.database, this.user, this.pass);
            if (this.connection != null)
            {
                createTables(this.connection);
                return true;
            }
            else
            {
                this.pl.getLogger().severe("Database was not connected, and connection was not able to be re-established.");
                return false;
            }
        }
    }
}
