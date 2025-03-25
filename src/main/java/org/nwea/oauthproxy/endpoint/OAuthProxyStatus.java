package org.nwea.oauthproxy.endpoint;

import org.springframework.stereotype.Component;

import java.util.List;

public class OAuthProxyStatus {
    private String name;

    private String version;
    private String buildDate;
    private String deployDate;


    private List<MongDBStatus> dependencies;
    private boolean successful;






    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public List<MongDBStatus> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<MongDBStatus> dependencies) {
        this.dependencies = dependencies;
    }

    public String getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(String deployDate) {
        this.deployDate = deployDate;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}

