export interface IGame {
  gameNo: number;
  gameImg: string;
  gameName: string;
  gameKorName: string;
  gameMinPlayer: number;
  gameMaxPlayer: number;
  gameCategory: string;
  gameTotalScore: number;
  like: boolean;
}

export interface IGameDetail extends IGame {
  gameNameKr: string;
  gameMinTime: number;
  gameMaxTime: number;
  gameYear: number;
  gameWeight: number;
  gameAge: number;
  gameDesc: string;
  gameKorDesc: string;
  myScore: number;
}
