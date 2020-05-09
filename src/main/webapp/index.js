var totalCount;
var currentPage;
var v;

$(function() {
	v = jQuery("#empSaveForm").validate({// 需要在页面加载时调用以绑定校验事件
		rules : {
			lastName : {
				required : true,
				minlength : 5,
				remote : { // 验证用户名是否存在
					type : "GET",
					url : contextPath + "/checkEmp",
					data : {
						lastName : function() {
							return $("#inputEmpName").val();
						}
					}
				}
			},
			email : {
				required : true,
				email : true
			}
		},
		messages : {
			lastName : {
				remote : "用戶名已存在"
			}
		}
	});

	toPage(1);
});

function toPage(pn) {
	$.ajax({
		url : contextPath + "/emps",
		data : "pn=" + pn,
		type : "GET",
		success : function(result) {
			createEmpTable(result);
			createPageInfo(result);
			createPageNavigator(result);
		}
	});
}

function createEmpTable(result) {
	$("#emps-table tbody").empty();
	var emps = result.extend.pageInfo.list;
	$.each(emps, function(index, item) {
		var checkTd = $("<td><input type='checkbox' class='checkItem'/></td>");
		var idTd = $("<td></td>").append(item.id);
		var nameTd = $("<td></td>").append(item.lastName);
		var emailTd = $("<td></td>").append(item.email);
		var genderTd = $("<td></td>").append(item.gender == 0 ? "女" : "男");
		var departmentTd = $("<td></td>").append(item.department.name);
		var editBtn = $("<button></button>").addClass(
				"btn btn-danger btn-sm edtBtn").append(
				$("<span></span>").addClass("glyphicon glyphicon-pencil"))
				.append("編輯");
		editBtn.attr("empID", item.id);
		var delBtn = $("<button></button>").addClass(
				"btn btn-primary btn-sm delBtn").append(
				$("<span></span>").addClass("glyphicon glyphicon-trash"))
				.append("刪除");
		delBtn.attr("empID", item.id);
		var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
		$("<tr></tr>").append(checkTd).append(idTd).append(nameTd).append(
				emailTd).append(genderTd).append(departmentTd).append(btnTd)
				.appendTo("#emps-table tbody");
	});
}

function createPageInfo(result) {
	$("#page-info").empty();
	currentPage = result.extend.pageInfo.pageNum;
	var pages = result.extend.pageInfo.pages;
	totalCount = result.extend.pageInfo.total;
	$("#page-info").append(
			"當前 " + currentPage + " 頁，總 " + pages + "頁，總 " + totalCount
					+ " 條記錄");
}

/**
 * 創建導航條
 * 
 * @param result
 * @returns
 */
function createPageNavigator(result) {
	$("#page-navigation").empty();
	var firstPage = $("<li></li>").append($("<a></a>").append("首頁")).addClass(
			"disabled");
	var lastPage = $("<li></li>").append($("<a></a>").append("末頁")).addClass(
			"disabled");
	var prePage = $("<li></li>").append($("<a></a>").append("&laquo;"));
	var nextPage = $("<li></li>").append($("<a></a>").append("&raquo;"));

	var navigatePages = result.extend.pageInfo.navigatepageNums;
	var ul = $("<ul></ul>").addClass("pagination");

	prePage.click(function() {
		toPage(result.extend.pageInfo.pageNum - 1);
	});
	nextPage.click(function() {
		toPage(result.extend.pageInfo.pageNum + 1);
	});

	ul.append(firstPage)
	if (result.extend.pageInfo.hasPreviousPage) {
		ul.append(prePage);
		firstPage.removeClass("disabled");
		firstPage.click(function() {
			toPage(1);
		});
	}
	$.each(navigatePages, function(i, elt) {
		var li = $("<li></li>").append($("<a></a>").append(elt));
		if (result.extend.pageInfo.pageNum == elt) {
			li.addClass("active");
		}
		li.click(function() {
			toPage(elt);
		});
		ul.append(li);
	});
	if (result.extend.pageInfo.hasNextPage) {
		ul.append(nextPage);
		lastPage.removeClass("disabled");
		lastPage.click(function() {
			toPage(result.extend.pageInfo.pages);
		});
	}
	ul.append(lastPage);
	$("#page-navigation").append(
			$("<nav></nav>").attr("aria-label", "Page navigation").append(ul));
}

/**
 * 新增員工
 * 
 * @returns
 */
$("#empSave").click(function() {
	if ($("#empSaveForm").valid()) {
		$.ajax({
			url : contextPath + "/emps",
			type : "POST",
			data : $("#empModal form").serialize(),
			success : function(result) {
				if (result.code == 200) {
					$("#empModal").modal("hide");
					toPage(totalCount);
				} else {
					$.each(result.extend.error, function(i, elt) {
						alert(i + " : " + elt);
					});
				}
			}
		});
	}

});

/**
 * 部門信息獲取
 * 
 * @returns
 */
function getDepartments() {
	$("#empModal select").empty();
	$.ajax({
		url : contextPath + "/departments",
		type : "GET",
		success : function(result) {
			$.each(result.extend.depts, function(i, elt) {
				var option = $("<option></option>").append(elt.name).attr(
						"value", elt.id);
				$("#empModal select").append(option);
			});
		}
	});
}

function getEmp(id) {
	$
			.ajax({
				url : contextPath + "/emp/" + id,
				type : "GET",
				success : function(result) {
					console.log(result);
					$("#inputEmpName").val(result.extend.emp.lastName).attr(
							"disabled", "disabled");
					$("#inputEmail").val(result.extend.emp.email);
					$("#empModal input[name=gender]").val(
							[ result.extend.emp.gender ]);
					$("#empModal select").val([ result.extend.emp.dId ]);
				}
			});
}

/**
 * 彈出新增模態框
 * 
 * @returns
 */
$("#empAddBtn").click(function() {
	v.resetForm();
	$("#inputEmpName").removeAttr("disabled");
	$("#empUpdate").css("display", "none");
	$("#empSave").css("display", "inline");
	$("#empModal input[name!=gender]").val("");
	getDepartments();
	$("#empModal").modal({
		backdrop : "static"
	});
});

/**
 * 彈出編輯模態框
 * 
 * @returns
 */
$(document).on(
		"click",
		".edtBtn",
		function() {
			v.resetForm();
			$("#empModal input[name!=gender]").val("");
			getDepartments();
			getEmp($(this).attr("empID"));
			$("#empUpdate").attr("empID", $(this).attr("empID")).css("display",
					"inline");
			$("#empSave").css("display", "none");
			$("#empModal").modal({
				backdrop : "static"
			});
		});

/**
 * 單個刪除
 * 
 * @returns
 */
$(document).on("click", ".delBtn", function() {
	var empName = $(this).parents("tr").find("td:eq(2)").text();
	if (confirm("確認刪除【" + empName + "】嗎？")) {
		$.ajax({
			url : contextPath + "/emp/" + $(this).attr("empID"),
			type : "DELETE",
			success : function(result) {
				if (result.code == 200) {
					$("#empModal").modal("hide");
					toPage(currentPage);
				} else {
					$.each(result.extend.error, function(i, elt) {
						alert(i + " : " + elt);
					});
				}
			}
		});
	}
});
/**
 * 更新員工
 * 
 * @returns
 */
$("#empUpdate").click(function() {
	if ($("#empSaveForm").valid()) {
		$.ajax({
			url : contextPath + "/emp/" + $(this).attr("empID"),
			type : "PUT",
			data : $("#empModal form").serialize(),
			success : function(result) {
				if (result.code == 200) {
					$("#empModal").modal("hide");
					toPage(currentPage);
				} else {
					$.each(result.extend.error, function(i, elt) {
						alert(i + " : " + elt);
					});
				}
			}
		});
	}
});

$("#checkAll").click(function() {
	$(".checkItem").prop("checked", $(this).prop("checked"));
});

$(document).on("click", ".checkItem", function() {
	var flag = $(".checkItem:checked").length == $(".checkItem").length;
	$("#checkAll").prop("checked", flag);
});

/**
 * 批量刪除
 * 
 * @returns
 */
$("#empDelBtn").click(
		function() {
			var empNameList = "";
			var idList = "";
			$.each($(".checkItem:checked"), function() {
				empNameList += $(this).parents("tr").find("td:eq(2)").text()
						+ ",";
				idList += $(this).parents("tr").find("td:eq(1)").text() + "-";

			});

			if (confirm("確認刪除【" + empNameList.substr(0, empNameList.length - 1)
					+ "】嗎？")) {
				$.ajax({
					url : contextPath + "/emp/"
							+ idList.substr(0, idList.length - 1),
					type : "DELETE",
					success : function(result) {
						if (result.code == 200) {
							toPage(currentPage);
						} else {
							$.each(result.extend.error, function(i, elt) {
								alert(i + " : " + elt);
							});
						}
					}
				})
			}

		});