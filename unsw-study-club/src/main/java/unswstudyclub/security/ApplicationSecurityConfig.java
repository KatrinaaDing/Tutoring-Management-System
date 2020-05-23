package unswstudyclub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static unswstudyclub.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // need to specify to recognize @PreAuthorize in controller
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf (Cross Site Request Forgery) token disabled. use for preventing attacker forging a request. Recommend to use CSRF protection for any request that could be processed by a browser by normal users, and can be disable if the service is used by non-browser clients (e.g. postman)
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // enable csrf and send automatically by spring boot
//                .and()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
//              .antMatchers("/api/**").hasRole(STUDENT.name())   // permission on role

                // order of matchers matter
                // those can be deleted if annotations are using

                //.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) // authorization only on DELETE with specific api
                //.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                //.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                //.antMatchers("/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRANIEE.name())                  // auth on role
                .anyRequest()
                .authenticated()
                .and()

                // Basic auth: HTTPS recommended, simple and fast, can't logout
//                .httpBasic()

                // form base auth: Username & pw, standard in most websites, forms (full control), can logout, https recommended
                // Session id usually expires after 30 min , but can be stored in "in memory database" by default
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/courses", true)
                    .passwordParameter("password")      // parameter: get the value from other input element. value = name of the new element in html
                    .usernameParameter("username")      // no need if want to use default name (e.g. "password", "username"
                .and()
                .rememberMe()   // session id expired in 2 wks by default, and has its own cookie stored in db: 1. username, 2. expiration time, 3. md5 hash of the above 2 values
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))    // extends validation date of session id
                    .key("somethingverysecured")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()       // enable logout with deleting cookies
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))  // should be deleted if using csrf (using csrf should use POST to logout)
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login")
        ;
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails kat = User.builder()
                .username("katrina")
                .password(passwordEncoder.encode("pw123"))
//                .roles(ApplicationUserRole.STUDENT.name())    // authority on roles
                .authorities(STUDENT.getGrantedAuthorities())   // authority on permission
                .build();

        UserDetails linda = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("pw123"))
//                .roles(ApplicationUserRole.ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())   // authority on permission
                .build();

        UserDetails tom = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("pw123"))
//                .roles(ApplicationUserRole.ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())   // authority on permission
                .build();

        return new InMemoryUserDetailsManager(
                kat,
                linda,
                tom
        );
    }
}
