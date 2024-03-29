package me.zhengjie.modules.merchant.domain.vo;/*
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


import lombok.Data;

/**
*   搜索参数构造对象
* @author ChinaJoy
* @date 2023-11-22
**/
@Data
public class CompanyQueryCriteria{

    //关键字搜索
    private String keywords;
    //联系人电话搜索
    private String phone;
    //创建人
    private String createBy;
    private String areas;
}