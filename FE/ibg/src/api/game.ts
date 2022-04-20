import { apiInstance } from ".";
import { ISearchFilter } from "../page/BoardGame/BoardGameSearch/component/GameFilter";

const api = apiInstance();

// 게임 상세정보 조회
async function getGameDetail(gameNo: number, userNo: number) {
  return (await api.get(`/search/${gameNo}/${userNo}`)).data.data;
}

// 게임 이름으로 검색
async function SearchByName(
  searchName: string,
  userNo: number,
  signal: AbortSignal
) {
  return (
    await api.post(`/search/auto`, {
      searchName,
      userNo,
      signal,
    })
  ).data.data;
}

// 필터 검색
async function searchByFilter(data: ISearchFilter) {
  return (await api.post(`/search/filter`, data)).data;
}

// 전체 게임 목록 조회(자동완성용)
async function getAutoAllGame() {
  return (await api.get(`/search/auto`)).data;
}

export { getGameDetail, SearchByName, searchByFilter, getAutoAllGame };
