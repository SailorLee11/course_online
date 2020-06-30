<template>
    <div>
        <p>
            <button v-on:click="add()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增
            </button>
             &nbsp;
            <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
                <i class="ace-icon fa fa-refresh "></i>
                刷新
            </button>
        </p>

<!--        绑定一个list方法，及获取外部主键list的方法-->
        <pagination ref="pagination" v-bind:list="list"></pagination>

        <table id="simple-table" class="table  table-bordered table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>名称</th>
                    <th>课程ID</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>
<!-- chapters是在js里面用的变量，chapter是里面用的变量               -->
                <tr v-for="chapter in chapters">

                    <td>{{chapter.id}}</td>
                    <td>{{chapter.name}}</td>
                    <td>{{chapter.courseId}}</td>

                    <td>
                        <div class="hidden-sm hidden-xs btn-group">
<!--这个chapter就是上面 v-for的变量 -->
                            <button v-on:click="edit(chapter)" class="btn btn-xs btn-info">
                                <i class="ace-icon fa fa-pencil bigger-120"></i>
                            </button>

                            <button v-on:click="del(chapter.id)" class="btn btn-xs btn-danger">
                                <i class="ace-icon fa fa-trash-o bigger-120"></i>
                            </button>

                        </div>

                        <div class="hidden-md hidden-lg">
                            <div class="inline pos-rel">
                                <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                    <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                </button>

                                <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                    <li>
                                        <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																			<span class="blue">
																				<i class="ace-icon fa fa-search-plus bigger-120"></i>
																			</span>
                                        </a>
                                    </li>

                                    <li>
                                        <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																			<span class="green">
																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																			</span>
                                        </a>
                                    </li>

                                    <li>
                                        <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																			<span class="red">
																				<i class="ace-icon fa fa-trash-o bigger-120"></i>
																			</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </td>
                </tr>

                </tbody>
            </table>

        <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">表单</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">名称</label>
                                <div class="col-sm-10">
                                    <input v-model="chapter.name" class="form-control" placeholder="名称">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">课程ID</label>
                                <div class="col-sm-10">
                                    <input v-model="chapter.courseId" class="form-control" placeholder="课程Id">
                                </div>
                            </div>

<!--                            <div class="form-group">-->
<!--                                <label class="col-sm-2 control-label">课程</label>-->
<!--                                <div class="col-sm-10">-->
<!--                                    <p class="form-control-static">{{course.name}}</p>-->
<!--                                </div>-->
<!--                            </div>-->
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>
</template>


<script>
    import Pagination from "../../components/pagination";
    export default{
        name:"chapter",
        components: {Pagination},
        data: function(){
          return{
              chapter:{},
              chapters:[],
          }
        },
        mounted:function () {
            let _this = this;
            _this.list();
            //sidebar激活样式 方法一
            // this.$parent.activeSidebar("business-chapter-sidebar")
        },
        methods:{
            /**
             * 点击【新增】
             * modal是css的选择器，静态框代码里的modal的样式
             */
            add() {
                let _this = this;
                _this.chapter = {};
                $("#form-modal").modal("show");
            },
            /**
             * 点击【编辑】
             * 修改
             */
            edit(chapter) {
                let _this = this;
                //把chapter复制到{}里面
                _this.chapter = $.extend({},chapter);
                $("#form-modal").modal("show");
            },
            /**
             * 列表
             */
            list(page){
                let _this = this;
                _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/list',{
                    page:page,
                    size:_this.$refs.pagination.size,
                }).then((response)=>{
                    console.log("查询大章列表结果:",response);
                    let resp = response.data;//resp就是我们responsedto
                    _this.chapters = resp.content.list;
                    //重新渲染该组件
                    _this.$refs.pagination.render(page,resp.content.total);
                })
            },

            /**
             * 点击【保存】
             */
            save(page){
                let _this = this;
                _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/save',_this.chapter).then((response)=>{
                    console.log("保存大章列表结果:",response);
                    let resp = response.data;
                    if (resp.success){
                        $("#form-modal").modal("hide");
                        _this.list(1);
                    }
                })
            },
            del(id){
                let _this = this;
                Swal.fire({
                    title: '确认删除？',
                    text: "删除后不可恢复，确认删除？",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '是的，删他！'
                }).then((result) => {
                    if (result.value) {
                        _this.$ajax.delete('http://127.0.0.1:9000/business/admin/chapter/delete/'+id).then((response)=>{
                            console.log("删除大章列表结果:",response);
                            let resp = response.data;
                            if (resp.success){
                                _this.list(1);
                                Swal.fire(
                                    '删除成功',
                                    'Your file has been deleted.',
                                    'success'
                                )
                            }
                        })

                    }
                })

            }
        }
    }
</script>