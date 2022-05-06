<%@ page language="java" import="com.beans.ProAndSta,java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>水质指标分析</title>
<link href="css/common.css" rel="stylesheet">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-table.css" rel="stylesheet">
<script src="js/Plugin/jquery-3.3.1.min.js"></script>
<script src="js/Plugin/echarts.min.js"></script>
<script src="js/common.js"></script>
<!-- <script src="js/statistics.js"></script> -->
<script language="JavaScript" type="text/javascript">
   //定义了断面的二维数组，里面的顺序跟断面的顺序是相同的。通过selectedIndex获得省份的下标值来得到相应的断面数组
   var staArr = [];
   
   <c:forEach var="provincename" items="${provinceList}" >	
   staArr["${provincename}"] = 
	   [
		   <c:forEach var="ps" items="${psList}">
		   <c:if test="${ps.province == provincename}">
		   		{txt:"${ps.staname}", val:"${ps.staname}"},
		   </c:if>
		   </c:forEach>
	   ];
	</c:forEach>
	
 
	function setSta(province) {
        console.log(province);
        //setSelectOption('city', cityArr[province], '-请选择-');
        var sta = document.getElementById('Staname');
        var sta_len = sta.options.length;
        console.log(sta_len);
        for(var i = 0; i < sta_len; i++) {
            if(sta_len > 0) {
            	sta.options.remove(0);
            }
        }
 
        if(province == "0") {
            var opt = new Option('请选择断面名称', 0);
            sta.options.add(opt);
        }
        else {
            var province_len = staArr[province].length;
            for(i = 0;i< province_len; i++) {
                var opt = new Option(staArr[province][i].txt, staArr[province][i].val);
                sta.options.add(opt);
            }
        }
    }
 </script>
</head>
<body>
<!--顶部-->
<div class="header left">
    <div class="left nav text_left">
        <ul>
            <li><i class="nav_1"></i><a href="collected-water">采集概况</a> </li>
            <li class="nav_active"><i class="nav_2"></i><a href="statistical-water">指标分析</a></li>
            <li><i class="nav_3"></i><a href="predicted-water">趋势预测</a> </li>
            <li><i class="nav_4"></i><a href="search-water">信息浏览</a> </li>
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
	<form method="post" action="statistical-water" name="PSform">
    <!-- <select name="Staname" style="width: 210px;height: 40px;border-radius: 3px;text-indent: 1em;border: 1px solid#4b8df8;color: #000;font-size: 20px;">
         <c:forEach var="staname" items="${staList}" >	
                  <option>${staname}</option>
           </c:forEach>
      </select> -->
      <select name="Proname" id="Proname" style="width: 210px;height: 40px;border-radius: 3px;text-indent: 1em;border: 1px solid#4b8df8;color: #000;font-size: 20px;" onchange="
      	if(this.value != '')
            setSta(this.options[this.selectedIndex].value);">
           <!-- <select name="Proname" style="width: 210px;height: 40px;border-radius: 3px;text-indent: 1em;border: 1px solid#4b8df8;color: #000;font-size: 20px;" onChange="getSta()"> -->
         <option value="0">请选择省份</option>
         <c:forEach var="provincename" items="${provinceList}" >	
               <option>${provincename}</option>
           </c:forEach>
         <!-- <option>北京市</option><option>天津市</option><option>河北省</option><option>山西省</option><option>内蒙古自治区</option>
         <option>辽宁省</option><option>吉林省</option><option>黑龙江省</option><option>上海市</option><option>江苏省</option>
         <option>浙江省</option><option>安徽省</option><option>福建省</option><option>江西省</option><option>山东省</option>
         <option>河南省</option><option>湖北省</option><option>湖南省</option><option>广东省</option><option>广西壮族自治区</option>
         <option>海南省</option><option>重庆市</option><option>四川省</option><option>贵州省</option><option>云南省</option>
         <option>西藏自治区</option><option>陕西省</option><option>甘肃市</option><option>青海省</option><option>宁夏回族自治区</option>
         <option>新疆维吾尔自治区</option> -->
      </select>
      <select name="Staname" id="Staname" style="width: 210px;height: 40px;border-radius: 3px;text-indent: 1em;border: 1px solid#4b8df8;color: #000;font-size: 20px;">
      <option value="0">请选择断面名称</option>
      </select>
      <select name="Parametername" style="width: 170px;height: 40px;border-radius: 3px;text-indent: 1em;border: 1px solid#4b8df8;color: #000;font-size: 20px;">
         <option>水温(℃)</option>
         <option>pH(无量纲)</option>
         <option>溶解氧(mg/L)</option>
         <option>高锰酸盐指数(mg/L)</option>
         <option>氨氮(mg/L)</option>
         <option>总磷(mg/L)</option>
      </select>
      <select name="Meanvalue" style="width: 120px;height: 40px;border-radius: 3px;text-indent: 1em;border: 1px solid#4b8df8;color: #000;font-size: 20px;">
         <option>月均值</option>
         <option>年均值</option>
         <option>每日值</option>
      </select>
      <span style="color:#fff;font-size: 20px;margin-left:40px;"><strong>开始时间</strong></span>
     <input name="Statime" id="Statime" type="date" style="width: 180px;height: 40px;border-radius: 1px;text-indent: 10px;border: 1px solid#4b8df8;color: #000;font-size: 20px;" name="Statime" value="2022-01-01" min="2021-01-01" max="2022-12-31"/>
    <span style="color:#fff;font-size: 20px;margin-left:20px;"><strong>结束时间</strong></span>
     <input name="Endtime" id="Endtime" type="date" style="width: 180px;height: 40px;border-radius: 1px;text-indent: 10px;border: 1px solid#4b8df8;color: #000;font-size: 20px;" name="Endtime" value="2022-04-30" min="2021-01-01" max="2022-12-31"/>
    <input class="btn btn-primary btn-sm" type="submit" style="margin-left:20px" value="查询"/>
	</form>
    <!--内容部分-->
<div class="con left" style="width:60%;">
    <!--统计分析图-->
    <div class="div_any">
        <div class="left div_any01" style="width:100%;">
            <div class="div_any_child" style="width:98%;position:relative;height: 420px;">
                <div class="div_any_title"><img src="images/title_15.png">历史监测数据指标统计</div>
                <!-- <div id="histogramChart"  style="width: 45%;display: inline-block;height: 400px;margin-top: 15px;"></div> -->
                <div id="histogramChart"  style="width: 97%;display: inline-block;height: 400px;margin-top: 15px;"></div>
                <!-- <div id="lineChart"  style="width: 52%;height: 66%;display: inline-block;"></div> -->
                <div style="width: 50%;height: 20%;display: inline-block;color:red;position: absolute;right: 2%;top: 8%;">
                  <div style="display: inline-block;width:33%;text-align:center;">
                    <div style="color:#fff;font-size: 18px;line-height: 32px;">最大值</div>
                    <div style="color:#87cefa;font-size: 30px;line-height: 42px;">${maxaver}</div>
                  </div><div style="display: inline-block;width:33%;text-align:center;">
                    <div style="color:#fff;font-size: 18px;line-height: 32px;">最小值</div>
                    <div style="color:red;font-size: 30px;line-height: 42px;">${minaver}</div>
                  </div><div style="display: inline-block;width:33%;text-align:center;">
                    <div style="color:#fff;font-size: 18px;line-height: 32px;">差值</div>
                    <div style="color:#ff7f50;font-size: 30px;line-height: 42px;">${difference}</div>
                  </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="con right" style="width:40%;">
  <div class="div_any">
      <div class="left div_table_box" style="width: 100%;">
              <div class="div_any_child" style="height: 420px;">
                  <div class="div_any_title"><img src="images/title_10.png">水质评价标准</div>
                  <div class="table_p">
                      <table>
                          <thead><tr>
                              <th>序号</th>
                              <th>项目</th>
                              <th>Ⅰ类</th>
                              <th>Ⅱ类</th>
                              <th>Ⅲ类</th>
                              <th>Ⅳ类</th>
                              <th>Ⅴ类</th>
                          </tr>
                          </thead>
                          <tbody>
                          <tr><td>1</td><td>水温(℃)</td><td colspan="5">人为造成的环境水温变化应限制在:周平均最大温升≤1;周平均最大温降&lt;2</td></tr>
                          <tr><td>2</td><td>pH(无量纲)</td><td colspan="5">6~9</td></tr>
                          <tr><td>3</td><td>溶解氧(mg/L)≥</td><td>饱和率90%(或7.5)</td><td>6</td><td>5</td><td>3</td><td>2</td></tr>
                          <tr><td>4</td><td>高锰酸盐指数(mg/L)≤</td><td>2</td><td>4</td><td>6</td><td>10</td><td>15</td></tr>
                          <tr><td>5</td><td>氨氮(mg/L)≤</td><td>0.15</td><td>0.5</td><td>1.0</td><td>1.5</td><td>2.0</td></tr>
                          <tr><td>6</td><td>总磷(mg/L)≤</td><td>0.02(湖、库0.01)</td><td>0.1(湖、库0.025)</td><td>0.2(湖、库0.05)</td><td>0.3(湖、库0.1)</td><td>0.4(湖、库0.2)</td></tr>
                          </tbody>
                      </table>
                  </div>
              </div>
          </div>
  </div>
</div>
    <!-- <div class="left div_any01" style="width:100%;">
            <div class="div_any_child" style="width:98%;position:relative;height: 420px;">
                <div class="div_any_title"><img src="images/title_15.png">历史监测数据指标统计</div>
                <div id="histogramChart"  style="width: 45%;display: inline-block;height: 400px;margin-top: 15px;"></div>
                <div id="lineChart"  style="width: 52%;height: 66%;display: inline-block;"></div>
                <div style="width: 50%;height: 20%;display: inline-block;color:red;position: absolute;right: 2%;top: 8%;">
                  <div style="display: inline-block;width:33%;text-align:center;">
                    <div style="color:#fff;font-size: 18px;line-height: 32px;">最高均值</div>
                    <div style="color:#87cefa;font-size: 30px;line-height: 42px;">${maxaver}</div>
                  </div><div style="display: inline-block;width:33%;text-align:center;">
                    <div style="color:#fff;font-size: 18px;line-height: 32px;">最低均值</div>
                    <div style="color:red;font-size: 30px;line-height: 42px;">${minaver}</div>
                  </div><div style="display: inline-block;width:33%;text-align:center;">
                    <div style="color:#fff;font-size: 18px;line-height: 32px;">差值</div>
                    <div style="color:#ff7f50;font-size: 30px;line-height: 42px;">${difference}</div>
                  </div>
                </div>
            </div>
        </div> -->


    </div>
<script>

$(function(){


  init();

})


 function init(){
   var myColor = ['#1089E7', '#F57474', '#56D0E3', '#F8B448', '#8B78F6'];
   //柱状图
   var histogramChart1 = echarts.init(document.getElementById('histogramChart'));
   
   var arr = new Array();
   var index = 0;
   <c:forEach var="oneMean" items="${defaultMean}">
   arr[index++] = ${oneMean.average};
</c:forEach>
   <%
   String s=(String)session.getAttribute("staname");
   String p=(String)session.getAttribute("parameter");
   %>
   //var parameter = ${parameter};
   
   histogramChart1.setOption({
	   title: {
	        text: "<%=s%>"+"<%=p%>"+"值",
	        //text: staname,
	        textStyle:{
	           fontSize:16,
	           color:'#5bc0de'
	       },
	       left:20,	//根据相应位置自动对齐
	    },
     color:['#5bc0de'],
     grid:{
         left: '5%',
         right: '5%',
         bottom: '5%',
         containLabel: true
     },
     tooltip : {
        trigger: 'item',
        formatter: "{a}<br/>{b}<br/>{c}"
    },
     calculable : true,
     xAxis : [
         {
             type : 'category',
             //data : ['a','b','c','d','e','f','g','h','i'],
             //data : arr1,
             data : [
                      <c:forEach var="oneMean" items="${defaultMean}">
                      ["${oneMean.time}"],
                      </c:forEach>
                  ],
             axisLine:{
                  lineStyle:{
                      color: '#5bc0de'
                  },
              },
              axisLabel : {
                interval:0,
                rotate:40,
                  textStyle: {
                      color: '#fff'
                  }
              }
         }
     ],
     yAxis : [
         {
             type : 'value',
             axisLine:{
                 lineStyle:{
                     color: '#5bc0de'
                 },
             },
             splitLine: {
                 "show": false
             },
             axisLabel: {
                 textStyle: {
                     color: '#fff'
                 },
                 formatter: function (value) {
                     return value + ""
                 },
             },
         }
     ],
     series : [
         {
             //name:parameter+'平均值',
             name:"<%=p%>"+'值',
             type:'bar',
             barWidth : 20,
             //data:[2210,1085,926,669,634,452,412,312,156],
             data : arr,
             
         },
     ]
   })


 }
</script>
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