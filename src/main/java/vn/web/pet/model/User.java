package vn.web.pet.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tbl_user")
public class User extends BaseModel implements UserDetails{

	@Column(name = "username", length = 120, nullable = false)
	private String username;

	@Column(name = "password", length = 120, nullable = false)
	private String password;

	@Column(name = "name", length = 120, nullable = true)
	private String name;

	@Column(name = "email", length = 200, nullable = true)
	private String email;

	@Column(name = "mobile", length = 60, nullable = true)
	private String mobile;

	@Column(name = "address", length = 300, nullable = true)
	private String address;

//	@Column(name = "avatar", length = 300, nullable = true)
//	private String avatar;

	@Column(name = "description", length = 500, nullable = true)
	private String description;

//---------------Mapping many-to-many: tbl_user-to-tbl_role---------------------------
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "users")
	private List<Role> roles = new ArrayList<Role>();

	// Add and remove elements out of relational user-role list
	public void addRelationalUserRole(Role role) {
		role.getUsers().add(this);
		roles.add(role);
	}

	public void removeRelationalUserRole(Role role) {
		role.getUsers().remove(this);
		roles.remove(role);
	}

//---------Mapping one-to-many: tbl_user-to-tbl_category-----------	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userCreateCategory")
	private List<Category> creatCategories = new ArrayList<Category>();

//---------Mapping one-to-many: tbl_user-to-tbl_category-----------	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userUpdateCategory")
	private List<Category> updateCategories = new ArrayList<Category>();

	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userCreateProduct")
	private Set<Product> userCreateProducts = new HashSet<Product>();

	public void addRelationalUserCreateProduct(Product product) {
		userCreateProducts.add(product);
		product.setUserCreateProduct(this);
	}

	public void removeRelationalUserCreateProduct(Product product) {
		userCreateProducts.remove(product);
		product.setUserCreateProduct(null);
	}

// Mapping one-to-many: tbl_user-to-tbl_product (user update product)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userUpdateProduct")
	private Set<Product> userUpdateProducts = new HashSet<Product>();

	public void addRelationalUserUpdateProduct(Product product) {
		userUpdateProducts.add(product);
		product.setUserUpdateProduct(this);
	}

	public void removeRelationalUserUpdateProduct(Product product) {
		userUpdateProducts.remove(product);
		product.setUserUpdateProduct(null);
	}
	public User() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

//	public String getAvatar() {
//		return avatar;
//	}
//
//	public void setAvatar(String avatar) {
//		this.avatar = avatar;
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Category> getCreatCategories() {
		return creatCategories;
	}

	public void setCreatCategories(List<Category> creatCategories) {
		this.creatCategories = creatCategories;
	}

	public List<Category> getUpdateCategories() {
		return updateCategories;
	}

	public void setUpdateCategories(List<Category> updateCategories) {
		this.updateCategories = updateCategories;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
