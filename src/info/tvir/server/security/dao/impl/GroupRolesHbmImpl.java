package info.tvir.server.security.dao.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springsource.util.common.Assert;

import info.tvir.hibernateutils.dao.impl.HibernateDaoImpl;
import info.tvir.server.security.dao.IGroupRolesDao;
import info.tvir.server.security.model.Group;
import info.tvir.server.security.model.GroupRoles;
import info.tvir.server.security.model.Role;

@Repository("groupRolesDao")
public class GroupRolesHbmImpl extends HibernateDaoImpl<GroupRoles, Long>
		implements IGroupRolesDao {
	private static final Class<GroupRoles> CLASS = GroupRoles.class;

	@Resource
	private HibernateTemplate template;

	public GroupRolesHbmImpl() {
		super(CLASS);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Set<Role> getRolesByGroup(Group group) {
		Set<Role> result = new HashSet<Role>();
		List<Role> list = (List<Role>) template.find(QUERY_ROLES_BY_GROUP,
				group);
		result.addAll(list);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Set<Role> getRolesByGroupId(Long groupId) {
		Assert.notNull(groupId, "groupId == null");
		
		List<Role> list = (List<Role>) template.find(QUERY_ROLES_BY_GROUP_ID,
				groupId);
		if (list == null){
			return new HashSet<Role>();
		}
		return new HashSet<Role>(list);
	}

	@Override
	public void removeRolesFromGroup(Group group, Collection<Long> ids) {
		Assert.notNull(ids, "ids == null");
		Assert.notEmpty(ids, "ids is empty");
		
		for (Long eachId : ids){
			template.bulkUpdate(QUERY_REMOVE_ROLES_FROM_GROUP, group, eachId);
		}
		
	}
	
	@Override
	@Transactional
	public Long create(GroupRoles groupRoles){
		return (Long) template.save(groupRoles);
	}
	
	@Override
	@Transactional
	public void update(GroupRoles groupRoles){
		template.update(groupRoles);
	}
	
	@Override
	@Transactional
	public void delete(GroupRoles groupRoles){
		template.delete(groupRoles);
	}

}
