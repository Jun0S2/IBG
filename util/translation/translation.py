import time

import pandas as pd
import os
import sys
import json
import urllib.request

c_result_name_list = []
# c_result_desc_list = []

def translate(name, option):
    client_id = "" # 개발자센터에서 발급받은 Client ID 값
    client_secret = "" # 개발자센터에서 발급받은 Client Secret 값
    encText = urllib.parse.quote(name) # 번역할 내용
    data = "source=en&target=ko&text=" + encText # 영어 -> 한국어
    url = "https://openapi.naver.com/v1/papago/n2mt"
    request = urllib.request.Request(url)
    request.add_header("X-Naver-Client-Id", client_id)
    request.add_header("X-Naver-Client-Secret", client_secret)
    response = urllib.request.urlopen(request, data=data.encode("utf-8"))
    rescode = response.getcode()
    if(rescode==200):
        response_body = response.read()
        res = json.loads(response_body.decode('utf-8'))
        result = res['message']['result']['translatedText']
        if option == "name":
            c_result_name_list.append(result)
            print(result)
            time.sleep(5)
        # else:
        #     c_result_desc_list.append(result)
        #     print(result)
        #     time.sleep(5)
    else:
        print("Error Code:" + rescode)

def setData():
    # 번역할 파일
    df = pd.read_csv("C:/workspace/projects/IBG/util/data/game_220330_translated.csv")
    df_desc = pd.read_csv("C:/workspace/projects/IBG/util/data/game_desc_translated.csv", encoding='cp949')
    # c_list_name = df['game_name'].tolist()
    c_list_kor_desc = df_desc['game_kor_desc'].tolist()
    # c_list_desc = df['gameDesc'].tolist()
    # 모든 행 순회
    # for i in range(len(c_list_name)):
    #     translate(c_list_name[i], "name")
        # translate(c_list_desc[i], "desc")
    # df에 저장
    # df['game_kor_name'] = c_result_name_list
    for i in range(len(c_list_kor_desc)):
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("&lt;", "<")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("&gt;", ">")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("&nbsp;", " ")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("&amp;", "&")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("&quot;", "\"")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("&ndash;", "?")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("&ndash;", "?")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("&ldquo;", "\"")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("&rdquo;", "\"")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("&mdash;", "?")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("<br/>", "\n")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("<br />", "\n")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("< br / >", "\n")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("<br>", "")
        c_list_kor_desc[i] = c_list_kor_desc[i].replace("&euml;", "ë")

    df['game_kor_desc'] = c_list_kor_desc
    df.to_csv("C:/workspace/projects/IBG/util/data/game_220330_translated_desc.csv",
              sep=',', # 구분자
              na_rep='NaN',# 결측값 표기
              encoding="utf-8-sig") # 한글 인코딩처리


if __name__ == '__main__':
    setData()