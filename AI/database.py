from sqlalchemy import create_engine, MetaData, Table, select
from sqlalchemy.orm import sessionmaker
from dotenv import load_dotenv
import os

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
load_dotenv(os.path.join(BASE_DIR, ".env"))

engine = create_engine(os.environ["DATABASE_URL"])
Session = sessionmaker(bind=engine)
session = Session()
metadata = MetaData()
metadata.bind = engine

food_table = Table("Food", metadata, autoload_with=engine)
preference_table = Table("Preference", metadata, autoload_with=engine)

# def getFoodListByKcal(kcal: int):
#     query = select(food_table.c.food_id).where(food_table.c.calorie <= kcal).where(food_table.c.dbGroup == '음식')
#     return session.execute(query).fetchall()


def getFoodListByKcal(kcal: int):
    session = Session()  # 요청마다 새 세션 생성
    try:
        query = select(food_table.c.food_id).where(food_table.c.calorie <= kcal).where(food_table.c.dbGroup == "음식")
        result = session.execute(query).fetchall()
        session.commit()  # 성공적으로 쿼리 실행 후 커밋
        return result
    except Exception as e:
        session.rollback()  # 예외 발생 시 롤백
        raise
    finally:
        session.close()  # 작업 완료 후 세션 닫기


def getPreferenceList():
    query = select(preference_table)
    return session.execute(query).fetchall()
