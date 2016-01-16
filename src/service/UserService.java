package service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.softSafety.model.User;

@ManagedBean(name = "userService")
@ApplicationScoped
public class UserService {
	
	
	public Connection getConnection() {
		Connection con = null;

		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/softSafety";
		String user = "root";
		String password = "3113327";
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return con;
	}

	public boolean save(User user) throws SQLException, NoSuchAlgorithmException {
		boolean isSucces = false;
		Connection conn = getConnection();
		      
		PreparedStatement statement = conn.prepareStatement("insert into user(name, surname, username, password) values(?,?,?,?)");

		statement.setString(1, user.getName());
		statement.setString(2, user.getSurname());
		statement.setString(3, user.getUsername());
		statement.setString(4, createPasswordHash(user.getPassword()));
		statement.execute();
		      
		isSucces = true;
		conn.close();
		return isSucces;
		
	}
	
	public static String createPasswordHash(String pass) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(pass.getBytes());
		byte[] bytes = md.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i]&0xff)+0x100,16).substring(1));
		}
		String passwordHashCode = sb.toString();
		System.out.println(passwordHashCode);
		return passwordHashCode;
	}


}
