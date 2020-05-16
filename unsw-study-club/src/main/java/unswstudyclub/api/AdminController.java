package unswstudyclub.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import unswstudyclub.model.Admin;
import unswstudyclub.service.AdminService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v6")
@RestController
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(path="/admin")
    public int addAdmin(@Valid @NonNull @RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }

    @DeleteMapping(path="/admin/{id}")
    public int removeAdmin(@NotNull @PathVariable("id") UUID id) {
        return adminService.deleteAdminById(id);
    }

    @GetMapping(path="/admin")
    public List<Admin> selectAllAdmin(){
        return adminService.selectAllAdmin();
    }

    @GetMapping(path="/admin/{id}")
    public Admin selectAdminById(@PathVariable("id") UUID id) {
        return adminService.selectAdminById(id).orElse(null);
    }

    @PutMapping(path="/admin/{id}")
    public int updateAdminById(@Valid @PathVariable("id") UUID id,
                                @Valid @NonNull @RequestBody Admin admin) {
        return adminService.updateAdminById(id, admin);
    }
}
