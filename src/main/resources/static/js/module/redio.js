/**
 * options: url 、code、 name、prop、elem
 * $elem: convert
 */
layui.define(['table','laytpl','tool'],function(exports){
    var config = {
        PROP:'prop',
        CODE:'code',
        NAME:'name'
    }
    var tool = layui.tool,laytpl=layui.laytpl,$ = layui.$,
        tpl = '<input type="radio" name="{{d.prop}}" value="{{d.code}}" title="{{d.name}}" {{#  if(d.code == d.defValue){ }} checked {{#  } }} >';

    var Redio = function(){

    }
    Redio.prototype.render = function(options){
        if(!options || !options.elem) return;
        var initOpt = tool.object.strToObject(options.elem);
        $.extend(options,initOpt);

        var opts = [],$elem = options.elem,convert = $elem.data('convert')||options.convert;
        var defValue = $elem.data('def');
        if(convert && layui._idata){
            for(var prop in layui._idata[convert]){
                var data = {
                    defValue:defValue,
                    prop:$elem.attr('name'),
                    code:layui._idata[convert][prop].code,
                    name:layui._idata[convert][prop].caption
                }
                laytpl(tpl).render(data, function(html){
                    opts.push(html)
                });
            }
            $elem.parent().html(opts.join(''))
            return;
        }
        if((options.url && options.code && options.name)||(options.url && options.code=='#')){
            tool.http.ajax({
                url:options.url,
                method:'get',
                success:function (res) {
                    for(i in res.result){
                        var _data = {defValue:defValue, prop:options.prop||options.code}
                        var data = options.code=='#'?
                            {
                                code:res.result[i],
                                name:res.result[i]
                            }:{
                                code:res.result[i][options.code],
                                name:res.result[i][options.name]
                            }
                        $.extend(data,_data);
                        laytpl(tpl).render(data, function(html){
                            opts.push(html)
                        });
                    }
                    $elem.parent().html(opts.join(''))
                }
            })
        }
    }
    exports('redio',new Redio)
})