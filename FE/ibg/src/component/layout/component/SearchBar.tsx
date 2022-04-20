import { styled, alpha, Autocomplete, Box, TextField } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";

// 검색창 스타일
const Search = styled("div")(({ theme }) => ({
  position: "relative",
  borderRadius: 18,
  // backgroundColor: alpha(theme.palette.warning.main, 0.2),
  backgroundColor: alpha("#FFFF", 0.5),
  "&:hover": {
    // backgroundColor: alpha(theme.palette.warning.main, 0.25),
    backgroundColor: alpha("#FFFF", 0.8),
  },
  marginRight: theme.spacing(2),
  marginLeft: theme.spacing(5),
  // marginBottom: theme.spacing(2),
  width: "100%",
  [theme.breakpoints.up("sm")]: {
    marginLeft: theme.spacing(3),
    width: "auto",
  },
  boxShadow: "rgba(0, 0, 0, 0.04) 0px 3px 5px",
}));

const SearchIconWrapper = styled("div")(({ theme }) => ({
  padding: theme.spacing(0, 2),
  height: "100%",
  position: "absolute",
  pointerEvents: "none",
  display: "flex",
  alignItems: "center",
  justifyContent: "center",
  color: "grey",
}));

const StyledTextField = styled(TextField)(({ theme }) => ({
  "& .MuiOutlinedInput-root": {
    fontSize: "0.85rem",
    padding: theme.spacing(0, 1),
    // vertical padding + font size from searchIcon
    paddingLeft: `calc(1.8em + ${theme.spacing(2)})`,
    transition: theme.transitions.create("width"),
    width: "100%",
    [theme.breakpoints.up("sm")]: {
      width: "40ch",
    },
  },
  fieldset: {
    border: "none",
  },
}));

interface IAutoGame {
  gameKorName: string;
  gameName: string;
  gameNo: number;
}

export default function SearchBar(props: {
  gameList: IAutoGame[];
  placeholder?: string;
  onClickItem: Function;
}) {
  // 검색어에서 영어 이름을 추출하여 검색하기 위함(자동완성의 각 option에서 gameNo 찾아오는 방법을 못찾았음 ㅠ)
  const callMoveDetail = (name: string) => {
    // 검색창에 값이 있을 경우에만 수행
    if (name) {
      let extractName: string;
      extractName = name.match(/\(.*\)/gi)?.toString() as string;
      // 괄호가 있는 완전한 값일 때만 수행(괄호가 짝이 안맞거나 없으면 undefined가 뜸)
      if (extractName) {
        extractName = extractName.split("(").join("").split(")").join("");

        const extractGame = props.gameList.filter(
          (game) => game.gameName === extractName
        );

        // 게임을 목록에서 정상적으로 찾았다면, 해당 번호에 맞는 상세 페이지로 이동
        if (extractGame) props.onClickItem(extractGame[0].gameNo);
      }
    }
  };

  return (
    <Search>
      <SearchIconWrapper>
        <SearchIcon />
      </SearchIconWrapper>
      <Autocomplete
        autoComplete
        clearOnEscape
        options={props.gameList}
        getOptionLabel={(option) => `${option.gameKorName}(${option.gameName})`}
        renderOption={(props, option) => (
          <Box component="li" {...props} sx={{ fontSize: "0.8rem" }}>
            {option.gameKorName}({option.gameName})
          </Box>
        )}
        renderInput={(params) => (
          <StyledTextField {...params} placeholder={props.placeholder} />
        )}
        onInputChange={(e, value) => callMoveDetail(value)}
      />
    </Search>
  );
}
