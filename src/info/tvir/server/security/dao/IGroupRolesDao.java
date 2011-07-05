package info.tvir.server.security.dao;

import java.util.Collection;
import java.util.Set;

import info.tvir.hibernateutils.dao.IDao;
import info.tvir.server.security.model.Group;
import info.tvir.server.security.model.GroupRoles;
import info.tvir.server.security.model.Role;

public interface IGroupRolesDao extends IDao<GroupRoles, Long>{
	
	final String QUERY_ROLES_BY_GROUP = "SELECT gr.role FROM GroupRoles gr WHERE gr.group = ?";
	final String QUERY_ROLES_BY_GROUP_ID = "SELECT gr.role FROM GroupRoles gr WHERE gr.group.id = ?";
	final String QUERY_REMOVE_ROLES_FROM_GROUP = "DELETE FROM GroupRoles gr WHERE gr.group = ? AND gr.role.id = ? ";
	
	Set<Role> getRolesByGroup(Group group);
	Set<Role> getRolesByGroupId(Long groupId);
	void removeRolesFromGroup(Group group, Collection<Long> ids);

}
