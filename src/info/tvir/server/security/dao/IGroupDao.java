package info.tvir.server.security.dao;

import java.util.List;

import info.tvir.hibernateutils.dao.IDao;
import info.tvir.server.security.model.Group;

public interface IGroupDao extends IDao<Group, Long> {
	
	final String QUERY_GROUP_BY_NAME = "FROM Group g WHERE g.name = ?";
	final String QUERY_ALL = "FROM Group";

	Group getGroupByName(String name);
	List<Group> getAll();
	
}
