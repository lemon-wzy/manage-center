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
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;
import me.zhengjie.base.BaseEntity;
import me.zhengjie.modules.merchant.enums.ScheduleEnum;

/**
* @description /
* @author ChinaJoy
* @date 2023-11-26
**/
@Data
@Accessors(chain = true)

@TableName("sys_project_schedule")
public class ProjectSchedule extends BaseEntity implements Serializable {

    @TableId(value = "schedule_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "进度状态")
    private ScheduleEnum scheduleStatus;

    @ApiModelProperty(value = "指派人")
    private String assignUser;
    @ApiModelProperty(value = "指派人Id")
    private Long assignUserId;

    @ApiModelProperty(value = "创建者名字")
    private String nickName;
    @ApiModelProperty(value = "金额百分比")
    private Integer amountPercent;
    @ApiModelProperty(value = "进度描述")
    private String scheduleDesc;

    @ApiModelProperty(value = "指派状态",allowableValues = "0:指派,1:指派作废")
    private Integer assignStatus;
    @ApiModelProperty(value = "汇款时间")
    private LocalDateTime remittanceTime;

    public void copy(ProjectSchedule source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
