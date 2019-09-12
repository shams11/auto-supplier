package com.auto.supplier.controllers;

import com.auto.supplier.commons.models.Role;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.mappers.RoleMapper;
import com.auto.supplier.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@LoggingProfiler
@RequestMapping("/v1/roles")
public class RoleController {

  private final RoleService roleService;
  private final RoleMapper roleMapper;

  @Autowired
  public RoleController(RoleService roleService, RoleMapper roleMapper) {
    this.roleService = roleService;
    this.roleMapper = roleMapper;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Role findByUniqueName(@RequestParam(value = "uniqueName") String uniqueName) {
    return roleMapper.toPojo(roleService.findByUniqueName(uniqueName).get());
  }
}
