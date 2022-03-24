package org.metahut.starfish.ingestion.collector.hive;

import org.metahut.starfish.ingestion.collector.api.AbstractCollectorParameter;

import java.util.Objects;

public class HiveParameter extends AbstractCollectorParameter {

    private String driverName;
    private String url;
    private static String user = "hive";
    private static String password = "";
    private static Boolean onSecure = false;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        HiveParameter.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        HiveParameter.password = password;
    }

    public static Boolean getOnSecure() {
        return onSecure;
    }

    public static void setOnSecure(Boolean onSecure) {
        HiveParameter.onSecure = onSecure;
    }

    public boolean checkParam() {
        if (Objects.isNull(driverName)) {
            return false;
        }
        if (Objects.isNull(url)) {
            return false;
        }
        return true;
    }
}
