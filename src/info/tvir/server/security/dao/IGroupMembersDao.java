package info.tvir.server.security.dao;

import java.util.Collection;
import java.util.Set;

import info.tvir.hibernateutils.dao.IDao;
import info.tvir.server.security.model.Group;
import info.tvir.server.security.model.GroupMembers;
import info.tvir.server.security.model.Person;
import info.tvir.server.security.model.Role;

public interface IGroupMembersDao extends IDao<GroupMembers, Long> {
	final String QUERY_GROUPS_BY_PERSON = "SELECT gm.group FROM GroupMembers gm WHERE gm.person = ?";
	final String QUERY_PERSONS_BY_GROUP = "SELECT gm.person FROM GroupMembers gm WHERE gm.group = ?";
	final String QUERY_PERSONS_BY_GROUP_ID = "SELECT gm.person FROM GroupMembers gm WHERE gm.group.id = ?";
	final String QUERY_REMOVE_PERSONS_FROM_GROUP = "DELETE FROM GroupMembers gm WHERE gm.group = ? AND gm.person.id = ? ";
	final boolean WITH_PARENTS = true;
	final boolean WITHOUT_PARENTS = false;

	Set<Group> getGroupsByPerson(Person person);

	Set<Group> getGroupsByPerson(Person person, boolean withParents);

	Set<Role> getRolesByPerson(Person person);
	
	Set<Person> getPersonsByGroup(Group group);
	
	Set<Person> getPersonsByGroupId(Long groupId);
	
	void removePersonsFromGroup(Group group, Collection<Long> ids);

}
