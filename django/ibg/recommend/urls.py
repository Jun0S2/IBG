from django.urls import path, include
from rest_framework.urlpatterns import format_suffix_patterns
from .views import UserView


urlpatterns = format_suffix_patterns([
    path('auth/', include('rest_framework.urls', namespace='rest_framework')),
    path("predict/user/<int:user_no>/",  UserView.recommend_by_predicted_score),
    path("predict/user2/<int:user_no>/", UserView.recommend_by_predicted_score_for_svd),
    path("predict/user3/<int:user_no>/", UserView.recommend_by_predicted_score_for_lasso),
    path("predict/popular", UserView.recommend_by_score_on_score_count),
    path("predict/desc/<int:game_no>/", UserView.recommend_by_desc_similarity),
    path("predict/category/<int:game_no>", UserView.recommend_by_category_similartiy)
])