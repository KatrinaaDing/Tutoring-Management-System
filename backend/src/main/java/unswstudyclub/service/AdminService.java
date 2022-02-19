package unswstudyclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import unswstudyclub.dao.UnswStudyClubDao;
import unswstudyclub.model.Admin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {

    private final UnswStudyClubDao unswStudyClubDao;

    @Autowired
    public AdminService(@Qualifier("postgres") UnswStudyClubDao unswStudyClubDao) {
        this.unswStudyClubDao = unswStudyClubDao;
    }

    public int addAdmin(Admin admin) {
        return unswStudyClubDao.addAdmin(admin);
    }

    public int deleteAdminById(UUID id) {
        return unswStudyClubDao.deleteAdminById(id);
    }

    public List<Admin> selectAllAdmin() {
        return unswStudyClubDao.selectAllAdmin();
    }

    public Optional<Admin> selectAdminById(UUID id) {
        return unswStudyClubDao.selectAdminById(id);
    }

    public int updateAdminById(UUID id, Admin admin) {
        return unswStudyClubDao.updateAdminById(id, admin);
    }
}
