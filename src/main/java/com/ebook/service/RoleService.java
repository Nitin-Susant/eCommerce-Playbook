package com.ebook.service;

import java.util.List;

import com.ebook.entity.Roles;

public interface RoleService {

	public List<Roles> getAllRoless();

	public Roles getRolesById(Long id);

	public Roles createRoles(Roles Roles);

	public Roles updateRoles(Long id, Roles Roles);

	public void deleteRoles(Long id);

	 

}
