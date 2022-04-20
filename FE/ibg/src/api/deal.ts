import { apiInstance } from ".";

const api = apiInstance();

// 거래 전체 조회
async function getDealLists() {
  return (await api.get(`/deal`)).data;
}

// 거래 검색
async function getDealSearch(gameName: string) {
  return (
    await api.post(`/deal/search`, {
      gameName,
    })
  ).data;
}

// 거래 상세
async function getDealDetail(dealNo: number) {
  return (await api.get(`/deal/${dealNo}`)).data;
}

// 거래 등록
async function writeDeal(
  gameNo: string,
  userNo: string,
  dealTitle: string,
  dealContent: string,
  file: File | Blob,
  dealPrice: string
) {
  const form = new FormData();
  form.append("gameNo", gameNo);
  form.append("userNo", userNo);
  form.append("dealTitle", dealTitle);
  form.append("dealContent", dealContent);
  form.append("file", file);
  form.append("dealPrice", dealPrice);
  return (
    await api.post(`/deal`, form, {
      headers: { "Content-Type": "multipart/form-data" },
    })
  ).data;
}

// 거래 완료
async function closeDeal(dealNo: number) {
  return (
    await api.put(`/deal`, {
      dealNo,
    })
  ).data;
}

// 댓글 등록
async function addDealReview(
  dealNo: number,
  userNo: number,
  dealReviewContent: string
) {
  return (await api.post(`/deal/review`, { dealNo, userNo, dealReviewContent }))
    .data;
}

// 댓글 목록 가져오기
async function getDealReviewList(dealNo: number) {
  return (await api.get(`/deal/review/${dealNo}`)).data;
}

export {
  getDealLists,
  getDealSearch,
  getDealDetail,
  writeDeal,
  closeDeal,
  addDealReview,
  getDealReviewList,
};
