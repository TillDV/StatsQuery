package de.tilldv.statsquery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class MySQL {

    private final String host, database, username, password;
    private Connection connection = null;

    public MySQL(String host, String database, String username, String password) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
    }


    public void connect() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?autoReconnect=true", username, password);
    }

    public PreparedStatement createStatement(String sql) throws SQLException {
        if(this.connection == null)
            throw new SQLException("MySQL not connected");
        return connection.prepareStatement(sql);
    }

    public boolean isConnected() throws SQLException {
        if(this.connection == null)
            return false;
        return !this.connection.isClosed();
    }

    public boolean close() throws SQLException {
        if(this.connection == null)
            return false;
        this.connection.close();
        return true;
    }

}
