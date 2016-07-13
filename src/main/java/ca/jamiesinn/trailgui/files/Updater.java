package ca.jamiesinn.trailgui.files;

import ca.jamiesinn.trailgui.TrailGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Updater
{
    private TrailGUI trailGUI;

    public Updater(TrailGUI trailGUI)
    {
        this.trailGUI = trailGUI;

    }

    private int getLatestBuildNumber() throws IOException
    {
        URL url = new URL("http://wat.sinnpi.com/api.php?api=latestjenkins&server=http://ci.md-5.net&job=TrailGUI");
        URLConnection conn = url.openConnection();
        String output = "";
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream())))
        {

            String inputLine;

            while ((inputLine = br.readLine()) != null)
            {
                output += inputLine;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return Integer.parseInt(output);
    }

    private int getCurrentBuildNumber()
    {
        //Format: ${primaryVersion}-b${BUILD_NUMBER}-${releaseType}
        String currentVersion = trailGUI.getDescription().getVersion();
        String[] split = currentVersion.split("-");
        String build = split[1].replaceAll("[A-z]", "");
        return Integer.parseInt(build);
    }

    private boolean isItUpToDate() throws IOException
    {
        return getLatestBuildNumber() <= getCurrentBuildNumber();
    }


    public void check() throws IOException
    {
        if (isItUpToDate()) return;

        trailGUI.getLogger().info("You are not running the latest build of TrailGUI. The latest build is "
                + getLatestBuildNumber()
                + " and your build is " + getCurrentBuildNumber() + ". Please update");
    }
}
