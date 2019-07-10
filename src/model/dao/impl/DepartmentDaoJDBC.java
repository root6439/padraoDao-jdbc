package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {

		PreparedStatement ps = null;

		try {

			ps = conn.prepareStatement("insert into Department (Name) values (?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, obj.getName());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Algo deu errado na inserção");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public void update(Department obj) {

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("update Department set Name = ? where Id = ?");

			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getId());

			int rows = ps.executeUpdate();

			if (rows == 0) {
				throw new DbException("Algo deu errado durante a atualização");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("delete from Department where Id = ?");

			ps.setInt(1, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public Department findById(Integer id) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("select * from Department where Id = ?");

			ps.setInt(1, id);

			rs = ps.executeQuery();
			
			if(rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("Id"));
				dep.setName(rs.getString("Name"));
				
				return dep;
			}
			
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("select * from Department");
			
			rs = ps.executeQuery();
			
			List<Department> list = new ArrayList<>();
			while(rs.next()) {
				int id = rs.getInt("Id");
				String name = rs.getString("Name");
				list.add(new Department(id, name));
			}
			
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

}
