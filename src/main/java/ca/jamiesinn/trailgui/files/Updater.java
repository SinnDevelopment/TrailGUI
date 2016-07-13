package ca.jamiesinn.trailgui.files;

import ca.jamiesinn.trailgui.TrailGUI;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Artifact;
import com.offbytwo.jenkins.model.JobWithDetails;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Updater
{
    private JenkinsServer jenkins = new JenkinsServer(new URI("http://ci.md-5.net"));
    private JobWithDetails job;
    private TrailGUI trailGUI;

    public Updater(TrailGUI trailGUI) throws URISyntaxException, IOException
    {
        this.trailGUI = trailGUI;
        getTrailGUIJob();
    }

    private void getTrailGUIJob() throws IOException
    {
        this.job = jenkins.getJobs().get("TrailGUI").details();
    }

    private int getLatestBuildNumber() throws IOException
    {
        return job.getLastStableBuild().getNumber();
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

    private boolean downloadLatest() throws IOException, URISyntaxException
    {
        if (isItUpToDate()) return false;

        Artifact latest = job.getLastStableBuild().details().getArtifacts().get(0);
        ReadableByteChannel rbc = Channels.newChannel(job.getLastStableBuild().details().downloadArtifact(latest));
        File outputFile = new File(trailGUI.getDataFolder() + File.pathSeparator +latest.getFileName());
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        if(outputFile.exists()) return false;
        outputStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        return true;
    }

    public void check() throws IOException, URISyntaxException
    {
        if (isItUpToDate()) return;

        trailGUI.getLogger().info("You are not running the latest build of TrailGUI. The latest build is "
                + getLatestBuildNumber()
        +" and your build is " + getCurrentBuildNumber() + ".");
        if(!trailGUI.getConfig().getBoolean("auto-dl-latest"))
            trailGUI.getLogger().info("To download the latest version please use this URL: " + job.getLastStableBuild().getUrl());
        else
        {
            trailGUI.getLogger().info("Downloading the latest file");
            if (!downloadLatest())
                trailGUI.getLogger().info("File with the name of " + job.getLastStableBuild().details().getArtifacts().get(0).getFileName() + " already exists, not downloading");
        }
    }
}
