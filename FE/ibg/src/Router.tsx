import { Route, Routes } from "react-router-dom";
import Main from "./page/Main/index";
import SignIn from "./page/User/SignIn/index";
import SignUp from "./page/User/SignUp/index";
import Complete from "./page/User/Complete/index";
import BoardGameDetail from "./page/BoardGame/BoardGameDetail";
import BoardGameSearch from "./page/BoardGame/BoardGameSearch/index";
import BoardGameMarket from "./page/BoardGame/BoardGameMarket/index";
import Survey from "./page/User/Survey/index";
import MyGames from "./page/User/Interest/index";
import Map from "./page/Map/index";
import Detail from "./page/BoardGame/BoardGameMarket/DealDetail/index";

function Router() {
  return (
    <Routes>
      <Route path="/" element={<Main />} />
      <Route path="/signin" element={<SignIn />} />
      <Route path="/signup" element={<SignUp />} />
      <Route path="/detail/:no" element={<BoardGameDetail />} />
      <Route path="/search" element={<BoardGameSearch />} />
      <Route path="/complete" element={<Complete />} />
      <Route path="/market" element={<BoardGameMarket />} />
      <Route path="/survey" element={<Survey />} />
      <Route path="/mygames" element={<MyGames />} />
      <Route path="/map" element={<Map />} />
      <Route path="/market/detail/:dealNo" element={<Detail />} />
    </Routes>
  );
}

export default Router;
