# Python 3.12.2를 기반 이미지로 사용
FROM python:3.12.2

# 작업 디렉토리 설정
WORKDIR /app

# 현재 디렉토리의 내용을 컨테이너의 작업 디렉토리로 복사
COPY . /app

ARG DATABASE_URL

ENV DATABASE_URL=${DATABASE_URL}

# 필요한 Python 패키지 설치
RUN pip install --no-cache-dir -r requirements.txt

# FastAPI 실행 명령어
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8081"]
