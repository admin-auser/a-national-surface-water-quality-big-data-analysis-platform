import pymysql


def readAndWrite():
    # 四个参数分别为数据库地址，用户名，密码，数据库名称
    read = pymysql.connect(host='localhost', user='root', passwd='', db='waterquality', charset='utf8')
    Rcursor = read.cursor()
    readSql = "SELECT * FROM current_data"
    Rcursor.execute(readSql)
    readResult = Rcursor.fetchall()
    list = []
    num = 0
    writeSql = "INSERT INTO all_data(province, valley, staname, sta_time, water_l, water_temp, sta_ph_v, sta_do_v, conductivity, turbidity, sta_pp_v, sta_an_v, sta_tp_v, sta_tn_v, chlorophyll, algal_density, status_label) VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    write = pymysql.connect(host='localhost', user='root', passwd='', db='waterquality', charset='utf8')
    Wcursor = write.cursor()
    for value in readResult:
        if "2022" in value[3]:
            time = str(value[3])
        elif len(str(value[3])) < 2:
            time = str(value[3])
        else:
            time = "2022-" + str(value[3])
            time = time.replace("/", "-")
            time = time.replace("12:00", "12:00:00")
            time = time.replace("08:00", "12:00:00")
            time = time.replace("04:00", "12:00:00")
        data = (str(value[0]), str(value[1]), str(value[2]), time, str(value[4]), str(value[5]),
                str(value[6]), str(value[7]), str(value[8]), str(value[9]), str(value[10]),
                str(value[11]), str(value[12]), str(value[13]), str(value[14]), str(value[15]), str(value[16]))
        list.append(data)
        # print(writeSql)
        try:
            num += 1
            if num == 1000:
                Wcursor.executemany(writeSql, list)  # 执行sql语句
                write.commit()
                num = 0  # 计数归零
                list.clear()  # 清空list
        except:
            write.rollback()
    try:
        if len(list) != 0:
            Wcursor.executemany(writeSql, list)  # 执行sql语句
            write.commit()
    except:
        write.rollback()
    write.close()
    read.close()
    print("运行成功！！")


if __name__ == '__main__':
    readAndWrite()