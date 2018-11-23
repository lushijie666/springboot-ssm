$(function(){
    base.init();
})
var base = {
    init : function(){
        $(document).ajaxComplete(function(event,obj,settings){
            if (obj.responseText == 'timeout') {
                location.href='/';
            }
        })
    }
};

