from fastapi import FastAPI, Query
from typing import Optional
from recommend import getRecommendFoodList

app = FastAPI()

@app.get("/recommend")
def getRecommend(user_id: Optional[int] = Query(...), kcal: Optional[int] = Query(...)):
    return getRecommendFoodList(kcal, user_id)
