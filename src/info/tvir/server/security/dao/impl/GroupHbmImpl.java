package info.tvir.server.security.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import info.tvir.hibernateutils.dao.impl.HibernateDaoImpl;
import info.tvir.server.security.dao.IGroupDao;
import info.tvir.server.security.model.Group;

@Repository("groupDao")
public class GroupHbmImpl extends HibernateDaoImpl<Group, Long> implements IGroupDao{
	private static final Class<Group> CLASS = Group.class;
	
	@Resource
	private HibernateTemplate template;
	
	public GroupHbmImpl(){
		super(CLASS);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public Group getGroupByName(String name) {
		Group result = null;
		List<Group> list = (List<Group>) template.find(QUERY_GROUP_BY_NAME, name);
		
		if (list != null && list.size() > 0){
			result = list.get(0);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Group> getAll() {		
		return template.find(QUERY_ALL);
	}
	
	@Override
	@Transactional
	public Long create(Group group){
		return (Long) template.save(group);
	}
	
	@Override
	@Transactional
	public void update(Group group){
		template.update(group);
	}
	
	@Override
	@Transactional
	public void delete(Group group){
		template.delete(group);
	}

}
