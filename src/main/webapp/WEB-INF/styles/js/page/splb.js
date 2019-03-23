/*
 * 2015年1月20日 07:06:06
 * 
 * 商品类别
 * 
 * */

$(function() {

	$('#btn_splb_view_save').linkbutton({
		iconCls : 'icon-save'
	});
	$('#btn_splb_view_edit').linkbutton({
		iconCls : 'icon-edit'
	});
	$('#btn_splb_view_remove').linkbutton({
		iconCls : 'icon-tip'
	});

	$('#btn_splb_view_save_do').linkbutton({
		iconCls : 'icon-save'
	});
	$('#btn_splb_view_edit_do').linkbutton({
		iconCls : 'icon-edit'
	});
	$('#btn_splb_view_remove_do').linkbutton({
		iconCls : 'icon-remove'
	});

	// 默认全部隐藏
	$("#div_splb_node_save").hide();
	$("#div_splb_node_edit").hide();
	$("#div_splb_node_view").hide();

	if ('${selectedSplbLevel}' == '-1') {
		// 非法访问
	} else if ('${selectedSplbLevel}' == '0') {
		// 没有编辑当前节点
		$("#btn_splb_view_edit").hide();
		$("#btn_splb_view_remove").hide();
		$("#div_splb_node_save").show();

		$("#splbLevel_edit").val('');
		$("#splbPid_edit").val('');

		$("#splbLevel_save").val('1');
		$("#splbPid_save").val('0');

	} else if ('${selectedSplbLevel}' == '1') {
		// 全部都有
		$("#div_splb_node_view").show();

		$("#splbLevel_edit").val('1');
		$("#splbPid_edit").val('${mkSplb.splbPid}');

		$("#splbLevel_save").val('2');
		$("#splbPid_save").val('${mkSplb.splbId}');
	} else if ('${selectedSplbLevel}' == '2') {
		// 没有添加子节点
		$("#btn_splb_view_save").hide();
		$("#div_splb_node_view").show();

		$("#splbLevel_edit").val('${mkSplb.splbLevel}');
		$("#splbPid_edit").val('${mkSplb.splbPid}');
	}

	$('#btn_splb_view_save').bind('click', function() {
		$("#div_splb_node_save").show();
		$("#div_splb_node_edit").hide();
		$("#div_splb_node_view").hide();
	});
	$('#btn_splb_view_edit').bind('click', function() {
		$("#div_splb_node_save").hide();
		$("#div_splb_node_edit").show();
		$("#div_splb_node_view").hide();
	});
	$('#btn_splb_view_remove').bind('click', function() {
		$("#div_splb_node_view").show();
		$("#div_splb_node_save").hide();
		$("#div_splb_node_edit").hide();
	});

	$("#div_splb_node_save_inner").panel({
		title : '新增子节点',
		iconCls : 'icon-save',
		collapsible : true,
		height : 400
	});
	$("#div_splb_node_edit_inner").panel({
		title : '编辑当前节点',
		iconCls : 'icon-edit',
		collapsible : true,
		height : 400
	});
	$("#div_splb_node_view_inner").panel({
		title : '查看当前节点',
		iconCls : 'icon-tip',
		collapsible : true,
		height : 400
	});

	// set values
	if ('${!empty mkSplb}' == 'true') {

		$("#splbName_view").val('${mkSplb.splbName}');
		$("#splbDesc_view").val('${mkSplb.splbDesc}');
		$("#splbIcon_view").val('${mkSplb.splbIcon}');

		$("#splbName_edit").val('${mkSplb.splbName}');
		$("#splbDesc_edit").val('${mkSplb.splbDesc}');
		$("#splbIcon_edit").val('${mkSplb.splbIcon}');
	}

	// btn action
	$('#btn_splb_view_save_do').bind('click', function() {
		$("#div_splb_node_save").show();
		$("#div_splb_node_edit").hide();
		$("#div_splb_node_view").hide();
	});
	$('#btn_splb_view_edit_do').bind('click', function() {
		$("#div_splb_node_save").hide();
		$("#div_splb_node_edit").show();
		$("#div_splb_node_view").hide();
	});
	$('#btn_splb_view_remove_do').bind('click', function() {
		$("#div_splb_node_view").show();
		$("#div_splb_node_save").hide();
		$("#div_splb_node_edit").hide();
	});
});
