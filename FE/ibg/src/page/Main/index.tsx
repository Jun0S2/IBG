import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import ThemeList from "./component/ThemeList";
import {
  getRecommByAge,
  getRecommByCategory,
  getRecommByDesc,
  getRecommByNewbie,
  getRecommByPlayer,
  getRecommByReviews,
  getRecommByScore,
  getRecommByTime,
  getRecommByUser,
  getRecommByWeight,
} from "../../api/recommend";
import { RootStateOrAny, shallowEqual, useSelector } from "react-redux";
import SkelBoardCard from "../../component/SkelBoardCard";
import AliceCarousel from "react-alice-carousel";
import { IGame } from "../../types/IGame";
import LegoSpinner from "../../component/LegoSpinner";
import { Box, Container, Skeleton, Typography } from "@mui/material";

// 테마별 게임리스트: sm(600) 이상(pc)에서는 버튼으로, 이하(모바일)에서는 스크롤로 동작
export default function Main() {
  const userNo = useSelector(
    (state: RootStateOrAny) => state.user.userNo,
    shallowEqual
  );
  const [descGame, setDescGame] = useState("");
  const [categoryGame, setCategoryGame] = useState("");

  const [userGameList, setUserGameList] = useState<IGame[]>([]);
  const [newbieGameList, setNewbieGameList] = useState<IGame[]>([]);
  const [descGameList, setDescGameList] = useState<IGame[]>([]);
  const [categoryGameList, setCategoryGameList] = useState<IGame[]>([]);
  const [weightGameList, setWeightGameList] = useState<IGame[]>([]);
  const [playerGameList, setPlayerGameList] = useState<IGame[]>([]);
  const [timeGameList, setTimeGameList] = useState<IGame[]>([]);
  const [ageGameList, setAgeGameList] = useState<IGame[]>([]);
  const [reviewGameList, setReviewGameList] = useState<IGame[]>([]);
  const [scoreGameList, setScoreGameList] = useState<IGame[]>([]);

  const [userLoading, setUserLoading] = useState<boolean>(userNo);
  const [newbieLoading, setNewbieLoading] = useState<boolean>(true);
  const [descLoading, setDescLoading] = useState<boolean>(userNo);
  const [categoryLoading, setCategoryLoading] = useState<boolean>(userNo);
  const [weightLoading, setWeightLoading] = useState<boolean>(userNo);
  const [playerLoading, setPlayerLoading] = useState<boolean>(userNo);
  const [timeLoading, setTimeLoading] = useState<boolean>(userNo);
  const [ageLoading, setAgeLoading] = useState<boolean>(userNo);
  const [reviewLoading, setReviewLoading] = useState<boolean>(true);
  const [scoreLoading, setScoreLoading] = useState<boolean>(true);

  const cardImg = require("../../assets/card.png");
  const navigate = useNavigate();

  useEffect(() => {
    let isMounted = true;
    const controller = new AbortController();
    const signal = controller.signal;

    // 로그인 한 경우
    if (userNo) {
      getRecommByUser(userNo, signal)
        .then((data) => {
          if (data.code === 200 && data.data && isMounted) {
            setUserGameList(data.data);
          }
          isMounted && setUserLoading(false);
        })
        .catch(() => {
          isMounted && setUserLoading(false);
        });
      // 조금 느림(3)
      getRecommByDesc(userNo, signal)
        .then((data) => {
          if (data.code === 200 && isMounted) {
            setDescGame(data.data.title);
            setDescGameList(data.data.recommendResultResponses);
          }
          isMounted && setDescLoading(false);
        })
        .catch(() => {
          isMounted && setDescLoading(false);
        });
        // TODO : Issue!
      // 많이 느림(5)
      getRecommByCategory(userNo, signal)
        .then((data) => {
          if (data.code === 200 && isMounted) {
            setCategoryGame(data.data.title);
            setCategoryGameList(data.data.recommendResultResponses);
          }
          isMounted && setCategoryLoading(false);
        })
        .catch(() => {
          isMounted && setCategoryLoading(false);
        });
      getRecommByWeight(userNo, signal)
        .then((data) => {
          if (data.code === 200 && isMounted) {
            setWeightGameList(data.data);
          }
          isMounted && setWeightLoading(false);
        })
        .catch(() => {
          isMounted && setWeightLoading(false);
        });
      getRecommByPlayer(userNo, signal)
        .then((data) => {
          if (data.code === 200 && isMounted) {
            setPlayerGameList(data.data);
          }
          isMounted && setPlayerLoading(false);
        })
        .catch(() => {
          isMounted && setPlayerLoading(false);
        });
      getRecommByTime(userNo, signal)
        .then((data) => {
          if (data.code === 200 && isMounted) {
            setTimeGameList(data.data);
          }
          isMounted && setTimeLoading(false);
        })
        .catch(() => {
          isMounted && setTimeLoading(false);
        });
      getRecommByAge(userNo, signal)
        .then((data) => {
          if (data.code === 200 && isMounted) {
            setAgeGameList(data.data);
          }
          isMounted && setAgeLoading(false);
        })
        .catch(() => {
          isMounted && setAgeLoading(false);
        });
    }
    // 공통
    getRecommByNewbie(userNo, signal)
      .then((data) => {
        if (data.code === 200 && isMounted) {
          setNewbieGameList(data.data);
        }
        isMounted && setNewbieLoading(false);
      })
      .catch(() => {
        isMounted && setNewbieLoading(false);
      });
    getRecommByReviews(userNo, signal)
      .then((data) => {
        if (data.code === 200 && isMounted) {
          setReviewGameList(data.data);
        }
        isMounted && setReviewLoading(false);
      })
      .catch(() => {
        isMounted && setReviewLoading(false);
      });
    getRecommByScore(userNo, signal)
      .then((data) => {
        if (data.code === 200 && isMounted) {
          setScoreGameList(data.data);
        }
        isMounted && setScoreLoading(false);
      })
      .catch(() => {
        isMounted && setScoreLoading(false);
      });

    // 컴포넌트가 willunmount될 떄 실행되는 함수
    return () => {
      setUserGameList([]);
      setDescGameList([]);
      // setCategoryGameList([]);
      setWeightGameList([]);
      setPlayerGameList([]);
      setTimeGameList([]);
      setAgeGameList([]);
      // 실행중인 DOM 요청이 완료되기 전에 취소
      controller.abort();
      isMounted = false;
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [userNo]);

  // 페이지 이동
  const movePage = (page: string) => {
    navigate(page);
  };

  const skelCards = [1, 1, 1, 1, 1].map(() => <SkelBoardCard marginX={0.5} />);

  const SkelTheme = () => {
    return (
      <Box sx={{ position: "relative" }}>
        <Skeleton animation="wave" width="30%" height={50} sx={{ mt: 4 }} />
        <AliceCarousel
          paddingLeft={15}
          paddingRight={15}
          items={skelCards}
          disableButtonsControls
          disableDotsControls
          controlsStrategy="responsive"
          responsive={{
            0: { items: 1.5 },
            400: { items: 2 },
            550: { items: 3 },
            700: { items: 4 },
            900: { items: 5 },
          }}
        ></AliceCarousel>
        <LegoSpinner
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
          }}
        />
      </Box>
    );
  };

  return (
    <Container style={{ padding: 20 }} sx={{ mt: 5 }}>
      {!userNo && (
        <Box
          onClick={() => movePage("/signin")}
          sx={{
            mt: 5,
            py: 1,
            px: 2,
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            backgroundColor: "grey.200",
            borderRadius: 3,
            cursor: "pointer",
          }}
        >
          <img src={cardImg} alt="card" width={50} />
          <Typography
            sx={{ fontSize: { xs: 15, md: 18 }, ml: 0.5 }}
            align="left"
          >
            로그인을 하면,
            <Box component="span" ml={1} sx={{ fontWeight: 600 }}>
              나에게 맞는 보드게임을 추천
            </Box>
            받을 수 있어요!
          </Typography>
        </Box>
      )}

      {userLoading ? (
        <SkelTheme />
      ) : (
        userGameList.length > 0 && (
          <ThemeList title="나의 맞춤 추천 게임" gameList={userGameList} />
        )
      )}
      {newbieLoading ? (
        <SkelTheme />
      ) : (
        userGameList.length <= 0 &&
        newbieGameList.length > 0 && (
          <ThemeList
            title="초보자라면 이 게임 어때요?"
            gameList={newbieGameList}
          />
        )
      )}
      {weightLoading ? (
        <SkelTheme />
      ) : (
        weightGameList.length > 0 && (
          <ThemeList
            title="내가 좋아하는 난이도의 게임"
            gameList={weightGameList}
          />
        )
      )}
      {playerLoading ? (
        <SkelTheme />
      ) : (
        playerGameList.length > 0 && (
          <ThemeList
            title="내가 즐겨하는 인원 수의 게임"
            gameList={playerGameList}
          />
        )
      )}
      {timeLoading ? (
        <SkelTheme />
      ) : (
        timeGameList.length > 0 && (
          <ThemeList
            title="내가 즐겨하는 플레이 시간의 게임"
            gameList={timeGameList}
          />
        )
      )}
      {ageLoading ? (
        <SkelTheme />
      ) : (
        ageGameList.length > 0 && (
          <ThemeList
            title="내가 즐겨하는 나이대의 게임"
            gameList={ageGameList}
          />
        )
      )}
      {descLoading ? (
        <SkelTheme />
      ) : (
        descGameList.length > 0 && (
          <ThemeList
            title={`'${descGame}'와/과 비슷한 유형의 게임 추천`}
            gameList={descGameList}
          />
        )
      )}
      {categoryLoading ? (
        <SkelTheme />
      ) : (
        categoryGameList.length > 0 && (
          <ThemeList
            title={`'${categoryGame}'와/과 비슷한 장르의 게임 추천`}
            gameList={categoryGameList}
          />
        )
      )}
      {reviewLoading ? (
        <SkelTheme />
      ) : (
        reviewGameList.length > 0 && (
          <ThemeList title="리뷰가 많은 게임" gameList={reviewGameList} />
        )
      )}
      {scoreLoading ? (
        <SkelTheme />
      ) : (
        scoreGameList.length > 0 && (
          <ThemeList title="이보게 평점이 높은 게임" gameList={scoreGameList} />
        )
      )}
    </Container>
  );
}
