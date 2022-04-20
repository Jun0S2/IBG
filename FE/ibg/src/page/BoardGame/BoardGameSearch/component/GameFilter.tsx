import React, { useState } from "react";
import { CategoryData } from "./CategoryData";
import {
  styled,
  alpha,
  AccordionSummary,
  List,
  ListItem,
  ListItemText,
  InputBase,
  Divider,
  Button,
  Chip,
  Box,
  Autocomplete,
  TextField,
} from "@mui/material";
import MuiAccordion, { AccordionProps } from "@mui/material/Accordion";
import MuiAccordionDetails from "@mui/material/AccordionDetails";
import FilterListIcon from "@mui/icons-material/FilterList";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

// 필터 항목
const playerItems = [
  { value: 2, label: "2명 이상" },
  { value: 3, label: "3명 이상" },
  { value: 4, label: "4명 이상" },
  { value: 5, label: "5명 이상" },
];

const timeItems = [
  { value: 20, label: "20분 이상" },
  { value: 30, label: "30분 이상" },
  { value: 60, label: "60분 이상" },
  { value: 120, label: "120분 이상" },
];

const weightItems = [
  { value: 1, label: "매우 쉬움" },
  { value: 2, label: "쉬움" },
  { value: 3, label: "보통" },
  { value: 4, label: "어려움" },
  { value: 5, label: "매우 어려움" },
];

const ageItems = [
  { value: 5, label: "5-" },
  { value: 6, label: "6-" },
  { value: 7, label: "7-" },
  { value: 8, label: "8-" },
  { value: 9, label: "9-" },
  { value: 10, label: "10-" },
  { value: 11, label: "11-" },
  { value: 15, label: "15-" },
  { value: 17, label: "17-" },
];

const scoreItems = [
  { value: 2, label: "2+" },
  { value: 4, label: "4+" },
  { value: 6, label: "6+" },
  { value: 8, label: "8+" },
];

// 아코디언 스타임
const Accordion = styled((props: AccordionProps) => (
  <MuiAccordion disableGutters elevation={0} square {...props} />
))(({ theme }) => ({
  border: `1px solid ${theme.palette.divider}`,
  "&:before": {
    display: "none",
  },
}));

const AccordionDetails = styled(MuiAccordionDetails)(({ theme }) => ({
  padding: theme.spacing(0),
  borderTop: "1px solid rgba(0, 0, 0, .125) ",
  [theme.breakpoints.up("sm")]: {
    padding: theme.spacing(2),
  },
}));

// 필터 테이블 스타일
const FilterItem = styled(ListItem)(({ theme }) => ({
  flexDirection: "column",
  [theme.breakpoints.up("sm")]: {
    flexDirection: "row",
  },
}));

const FilterTitle = styled(ListItemText)(({ theme }) => ({
  width: "100%",
  [theme.breakpoints.up("sm")]: {
    width: "20%",
  },
}));

// 검색창 스타일
const Search = styled("div")(({ theme }) => ({
  position: "relative",
  borderRadius: 18,
  backgroundColor: alpha(theme.palette.primary.main, 0.2),
  "&:hover": {
    backgroundColor: alpha(theme.palette.primary.main, 0.25),
  },
  width: "100%",
}));

const StyledInputBase = styled(InputBase)(({ theme }) => ({
  fontSize: 13.5,
  width: "100%",
  "& .MuiInputBase-input": {
    padding: theme.spacing(1, 1, 1, 0),
    paddingLeft: `calc(1em + ${theme.spacing(1)})`,
    transition: theme.transitions.create("width"),
    width: "100%",
  },
}));

export interface ISearchFilter {
  gameName?: string;
  gameKorName?: string;
  gamePlayer?: number;
  gameTime?: number;
  gameWeight?: number;
  gameAge?: number;
  gameScore?: number;
  gameCategory?: string[];
  userNo?: number;
}

export default function GameFilter(props: { searchCallback: Function }) {
  const [expanded, setExpanded] = useState(false);
  const [name, setName] = useState("");
  const [player, setPlayer] = useState(1);
  const [time, setTime] = useState(0);
  const [weight, setWeight] = useState(0);
  const [age, setAge] = useState(0);
  const [score, setScore] = useState(0);
  const [category, setCategory] = useState<string[]>([]);

  const handleAccordion = () => {
    setExpanded(!expanded);
  };

  // 필터 데이터 변경
  const handleName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
  };

  const handlePlayer = (val: number) => {
    if (val === player) setPlayer(1);
    else setPlayer(val);
  };

  const handleTime = (val: number) => {
    if (val === time) setTime(0);
    else setTime(val);
  };

  const handleWeight = (val: number) => {
    if (val === weight) setWeight(0);
    else setWeight(val);
  };

  const handleAge = (val: number) => {
    if (val === age) setAge(0);
    else setAge(val);
  };

  const handleScore = (val: number) => {
    if (val === score) setScore(0);
    else setScore(val);
  };

  const handleCategory = (e: React.SyntheticEvent, val: string[]) => {
    setCategory(val);
  };

  const resetFilter = () => {
    setName("");
    setPlayer(1);
    setTime(0);
    setWeight(0);
    setAge(0);
    setScore(0);
    setCategory([]);
  };

  const callSearchFilter = () => {
    const checkKor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
    let filter = {} as ISearchFilter;

    if (name) {
      if (!checkKor.test(name)) filter.gameName = name;
      else filter.gameKorName = name;
    }
    if (player) filter.gamePlayer = player;
    if (time) filter.gameTime = time;
    if (weight) filter.gameWeight = weight;
    if (age) filter.gameAge = age;
    if (score) filter.gameScore = score;
    if (category) filter.gameCategory = category;

    props.searchCallback(filter);
  };

  return (
    <Accordion expanded={expanded} onChange={handleAccordion}>
      <AccordionSummary
        expandIcon={<ExpandMoreIcon />}
        sx={{ backgroundColor: "rgba(0, 0, 0, .03)" }}
      >
        <FilterListIcon sx={{ mr: 1, color: "gray" }} />
        상세검색(Filter)
      </AccordionSummary>
      <AccordionDetails>
        <List dense>
          <FilterItem>
            <FilterTitle>보드게임명</FilterTitle>
            <ListItem component="div" sx={{ px: { xs: 0, sm: 2 } }}>
              <Search>
                <StyledInputBase
                  placeholder="보드게임 명"
                  inputProps={{ "aria-label": "search" }}
                  value={name}
                  onChange={handleName}
                  onKeyUp={(e) => {
                    if (e.key === "Enter") {
                      callSearchFilter();
                    }
                  }}
                />
              </Search>
            </ListItem>
          </FilterItem>
          <Divider />
          <FilterItem>
            <FilterTitle>인원 수</FilterTitle>
            <ListItem
              component="div"
              sx={{ flexWrap: "wrap", px: { xs: 0, sm: 2 } }}
            >
              {playerItems.map((i) => (
                <Chip
                  key={i.value}
                  label={i.label}
                  variant={player === i.value ? "filled" : "outlined"}
                  color="primary"
                  clickable
                  sx={{
                    mr: 0.7,
                    mb: { xs: 0.5, sm: 0 },
                    fontSize: { xs: "0.6rem", sm: "0.8rem" },
                  }}
                  onClick={() => handlePlayer(i.value)}
                />
              ))}
            </ListItem>
          </FilterItem>
          <Divider />
          <FilterItem>
            <FilterTitle>플레이 시간</FilterTitle>
            <ListItem
              component="div"
              sx={{ flexWrap: "wrap", px: { xs: 0, sm: 2 } }}
            >
              {timeItems.map((i) => (
                <Chip
                  key={i.value}
                  label={i.label}
                  variant={time === i.value ? "filled" : "outlined"}
                  color="primary"
                  clickable
                  sx={{
                    mr: 0.7,
                    mb: { xs: 0.5, sm: 0 },
                    fontSize: { xs: "0.6rem", sm: "0.8rem" },
                  }}
                  onClick={() => handleTime(i.value)}
                />
              ))}
            </ListItem>
          </FilterItem>
          <Divider />
          <FilterItem>
            <FilterTitle>난이도</FilterTitle>
            <ListItem
              component="div"
              sx={{ flexWrap: "wrap", px: { xs: 0, sm: 2 } }}
            >
              {weightItems.map((i) => (
                <Chip
                  key={i.value}
                  label={i.label}
                  variant={weight === i.value ? "filled" : "outlined"}
                  color="primary"
                  clickable
                  sx={{
                    mr: 0.7,
                    mb: { xs: 0.5, sm: 0 },
                    fontSize: { xs: "0.6rem", sm: "0.8rem" },
                  }}
                  onClick={() => handleWeight(i.value)}
                />
              ))}
            </ListItem>
          </FilterItem>
          <Divider />
          <FilterItem>
            <FilterTitle>나이</FilterTitle>
            <ListItem
              component="div"
              sx={{ flexWrap: "wrap", px: { xs: 0, sm: 2 } }}
            >
              {ageItems.map((i) => (
                <Chip
                  key={i.value}
                  label={i.label}
                  variant={age === i.value ? "filled" : "outlined"}
                  color="primary"
                  clickable
                  sx={{
                    mr: 0.7,
                    mb: { xs: 0.5, sm: 0 },
                    fontSize: { xs: "0.6rem", sm: "0.8rem" },
                  }}
                  onClick={() => handleAge(i.value)}
                />
              ))}
            </ListItem>
          </FilterItem>
          <Divider />
          <FilterItem>
            <FilterTitle>평점</FilterTitle>
            <ListItem
              component="div"
              sx={{ flexWrap: "wrap", px: { xs: 0, sm: 2 } }}
            >
              {scoreItems.map((i) => (
                <Chip
                  key={i.value}
                  label={i.label}
                  variant={score === i.value ? "filled" : "outlined"}
                  color="primary"
                  clickable
                  sx={{
                    mr: 0.7,
                    mb: { xs: 0.5, sm: 0 },
                    fontSize: { xs: "0.6rem", sm: "0.8rem" },
                  }}
                  onClick={() => handleScore(i.value)}
                />
              ))}
            </ListItem>
          </FilterItem>
          <Divider />
          <FilterItem>
            <FilterTitle>카테고리</FilterTitle>
            <ListItem component="div" sx={{ px: { xs: 0, sm: 2 } }}>
              <Autocomplete
                value={category}
                multiple
                filterSelectedOptions
                options={CategoryData}
                renderInput={(params) => (
                  <TextField
                    {...params}
                    variant="standard"
                    sx={{ width: "100%" }}
                  />
                )}
                ChipProps={{ color: "primary" }}
                includeInputInList
                onChange={handleCategory}
                sx={{ width: "100%" }}
              />
            </ListItem>
          </FilterItem>
          <Divider />
        </List>
        <Box textAlign="right" sx={{ padding: { xs: 1, md: 0 } }}>
          <Button
            variant="outlined"
            onClick={resetFilter}
            sx={{
              mr: 1,
              boxShadow: "rgba(0, 0, 0, 0.16) 0px 1px 4px",
            }}
          >
            초기화
          </Button>
          <Button variant="contained" onClick={callSearchFilter}>
            검색
          </Button>
        </Box>
      </AccordionDetails>
    </Accordion>
  );
}
