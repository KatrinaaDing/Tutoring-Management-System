package unswstudyclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import unswstudyclub.dao.UnswStudyClubDao;

@Service
public class ApplicationUserService implements UserDetailsService {

//    private final ApplicationUserDAO applicationUserDAO;
    private final UnswStudyClubDao unswStudyClubDao;

//    @Autowired
//    public ApplicationUserService(@Qualifier("postgres") ApplicationUserDAO applicationUserDAO) {
//        this.applicationUserDAO = applicationUserDAO;
//    }

    @Autowired
    public ApplicationUserService(UnswStudyClubDao unswStudyClubDao) {
        this.unswStudyClubDao = unswStudyClubDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return applicationUserDAO
        return unswStudyClubDao
                .selectApplicationUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)))
                ;
    }
}
