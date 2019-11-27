package be.cixxor.vote.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class SQLManager {
	
	private String baseurl;
	private String host;
	private String database;
	private String username;
	private String password;
	private String table;
	private Connection connection;
	
	public SQLManager(String baseurl, String host, String database, String username, String password, String table) {
		this.baseurl = baseurl;
		this.host = host;
		this.database = database;
		this.username = username;
		this.password = password;
		this.table = table;
	}
	
	public void connection(){
		if(!isConnected()) {
			try {
				this.connection = DriverManager.getConnection(this.baseurl + this.host + "/" + this.database);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void disconnect() {
		if(isConnected()) {
			try {
				this.connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isConnected() {
		try {
			if(this.connection == null || this.connection.isClosed() || this.connection.isValid(5)) {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private Connection getConnection() {
		return this.connection;
	}
	
	public void updatePlayerName(Player player) {
		try {
			PreparedStatement st = getConnection().prepareStatement("SELECT pseudo FROM " + this.table + " WHERE UUID = ?");
			st.setString(1, player.getUniqueId().toString());
			ResultSet rs = st.executeQuery();
			if(!rs.next()) {
				return;
			}
			if(!rs.getString("pseudo").equals(player.getName())) {
				st.close();
				PreparedStatement stp = getConnection().prepareStatement("UPDATE " + this.table + " SET pseudo = ? WHERE UUID = ?");
				stp.setString(1, player.getName());
				stp.setString(2, player.getUniqueId().toString());
				stp.executeUpdate();
				stp.close();
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
