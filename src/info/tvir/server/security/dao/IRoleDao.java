package info.tvir.server.security.dao;

import java.util.Set;

import info.tvir.hibernateutils.dao.IDao;
import info.tvir.server.security.model.Role;

public interface IRoleDao extends IDao<Role, Long> {

	final String QUERY_ROLE_BY_NAME = "FROM Role r WHERE r.name = ?";
	final String QUERY_ALL = "FROM Role";

	Role getRoleByName(String name);
	
	Set<Role> getAllRoles();

}
