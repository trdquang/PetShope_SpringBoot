package vn.web.pet.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import vn.web.pet.service.UserdetailServiceJw;

@Configuration
@EnableWebSecurity
public class SecureConfigurer extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		// Bat dau cau hinh
		//kiem tra quyền của request (action sẽ là vai trò quản trị hay khách)
		http.csrf().disable().authorizeRequests() // Bat cac request tu browser

		// Cho phep cac request, static resources khong bi rang buoc login
		//những tài nguyên có thể truy cập khi không (có) login
		.antMatchers("/frontend/**", "/backend/**", 
				"/UploadFiles/**", "/login", "/logout").permitAll()

		// Cac requests kieu "/admin/**" phai login (xac thuc)
		//quy định đăng nhập cho tiền tố admin
//		 .antMatchers("/admin/**").authenticated() //step 1+2
		
		 // Cac request kieu /admin/** phai co role la ADMIN //step 3
		.antMatchers("/admin/**").hasAuthority("ADMIN")

		.and()

		// Neu chua login (xac thuc) thi redirect den trang login
		// Voi "/login" la 1 request (trong LoginController)
		//khi login sẽ kích hoạt action được định nghĩa 
		.formLogin().loginPage("/login").loginProcessingUrl("/login_processing_url")

//		.defaultSuccessUrl("/admin/home", true) //login thanh cong thi vao trang home
		// (backend) step 1+2
		// Login thanh cong: Chuyen den request phu hop voi role step 3
		.successHandler(new UrlAuthenticationSuccessHandler()) 

		// Login khong thanh cong
		.failureUrl("/login?login_error=true")

		.and()

		// Cau hinh phan logout
		.logout().logoutUrl("/logout").logoutSuccessUrl("/index")
			.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")

		.and()
		.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400);
	}
	
	@Autowired
	private UserdetailServiceJw userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				new BCryptPasswordEncoder(4));
	}
	
//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder(4).encode("12345"));
//	}
	
}
