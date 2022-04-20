from django.shortcuts import get_list_or_404, get_object_or_404
from rest_framework import viewsets
from .models import Score, Game, Recommend, User
from rest_framework import permissions
from rest_framework.decorators import api_view
from rest_framework.response import Response
import pandas as pd
import numpy as np

# 사용자 평점 기반 추천 svd
from scipy.sparse.linalg import svds
from sklearn.linear_model import Lasso

# 유형별 추천 TfidfVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel

# 카테고리 기반
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity

import logging
from rest_framework import status


class UserView(viewsets.ModelViewSet):

    """
         @author : 박민주
         @date : 2022-04-4 오전 16:00
         @desc: 유저가 플레이 했던 게임 중 설명이 유사한 정도로 추천
    """

    @api_view(['GET'])
    def recommend_by_desc_similarity(self, game_no):
        df_games = Game.objects.all()
        df_games = pd.DataFrame(df_games.values("game_no", "game_name", "game_desc"))

        # Define a TF-IDF Vectorizer Object. Remove all english stop words such as 'the', 'a'
        tfidf = TfidfVectorizer(stop_words='english')

        # Replace NaN with an empty string
        df_games['game_desc'] = df_games['game_desc'].fillna('')

        # Construct the required TF-IDF matrix by fitting and transforming the data
        tfidf_matrix = tfidf.fit_transform(df_games['game_desc'])

        # 300개의 게임을 설명하는데 7000개 이상의 다른 단어가 사용되었다. 이 행렬을 사용해 유사성 점수를 계산할 수 있습니다. 코사인 유사성을 사용해 두 영화간의 유사성을 계산한다.
        # 코사인 유사성 사용 이유: 크기와 무관하고 계산이 비교적 쉽고 빠르기 때문.
        # Compute the cosine similarity matrix
        cosine_sim = linear_kernel(tfidf_matrix, tfidf_matrix)

        # Construct a reverse map of indices and movie titles
        indices = pd.Series(df_games.index, index=df_games.index).drop_duplicates()

        # Function that takes in movie title as input and outputs most similar movies
        def get_recommendations(title, cosine_sim=cosine_sim):
            # Get the index of the movie that matches the title
            idx = indices[title]

            # Get the pairwsie similarity scores of all movies with that movie
            sim_scores = list(enumerate(cosine_sim[idx]))

            # Sort the movies based on the similarity scores
            sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)

            # Get the scores of the 10 most similar movies
            sim_scores = sim_scores[1:11]

            # Get the movie indices
            game_indices = [i[0] for i in sim_scores]

            # Return the top 10 most similar movies
            return df_games['game_name'].iloc[game_indices]

        recommendations = get_recommendations(game_no)

        game_no_list = list(recommendations.index)
        return Response(game_no_list)

    """
         @author : 박민주
         @date : 2022-04-4 오전 16:00
         @desc: 평점 수 대비 평점이 높은 순으로 추천 => 인기순
    """

    @api_view(['GET'])
    def recommend_by_score_on_score_count(request):
        df_games = Game.objects.only("game_no", "game_total_score").all()
        df_games = pd.DataFrame(df_games.values("game_no", "game_total_score"))

        df_scores1 = Score.objects.all().values("game_no", "score_rating")
        df_scores = pd.DataFrame(df_scores1)

        df_games['score_count'] = df_scores.groupby('game_no')['score_rating'].count()

        C = df_games['game_total_score'].mean()

        # 게임이 차트에 오르려면 목록에 있는 게임의 투표수가 90%이 되어야 한다.
        m = df_games['score_count'].quantile(0.9)

        q_games = df_games.copy().loc[df_games['score_count'] >= m]

        def weighted_rating(x, m=m, C=C):
            v = x['score_count']
            R = x['game_total_score']
            # Calculation based on the IMDB formula
            return (v / (v + m) * R) + (m / (m + v) * C)

        q_games['score2'] = q_games.apply(weighted_rating, axis=1)
        q_games = q_games.sort_values('score2', ascending=False)

        game_list = list(q_games['game_no'])

        return Response(game_list)

    """
         @author : 박민주
         @date : 2022-04-06 오전 01:00
         @desc: 유저가 플레이하지 않은 게임들의 평점을 예측하여 추천 => LinearRegression Lasso 알고리즘
    """

    @api_view(['GET'])
    def recommend_by_predicted_score_for_lasso(self, user_no):
        Recommend.objects.filter(user_no=user_no).delete()

        games = Game.objects.only('game_no', 'game_category').all()
        game_list = pd.DataFrame(games.values("game_no", "game_category")).set_index('game_no')
        categorys = game_list['game_category'].str.get_dummies("|")

        scores = Score.objects.filter(user_no=user_no)
        user_score_list = pd.DataFrame(scores.values("score_no", "game_no", "user_no", "score_rating")).set_index('score_no')
        user_score_list = user_score_list.merge(categorys, left_on='game_no', right_index=True)

        model = Lasso(alpha=0.003)
        X = user_score_list[categorys.columns]
        y = user_score_list['score_rating']
        model.fit(X, y)
        # user_profile = [model.intercept_, *model.coef_]

        # recoomendations가 모든 게임 리스트 가져옴
        recommendations = game_list.copy()
        # 카테고리로 정리
        recommend_category = recommendations['game_category'].str.get_dummies("|")
        
        # lasso로 만든 예측 모델
        predict = model.predict(recommend_category)

        # 300개의 게임에 대한 예측값을 'predict' 컬럼에 저장
        recommendations['predict'] = predict
        # print(recommendations)

        # 내가 보지 않은 게임
        recommendations = recommendations[~game_list.index.isin(user_score_list['game_no'])]
        # print(recommendations)

        # 유저 찾고
        findUser = get_object_or_404(User, pk=user_no)

        # TODO : 인덱스(gameNo) 변경되는지 확인 요망
        recommendations = recommendations.sort_values('predict', ascending=False)
        # print(recommendations)

        # 행 순회하면서 저장
        # TODO: 50개 순회로 변경 .range()
        k = 0
        for idx, row in recommendations.iterrows():
            if k == 50:
                break
            findGame = get_object_or_404(Game, pk=idx)
            r = Recommend(game_no=findGame, user_no=findUser, recommend_rating=row['predict']);
            r.save()
            k += 1
        # print(recommendations)

        # recommendations = recommendations.index[:10]
        # print(recommendations.values.tolist())
        # return Response(recommendations.values.tolist())
        return Response()
        
    """
         @author : 박민주
         @date : 2022-04-4 오전 16:00
         @desc: 유저가 플레이하지 않은 게임들의 평점을 예측하여 추천 => SVD 알고리즘
    """

    @api_view(['GET'])
    def recommend_by_predicted_score_for_svd(request, user_no):
        # 이미 Recommend에 저장된 것 다 지우기
        Recommend.objects.filter(user_no=user_no).delete()

        games = Game.objects.all()
        game_list = pd.DataFrame(games.values("game_no", "game_category"))

        scores = Score.objects.all()
        score_list = pd.DataFrame(scores.values("score_no", "game_no", "user_no", "score_rating"))

        user_game_score = pd.merge(score_list, game_list, on="game_no")

        # 사용자의 각 영화 평점
        user_game_score = user_game_score.pivot_table('score_rating', index='user_no', columns="game_no")

        df_user_game_score = user_game_score.fillna(0)

        matrix = df_user_game_score.to_numpy()
        # 총 유저 평점의 평균
        user_score_mean = np.mean(matrix, axis=1)

        matrix_user_mean = matrix - user_score_mean.reshape(-1, 1)

        U, sigma, Vt = svds(matrix_user_mean, k=12)

        sigma = np.diag(sigma)

        # 원본 행렬 복구. 내적 수행
        svd_user_predicted_scores = np.dot(np.dot(U, sigma), Vt) + user_score_mean.reshape(-1, 1)

        df_svd_preds = pd.DataFrame(svd_user_predicted_scores, columns=df_user_game_score.columns)

        def recommend_games(df_svd_preds, user_id, ori_games_df, ori_ratings_df, num_recommendations=5):
            # 현재 index로 적용 되어 있으므로 user_id-1 해야함.
            user_row_number = user_id - 1

            # 최종적으로 만든 pred_df에서 사용자 index에 따라 영화 데이터 정렬 -> 영화 평점이 높은 순으로 정렬
            sorted_user_predictions = df_svd_preds.iloc[user_row_number].sort_values(ascending=False)

            # 원본 평점 데이터에서 user_id에 해당하는 데이터 뽑기
            user_data = ori_ratings_df[ori_ratings_df.user_no == user_id]

            # 위에서 뽑은 user_data와 원본 데이터를 합친다.
            user_history = user_data.merge(ori_games_df, on='game_no').sort_values(['score_rating'], ascending=False)

            # 원본 게임 데이터에서 사용자가 한 게임 데이터를 제외한 데이터 추출
            recommendations = ori_games_df[~ori_games_df['game_no'].isin(user_history['game_no'])]
            # 사용자의 게임 평점이 높은 순으로 정렬된 데이터와 위 recommendations를 합친다.
            recommendations = recommendations.merge(pd.DataFrame(sorted_user_predictions).reset_index(), on='game_no')
            # 컬럼명을 변경하고 정렬해서 return
            recommendations = recommendations.rename(columns={user_row_number: 'Predictions'}).sort_values(
                'Predictions',
                ascending=False).iloc[
                              :num_recommendations, :]

            return user_history, recommendations

        already_rated, predictions = recommend_games(df_svd_preds, user_no, game_list, score_list, 10)

        return Response()

    """
         @author : 박민주
         @date : 2022-04-4 오전 16:00
         @desc: 유저가 플레이하지 않은 게임들의 평점을 예측하여 추천 => 카테고리별 점수 평균
    """

    @api_view(['GET'])
    def recommend_by_predicted_score(request, user_no):
        # 추천 받아서 결과 반환

        # 이미 Recommend에 저장된 것 다 지우기
        Recommend.objects.filter(user_no=user_no).delete()

        games = Game.objects.all()
        game_list = pd.DataFrame(games.values("game_no", "game_category")).set_index("game_no")
        game_list['game_category'] = game_list['game_category'].fillna("")
        category_list = list(game_list['game_category'].apply(lambda x: x.split("|")))

        category_dummies = game_list['game_category'].str.get_dummies(sep="|")
        category_dummies = category_dummies.replace(0, np.nan)

        scores = Score.objects.filter(user_no=user_no)
        score_list = pd.DataFrame(scores.values("score_no", "game_no", "user_no", "score_rating"))

        user_predict_list = score_list.merge(category_dummies, left_on="game_no", right_on="game_no")
        user_predict_list = user_predict_list.replace(0, np.nan)
        # category_dummies에서 해당 game_no 삭제하기

        for cols in category_dummies.columns:
            user_predict_list[cols] = user_predict_list[cols] * user_predict_list['score_rating']

        user_profile = user_predict_list[category_dummies.columns].mean()

        # 모든 게임에 대해서 predict 구하기

        # 이미 한 게임은 지우자
        user_del_game_list = score_list['game_no']

        category_dummies.drop(user_del_game_list, axis=0, inplace=True)
        game_list.drop(user_del_game_list, axis=0, inplace=True)

        # category_dummies에서 유저가 사용한 게임 제거하기
        predict = []
        for idx, row in category_dummies.iterrows():
            predict.append((user_profile * row[category_dummies.columns]).mean())

        game_list['recommend_rating'] = predict
        predict = game_list.drop(['game_category'], axis=1)

        predict = predict.fillna(score_list['score_rating'].mean())

        findUser = get_object_or_404(User, pk=user_no)
        for idx, row in predict.iterrows():
            findGame = get_object_or_404(Game, pk=idx)
            r = Recommend(game_no=findGame, user_no=findUser, recommend_rating=row['recommend_rating']);
            r.save()

        # 유저별 추천 결과를 DB에 넣고 스프링이 DB에 접근
        # 유저별 추천 결과를 장고에서 받아서 스프링이 DB에 접근

        # pk로 하나만 가져오기
        # user = get_object_or_404(User, pk=user_no)
        # serializer = UserSerializer(user)

        # user_no로 여러개 가져올 수 있는지 test
        # user = User.objects.filter(user_no=user_no)
        # serializer = UserSerializer(user, many=True)

        # user_no로 score데이터 가져오기

        return Response()

    """
         @author : 권오범
         @date : 2022-04-4 오전 16:00
         @desc: 카테고리의 유사도를 기반으로 추천
    """

    @api_view(['GET'])
    def recommend_by_category_similartiy(request, game_no):
        scores = Score.objects.all()
        score_data = pd.DataFrame(scores.values("score_no", "game_no", "user_no", "score_rating"))

        games = Game.objects.all()
        game_data = pd.DataFrame(games.values("game_no", 'game_name', 'game_category', 'game_total_score'))

        game_data['vote_count'] = score_data.groupby('game_no')["user_no"].count()

        c = game_data['game_total_score'].mean()
        m = game_data['vote_count'].quantile(0.008)

        def weighted_rating(x, m=m, c=c):
            v = x['vote_count']
            r = x['game_total_score']

            return (v / (v + m) * r) + (m / (m + v) * c)

        game_data = game_data.loc[game_data['vote_count'] >= m]
        game_data['game_weighted_score'] = game_data.apply(weighted_rating, axis=1)  # 함수 인자로 m, c 넘겨줌

        game_data = game_data[game_data['game_category'] != 'nan']

        game_data['game_category'] = game_data['game_category'].apply(
            lambda x: x.replace('|', '/').replace(' ', '').replace('/', ' '))

        count_vector = CountVectorizer(ngram_range=(1, 3))
        c_vector_category = count_vector.fit_transform(game_data['game_category'])
        category_c_sim = cosine_similarity(c_vector_category, c_vector_category).argsort()[:, ::-1]

        game = Game.objects.get(game_no=game_no)
        game_name = game.game_name

        target_game_index = game_data[game_data['game_name'] == game_name].index.values

        sim_index = category_c_sim[target_game_index, :30].reshape(-1)

        sim_index = sim_index[sim_index != target_game_index]

        response = game_data.iloc[sim_index].sort_values('game_total_score', ascending=False)[:10]  # 게임 번호로 수정
        response = response['game_no'].values.tolist()
        return Response(response)
