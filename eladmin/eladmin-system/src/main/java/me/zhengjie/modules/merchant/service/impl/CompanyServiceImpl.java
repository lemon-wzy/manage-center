package me.zhengjie.modules.merchant.service.impl;/*
*  Copyright 2019-2023 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.merchant.domain.Company;
import me.zhengjie.modules.merchant.domain.vo.CompanyQueryCriteria;
import me.zhengjie.modules.merchant.mapper.CompanyMapper;
import me.zhengjie.modules.merchant.service.CompanyService;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.zhengjie.utils.PageUtil;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import me.zhengjie.utils.PageResult;

/**
* @description 服务实现
* @author ChinaJoy
* @date 2023-11-22
**/
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    private final CompanyMapper companyMapper;

    @Override
    public PageResult<Company> queryAll(CompanyQueryCriteria criteria, Page<Object> page){
        IPage<Company> page1 = new Page<>(page.getCurrent(),page.getSize());
        return PageUtil.toPage(this.page(page1));
    }

    @Override
    public List<Company> queryAll(CompanyQueryCriteria criteria){
        return companyMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Company resources) {
        List<Company> company = list(new LambdaQueryWrapper<Company>().eq(Company::getName, resources.getName()));
        if(!company.isEmpty()){
            throw new BadRequestException("公司名称已存在");
        }
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Company resources) {
        List<Company> list = list(new LambdaQueryWrapper<Company>().eq(Company::getName, resources.getName())
                .ne(Company::getId, resources.getId()));
        if(!list.isEmpty()){
            throw new BadRequestException("公司名称已存在");
        }
        Company company = getById(resources.getId());
        company.copy(resources);
        saveOrUpdate(company);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Long> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<Company> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Company company : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("公司名称", company.getName());
            map.put("公司联系人", company.getUserName());
            map.put("公司联系人移动电话", company.getUserMobile());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}