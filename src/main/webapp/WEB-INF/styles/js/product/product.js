
/**
 * 商品详情页展示
 * @param ctxPath
 * @param id 商品ID
 */
product_view = function(ctxPath, id){
	openWindowRandom('【商品详情】',
			ctxPath + '/common/productView/' + id + '?_t=' + Math.random(), 
			800, 600);
};

/**
 * 删除购物车
 * @param ctxPath
 * @param id
 */
removeOrderCar = function(ctxPath, id, dataGridId){
    $.messager.confirm("系统提示","确定要删除？",function(r){
        if(r){ 
        	var url = ctxPath+'/company/agent/cartRemove/'+id+'?_t=' + Math.random();
			$.post(url, {"itemId": id}, function(data) {
						if (data.r1 == '200') {
							tipsinfo("操作成功!");
							// 刷新列表
							$('#'+dataGridId).datagrid('load'); //重新加载
						} else if(data.r1=='600'){
							tipsinfo("非法访问！");
		                } else if(data.d1){
		                	tipsinfo("操作失败："+data.d1);
						} else{
							tipsinfo("网络不给力，请稍后再试！");
		                }
					}, 'json');
        }
    });
};

/**
 * 修改购物车
 * @param ctxPath
 * @param id
 */
updateOrderCar = function(ctxPath, id, count, dataGridId){
	var url = ctxPath+'/company/agent/cartUpdate/'+id+'?_t=' + Math.random();
	$.post(	url, 
			{"itemId": id,
			"productCount": count}, 
			function(data) {
				if (data.r1 == '200') {
					tipsinfo("操作成功!");
					// 刷新列表
					$('#'+dataGridId).datagrid('load'); //重新加载
				} else if(data.r1=='600'){
					tipsinfo("非法访问！");
		        } else if(data.d1){
		        	tipsinfo("操作失败："+data.d1);
				} else{
					tipsinfo("网络不给力，请稍后再试！");
		        }
			}, 'json');
};

/**
 * 商品-圈口
 * @param ctxPath
 * @param id 商品ID
 */
to_product_size = function(ctxPath, id){
	openWindowRandom('【圈口】',
			ctxPath + '/company/admin/toProductSizeList/' + id + '?_t=' + Math.random(), 
			800, 600);
};

/**
 * 商品-出入库
 * @param ctxPath
 * @param id 商品ID
 */
to_product_ware = function(ctxPath, id){
	openWindowRandom('【出入库】',
			ctxPath + '/company/admin/productWare/' + id + '?_t=' + Math.random(), 
			800, 600);
};