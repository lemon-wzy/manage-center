/*
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
package me.zhengjie.modules.merchant.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import java.sql.Timestamp;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import me.zhengjie.base.BaseEntity;

/**
* @description /
* @author ChinaJoy
* @date 2023-11-23
**/
@Data
@TableName("sys_visit_log")
public class VisitLog extends BaseEntity implements Serializable {

    @TableId(value = "log_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "公司id")
    private Long companyId;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "日志内容")
    private String content;

    @ApiModelProperty(value = "创建者名字")
    private String nickName;



    public void copy(VisitLog source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
