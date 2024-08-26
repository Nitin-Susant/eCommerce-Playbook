package com.ebook.serviceImplimentation;

 
 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebook.entity.Roles;
import com.ebook.repositery.RolesRepositrry;
import com.ebook.service.RoleService;

@Service
public class RolesServiceImpl implements RoleService {

    @Autowired
    private RolesRepositrry RolesRepository;

    public List<Roles> getAllRoless() {
        return RolesRepository.findAll();
    }

    public Roles getRolesById(Long id) {
        Optional<Roles> Roles = RolesRepository.findById(id);
        return Roles.orElse(null);
    }

    public Roles createRoles(Roles Roles) {
        return RolesRepository.save(Roles);
    }

    public Roles updateRoles(Long id, Roles Roles) {
        Roles.setId(id); // Ensure the correct ID is set
        return RolesRepository.save(Roles);
    }

    public void deleteRoles(Long id) {
        RolesRepository.deleteById(id);
    }

	 
}
