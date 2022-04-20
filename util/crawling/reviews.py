# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
from bs4 import BeautifulSoup
from selenium import webdriver
import time
import pandas as pd
import re


# from webdriver_manager.chrome import ChromeDriverManager

def get_review_page(g):
    for i in range(17, 50):
        # Chrome의 경우 | 아까 받은 chromedriver의 위치를 지정해준다.
        driver1 = webdriver.Chrome(r'C:\Users\SSAFY\Downloads\chromedriver2')
        # 암묵적으로 웹 자원 로드를 위해 3초까지 기다려 준다.
        driver1.implicitly_wait(3)
        # url에 접근한다.
        driver1.get(g['bgg_url'][i] + '/ratings');
        time.sleep(2)
        html1 = driver1.page_source
        soup1 = BeautifulSoup(html1, 'html.parser')
        page_idx = soup1.select('ul.pagination li:nth-last-child(3) > a.ng-binding')
        page = 0
        max = 1
        for idx in page_idx:
            if max < int(idx.get_text().strip()):
                page = int(idx.get_text().strip())

        if page == 0:
            page_idx = soup1.select('ul.pagination li:nth-last-child(3) > a.ng-binding')

            for idx in page_idx:
                if max < int(idx.get_text().strip()):
                    page = int(idx.get_text().strip())


        print(g['names'][i])
        print(page)
        get_review_info(i, page, g)
        driver1.close()


def get_review_info(ii, page, g):
    game_id_ = []
    ratings_ = []
    ids_ = []
    dates_ = []
    comments_ = []

    # Chrome의 경우 | 아까 받은 chromedriver의 위치를 지정해준다.
    driver = webdriver.Chrome(r'C:\Users\SSAFY\Downloads\chromedriver')
    # 암묵적으로 웹 자원 로드를 위해 3초까지 기다려 준다.
    driver.implicitly_wait(3)

    for p in range(1, page + 1):

        # print(g['bgg_url'][ii] + '/ratings?pageid=' + str(p))
        driver.get(g['bgg_url'][ii] + '/ratings?pageid=' + str(p))
        time.sleep(3)
        html = driver.page_source
        soup = BeautifulSoup(html, 'html.parser')

        print('pageCnt: '+str(p))
        #
        # ratings = soup.find_all('div', {'class': 'rating-angular'})
        # ids = soup.find_all('a', {'class': 'comment-header-user ng-binding'})
        # dates = soup.find_all('span', {'class': 'comment-header-timestamp'})
        # comments = soup.find_all('div', {'class': 'comment-body summary-item-body'})

        ratings = soup.select('div.summary-item-callout > div')
        ids = soup.select('div.comment-header-title > a')
        dates = soup.select('div.comment-header-title span.comment-header-timestamp')
        comments = soup.select('div.comment-body.summary-item-body div.expandable-body > span')

        for cl in comments:
            cl2 = cl.get_text().strip().replace('+ More   - Less', '')
            comments_.append(cl2)
            # print(cl2)

        for r in ratings:
            game_id_.append(g['game_id'][ii])
            ratings_.append(r.get_text().strip())
            # print(r.get_text().strip())

        for i in ids:
            ids_.append(i.get_text().strip())
            # print(i.get_text().strip())

        for d in dates:
            dates_.append(d.get_text().strip())
            # print(d.get_text().strip())

        print(len(dates_))
        print(len(ids_))
        print(len(comments_))
        print(len(ratings_))

    driver.close()
    mCols = []
    df = pd.DataFrame(columns=mCols)
    df['game_id'] = game_id_
    df['rating'] = ratings_
    df['id'] = ids_
    df['date'] = dates_
    df['comment'] = comments_
    df.to_csv('C:/Users/SSAFY/Downloads/Game_review_' + str(ii) + '.csv')


# def set_csv(ii, game_id_, ratings_, ids_, dates_, comments_):
#     mCols = []
#     df = pd.DataFrame(columns=mCols)
#     df['game_id'] = game_id_
#     df['rating'] = ratings_
#     df['id'] = ids_
#     df['date'] = dates_
#     df['comment'] = comments_
#     df.to_csv('C:/Users/SSAFY/Downloads/Game_review_' + str(ii) + '.csv')


if __name__ == '__main__':
    g = pd.read_csv('C:/Users/SSAFY/Downloads/Game_res.csv')

    get_review_page(g)
    # Game = pd.read_csv('C:/Users/SSAFY/Downloads/Game.csv')
