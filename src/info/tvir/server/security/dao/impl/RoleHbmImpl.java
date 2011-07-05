package info.tvir.server.security.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import info.tvir.hibernateutils.dao.impl.HibernateDaoImpl;
import info.tvir.server.security.dao.IRoleDao;
import info.tvir.server.security.model.Role;

@Repository("roleDao")
public class RoleHbmImpl extends HibernateDaoImpl<Role, Long> implements IRoleDao{
	private static final Class<Role> CLASS = Role.class;
	
	@Resource
	private HibernateTemplate template;
	
	public RoleHbmImpl(){
		super(CLASS);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Role getRoleByName(String name) {
		Role result = null;
		
		List<Role> list = (List<Role>) template.find(QUERY_ROLE_BY_NAME, name);
		
		if (list != null && list.size() > 0){
			result = list.get(0);
		}
		
		return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Set<Role> getAllRoles() {		
		List<Role> list = (List<Role>) template.find(QUERY_ALL);
		if (list == null) {
			return new HashSet<Role>();
		}
		return new HashSet<Role>(list);	
	}
	
	@Override
	@Transactional
	public Long create(Role role){
		return (Long) template.save(role);
	}
	
	@Override
	@Transactional
	public void update(Role role){
		template.update(role);
	}

	
	@Override
	@Transactional
	public void delete(Role role){
		template.delete(role);
	}
	
}
