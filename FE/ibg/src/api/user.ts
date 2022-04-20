import { apiInstance, loginApiInstance } from "./index";

const api = apiInstance();

// 회원 가입
async function join(email: string, nickname: string, password: string) {
  return await api.post(`/join`, {
    userEmail: email,
    userNick: nickname,
    userPwd: password,
  });
}

//로그인
async function login(email: string, password: string) {
  const loginApi = loginApiInstance();
  return await loginApi.post(`/login`, { userEmail: email, userPwd: password });
}

// 회원 정보
async function userInfo(userNo: number) {
  const loginApi = loginApiInstance();
  return (await loginApi.get(`/user/account/${userNo}`)).data.data; //유저 객체라 data 썼는데 일단 자료가 들어와바야 알겠다.
}

//이메일 중복 조회
async function checkEmail(email: string) {
  return (await api.post(`/email`, { userEmail: email })).data;
}
//이메일 중복 조회
async function checkNickname(nickname: string) {
  return (await api.post(`/nickname`, { userNick: nickname })).data;
}

//회원 별점 등록
async function rateGame(userNo: number, gameNo: number, scoreRating: number) {
  const loginApi = loginApiInstance();
  return (
    await loginApi.post(`/user/score`, {
      userNo: userNo,
      gameNo: gameNo,
      scoreRating: scoreRating,
    })
  ).data; //응답 코드
}

// 관심목록 추가 및 제거
async function addDelLike(userNo: number, gameNo: number) {
  const loginApi = loginApiInstance();
  return await (
    await loginApi.post(`user/like`, { userNo: userNo, gameNo: gameNo })
  ).data;
}

// 회원가입 설문조사
// async function initSurvey(userNo: number) {
//   const loginApi = loginApiInstance();
//   return (await loginApi.get(`user/survey/${userNo}`)).data.data;
// }
// 회원가입 설문조사 : 백앤드 완료되면 이것으로 대체하기
async function initSurvey() {
  const loginApi = loginApiInstance();
  return (await loginApi.get(`user/survey/`)).data.data;
}

// 관심 목록 가져오기
async function getLikedList(userNo: number) {
  const loginApi = loginApiInstance();
  return await (
    await loginApi.get(`user/like/${userNo}`)
  ).data.data;
}

export {
  join,
  login,
  userInfo,
  checkEmail,
  checkNickname,
  rateGame,
  addDelLike,
  initSurvey,
  getLikedList,
};
