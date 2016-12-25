package org.savand.mailsender.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.savand.mailsender.model.User;
import org.savand.mailsender.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDaoImpl implements IUserDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

	private static final String SELECT_USER_BY_ID = "select * from users where id=?";
	private static final String SELECT_USERS = "select * from users";
	
	Connection conn;
	
	public UserDaoImpl() {
		Properties propertyDb = null;
		String propertiesFileName = "connectionDb";
		
		try {
			propertyDb = PropertyUtil.getPropertyFileFromResources(propertiesFileName);
		} catch (IOException e1) {
			LOGGER.debug("Can not load " + propertiesFileName);
			e1.printStackTrace();
		}
		
		try {
			LOGGER.debug("Establishing connection to db...");
			conn = DriverManager.getConnection(propertyDb.getProperty("url"), propertyDb);
			LOGGER.debug("Connection to db successfully established");
		} catch (SQLException e) {
			LOGGER.debug("Can not connect to DB");
			e.printStackTrace();
		}
	}

	@Override
	public List<User> getUsers() throws SQLException {
		List<User> users = null;
		
		try(Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(SELECT_USERS)){
			
			users = new LinkedList<>();
			
			while (rs.next()){
			  User us = new User(rs.getInt("id"), rs.getString("email"));
				users.add(us);
			}
				
		} finally {
			conn.close();
		}
		
		return users;
	}

	@Override
	public User getUserById(int id) throws SQLException {
		User userById = null;
		
		try(PreparedStatement prepareStatement = createPreparedStatementUserById(id);
			ResultSet rs = prepareStatement.executeQuery(); ){
			
			prepareStatement.setInt(1, id);
			
			while(rs.next()){
			  userById = new User(rs.getInt("id"), rs.getString("email"));
			}
			
		}finally{
			conn.close();
		}
		
		return userById;
	}

	private PreparedStatement createPreparedStatementUserById(int id) throws SQLException {
		PreparedStatement prepareStatement = conn.prepareStatement(SELECT_USER_BY_ID);
		prepareStatement.setInt(1, id);
		return prepareStatement;
	}
	
	
	
	public static void main(String[] args) throws SQLException {
		UserDaoImpl userDaoImpl = new UserDaoImpl();
		LOGGER.debug(userDaoImpl.getUserById(2).toString());
    UserDaoImpl userDaoImpl2 = new UserDaoImpl();
		LOGGER.debug(userDaoImpl2.getUsers().toString());
	}

}
