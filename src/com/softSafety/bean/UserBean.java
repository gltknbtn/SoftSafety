package com.softSafety.bean;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import service.UserService;

import com.softSafety.model.User;

@ManagedBean(name = "user")
@SessionScoped
public class UserBean implements Serializable {
	private static final long serialVersionUID = 6018940086572715862L;
	private List<User> list = new ArrayList<User>();
	private Boolean login = false;
	private Boolean updateMode = false;
	private Boolean newUserAdded = false;
	private Integer id;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String rePassword;
	private String status = "";
	private String loginMessage;
	private String newUserAddedMessage;

	private User newUser;
	
	@ManagedProperty("#{userService}")
    private UserService service;

	public void initUser() {
		newUser = new User();
		updateMode = false;
	}

	public void forwardToLoginIfNotLoggedIn(ComponentSystemEvent cse) {

		if (!login) {
			System.out.println("login: " + login);
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,"/login?faces-redirect=true");
		}
	}

	public void updateUser(Integer id) throws SQLException {
		// TODO user find id set newUser
		newUser = findById(id);
		updateMode = true;
	}

	public void reset() {
		// TODO user find id set newUser
		newUser = null;
	}

	public String save() {

		boolean succes = false;
		try {
			succes = insertUser(newUser.getName(), newUser.getSurname(),
					newUser.getUsername(), newUser.getPassword());
			getCustomerList();
			newUser = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (succes) {
			status = "Kullanici Eklendi.";
		} else {
			status = "Beklenmedik bir hata olustur.";
		}
		return "index";
	}

	public String update() {

		boolean succes = false;
		try {
			succes = updateUser(newUser);
			getCustomerList();
			newUser = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (succes) {
			status = "Kullanici güncellendi.";
		} else {
			status = "Beklenmedik bir hata olustu.";
		}
		return "index";
	}

	public String logout() {
		login = false;
		username = "";
		password = "";
		loginMessage = "";
		return "/login?faces-redirect=true";
	}

	public String login() throws NoSuchAlgorithmException {
		System.out.println("login");

		try {
			login = validateLogin(username, password);
			getCustomerList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (login) {
			return "/index?faces-redirect=true";
		} else {
			loginMessage = "Giriş başarısız!!!";
			return "/login?faces-redirect=true";
		}
	}

	public User findById(Integer id) throws SQLException {

		User user = new User();
		ResultSet result = null;
		Statement stmt = null;
		Connection con = getConnection();
		String sql = "select * from User where id='" + id + "'";
		stmt = con.createStatement();
		result = stmt.executeQuery(sql);

		while (result.next()) {
			user.setId(result.getInt("id"));
			user.setName(result.getString("name"));
			user.setSurname(result.getString("surname"));
			user.setUsername(result.getString("username"));
			user.setPassword(result.getString("password"));

		}
		return user;
	}

	public boolean updateUser(User user) throws SQLException {
		boolean auth = false;
		Statement stmt = null;
		Connection con = getConnection();
		String sql = "update User set name='" + user.getName() + "', surname='"
				+ user.getSurname() + "', username='" + user.getUsername()
				+ "', password='" + user.getPassword() + "'";
		stmt = con.createStatement();
		auth = stmt.execute(sql);
		return auth;
	}

	public boolean insertUser(String username, String password, String name,
			String surname) throws SQLException {
		boolean auth = false;
		Statement stmt = null;
		Connection con = getConnection();
		String sql = "insert into User  (name,surname,username,password) values('"
				+ name
				+ "','"
				+ surname
				+ "','"
				+ username
				+ "','"
				+ password
				+ "')";
		stmt = con.createStatement();
		auth = stmt.execute(sql);
		return auth;
	}

	public boolean validateLogin(String username, String password)
			throws SQLException, NoSuchAlgorithmException {

		boolean auth = false;
		ResultSet result = null;
		
		Connection conn = getConnection();
		
		/*
		Statement stmt = null;
		String sql = "select * from User where username='" + username
				+ "' and password='" + password + "'";
		System.out.println("sql: " + sql);
		stmt = conn.createStatement();
		result = stmt.executeQuery(sql);
		*/
		
		PreparedStatement statement = conn.prepareStatement( "SELECT * FROM User WHERE username= ? AND password= ? " );
		statement.setString(1, username); 
		statement.setString( 2, service.createPasswordHash(password)); 
		result = statement.executeQuery();

		while (result.next()) {
			User user = new User();
			user.setId(result.getInt("id"));
			user.setUsername(result.getString("username"));
			user.setPassword(result.getString("password"));
			auth = true;
		}
		return auth;
	}

	// connect to DB and get customer list
	public String getCustomerList() throws SQLException {
		list = new ArrayList<User>();
		ResultSet result = null;
		Statement stmt = null;
		Connection con = getConnection();
		String sql = "select * from User";
		stmt = con.createStatement();
		result = stmt.executeQuery(sql);

		while (result.next()) {
			User cust = new User();

			cust.setId(result.getInt("id"));
			cust.setName(result.getString("name"));
			cust.setSurname(result.getString("surname"));
			cust.setUsername(result.getString("username"));
			cust.setPassword(result.getString("password"));

			list.add(cust);
		}

		return "index";
	}
	
	public void saveNewUser() {
		
		try {
			System.out.println("savePersonel");
			System.out.println("---------------------------");
			System.out.println("username: " + name);
			System.out.println("surname: " + getSurname());
			System.out.println("username: " + username);
			System.out.println("password: " + password);
			System.out.println("repassword: " + rePassword);
			System.out.println("---------------------------");
			
			if (password.equals(rePassword)) {
				User user = new User();
				user.setName(name);
				user.setSurname(surname);
				user.setPassword(password);
				user.setUsername(username);
				user.setPassword(password);
				
				newUserAdded = getService().save(user);
				if (newUserAdded) {
					newUserAddedMessage = "New user "+username+ " successfully added";
				}
				
			}else{
				newUserAdded = true;
				newUserAddedMessage = "User creation failed. Please check your information";
			}
			System.out.println("newUserAdded: " + newUserAdded);
			System.out.println(newUserAddedMessage);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,"/login?faces-redirect=true");
			
		}
		
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
			System.out.println("database connection completed.");
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

	public Boolean getLogin() {
		return login;
	}

	public void setLogin(Boolean login) {
		this.login = login;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

	public Boolean getUpdateMode() {
		return updateMode;
	}

	public void setUpdateMode(Boolean updateMode) {
		this.updateMode = updateMode;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Boolean getNewUserAdded() {
		return newUserAdded;
	}

	public void setNewUserAdded(Boolean newUserAdded) {
		this.newUserAdded = newUserAdded;
	}

	public String getNewUserAddedMessage() {
		return newUserAddedMessage;
	}

	public void setNewUserAddedMessage(String newUserAddedMessage) {
		this.newUserAddedMessage = newUserAddedMessage;
	}

	public UserService getService() {
		return service;
	}

	public void setService(UserService service) {
		this.service = service;
	}


}
