import re
import sys
sys.path.append("../")
from sys import argv
import pandas as pd
import pystan
from fbprophet import Prophet
#import matplotlib.pyplot as plt


def is_number(s):
    try:
        float(s)
        return True
    except ValueError:
        pass

    try:
        import unicodedata
        unicodedata.numeric(s)
        return True
    except (TypeError, ValueError):
        pass

    return False

#pdata = pd.read_csv("D:\\Program Files\\Tomcat 9.0_Tomcat9.0\\wtpwebapps\\water-quality\\WEB-INF\\classes\\com\\data\\all_data.csv", low_memory=False, usecols=['staname', 'sta_time', argv[3]])
pdata = pd.read_csv(argv[1], low_memory=False, usecols=['staname', 'sta_time', argv[3]])
pdata = pdata[pdata["staname"].str.contains(argv[2])]
pdata = pdata[['sta_time', argv[3]]]
pdata = pdata[pdata[argv[3]].apply(
    lambda x: True if re.match(r"([1-9][0-9]*|0)\.[0-9]+", str(x)) and re.match(
        r"([1-9][0-9]*|0)\.[0-9]+", str(x)).group(0) == str(x) else False)].copy()
pdata.rename(columns={'sta_time':'ds',argv[3]:'y'},inplace=True)
pdata['ds'] = pd.to_datetime(pdata['ds'],format='%Y/%m/%d')

#pdata['ds']=pdata[0]
#pdata['y'] =pdata[2]

#创建一个模型
m = Prophet(changepoint_prior_scale=0.5, yearly_seasonality=True, seasonality_prior_scale=11, daily_seasonality=True)
m.add_seasonality(name='monthly', period=30.5, fourier_order=5)
#m = Prophet(growth='logistic')
#m = Prophet(growth='logistic',changepoint_prior_scale=2,changepoints=['2021/1/24','2021/2/14','2021/2/20'])
#pdata['cap']=69000
m.fit(pdata)#传入模型需要预测的数据
    #创建一个包含预测时间的dataframe，这里period时预测时，单位默认是天
future = m.make_future_dataframe(periods=int(argv[4]))
#future['cap']=69000
    #进行预测，返回预测值及其其他相关值，格式是dataframe。
forecast = m.predict(future)
    #绘画出图形
#m.plot(forecast)
#plt.show()#显示图片
test = forecast[['ds','yhat']].tail(int(argv[5]))#输出预测的20天的值
#print(forecast[-20:])
print(test)