<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>水质采集概况</title>
<link href="css/common.css" rel="stylesheet">
<script src="js/Plugin/jquery-3.3.1.min.js"></script>
<script src="js/Plugin/echarts.min.js"></script>
<script src="js/Plugin/bmap.min.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=LelFSt6BfykGf8m3PB5zr8LaXAG2cMg6"></script>
<script src="js/common.js"></script>
<!-- <script src="js/survey.js"></script> -->
<script src="js/Plugin/laydate.js"></script>
<script type="text/javascript" src="js/china.js"></script>
<script>
$(function(){
	map();
    //大屏
	function leida1(){
	var myChart = echarts.init(document.getElementById('map'));
	myChart.setOption(option);
	window.addEventListener("resize",function(){
        myChart.resize();
    });
	}
})


function map(){
		var myChart = echarts.init(document.getElementById('map'));
		let iconRQ = "image://data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABUAAAAUCAYAAABiS3YzAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTM4IDc5LjE1OTgyNCwgMjAxNi8wOS8xNC0wMTowOTowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKFdpbmRvd3MpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjNCRkRBMEJBQzgwRjExRUFBNUI0RTMyMThEN0UxMzFEIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjNCRkRBMEJCQzgwRjExRUFBNUI0RTMyMThEN0UxMzFEIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6M0JGREEwQjhDODBGMTFFQUE1QjRFMzIxOEQ3RTEzMUQiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6M0JGREEwQjlDODBGMTFFQUE1QjRFMzIxOEQ3RTEzMUQiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz5mT42iAAABQ0lEQVR42mL8LabOQCQIBOL1xChkItJAkLp+IBajpqFWQCwPxJ7UNDQCSgdQy1BmIA6Bsl2AmJMahjoAsTiUzQPETtQwNAKN709IAwvUayZQ/hcg/o0k/x6Ig9D0+ABxKJT9HYh/oMm/BBm6GYitgTgfiBmJcLkkEK/CIXcGiGNB3v8JxIVQF31gIA/8AeIWaNK7gRymG4BYH4hPkGjgXSC2A+JaWNChR9QjqIJeIP5PhIGzgdgAiI8Tin2QbSVAvIOAgROBOA0auUQlKV4gtidgqBGp6RSUFrmIKA/ESDEUPcGfBOIUIH6Lln29iTVUCIjdkJJKExDbAPFcqJdPEMpd2AwF5TBWaFKxBeJ6qOHIqaMbmjrcsBUw2AwNh7rKAEeaBaWOMiD2BeJvQOxOyFBuaFJJwZZU0MBWaHCIo0sABBgAetA4Jx5t/ToAAAAASUVORK5CYII="
		var dataMap =[];
	    $.ajax({
	        type : "post",
	        async : false, // 异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
	        url : "mapSite.do", // 请求发送到TestServlet处
	        data : {},
	        dataType : "json", // 返回数据形式为json
	        success : function(result) {
	            //接收后端的数据
	            if (result) {
	            	for(var i=0;i<result.length;i++){
	            		var json={name:result[i].province,value:result[i].stanum};
	                    //将后台数据以 {name:省份名称,value:数据}的形式存与集合中
	                    dataMap.push(json);
	                }
	                }
	            },
	        error : function(errorMsg) {
				alert("加载数据失败");
				}
	        })
		option = {
		    /*backgroundColor: '#000f1e',*/
		    geo: {
		        map: 'china',
		        aspectScale: 0.85,
		        layoutCenter: ["55%", "50%"], //地图位置
		        layoutSize: '100%',
		        itemStyle: {
		            normal: {
		                shadowColor: '#276fce',
		                shadowOffsetX: 0,
		                shadowOffsetY: 15,
		                opacity: 0.5,
		            },
		            emphasis: {
		                areaColor: '#276fce',
		            }
		        },
		        regions: [{
		            name: '南海诸岛',
		            itemStyle: {
		                areaColor: 'rgba(0, 10, 52, 1)',

		                borderColor: 'rgba(0, 10, 52, 1)',
		                normal: {
		                    opacity: 0,
		                    label: {
		                        show: false,
		                        color: "#009cc9",
		                    }
		                },
		            },
		            label: {
		                show: false,
		                color: '#FFFFFF',
		                fontSize: 12,
		            },


		        }],

		    },
		    tooltip:{//鼠标移上去时显示悬浮框
	            backgroundColor:'transparent',//悬浮框最外层设置了默认padding;5,背景色灰色，如果要自定义，设置padding:0;或者背景色透明。
	            padding:0,
	            //formatter: function (params) {
	                //var info = '<p style="font-size:18px">' + '你好' + '</p><p style="font-size:14px">这里可以写一些，想展示在页面上的别的信息</p>'
	                //return info;
	            //},
	            trigger:'item'//设置该属性之后，会与series中data数据对应，如上图。注意data中对象的键名为name。如果单纯的展示数据可用此属性，不与formatter同用。
	            // formatter:(params)=>{
	            //let obj = {img:require('../../assets/images/weather/湿度.png')}
	            //     return `<div class="test">${params.name}<img src=${obj.img}><div>`//模板字符串渲染悬浮框，注意图片的引入方式（require）,否则无法显示。
	            // },
	        },
	        visualMap: {  
                show : true,  
                x: 'left',  
                y: 'center', 
                textStyle : {
                    color : '#ffffff',
                },
                splitList: [   
                    {start: 125, end:150},{start: 100, end: 125},  
                    {start: 75, end: 100},{start: 50, end: 75},  
                    {start: 25, end: 50},{start: 0, end: 25},  
                ],  
                color: ['#5475f5', '#9feaa5', '#85daef','#74e2ca', '#e6ac53', '#9fb5ea']  
            },
		    series: [
		        // 常规地图
		        {
		        	name: '站点数量',
		            type: 'map',
		            mapType: 'china',
		            aspectScale: 0.85,
		            layoutCenter: ["55%", "50%"], //地图位置
		            layoutSize: '100%',
		            zoom: 1, //当前视角的缩放比例
		            // roam: true, //是否开启平游或缩放
		            scaleLimit: { //滚轮缩放的极限控制
		                min: 1,
		                max: 2
		            },
		            itemStyle: {
		                normal: {
		                    areaColor: '#0c274b',
		                    borderColor: '#1cccff',
		                    borderWidth: 1.5
		                },
		                emphasis: {
		                    areaColor: '#02102b',
		                    label: {
		                        color: "#fff"
		                    }
		                }
		            },
		            data: dataMap
		        },
		        { //首都星图标
		            name: '首都',
		            type: 'scatter',
		            coordinateSystem: 'geo',
		            data: [{
		                name: '首都',
		                value: [116.24, 41.55, 100],

		            }, ],
		            symbol: iconRQ,
		            symbolSize: 20,
		            label: {
		                normal: {
		                    show: false,

		                },
		                emphasis: {
		                    show: false
		                }
		            },
		        },
		    ]
		};

		myChart.on('click', function(params) {
		    console.log(params);
		});
		myChart.setOption(option);
		window.addEventListener("resize",function(){
	        myChart.resize();
	    });

	}
</script>
</head>
<body>
<!--顶部-->
<div class="header left">
    <div class="left nav">
        <ul>
            <li class="nav_active"><i class="nav_1"></i><a href="collected-water">采集概况</a> </li>
            <li><i class="nav_2"></i><a href="statistical-water">指标分析</a> </li>
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
<div class="con left">
    <!--数据总概-->
    <div class="con_div">
        <div class="con_div_text left">
            <div class="con_div_text01 left">
                <img src="images/info_1.png" class="left text01_img"/>
                <div class="left text01_div">
                    <p>总采集数据量(条)</p>
                    <p>${total_data}</p>
                </div>
            </div>
            <div class="con_div_text01 right">
                <img src="images/info_2.png" class="left text01_img"/>
                <div class="left text01_div">
                    <p>当前采集数据量(条)</p>
                    <p>${current_data}</p>
                </div>
            </div>
        </div>
        <div class="con_div_text left">
            <div class="con_div_text01 left">
                <img src="images/info_3.png" class="left text01_img"/>
                <div class="left text01_div">
                    <p>总站点数(个)</p>
                    <p class="sky">2000</p>
                </div>
            </div>
            <div class="con_div_text01 right">
                <img src="images/info_4.png" class="left text01_img"/>
                <div class="left text01_div">
                    <p>当前正常站点数(个)</p>
                    <p class="sky">${normal_site}</p>
                </div>
            </div>
        </div>
        <div class="con_div_text left">

            <div class="con_div_text01 left">
                <img src="images/nav_1.png" class="left text01_img"/>
                <div class="left text01_div">
                    <p>当前优质水质占比(%)</p>
                    <p class="pink">${high_water}</p>
                </div>
            </div>
            <div class="con_div_text01 right">
                <img src="images/nav_2.png" class="left text01_img"/>
                <div class="left text01_div">
                    <p>当前劣质水质占比(%)</p>
                    <p class="pink">${poor_water}</p>
                </div>
            </div>
        </div>
    </div>
    <!--统计分析图-->
    <div class="div_any">
        <div class="left div_any01" style="width:36%;">
            <div class="div_any_child">
                <div class="div_any_title"><img src="images/title_1.png">当前各流域水质类别概况</div>
                <p id="pieChart1" class="p_chart"></p>
            </div>
            <div class="div_any_child">
                <div class="div_any_title"><img src="images/title_2.png">当前各省市水质污染TOP7</div>
                <!-- <p id="histogramChart" class="p_chart"></p> -->
                <p id="pieChart2" class="p_chart"></p>
            </div>
        </div>
        <div class="div_any02 left" style="width:60%;">
            <div class="div_any_child div_height">
                <div class="div_any_title any_title_width"><img src="images/title_0.png">全国地图站点数量分布 </div>
                <!-- <div id="mapChart" style="width:97.5%;height:95%;display: inline-block;padding-left: 1.25%;padding-top:2.2%"></div> -->
                <div id="map" style="width:97.5%;height:95%;display: inline-block;padding-left: 1.25%;padding-top:2.2%"></div>
            </div>
        </div>
        <!-- <div class="right div_any01">
            <div class="div_any_child">
                <div class="div_any_title"><img src="../images/title_3.png">数据采集条数(当日)</div>
                <p id="lineChart" class="p_chart"></p>
            </div>
            <div class="div_any_child">
                <div class="div_any_title"><img src="../images/title_4.png">就诊人数(当日)</div>
                <p id="lineChart2" class="p_chart"></p>
            </div>
        </div> -->
    </div>

</div>
<script>
var symptomName = last_month_day();

$(function(){

  init();
  lay('.demo-input').each(function(){
     laydate.render({
        type: 'month',
         elem: this,
         trigger: 'click',
         theme: '#95d7fb',
         calendar: true,
         showBottom: true,
         done: function () {
            console.log( $("#startDate").val())

         }
     })
 });

})
function init(){

	var dataWater =[];
    $.ajax({
        type : "post",
        async : false, // 异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url : "waterCategory.do", // 请求发送到TestServlet处
        data : {},
        dataType : "json", // 返回数据形式为json
        success : function(result) {
            //接收后端的数据
            if (result) {
            	for(var i=0;i<result.length;i++){
            		//alert(result[i].type+result[i].num);
            		var json={name:result[i].type,value:result[i].num};
                    //将后台数据以 {name:省份名称,value:数据}的形式存与集合中
                    dataWater.push(json);
                }
                }
            },
        error : function(errorMsg) {
			alert("加载数据失败");
			}
        })
	//饼状图
	  var pieChart1 = echarts.init(document.getElementById('pieChart1'));
	  pieChart1.setOption({

	    color:["#87cefa","#ccffff","#00ccff","#00ff00","#ffff00","#ff9b00","#ff0000"],

	    legend: {
	        y : '260',
	        x : 'center',
	        textStyle : {
	            color : '#ffffff',

	        },
	         data : ['未知','Ⅰ级','Ⅱ级','Ⅲ级','Ⅳ级','Ⅴ级','劣Ⅴ级'],
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a}<br/>{b}<br/>{c} ({d}%)"
	    },
	    calculable : false,
	    series : [
	        {
	            name:'各级别水质数量',
	            type:'pie',
	            radius : ['40%', '70%'],
	            center : ['50%', '45%'],
	            itemStyle : {
	                normal : {
	                    label : {
	                        show : false
	                    },
	                    labelLine : {
	                        show : false
	                    }
	                },
	                emphasis : {
	                    label : {
	                        show : true,
	                        position : 'center',
	                        textStyle : {
	                            fontSize : '20',
	                            fontWeight : 'bold'
	                        }
	                    }
	                }
	            },
	            //data:[
	            //	{value:335, name:'未知'},
	            //    {value:310, name:'Ⅰ级'},
	            //    {value:234, name:'Ⅱ级'},
	             //   {value:235, name:'Ⅲ级'},
	           //     {value:410, name:'Ⅳ级'},
	            //    {value:334, name:'Ⅴ级'},
	            //    {value:235, name:'劣Ⅴ级'}
	               
	            //]
	            data:dataWater
	        }
	    ]
	    })
	    var dataPollution =[];
	  $.ajax({
        type : "post",
        async : false, // 异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url : "pollutionProportion.do", // 请求发送到TestServlet处
        data : {},
        dataType : "json", // 返回数据形式为json
        success : function(result) {
            //接收后端的数据
            if (result) {
            	for(var i=0;i<result.length;i++){
            		//alert(result[i].type+result[i].num);
            		var json={name:result[i].province,value:result[i].polpro};
                    //将后台数据以 {name:省份名称,value:数据}的形式存与集合中
                    dataPollution.push(json);
                }
                }
            },
        error : function(errorMsg) {
			alert("加载数据失败");
			}
        })
	//水质污染TOP5
	    var pieChart2 = echarts.init(document.getElementById('pieChart2'));
	    pieChart2.setOption({
	      //color:["#ff0000","#ff7f50","#32cd32","#87cefa","#da70d6",],
	      color:["#FF0000","#FF7D00","#FFFF00","#00FF00","#00FFFF","#0000FF","#FF00FF"],
	      tooltip : {
	       trigger: 'item',
	       formatter: "{a}<br/>{b}<br/>{c}%"
	      },
	      calculable : true,
	      series : [
	          {
	              name:'劣质水质占比',
	              type:'pie',
	              radius : [30, 110],
	              center : ['50%', '50%'],
	              roseType : 'area',
	              x: '50%',
	              max: 40,
	              sort : 'ascending',
	              data:dataPollution
	          }
	      ]
	    });


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