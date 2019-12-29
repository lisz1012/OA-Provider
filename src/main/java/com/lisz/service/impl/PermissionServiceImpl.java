package com.lisz.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lisz.entity.ResponseStatus;
import com.lisz.entity.Permission;
import com.lisz.mapper.PermissionMapper;
import com.lisz.service.PermissionService;

@Service(version = "1.0.0", timeout = 10000, interfaceClass = PermissionService.class)
@Component
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionMapper mapper;

	@Override
	public List<Permission> findAll() {
		return mapper.selectByExample(null);
	}

	@Override
	public PageInfo<Permission> findByPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize); // PageHelper和PageInfo是分页主要用到的工具类。
		return new PageInfo<Permission>(findAll(), 10);//包装成一个PageInfo对象，解决分页的问题.可以通过第二个参数动态调整最多显示的页码数,default = 8
	}

	@Override
	public ResponseStatus deleteById(int id) {
		// 1. 提示用户将要删除，不能直接删
		// 2. 回收站等通过删除标记来完成删除操作，以达到数据永远删不掉的效果 / update有的也是只增不改，多余的数据存储到别的机器或者数据库表
		int rows = mapper.deleteByPrimaryKey(id);
		if (rows == 1) {
			return new ResponseStatus(200, "OK", "Delete successfully");
		}
		return new ResponseStatus(500, "Internal error", "Delete failed.");
	}

	@Override
	public Permission findById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public ResponseStatus add(Permission permission) {
		int inserted = mapper.insert(permission);
		if (inserted == 1) {
			return new ResponseStatus(200, "OK", "Insertion succeeded!");
		} else {
			return new ResponseStatus(500, "Failed", "Insertion of a permission failed");
		}
	}

	@Override
	public ResponseStatus update(Permission permission) {
		int updated = mapper.updateByPrimaryKey(permission);
		if (updated == 1) {
			return new ResponseStatus(200, "OK", "Update succeeded!");
		} else {
			return new ResponseStatus(500, "Failed", "Update of a permission failed");
		}
	}
	
	@Override
	public PageInfo<Permission> getPermissionsForRoleId(int id) {
		List<Permission> permissions = mapper.getPermissionsForRoleId(id);
		return new PageInfo<Permission>(permissions);
	}
}
