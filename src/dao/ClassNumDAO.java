package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;


public class ClassNumDAO extends DAO {

	public ClassNum get(String class_num, School school) throws Exception {
		ClassNum classNum = new ClassNum();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from class_num=? and school_cd=?");

			statement.setString(1, class_num);
			statement.setString(2, school.getCd());

			ResultSet rSet = statement.executeQuery();

			SchoolDAO sDAO = new SchoolDAO();

			if (rSet.next()) {
				classNum.setClass_num(rSet.getString("class_num"));
				classNum.setSchool(sDAO.get(rSet.getString("school_cd")));
			} else {
				classNum = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return classNum;
	}


	public List<String> filter(School school) throws Exception {
		List<String> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;


		try {
			statement = connection.prepareStatement(
					"select class_num from class_num where school_cd=? order by class_num");

			statement.setString(1, school.getCd());

			ResultSet rSet = statement.executeQuery();

			while (rSet.next()) {
				list.add(rSet.getString("class_num"));
			}

		}catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}

}