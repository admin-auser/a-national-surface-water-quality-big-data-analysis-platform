var symptomName = last_year_month();


$(function(){


  init();

})

 function init(){
	alert("你好");
	alert(s);
   alert("<%=defaultMean.get(0)[1]%>");
   var myColor = ['#1089E7', '#F57474', '#56D0E3', '#F8B448', '#8B78F6'];

   //主要传染病
   var histogramChart1 = echarts.init(document.getElementById('histogramChart1'));
   histogramChart1.setOption({

     color:['#5bc0de'],
     grid:{
         left: '5%',
         right: '5%',
         bottom: '5%',
         containLabel: true
     },
     tooltip : {
        trigger: 'item',
        formatter: "{a}<br/>{b}<br/>{c}人"
    },
     calculable : true,
     xAxis : [
         {
             type : 'category',
             data : ['a','b','c','d','e','f','g','h','i'],
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
             name:'主要传染病',
             type:'bar',
             barWidth : 20,
             data:[2210,1085,926,669,634,452,412,312,156],
         },
     ]
   })

   //传染病发病趋势
   var lineChart1 = echarts.init(document.getElementById('lineChart1'));
   lineChart1.setOption({
     title: {
        text: '趋势',
        textStyle:{
           fontSize:16,
           color:'#ff7f50'
       },
    },
     color:["#ff7f50"],
     grid:{
         left: '15%',
         right: '5%',
         bottom: '15%',

     },
     tooltip : {
          trigger: 'item',
          formatter: "{a}<br/>{b}<br/>{c}人"
      },

     calculable : true,
         yAxis: [
             {
                 type: 'value',
                 axisLine:{
                     lineStyle:{
                         color: '#ff7f50'
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
         xAxis: [
             {
                 type: 'category',
                 data : symptomName,
                 boundaryGap : false,
                 axisLine:{
                     lineStyle:{
                         color: '#ff7f50'
                     },
                 },
                 splitLine: {
                     "show": false
                 },
                 axisLabel: {
                   // interval:0,
                   // rotate:40,
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
             name:'人数',
             type:'line',
             smooth:true,
             itemStyle: {normal: {areaStyle: {type: 'default'}}},
             data:[120, 132, 101, 134, 90, 230, 210,120, 132, 101, 134, 90]
         },
     ]

   })

 }




		
		
		


		









