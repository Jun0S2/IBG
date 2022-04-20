import { apiInstance } from ".";

const api = apiInstance();

// 리뷰 목록 조회
async function getReviewList(gameNo: number) {
  return (await api.get(`/review/${gameNo}`)).data.data;
}

// 리뷰 등록
async function addReview(gameNo: number, userNo: number, content: string) {
  return (
    await api.post(`/review`, {
      gameNo: gameNo,
      userNo: userNo,
      content: content,
    })
  ).data;
}

export { getReviewList, addReview };
