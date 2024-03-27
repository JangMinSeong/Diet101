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

food_table = Table('Food', metadata, autoload_with=engine)
preference_table = Table('Preference', metadata, autoload_with=engine)

def getFoodListByKcal(kcal: int):
    query = select(food_table.c.food_id).where(food_table.c.calorie <= kcal)
    return session.execute(query).fetchall()

def getPreferenceList():
    query = select(preference_table)
    return session.execute(query).fetchall()
