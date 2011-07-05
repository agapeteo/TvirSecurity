package info.tvir.server.security.model;

import info.tvir.server.security.model.IPerson;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "persons")
public class Person implements Serializable, Comparable<Person>, IPerson {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column(nullable = false, unique = true)
	private String login;

	@Column(nullable = false)
	private String password;

	@Column
	private String firstname;

	@Column
	private String lastname;

	@Column(nullable = false)
	private String displayname;

	@Column
	private boolean enabled;
	
	@Column
	private Date prevlogin;
	
	@Column
	private Date currlogin;
	
	@OneToMany(mappedBy="person",cascade=CascadeType.ALL)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private List<GroupMembers> groupMembers;

	private transient Set<GrantedAuthority> authorities;

	public Person() {
	}

	public Person(String login, String password, String displayname) {
		super();
		this.login = login;
		this.password = password;
		this.displayname = displayname;
		this.enabled = true;
	}

	public Person(String login, String password, String firstname,
			String lastname, String displayname, boolean enabled,
			Date prevlogin, Date currlogin) {
		super();
		this.login = login;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.displayname = displayname;
		this.enabled = enabled;
		this.prevlogin = prevlogin;
		this.currlogin = currlogin;		
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	@Override
	public String getDisplayname() {
		return displayname;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	@Override
	public int compareTo(Person o) {
		return this.displayname.compareTo(o.displayname);
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return isEnabled();
	}

	@Override
	public boolean isAccountNonLocked() {
		return isEnabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isEnabled();
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public Date getPrevlogin() {
		return prevlogin;
	}
	
	@Override
	public Date getCurrlogin() {
		return currlogin;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setPrevlogin(Date prevlogin) {
		this.prevlogin = prevlogin;
	}

	public void setCurrlogin(Date currlogin) {
		this.currlogin = currlogin;
	}

	public List<GroupMembers> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<GroupMembers> groupMembers) {
		this.groupMembers = groupMembers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currlogin == null) ? 0 : currlogin.hashCode());
		result = prime * result
				+ ((displayname == null) ? 0 : displayname.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((prevlogin == null) ? 0 : prevlogin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (currlogin == null) {
			if (other.currlogin != null)
				return false;
		} else if (!currlogin.equals(other.currlogin))
			return false;
		if (displayname == null) {
			if (other.displayname != null)
				return false;
		} else if (!displayname.equals(other.displayname))
			return false;
		if (enabled != other.enabled)
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (prevlogin == null) {
			if (other.prevlogin != null)
				return false;
		} else if (!prevlogin.equals(other.prevlogin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [authorities=" + authorities + ", currlogin="
				+ currlogin + ", displayname=" + displayname + ", enabled="
				+ enabled + ", firstname=" + firstname + ", id=" + id
				+ ", lastname=" + lastname + ", login=" + login + ", password="
				+ password + ", prevlogin=" + prevlogin + "]";
	}
	
}
