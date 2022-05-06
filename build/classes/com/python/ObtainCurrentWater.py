import datetime
import pandas as pd
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.options import Options     #建议使用谷歌浏览器
import time
from sqlalchemy import create_engine
#连接数据库
conn = create_engine('mysql+pymysql://root:@localhost:3306/waterquality?charset=utf8', encoding='utf8')
if __name__ == '__main__':
    chrome_options = Options()
    chrome_options.add_argument('--headless')
    #使不使用headless版本，也许你想感受一下浏览器自动打开，自动点击的快感，也不一定
    browser = webdriver.Chrome()
    #chromedriver下载下来之后复制到chrome.exe同文件夹下即可
    print("打开网页中。。。")
    browser.get("http://106.37.208.243:8068/GJZ/Business/Publish/Main.html")
    print("网页响应中。。。")
    wait = WebDriverWait(browser,20)#毕竟代码运行的速度和浏览器打开的速度不再一个量级，一个闪电侠，一个奥特曼
    wait.until(EC.frame_to_be_available_and_switch_to_it((By.ID,"MF")))#这一步很关键
    browser.find_element_by_id('ddm_Area').click()#模拟点击“区域”
    browser.find_element_by_xpath("/html/body/div[1]/div[1]/div/ul/li[1]").click()#模拟点击“所有区域”
    wait.until(EC.presence_of_element_located((By.CLASS_NAME,"panel")))#定位到数据
    print("获取网页数据中。。。")
    time.sleep(10)
    soup = BeautifulSoup(browser.page_source,"html.parser")
    browser.close()
    data_head = soup.select(".panel-heading")[0]
    grid_data = soup.select(".panel")[0]
    data_colhead = data_head.findAll("td")
    data_rows = grid_data.findAll("tr")
    water_df = pd.DataFrame(columns=[c.text for c in data_colhead])
    print("提取网页数据中。。。")
    for i,data_row in enumerate(data_rows):
        if i < 1:
            pass
        else:
            i = i - 1
            water_loc = water_df.iloc[:, 0].values
            water_data = water_df.iloc[:, 1].values
            row_dat = [r.text for r in data_row]
            water_df.loc[i] = row_dat
    #系统时间
    #data_str = datetime.datetime.now().strftime('%Y_%m_%d_%H')
    #print("/%s_data.csv" % (data_str))
    #可修改保存路径
    water_df.to_sql("current_data", con=conn, if_exists='replace', index=None)
    print("数据提取完成！！")
