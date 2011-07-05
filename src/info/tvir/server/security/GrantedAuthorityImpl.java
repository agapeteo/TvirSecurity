package info.tvir.server.security;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority{
	private static final long serialVersionUID = 1L;
	
	private String autority;	

	public GrantedAuthorityImpl(String autority){
		this.autority = autority;
	}

	@Override
	public String getAuthority() {		
		return autority;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((autority == null) ? 0 : autority.hashCode());
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
		GrantedAuthorityImpl other = (GrantedAuthorityImpl) obj;
		if (autority == null) {
			if (other.autority != null)
				return false;
		} else if (!autority.equals(other.autority))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GrantedAuthorityImpl [autority=" + autority + "]";
	}		

}
