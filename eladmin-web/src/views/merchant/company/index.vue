<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.keywords" 
          clearable size="small" 
          placeholder="输入公司名称或联系人进行搜索" 
          style="width: 230px;"
          class="filter-item" 
          @keyup.enter.native="crud.toQuery" 
        />
        <el-input v-model="query.areas" 
          clearable size="small" 
          placeholder="输入地区进行搜索" 
          style="width: 200px;"
          class="filter-item" @keyup.enter.native="crud.toQuery"
        />
        <el-input v-model="query.phone" 
          clearable size="small" 
          placeholder="输入联系人手机进行搜索" 
          style="width: 200px;"
          class="filter-item" @keyup.enter.native="crud.toQuery"
        />
        <rrOperation />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0"
        :title="crud.status.title" width="520px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">

          <el-form-item label="公司名称" prop="name">
            <el-input v-model="form.name" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="联系人">
            <el-input v-model="form.userName" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="联系人手机">
            <el-input v-model="form.userMobile" style="width: 350px;" maxlength="11" />
          </el-form-item>
          <el-form-item label="地区">
            <el-input v-model="form.areas" style="width: 370px;" />
          </el-form-item> 
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--拜访登记-->
      <el-dialog :close-on-click-modal="false" :visible.sync="visitVisible" width="520px" :title="拜访登记">
        <el-form ref="form" :model="visitFrom" :rules="visitRules" size="small" label-width="100px">
          <el-form-item label="公司名称">
            <el-input v-model="visitFrom.companyName" style="width: 350px;" disabled />
          </el-form-item>
          <el-form-item label="拜访内容" prop="content">
            <el-input v-model="visitFrom.content" style="width: 350px;" type="textarea" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="visitClose()">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => addVisit()">确认</el-button>
        </div>
      </el-dialog>
      <!--添加项目-->
      <el-dialog :close-on-click-modal="false" :visible.sync="projectVisible" width="520px" :title="添加项目">
        <el-form ref="form" :model="projectFrom" :rules="projectRules" size="small" label-width="100px">
          <el-form-item label="公司名称">
            <el-input v-model="projectFrom.companyName" style="width: 350px;" disabled />
          </el-form-item>
          <el-form-item label="项目名称">
            <el-input v-model="projectFrom.projectName" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="联系人">
            <el-input v-model="projectFrom.userName" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="联系人电话">
            <el-input v-model="projectFrom.userMobile" style="width: 350px;" maxlength="11" />
          </el-form-item>
          <el-form-item label="地区">
            <el-input v-model="projectFrom.areas" style="width: 370px;" />
          </el-form-item>  
          <el-form-item label="项目类型">
            <el-input v-model="projectFrom.projectTag" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="项目金额">
            <el-input v-model="projectFrom.projectAmount" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="金额百分比">
            <el-input v-model="projectFrom.amountPercent" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="项目描述" prop="projectDesc">
            <el-input v-model="projectFrom.projectDesc" style="width: 350px;" type="textarea" />
          </el-form-item>
          <el-form-item label="签单日期">
            <el-date-picker v-model="projectFrom.createTime" type="datetime" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="projectClose()">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => addProject()">确认</el-button>
        </div>
      </el-dialog>
      <!--添加专利-->
      <el-dialog :close-on-click-modal="false" :visible.sync="patentVisible" width="520px" :title="添加专利">
        <el-form ref="form" :model="patentFrom" :rules="rules" size="small" label-width="100px">
          <el-form-item label="公司名称">
            <el-input v-model="patentFrom.companyName" style="width: 350px;" disabled />
          </el-form-item>
          <el-form-item label="专利名称">
            <el-input v-model="patentFrom.patentName" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="发明专利">
            <el-input v-model="patentFrom.invention" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="地区">
            <el-input v-model="patentFrom.areas" style="width: 370px;" />
          </el-form-item>  
          <el-form-item label="实用新型专利">
            <el-input v-model="patentFrom.utilityModel" style="width: 350px;"  />
          </el-form-item>
          <el-form-item label="外观专利">
            <el-input v-model="patentFrom.appearance" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="软件著作">
            <el-input v-model="patentFrom.softwareWorks" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="版权">
            <el-input v-model="patentFrom.copyright" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="专利类型">
          
            <el-select
              v-model="form.patentTag"
              clearable            
              placeholder="专利类型"
              class="filter-item"
              style="width: 370px"
            >
              <el-option
                v-for="item in patentTypeOptions"
                :key="item.key"
                :label="item.value"
                :value="item.key"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="金额">
            <el-input v-model="patentFrom.projectAmount" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="金额百分比">
            <el-input v-model="patentFrom.amountPercent" style="width: 350px;" />
          </el-form-item>
          <el-form-item label="签单日期">
            <el-date-picker v-model="patentFrom.createTime" type="datetime" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="patentClose()">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="$event => addPatent()">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;"
        @select="crud.selectChange" @select-all="crud.selectAllChange" @selection-change="crud.selectionChangeHandler">
        <el-table-column :selectable="checkboxT" type="selection" width="55" />
        <el-table-column prop="name" label="公司名称" />
        <el-table-column prop="areas" label="地区" />
        <el-table-column prop="userName" label="联系人" />        
        <el-table-column prop="userMobile" label="联系人手机" />
        <el-table-column prop="createTime" label="创建日期" />
        <el-table-column prop="updateTime" label="更新时间" />
        <el-table-column v-if="checkPer(['admin', 'company:edit', 'company:del'])" label="操作" width="550px"
          align="center">
          <template slot-scope="scope">
            <udOperation :data="scope.row" :permission="permission" @addVisit="getVisit" @addProject="getProject" @addPatent="getPatent" />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudCompany from '@/api/merchant/company/company'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.visit'
import pagination from '@crud/Pagination'
import visitAdd from '@/api/merchant/visit'
import projectAdd from '@/api/merchant/project'
import patentAdd from '@/api/merchant/patent'
import rrOperation from '@crud/RR.operation'

const defaultForm = { id: null, name: null, userName: null, userMobile: null, createBy: null, updateBy: null, createTime: null, updateTime: null,areas:null}
export default {
  name: 'Company',
  components: { pagination, crudOperation, udOperation,rrOperation},
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '公司管理', url: 'api/merchant/company', sort: 'id,desc', crudMethod: { ...crudCompany } })
  },
  data() {
    return {
      permission: {
        add: ['admin', 'company:add'],
        edit: ['admin', 'company:edit'],
        del: ['admin', 'company:del']
      },
      //专利类型
      patentTypeOptions:[
        {key:  '发明', value :'发明'},
        {key:  '实用新型', value :'实用新型'},
        {key:  '外观', value :'外观'},
        {key:  '软著', value :'软著'},
        {key:  '版权', value :'版权'},
        {key:  '商标', value :'商标'}
      ],
      visitVisible: false,
      projectVisible:false,
      patentVisible: false,
      visitFrom: {
        companyId: 0,
        companyName: '',
        content: ''
      },
      projectFrom: {
        companyId: 0,
        companyName: '',
        projectName: '',
        projectDesc: '',
        projectTag: '',
        projectAmount: 0,
        userName: '',
        userMobile: '',
        amountPercent: 0,
        createTime: '',
        areas:""
      },
      patentFrom: {
        companyId: 0,
        companyName: '',
        invention: '',
        utilityModel: '',
        appearance: '',
        softwareWorks: '',
        copyright: '',
        projectAmount: 0,
        amountPercent: 0,
        patentTag: '',
        createTime: '',
        areas:"",
        patentName:""

      },
      rules: {
        name: [
          { required: true, message: '公司名称不能为空', trigger: 'blur' }
        ]
      },
      visitRules: {
        content: [
          { required: true, message: '拜访内容不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    getVisit(data) {
      console.log(data);
      this.visitFrom.companyId = data.id
      this.visitFrom.companyName = data.name
      this.visitVisible = true
    },
    addVisit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          visitAdd.add(this.visitFrom).then(() => {
            this.crud.notify('添加成功', 'success')
            this.visitVisible = false
          })
        }
      })
    },
    visitClose() {
      this.visitVisible = false
    },
    getProject(data) {
      this.projectFrom.companyId = data.id
      this.projectFrom.companyName = data.name
      this.projectFrom.projectName = ''
      this.projectFrom.projectDesc = ''
      this.projectFrom.projectTag = ''
      this.projectFrom.projectAmount = 0
      this.projectFrom.userName = ''
      this.projectFrom.userMobile = ''
      this.projectFrom.amountPercent = 0
      this.projectFrom.createTime = ''
      this.projectVisible = true
    },
    addProject() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          projectAdd.add(this.projectFrom).then(() => {
            this.crud.notify('添加成功', 'success')
            this.projectVisible = false
          })          
        }
      })
    },
    projectClose() {
      this.projectVisible = false
    },
    getPatent(data) {
      this.patentFrom.companyId = data.id
      this.patentFrom.companyName = data.name
      this.patentFrom.invention = ''
      this.patentFrom.utilityModel = ''
      this.patentFrom.appearance = ''
      this.patentFrom.softwareWorks = ''
      this.patentFrom.copyright = ''
      this.patentFrom.projectAmount = 0
      this.patentFrom.amountPercent = 0
      this.patentFrom.createTime = ''
      this.patentFrom.patentTag = ''
      this.patentVisible = true
    },
    addPatent() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          patentAdd.add(this.patentFrom).then(() => {
            this.crud.notify('添加成功', 'success')
            this.patentVisible = false
            this.patentFrom = {}
          })
        }
      })
    },
    patentClose() {
      this.patentVisible = false
    },
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped></style>
