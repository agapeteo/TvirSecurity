package info.tvir.server.security.dao.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springsource.util.common.Assert;

import info.tvir.hibernateutils.dao.impl.HibernateDaoImpl;
import info.tvir.server.security.dao.IGroupMembersDao;
import info.tvir.server.security.dao.IGroupRolesDao;
import info.tvir.server.security.model.Group;
import info.tvir.server.security.model.GroupMembers;
import info.tvir.server.security.model.Person;
import info.tvir.server.security.model.Role;

@Repository("groupMembersDao")
public class GroupMembersHbmImpl extends HibernateDaoImpl<GroupMembers, Long> implements IGroupMembersDao{
	private static final Class<GroupMembers> CLASS = GroupMembers.class;
	
	@Resource
	private HibernateTemplate template;
	
	@Resource
	private IGroupRolesDao groupRolesDao;
	
	public GroupMembersHbmImpl(){
		super(CLASS);
	}

	@Override
	public Set<Group> getGroupsByPerson(Person person) {		
		return getGroupsByPerson(person, WITHOUT_PARENTS);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Set<Group> getGroupsByPerson(Person person, boolean withParents) {		
		CopyOnWriteArraySet<Group> result = new CopyOnWriteArraySet<Group>();
		List<Group> list = (List<Group>) template.find(QUERY_GROUPS_BY_PERSON, person);
		result.addAll(list);
		
		if ( ! withParents ){
			return result;
		}
		
		for (Group each :  result){			
			Group parentGroup = each.getParent(); 			
			while ( parentGroup != null ){
				result.add( parentGroup );
				parentGroup = parentGroup.getParent();
			}
		}
	    return result;
	}

	@Override
	public Set<Role> getRolesByPerson(Person person) {		
		Set<Role> result = new HashSet<Role>();
		Set<Group> groups = getGroupsByPerson(person, WITH_PARENTS);
		for (Group eachGroup : groups){
			result.addAll( groupRolesDao.getRolesByGroup(eachGroup) );
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Set<Person> getPersonsByGroup(Group group) {
		Set<Person> result = null;
		List<Person> list = (List<Person>) template.find(QUERY_PERSONS_BY_GROUP, group);
		if (list == null){
			return new HashSet<Person>();
		}
		result = new HashSet<Person>(list);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Set<Person> getPersonsByGroupId(Long groupId) {
		Set<Person> result = null;
		List<Person> list = (List<Person>) template.find(QUERY_PERSONS_BY_GROUP_ID, groupId);
		if (list == null){
			return new HashSet<Person>();
		}
		result = new HashSet<Person>(list);
		return result;
	}

	@Override
	@Transactional
	public void removePersonsFromGroup(Group group, Collection<Long> ids) {
		Assert.notNull(ids, "ids == null");
		Assert.notEmpty(ids, "ids is empty");
		
		for (Long eachId : ids){
			template.bulkUpdate(QUERY_REMOVE_PERSONS_FROM_GROUP, group, eachId);
		}		
	}
	
	@Override
	@Transactional
	public Long create(GroupMembers groupMembers){
		return (Long) template.save(groupMembers);
	}
	
	@Override
	@Transactional
	public void update(GroupMembers groupMembers){
		template.update(groupMembers);
	}
	
	@Override
	@Transactional
	public void delete(GroupMembers groupMembers){
		template.delete(groupMembers);
	}
	

}
