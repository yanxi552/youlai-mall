package com.youlai.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.mapper.SysRolePermissionMapper;
import com.youlai.admin.pojo.entity.SysRolePermission;
import com.youlai.admin.service.ISysRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Override
    public List<Long> listPermissionIds(Long roleId, Integer type) {
        return this.baseMapper.listPermissionIds( roleId, type);
    }
}
