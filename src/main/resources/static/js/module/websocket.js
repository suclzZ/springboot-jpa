layui.define(['layer'],function(exports){
    var layer = layui.layer;
    var defConfig = {
        PORT: 8888,
        ENDPOINT : '/websocket',
        TIMEOUT : 30*1000
    }
    var _WebSocket = function(){
        if(!window.WebSocket){
            console.error("don't support WebSocket");
        }else{
            var taht = this,start = +new Date(),
            loop = setInterval(function () {
                taht.websocket = new WebSocket('ws://'+window.location.host+layui.contextPath+defConfig.ENDPOINT);
                (+new Date()-start>=defConfig.TIMEOUT) && clearInterval(loop);//超时处理
                taht.websocket.onopen = function () {
                    window.onbeforeunload = function(){
                        ws.close();
                    }
                    clearInterval(loop);
                };
                // 接收服务端数据时触发事件
                taht.websocket.onmessage = function (evt) {
                    layer.msg(evt.data,{offset: 'lb'});
                };
                taht.websocket.onclose = function () {
                    console.debug('websocket 关闭');
                };
                taht.websocket.onerror = function(){
                    console.debug('websocket 连接错误');
                };
            }, 1000);
        }
    }
    _WebSocket.prototype.send = function(message){
        this.websocket.send(message);
    }
    _WebSocket.prototype.close = function(options){
        this.websocket.close();
    }

    exports('websocket',_WebSocket)
})