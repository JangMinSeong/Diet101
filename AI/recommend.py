from database import getFoodListByKcal, getPreferenceList
from sklearn.metrics.pairwise import cosine_similarity
import pandas as pd
import numpy as np

def getRecommendFoodList(kcal: int, user_id: int):
    foods = getFoodListByKcal(kcal)
    preferences = getPreferenceList()

    preferences_pd = pd.DataFrame(preferences, columns=['food_id','user_id','weight'])
    foods_pd = pd.DataFrame(foods, columns=['food_id'])

    preferences_foods = pd.merge(preferences_pd, foods_pd, on="food_id")
    preferences_matrix = preferences_foods.pivot_table(index='user_id', columns='food_id', values='weight', fill_value=0)
    preferences_matrix_T = preferences_matrix.T

    # 유사도 행렬
    item_sim = cosine_similarity(preferences_matrix_T, preferences_matrix_T)
    item_sim_df = pd.DataFrame(item_sim, index=preferences_matrix_T.index, columns=preferences_matrix_T.index)

    preferences_pred = predicting_preferences(preferences_matrix.values, item_sim_df.values)
    preferences_pred_matrix = pd.DataFrame(data=preferences_pred, index=preferences_matrix.index, columns=preferences_matrix.columns)

    # 사용자 ID에 해당하는 음식 선호도 순으로 정렬
    user_preferences = preferences_pred_matrix.loc[user_id]
    user_preferences_sorted = user_preferences.sort_values(ascending=True)
    top_food_ids = user_preferences_sorted.index.tolist()
    return {"foods": top_food_ids}

def predicting_preferences(preferences_arr, item_sim_arr):
    sum_sr = preferences_arr @ item_sim_arr
    sum_s_abs = np.array([np.abs(item_sim_arr).sum(axis=1)])
    preferences_pred = sum_sr / sum_s_abs
    return preferences_pred