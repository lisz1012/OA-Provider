package com.lisz.mapper;

import com.lisz.entity.Account;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * AccountMapper继承基类
 */
@Repository
@Mapper
public interface AccountMapper extends MyBatisBaseDao<Account, Integer, AccountExample> {
	Account findById(Integer id);

	void addRolesForAccount(int[] roleIds, int accountId);

	Account findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}