/**
 * 菜单数据格式menu-data
 *  [
 *      {id:'',label:'',cls:'',href:'',children:[]}
 *  ]
 */
layui.use(['element','tool'],function(exports){
    var $ = layui.$,tool = layui.tool,element = layui.element;
    var menus = [ ];
    var Menu = function(){
        this.render();
    };
    Menu.prototype.render = function(data){
        tool.http.ajax({
            url:'/menu/tree',
            method:'get',
            success:function(res){
                $('.layui-nav.layui-nav-tree').html(build(res.result));
                element.render();
            },
        });
    }
    function build(data){
        var menuHtml = [];
        if( $.isArray(data)){
            data.forEach(function(e,i){
                menuHtml.push(' <li class="layui-nav-item layui-anim-upbit">');
                menuHtml.push('   <a href="javascript:;"><i class="'+e.cls+'"></i><em>'+e.label+'</em><span class="layui-nav-more"></span></a>');//<span class="layui-nav-more">
                if(e.children && $.isArray(e.children)){
                    menuHtml.push('<dl class="layui-nav-child">');
                    e.children.forEach(function (c,j) {
                        menuHtml.push('<dd><a href="javascript:;" data-url="'+c.href+'" data-id="'+c.id+'" data-text="'+c.label+'"><span class="l-line"></span>'+c.label+'</a></dd>');
                    });
                    menuHtml.push('</dl>');
                }
                menuHtml.push(' </li>');
            })
        }
        return menuHtml.join('');
    }
    new Menu();
    // exports('menu',new Menu)
});