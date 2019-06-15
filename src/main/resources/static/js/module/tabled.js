layui.define(['table'],function(exports){
    var table = layui.table,$ = layui.$,
        defConfig = {
            toolbar:'#toolbar',
            page:true,
            limit:10,
            defaultToolbar:null,
            request: {
                pageName: 'pager:pageIndex', //页码的参数名称，默认：page
                limitName: 'pager:pageSize' //每页数据量的参数名，默认：limit
            },
            response: {
                // statusName: 'status', //规定数据状态的字段名称，默认：code
                statusCode: 'OK', //规定成功的状态码，默认：0
                // msgName: 'hint', //规定状态信息的字段名称，默认：msg
                // countName: 'total', //规定数据总数的字段名称，默认：count
                // dataName: 'rows', //规定数据列表的字段名称，默认：data
            },
            parseData: function(res){ //res 即为原始返回的数据
                return {
                    "code": res.status, //解析接口状态
                    "msg": res.info, //解析提示文本
                    "count": res.result?(res.result.total||res.result.length):0, //解析数据长度
                    "data": res.result?res.result.result:[] //解析数据列表
                };
            }
        }

    var Tabled = function(){
        this;
    }
    $.extend(Tabled.prototype,table);

    Tabled.prototype.render = function(options){
        var options = options ||  {};
        if(options.elem && options.elem.indexOf('#')==0 && !options.id){
            options.id = options.elem.substring(1);
        }
        options = $.extend({},defConfig,options);
        if(options.url && options.url.indexOf('/')==0){
            options.url = layui.contextPath + options.url;
        }
        this.ins = table.render(options);
        this.init();
        return new DegelateTableIns(this.ins);
    }

    var DegelateTableIns = function(ins){
        if(ins){
            var that = this;
            for(var prop in ins){
                var oldProp = ins[prop];
                if($.isFunction(oldProp)){
                    ins[prop] = (function (fn) {
                        return function () {
                            if(arguments.length==1){
                                var options = arguments[0];
                                if(options.url && options.url.indexOf('/')==0){
                                    options.url = layui.contextPath + options.url;
                                }
                                return fn.call(this,options);
                            }
                            fn.call(this,arguments);
                        }
                    })(oldProp);
                }
            }
        }
        return ins;
    }

    Tabled.prototype.reload = function(){
        var options;
        if(arguments.length==2){
            options = arguments[1] ||  {};
            if(options.url && options.url.indexOf('/')==0){
                options.url = layui.contextPath + options.url;
            }
        }
        if(options){
            table.reload(arguments[0],options);
        }else{
            table.reload(arguments[0]);
        }
    }

    Tabled.prototype.init = function(){
        var ins = this.ins;
        if(ins){
            ins.config.elem.on('click', 'tr', function(){ //单击行
                $(this).addClass('layui-table-selected').siblings().removeClass('layui-table-selected');
            });
        }
    }

    Tabled.prototype.convert = function(code){
        var that = this;
        return (function(c){
            return function(row){
                var value = row[this.field];
                return that.codeConvert.call(that,c,value);
            }
        })(code);
    }
    Tabled.prototype.codeConvert = function(code,value){
        if(layui._icode && layui._icode[code]){
            return layui._icode[code][value];
        }
        return value;
    }

    exports('tabled',new Tabled)
});