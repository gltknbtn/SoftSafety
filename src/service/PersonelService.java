package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.softSafety.model.Personel;

@ManagedBean(name = "personelService")
@ApplicationScoped
public class PersonelService {
	
	public List<Personel> getPersonelList() throws SQLException {
		List<Personel> personelList = new ArrayList<Personel>();
		ResultSet result = null;
		Statement stmt = null;
		Connection con = getConnection();
		String sql = "select * from Personel";
		stmt = con.createStatement();
		result = stmt.executeQuery(sql);

		while (result.next()) {
			Personel personel = new Personel();

			personel.setId(result.getInt("id"));
			personel.setName(result.getString("name"));
			personel.setSurname(result.getString("surname"));
			personel.setAge(result.getString("age"));
			personel.setMail(result.getString("mail"));
			personel.setCountry(result.getString("country"));

			personelList.add(personel);
		}

		return personelList;
	}
	
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

	public boolean save(Personel personel) throws SQLException {
		
		boolean isSucces = false;
		Connection conn = getConnection();
		/*
		      Statement stmt = null;
		      String sql = "insert into personel(name,surname,age,country,mail) values('"+personel.getName()+"',"
		      																		  + "'"+personel.getSurname()+"',"
		      																		  + "'"+personel.getAge()+"',"
		      																		  + "'"+personel.getCountry()+"',"
		      																		  + "'"+personel.getMail()+"'"
		      																		  + ")";	
		      stmt = conn.createStatement();      
		      stmt.execute(sql); 
		      */
		      
		PreparedStatement statement = conn.prepareStatement("insert into personel(name,surname,age,country,mail) values(?,?,?,?,?)");
		statement.setString(1, personel.getName());
		statement.setString(2, personel.getSurname());
		statement.setString(3, personel.getAge());
		statement.setString(4, personel.getCountry());
		statement.setString(5, personel.getMail());
		statement.execute();
		      
		isSucces = true;
		conn.close();
		return isSucces;
	}

	public boolean delete(Integer id) {
		try {
			Connection conn = getConnection();
			Statement stmt;
			stmt = conn.createStatement();
			String sql = "DELETE FROM personel " + "WHERE id =" + id;
			stmt.executeUpdate(sql);
			conn.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void update(String personelIdToBeUpdate, String name,
			String surname, String age, String mail, String country) {
		
		try { 
			Connection conn = getConnection();
			/*
		 Statement stmt = null;
	      String sql = "update personel set name='"+name+"', "
	      								 + "surname='"+surname+"', "
	      								 + "age='"+age+"', "
	      								 + "mail='"+mail+"', "
	      								 + "country='"+country+"' "
	      								 + "where id='"+personelIdToBeUpdate+"'"
	      								 ;	
	      System.out.println("sql: " + sql);
			stmt = conn.createStatement();
			stmt.execute(sql); 
			*/
			
			PreparedStatement statement = conn.prepareStatement("update personel set name = ?, surname = ?, age = ?, mail = ?, country = ? where id = ?");
			statement.setString(1, name);
			statement.setString(2, surname);
			statement.setString(3, age);
			statement.setString(4, mail);
			statement.setString(5, country);
			statement.setString(6, personelIdToBeUpdate);
			System.out.println(statement);
			statement.execute();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
		
	}

	public List<Personel> search(Personel prsnl) {
		List<Personel> list = new ArrayList<Personel>();
		try {

			ResultSet result = null;
			Statement stmt = null;
			Connection con = getConnection();
			String sql = "select * from personel where name like '%"+prsnl.getName()+"%' "
											    + "and surname like '%"+prsnl.getSurname()+"%' "
											    + "and age like '%"+prsnl.getAge()+"%' "
											    + "and mail like '%"+prsnl.getMail()+"%' "
											    + "and country like '%"+prsnl.getCountry()+"%' "
											    + "";
			System.out.println("sql: " + sql);
			stmt = con.createStatement();
			result = stmt.executeQuery(sql);

			while (result.next()) {
				Personel personel = new Personel();

				personel.setId(result.getInt("id"));
				personel.setName(result.getString("name"));
				personel.setSurname(result.getString("surname"));
				personel.setAge(result.getString("age"));
				personel.setMail(result.getString("mail"));
				personel.setCountry(result.getString("country"));

				list.add(personel);
			}

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
	}

	public Personel getPersonelById(String id) {

		Personel personel = new Personel();
		try {

			ResultSet result = null;
			Statement stmt = null;
			Connection con = getConnection();
			String sql = "select * from personel where id = '" + id + "'";
			System.out.println("sql: " + sql);
			stmt = con.createStatement();
			result = stmt.executeQuery(sql);

			while (result.next()) {

				personel.setId(result.getInt("id"));
				personel.setName(result.getString("name"));
				personel.setSurname(result.getString("surname"));
				personel.setAge(result.getString("age"));
				personel.setMail(result.getString("mail"));
				personel.setCountry(result.getString("country"));
			}

			return personel;
		} catch (SQLException e) {
			e.printStackTrace();
			return personel;
		}

	}

	

}
