package org.savand.mailsender.dao;

import java.sql.SQLException;
import java.util.List;

import org.savand.mailsender.model.User;

public interface IUserDao {
	
	public List<User> getUsers() throws SQLException;
	
	public User getUserById(int id) throws SQLException;
	
}
