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
//                .csrf().disable() // csrf token. use for preventing attacker forging a request. Recommend to use CSRF protection for any request that could be processed by a browser by normal users, or can be disable if the service is used by non-browser clients
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
//                .antMatchers("/api/**").hasRole(STUDENT.name())

                // order of matchers matter
                // those can be deleted if annotations are using

                //.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) // authorization only on DELETE with specific api
                //.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                //.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                //.antMatchers("/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRANIEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
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
