<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>水质信息浏览</title>
<link href="css/common.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-table.css" rel="stylesheet">
<link href="css/pagination.css" rel="stylesheet">
<script src="js/Plugin/jquery-3.3.1.min.js"></script>
<script src="js/Plugin/echarts.min.js"></script>
<script src="js/Plugin/jquery.pagination.min.js"></script>
<script src="js/common.js"></script>
<script src="js/browsing.js"></script>
</head>
<body>
<!--顶部-->
<div class="header left">
    <div class="left nav text_left">
        <ul>
            <li><i class="nav_1"></i><a href="collected-water">采集概况</a> </li>
            <li><i class="nav_2"></i><a href="statistical-water">指标分析</a> </li>
            <li><i class="nav_3"></i><a href="predicted-water">趋势预测</a> </li>
            <li class="nav_active"><i class="nav_4"></i><a href="search-water">信息浏览</a> </li>
            <li><i class="nav_5"></i><a href="alarm-water">监测报警</a> </li>
          </ul>
    </div>
    <div class="header_center left" style="position:relative">
        <h2><strong>国家地表水水质大数据分析平台</strong></h2>

    </div>
    <div class="text_right">
		<h3 style="color:#fff;"><span id="time">时间加载中...</span></h3>
    </div>

</div>
<!--内容部分-->
<div class="con left" style="width: 98%;margin-left: 1%;margin-bottom: 25px;">
	<form method="post" action="search-water">
    <!-- <input type="text" placeholder="请输入姓名身份证" style="width: 180px;height: 28px;border-radius: 3px;text-indent: 1em;border: 1px solid#4b8df8;color: #fff;" /> -->
    <select name="Proname" style="width: 210px;height: 40px;border-radius: 3px;text-indent: 1em;border: 1px solid#4b8df8;color: #000;font-size: 20px;">
         <option>全国</option>
         <c:forEach var="provincename" items="${provinceList}" >	
                  <option>${provincename}</option>
           </c:forEach>
         <!-- <option>北京市</option>
         <option>天津市</option>
         <option>河北省</option>
         <option>山西省</option>
         <option>内蒙古自治区</option>
         <option>辽宁省</option>
         <option>吉林省</option>
         <option>黑龙江省</option>
         <option>上海市</option>
         <option>江苏省</option>
         <option>浙江省</option>
         <option>安徽省</option>
         <option>福建省</option>
         <option>江西省</option>
         <option>山东省</option>
         <option>河南省</option>
         <option>湖北省</option>
         <option>湖南省</option>
         <option>广东省</option>
         <option>广西壮族自治区</option>
         <option>海南省</option>
         <option>重庆市</option>
         <option>四川省</option>
         <option>贵州省</option>
         <option>云南省</option>
         <option>西藏自治区</option>
         <option>陕西省</option>
         <option>甘肃市</option>
         <option>青海省</option>
         <option>宁夏回族自治区</option>
         <option>新疆维吾尔自治区</option> -->
      </select>
     <input type="date" style="width: 180px;height: 40px;border-radius: 1px;text-indent: 10px;border: 1px solid#4b8df8;color: #000;font-size: 20px;" name="Spetime" value="2022-01-01" min="2021-01-01" max="2022-12-31"/>
    <input class="btn btn-primary btn-sm" type="submit" style="margin-left:20px" value="查询"/>
	</form>
	<!-- <form method="post" action="edit-water">
	<input class="btn btn-primary btn-sm" type="submit" style="margin-left:20px" value="编辑"/>
	</form> -->
    <div class="div_any_child">
          <div class="table_p" style="height: 96%;margin-top: 20px;">	
              <table id="idData">
                  <thead><tr>
                      <th>省份</th>
                      <th>流域</th>
                      <th>断面名称</th>
                      <th>监测时间</th>
                      <th>水质类别</th>
                      <th>水温(℃)</th>
                      <th>pH(无量纲)</th>
                      <th>溶解氧(mg/L)</th>
                      <th>电导率(μS/cm)</th>
                      <th>浊度(NTU)</th>
                      <th>高锰酸盐指数(mg/L)</th>
                      <th>氨氮(mg/L)</th>
                      <th>总磷(mg/L)</th>
                      <th>总氮(mg/L)</th>
                      <th>叶绿素α(mg/L)</th>
                      <th>藻密度(cells/L)</th>
                      <th>站点情况</th>
                      <!-- <th>操作</th> -->
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="water" items="${waterList}" >	
                  <tr>
                  <td>${water.province}</td><td>${water.valley}</td><td>${water.staname}</td>
                  <td>${water.sta_time}</td><td>${water.water_l}</td><td>${water.water_temp}</td>
                  <td>${water.sta_ph_v}</td><td>${water.sta_do_v}</td><td>${water.conductivity}</td>
                  <td>${water.turbidity}</td><td>${water.sta_pp_v}</td><td>${water.sta_an_v}</td>
                  <td>${water.sta_tp_v}</td><td>${water.sta_tn_v}</td><td>${water.chlorophyll}</td>
                  <td>${water.algal_density}</td><td>${water.status_label}</td>
                  </tr>
                  </c:forEach>
                  </tbody>
              </table>
          </div>
          <div class="box">
              <div id="pagination" class="page fl"></div>
          </div>
        </div>


    </div>
</div>
</body>
</html>

<script>
//顶部时间
    function getTime(){
        var myDate = new Date();
        var myYear = myDate.getFullYear(); //è·åå®æ´çå¹´ä»½(4ä½,1970-????)
        var myMonth = myDate.getMonth()+1; //è·åå½åæä»½(0-11,0ä»£è¡¨1æ)
        var myToday = myDate.getDate(); //è·åå½åæ¥(1-31)
        var myDay = myDate.getDay(); //è·åå½åææX(0-6,0ä»£è¡¨ææå¤©)
        var myHour = myDate.getHours(); //è·åå½åå°æ¶æ°(0-23)
        var myMinute = myDate.getMinutes(); //è·åå½ååéæ°(0-59)
        var mySecond = myDate.getSeconds(); //è·åå½åç§æ°(0-59)
        var week = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];
        var nowTime;

        nowTime = myYear+'年'+fillZero(myMonth)+'月'+fillZero(myToday)+'日'+'&nbsp;&nbsp;'+fillZero(myHour)+':'+fillZero(myMinute)+':'+fillZero(mySecond)+'&nbsp;&nbsp;'+week[myDay]+'&nbsp;&nbsp;';
        //console.log(nowTime);
        $('#time').html(nowTime);
    };
    function fillZero(str){
        var realNum;
        if(str<10){
            realNum	= '0'+str;
        }else{
            realNum	= str;
        }
        return realNum;
    }
  //大屏
    setInterval(getTime,1000);
</script>