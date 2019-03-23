/*
 * @author: nerona
 * @date: 2016.4.1
 * @param: msg  弹出信息
 * 
 */
var errorBox = {
		showBox: function(msg){
			/*Toast错误提示框*/
	            var errorBox = $('<div id="errorBox"><p></p></div>');
	            errorBox.find('p').html(msg);
	            errorBox.css({
	                'display': 'inline-block',
	                'box-sizing': 'border-box',
	                'text-align': 'center',
	                'padding': '0 6px',
	                'background-color': 'rgba(201,201,201,.8)',
	                'color': 'rgb(83,83,83)',
	                'line-height': '24px',
	                'position': 'fixed',
	                'top': '50%',
	                'left': '50%',
	                'margin-top': '-12px',
	                'font-size': '14px',
	                'letter-spacing': '2px',
	                'border-radius': '3px'
	            });
	            errorBox.appendTo($('body'));
	            var wid =  errorBox.width();
	            errorBox.css('marginLeft', -wid/2);
	            setTimeout(function(){
	                errorBox.remove();
	            }, 1000);
	        }
}